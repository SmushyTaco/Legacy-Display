package com.smushytaco.legacy_display
import com.smushytaco.legacy_display.mixin_logic.MixinSyntacticSugar.chunkUpdaters
import com.smushytaco.legacy_display.mixins.CurrentFPSMixin
import io.wispforest.owo.ui.core.OwoUIGraphics
import io.wispforest.owo.ui.core.ParentUIComponent
import io.wispforest.owo.ui.core.Surface
import kotlinx.coroutines.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
import net.minecraft.SharedConstants
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraft.resources.Identifier
import java.util.*
object LegacyDisplay : ClientModInitializer {
    val OWO_LIB_LEGACY_BACKGROUND = Surface { context: OwoUIGraphics, component: ParentUIComponent -> Screen.renderMenuBackgroundTexture(context, Screen.MENU_BACKGROUND, component.x(), component.y(), 0.0F, 0.0F, component.width(), component.height()) }
    private fun startRepeatingJob(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                chunkUpdateCount = Minecraft.getInstance().level?.chunkUpdaters?.size ?: 0
                delay(125L)
            }
        }
    }
    private var chunkUpdateCount = 0
    const val MOD_ID = "legacy_display"
    val MENU_BACKGROUND_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/menu_background.png")
    val INWORLD_MENU_BACKGROUND_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/inworld_menu_background.png")
    val HEADER_SEPARATOR_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/header_separator.png")
    val FOOTER_SEPARATOR_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/footer_separator.png")
    val MENU_LIST_BACKGROUND_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/menu_list_background.png")
    val INWORLD_MENU_LIST_BACKGROUND_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/inworld_menu_list_background.png")
    val TAB_HEADER_BACKGROUND_TEXTURE: Identifier = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/tab_header_background.png")
    val config = ModConfig.createAndLoad()
    private lateinit var coroutine: Job
    private val minecraftVersion = SharedConstants.getCurrentVersion().name()
    const val TEXT_COLOR = -1
    override fun onInitializeClient() {
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "hud")) { context, _ ->
            if (Minecraft.getInstance().debugOverlay.showDebugScreen()) return@addLast
            if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) {
                context.drawString(
                    Minecraft.getInstance().gui.font,
                    "${if (config.enableMinecraftKeywordDisplay) "Minecraft" else ""}${if (config.enableVersionDisplay && config.enableMinecraftKeywordDisplay) " " else ""}${if (config.enableVersionDisplay) minecraftVersion else ""}",
                    2, 2, TEXT_COLOR)
            }
            if (config.enableFPSDisplay || config.enableChunkUpdateDisplay) {
                if (config.enableChunkUpdateDisplay) {
                    if (LegacyDisplay::coroutine.isInitialized) {
                        if (!coroutine.isActive) coroutine = startRepeatingJob()
                    } else {
                        coroutine = startRepeatingJob()
                    }
                } else {
                    if (LegacyDisplay::coroutine.isInitialized && coroutine.isActive) coroutine.cancel()
                }
                context.drawString(
                    Minecraft.getInstance().gui.font,
                    "${if (config.enableFPSDisplay) "${CurrentFPSMixin.getCurrentFPS()} fps" else ""}${if (config.enableFPSDisplay && config.enableChunkUpdateDisplay) ", " else ""}${if (config.enableChunkUpdateDisplay) "$chunkUpdateCount chunk update${if (chunkUpdateCount != 1) "s" else ""}" else ""}",
                    2, if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) 14 else 2, TEXT_COLOR
                )
            } else {
                if (LegacyDisplay::coroutine.isInitialized && coroutine.isActive) coroutine.cancel()
            }
            if (config.enableCoordinateDisplay) {
                val unformattedCoordinates = String.format(Locale.ROOT, "%.3f %.5f %.3f", Minecraft.getInstance().cameraEntity?.x, Minecraft.getInstance().cameraEntity?.y, Minecraft.getInstance().cameraEntity?.z)
                val coordinateList = unformattedCoordinates.split(".", " ")
                val formattedCoordinates = StringBuilder()
                for (i in coordinateList.indices) {
                    if (i % 2 == 0) {
                        formattedCoordinates.append(coordinateList[i])
                        if (i != coordinateList.indices.last - 1) formattedCoordinates.append(", ")
                    }
                }
                context.drawString(
                    Minecraft.getInstance().gui.font, "${if (config.enablePositionKeywordInCoordinateDisplay) "Position: " else ""}$formattedCoordinates",
                    2, if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) && (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 26 else if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) xor (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 14 else 2, TEXT_COLOR)
            }
        }
    }
}