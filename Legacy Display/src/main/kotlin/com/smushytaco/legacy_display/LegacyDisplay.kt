package com.smushytaco.legacy_display
import com.smushytaco.legacy_display.DoubleFloorExtensionFunction.floor
import kotlinx.coroutines.*
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer
import com.smushytaco.legacy_display.mixin_logic.MixinSyntacticSugar.chunksToRebuild
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.SharedConstants
import net.minecraft.client.MinecraftClient
import com.smushytaco.legacy_display.configuration_support.ModConfiguration
import com.smushytaco.legacy_display.mixins.CurrentFPSMixin
object LegacyDisplay : ModInitializer {
    private fun startRepeatingJob(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                chunkUpdateCount = MinecraftClient.getInstance().worldRenderer?.chunksToRebuild?.size ?: 0
                delay(125L)
            }
        }
    }
    private var chunkUpdateCount = 0
    const val MOD_ID = "legacy_display"
    private lateinit var config: ModConfiguration
    private lateinit var coroutine: Job
    private val minecraftVersion = SharedConstants.getGameVersion().name
    private const val color = 16777215
    override fun onInitialize() {
        AutoConfig.register(ModConfiguration::class.java) { definition: Config, configClass: Class<ModConfiguration> ->
            GsonConfigSerializer(definition, configClass)
        }
        config = AutoConfig.getConfigHolder(ModConfiguration::class.java).config
        HudRenderCallback.EVENT.register(HudRenderCallback { matrixStack, _ ->
            if (MinecraftClient.getInstance().options.debugEnabled) return@HudRenderCallback
            if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) {
                MinecraftClient.getInstance().inGameHud.fontRenderer.drawWithShadow(matrixStack,
                    "${if (config.enableMinecraftKeywordDisplay) "Minecraft" else ""}${if (config.enableVersionDisplay && config.enableMinecraftKeywordDisplay) " " else ""}${if (config.enableVersionDisplay) minecraftVersion else ""}",
                    2.0F, 2.0F, color)
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
                MinecraftClient.getInstance().inGameHud.fontRenderer.drawWithShadow(matrixStack,
                    "${if (config.enableFPSDisplay) "${CurrentFPSMixin.getCurrentFPS()} fps" else ""}${if (config.enableFPSDisplay && config.enableChunkUpdateDisplay) ", " else ""}${if (config.enableChunkUpdateDisplay) "$chunkUpdateCount chunk update${if (chunkUpdateCount != 1) "s" else ""}" else ""}",
                    2.0F, if (config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) 14.0F else 2.0F, color
                )
            } else {
                if (LegacyDisplay::coroutine.isInitialized && coroutine.isActive) coroutine.cancel()
            }
            if (config.enableCoordinateDisplay) {
                MinecraftClient.getInstance().inGameHud.fontRenderer.drawWithShadow(matrixStack, "${if (config.enablePositionKeywordInCoordinateDisplay) "Position: " else ""}${MinecraftClient.getInstance().player?.x?.floor?.toInt()}, ${MinecraftClient.getInstance().player?.y?.floor?.toInt()}, ${MinecraftClient.getInstance().player?.z?.floor?.toInt()}",
                    2.0F, if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) && (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 26.0F else if ((config.enableMinecraftKeywordDisplay || config.enableVersionDisplay) xor (config.enableFPSDisplay || config.enableChunkUpdateDisplay)) 14.0F else 2.0F, color)
            }
        })
    }
}