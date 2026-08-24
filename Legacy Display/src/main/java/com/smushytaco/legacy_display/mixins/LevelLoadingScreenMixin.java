package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import com.smushytaco.legacy_display.mixin_logic.LevelLoadingScreenMixinLogic;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.progress.ChunkLoadStatusView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    @Shadow
    private float smoothedProgress;
    protected LevelLoadingScreenMixin(Component title) { super(title); }
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;extractChunksForRendering(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIILnet/minecraft/server/level/progress/ChunkLoadStatusView;)V"))
    private boolean hookRenderDrawChunkMap(GuiGraphicsExtractor graphics, int xCenter, int yCenter, int size, int margin, ChunkLoadStatusView statusView) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapWithCondition(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;drawProgressBar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIIIF)V"))
    private boolean hookDrawLoadingBar(LevelLoadingScreen instance, GuiGraphicsExtractor graphics, int left, int top, int width, int height, float progress) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;centeredText(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    private void hookRender(GuiGraphicsExtractor instance, Font font, Component text, int x, int y, int color, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) {
            LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(instance, smoothedProgress, width, height, this.font);
        } else {
            original.call(instance, font, text, x, y, color);
        }
    }
}