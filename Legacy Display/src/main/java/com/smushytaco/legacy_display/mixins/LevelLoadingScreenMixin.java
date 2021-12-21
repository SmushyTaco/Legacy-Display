package com.smushytaco.legacy_display.mixins;
import com.smushytaco.legacy_display.LegacyDisplay;
import com.smushytaco.legacy_display.mixin_logic.LevelLoadingScreenMixinLogic;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Text title) {
        super(title);
    }
    @Final
    @Shadow
    private WorldGenerationProgressTracker progressProvider;
    @Shadow
    private long lastNarrationTime;
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void hookRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyLoadingScreen()) return;
        renderBackground(matrices);
        long l = Util.getMeasuringTimeMs();
        if (l - lastNarrationTime > 2000L) {
            this.lastNarrationTime = l;
            narrateScreenIfNarrationEnabled(true);
        }
        LevelLoadingScreenMixinLogic.INSTANCE.hookRenderLogic(matrices, progressProvider, width, height, textRenderer);
        ci.cancel();
    }
}