package com.smushytaco.legacy_display.mixin_logic
import com.smushytaco.legacy_display.LegacyDisplay
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.WorldGenerationProgressTracker
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
object LevelLoadingScreenMixinLogic {
    fun hookRenderLogic(matrices: MatrixStack, progressProvider: WorldGenerationProgressTracker, width: Int, height: Int, textRenderer: TextRenderer) {
        val i = width / 2
        val j = height / 2 - 9 / 2 - 30
        DrawableHelper.drawCenteredString(matrices, textRenderer, "Generating level", i, j + 15, LegacyDisplay.TEXT_COLOR)
        DrawableHelper.drawCenteredString(matrices, textRenderer, "Building terrain", i, j + 39, LegacyDisplay.TEXT_COLOR)
        val progress = MathHelper.clamp(MathHelper.clamp(progressProvider.progressPercentage.toFloat(), 0.0f, 100.0f) / 100.0f * (i + 50.0f), (i - 50).toFloat(), (i + 50).toFloat()).toInt()
        DrawableHelper.fill(matrices, i - 50, j + 52, i + 50, j + 54, -0x7f7f80)
        DrawableHelper.fill(matrices, i - 50, j + 52, progress, j + 54, -0x7f0080)
    }
}