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
import net.minecraft.text.Text;
import net.minecraft.world.chunk.ChunkLoadMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    @Shadow
    private float loadProgress;
    protected LevelLoadingScreenMixin(Text title) { super(title); }
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;IIIILnet/minecraft/world/chunk/ChunkLoadMap;)V"))
    private boolean hookRenderDrawChunkMap(DrawContext context, int centerX, int centerY, int chunkLength, int chunkGap, ChunkLoadMap map) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/LevelLoadingScreen;drawLoadingBar(Lnet/minecraft/client/gui/DrawContext;IIIIF)V"))
    private boolean hookDrawLoadingBar(LevelLoadingScreen instance, DrawContext context, int x1, int y1, int width, int height, float delta) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"))
    private void hookRender(DrawContext instance, TextRenderer textRenderer, Text text, int centerX, int y, int color, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) {
            LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(instance, loadProgress, width, height, this.textRenderer);
        } else {
            original.call(instance, textRenderer, text, centerX, y, color);
        }
    }
}