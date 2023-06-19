package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(value = TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) { super(title); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V"))
    @SuppressWarnings("unused")
    private void renderDefaultBackgroundInstead(RotatingCubeMapRenderer instance, float delta, float alpha, Operation<Void> original, DrawContext context, int mouseX, int mouseY, float deltaTwo) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyTitleScreen()) {
            renderBackground(context);
        } else {
            original.call(instance, delta, alpha);
        }
    }
}