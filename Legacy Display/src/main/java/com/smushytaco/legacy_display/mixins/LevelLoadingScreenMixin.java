package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import com.smushytaco.legacy_display.mixin_logic.LevelLoadingScreenMixinLogic;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
import net.minecraft.server.WorldGenerationProgressTracker;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Text title) { super(title); }
    @Final
    @Shadow
    private WorldGenerationProgressTracker progressProvider;
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/server/WorldGenerationProgressTracker;IIII)V"))
    private boolean hookRenderDrawChunkMap(DrawContext context, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"))
    private void hookRender(DrawContext instance, TextRenderer textRenderer, Text text, int centerX, int y, int color, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) {
            LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(instance, progressProvider, width, height, this.textRenderer);
        } else {
            original.call(instance, textRenderer, text, centerX, y, color);
        }
    }
}