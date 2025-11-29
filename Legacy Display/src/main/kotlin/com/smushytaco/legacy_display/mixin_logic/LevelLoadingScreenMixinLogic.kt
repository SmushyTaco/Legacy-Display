package com.smushytaco.legacy_display.mixin_logic
import com.smushytaco.legacy_display.LegacyDisplay
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
object LevelLoadingScreenMixinLogic {
    fun hookRenderLogic(context: GuiGraphics, loadProgress: Float, width: Int, height: Int, textRenderer: Font) {
        val i = width / 2
        val j = height / 2 - 9 / 2 - 30
        context.drawCenteredString(textRenderer, "Generating level", i, j + 15, LegacyDisplay.TEXT_COLOR)
        context.drawCenteredString(textRenderer, "Building terrain", i, j + 39, LegacyDisplay.TEXT_COLOR)
        context.fill(i - 50, j + 52, i + 50, j + 54, -0x7f7f80)
        context.fill(i - 50, j + 52, i - 50 + (loadProgress * 100.0).toInt(), j + 54, -0x7f0080)
    }
}