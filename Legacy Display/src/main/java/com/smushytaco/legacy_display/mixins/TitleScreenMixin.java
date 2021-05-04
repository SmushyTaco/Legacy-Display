package com.smushytaco.legacy_display.mixins;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V"))
    private void renderDefaultBackgroundInstead(RotatingCubeMapRenderer rotatingCubeMapRenderer, float delta, float alpha, MatrixStack matrices, int mouseX, int mouseY, float d) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyTitleScreen()) {
            TitleScreen titleScreen = (TitleScreen) (Object) this;
            float f = ((TitleScreenAccessors) titleScreen).getDoBackgroundFade() ? (float)(Util.getMeasuringTimeMs() - ((TitleScreenAccessors) titleScreen).getBackgroundFadeStart()) / 1000.0F : 1.0F;
            ((TitleScreenAccessors) titleScreen).getBackgroundRenderer().render(delta, MathHelper.clamp(f, 0.0F, 1.0F));
            return;
        }
        this.renderBackground(matrices);
    }
}