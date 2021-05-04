package com.smushytaco.legacy_display.configuration_support
import com.smushytaco.legacy_display.LegacyDisplay
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
@Config(name = LegacyDisplay.MOD_ID)
class ModConfiguration: ConfigData {
    @Comment("Default value is yes. If set to yes the keyword \"Minecraft\" will be displayed in the top left corner. If set to no it won't.")
    val enableMinecraftKeywordDisplay = true
    @Comment("Default value is yes. If set to yes the version number will be displayed in the top left corner. If set to no it won't.")
    val enableVersionDisplay = true
    @Comment("Default value is yes. If set to yes an FPS counter will be displayed in the top left corner. If set to no it won't.")
    val enableFPSDisplay = true
    @Comment("Default value is yes. If set to yes a chunk update counter will be displayed in the top left corner. If set to no it won't.")
    val enableChunkUpdateDisplay = true
    @Comment("Default value is yes. If set to yes your coordinates will be displayed in the top left corner. If set to no it won't.")
    val enableCoordinateDisplay = true
    @Comment("Default value is yes. If set to yes the keyword \"Position\" will be displayed next to your coordinates in the top left corner. If set to no it won't.")
    val enablePositionKeywordInCoordinateDisplay = true
}