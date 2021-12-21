package com.smushytaco.legacy_display
import com.smushytaco.legacy_display.configuration_support.ModConfiguration
import com.smushytaco.legacy_display.mixin_logic.MixinSyntacticSugar.chunkUpdaters
import com.smushytaco.legacy_display.mixins.CurrentFPSMixin
import kotlinx.coroutines.*
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import java.util.*
object LegacyDisplay : ClientModInitializer {
    private fun startRepeatingJob(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                chunkUpdateCount = MinecraftClient.getInstance().world?.chunkUpdaters?.size ?: 0
                delay(125L)
            }
        }
    }
    private var chunkUpdateCount = 0
    const val MOD_ID = "legacy_display"
    lateinit var config: ModConfiguration
        private set
    private lateinit var coroutine: Job
    private val minecraftVersion = SharedConstants.getGameVersion().name
    const val TEXT_COLOR = 16777215
    override fun onInitializeClient() {
        AutoConfig.register(ModConfiguration::class.java) { definition: Config, configClass: Class<ModConfiguration> ->
            GsonConfigSerializer(definition, configClass)
        }
        config = AutoConfig.getConfigHolder(ModConfiguration::class.java).config
        HudRenderCallback.EVENT.register(HudRenderCallback { matrixStack, _ ->
            if (MinecraftClient.getInstance().options.debugEnabled) return@HudRenderCallback
            if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) {
                MinecraftClient.getInstance().inGameHud.textRenderer.drawWithShadow(matrixStack,
                    "${if (config.enableMinecraftKeywordDisplay) "Minecraft" else ""}${if (config.enableVersionDisplay && config.enableMinecraftKeywordDisplay) " " else ""}${if (config.enableVersionDisplay) minecraftVersion else ""}",
                    2.0F, 2.0F, TEXT_COLOR)
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
                MinecraftClient.getInstance().inGameHud.textRenderer.drawWithShadow(matrixStack,
                    "${if (config.enableFPSDisplay) "${CurrentFPSMixin.getCurrentFPS()} fps" else ""}${if (config.enableFPSDisplay && config.enableChunkUpdateDisplay) ", " else ""}${if (config.enableChunkUpdateDisplay) "$chunkUpdateCount chunk update${if (chunkUpdateCount != 1) "s" else ""}" else ""}",
                    2.0F, if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) 14.0F else 2.0F, TEXT_COLOR
                )
            } else {
                if (LegacyDisplay::coroutine.isInitialized && coroutine.isActive) coroutine.cancel()
            }
            if (config.enableCoordinateDisplay) {
                val unformattedCoordinates = String.format(Locale.ROOT, "%.3f %.5f %.3f", MinecraftClient.getInstance().cameraEntity?.x, MinecraftClient.getInstance().cameraEntity?.y, MinecraftClient.getInstance().cameraEntity?.z)
                val coordinateList = unformattedCoordinates.split(".", " ")
                val formattedCoordinates = StringBuilder()
                for (i in coordinateList.indices) {
                    if (i % 2 == 0) {
                        formattedCoordinates.append(coordinateList[i])
                        if (i != coordinateList.indices.last - 1) formattedCoordinates.append(", ")
                    }
                }
                MinecraftClient.getInstance().inGameHud.textRenderer.drawWithShadow(matrixStack, "${if (config.enablePositionKeywordInCoordinateDisplay) "Position: " else ""}$formattedCoordinates",
                    2.0F, if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) && (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 26.0F else if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) xor (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 14.0F else 2.0F, TEXT_COLOR)
            }
        })
    }
}