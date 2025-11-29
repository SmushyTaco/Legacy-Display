package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import com.smushytaco.legacy_display.mixin_logic.LevelLoadingScreenMixinLogic;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
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
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;renderChunks(Lnet/minecraft/client/gui/GuiGraphics;IIIILnet/minecraft/server/level/progress/ChunkLoadStatusView;)V"))
    private boolean hookRenderDrawChunkMap(GuiGraphics context, int centerX, int centerY, int chunkLength, int chunkGap, ChunkLoadStatusView map) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;drawProgressBar(Lnet/minecraft/client/gui/GuiGraphics;IIIIF)V"))
    private boolean hookDrawLoadingBar(LevelLoadingScreen instance, GuiGraphics context, int x1, int y1, int width, int height, float delta) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen(); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    private void hookRender(GuiGraphics instance, Font textRenderer, Component text, int centerX, int y, int color, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) {
            LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(instance, smoothedProgress, width, height, this.font);
        } else {
            original.call(instance, textRenderer, text, centerX, y, color);
        }
    }
}