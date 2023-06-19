package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import com.smushytaco.legacy_display.mixin_logic.LevelLoadingScreenMixinLogic;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
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
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/WorldGenerationProgressTracker;IIII)V"))
    @SuppressWarnings("unused")
    private boolean hookRenderDrawChunkMap(DrawContext instance, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    @SuppressWarnings("unused")
    private void hookRender(DrawContext instance, TextRenderer textRenderer, String text, int centerX, int y, int color, Operation<Void> original, DrawContext context, int mouseX, int mouseY, float delta) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) {
            LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(context, progressProvider, width, height, this.textRenderer);
        } else {
            original.call(instance, textRenderer, text, centerX, y, color);
        }
    }
}