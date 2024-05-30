package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(value = TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) { super(title); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V"))
    private void renderDefaultBackgroundInstead(TitleScreen instance, DrawContext context, float delta, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyTitleScreen()) {
            context.drawTexture(LegacyDisplay.INSTANCE.getMENU_BACKGROUND_TEXTURE(), 0, 0, 0, 0.0F, 0.0F, width, height, 32, 32);
        } else {
            original.call(instance, context, delta);
        }
    }
}