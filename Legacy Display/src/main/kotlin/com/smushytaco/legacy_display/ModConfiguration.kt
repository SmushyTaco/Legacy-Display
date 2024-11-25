package com.smushytaco.legacy_display
import io.wispforest.owo.config.annotation.*
@Modmenu(modId = LegacyDisplay.MOD_ID)
@Config(name = LegacyDisplay.MOD_ID, wrapperName = "ModConfig")
@Suppress("UNUSED")
class ModConfiguration {
    @JvmField
    var enableLegacyTitleScreen = true
    @JvmField
    var enableLegacyDirtScreen = true
    @JvmField
    var enableLegacyLoadingScreen = true
    @JvmField
    var enableMinecraftKeywordDisplay = true
    @JvmField
    var enableVersionDisplay = true
    @JvmField
    var enableFPSDisplay = true
    @JvmField
    var enableChunkUpdateDisplay = true
    @JvmField
    var enableCoordinateDisplay = true
    @JvmField
    var enablePositionKeywordInCoordinateDisplay = true
}