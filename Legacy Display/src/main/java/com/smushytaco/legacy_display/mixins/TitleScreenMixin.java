package com.smushytaco.legacy_display.mixins;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
@Mixin(value = TitleScreen.class, priority = 1001)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }
    @Final
    @Shadow
    private RotatingCubeMapRenderer backgroundRenderer;
    @Final
    @Shadow
    private boolean doBackgroundFade;
    @Shadow
    private long backgroundFadeStart;
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V"))
    private void renderDefaultBackgroundInstead(RotatingCubeMapRenderer rotatingCubeMapRenderer, float delta, float alpha, MatrixStack matrices, int mouseX, int mouseY, float d) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyTitleScreen()) {
            float f = doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - backgroundFadeStart) / 1000.0F : 1.0F;
            backgroundRenderer.render(delta, MathHelper.clamp(f, 0.0F, 1.0F));
            return;
        }
        this.renderBackground(matrices);
    }
}