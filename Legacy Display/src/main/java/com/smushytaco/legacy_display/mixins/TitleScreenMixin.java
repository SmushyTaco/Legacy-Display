package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(value = TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component title) { super(title); }
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V"))
    private void renderDefaultBackgroundInstead(TitleScreen instance, GuiGraphics context, float delta, Operation<Void> original) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyTitleScreen()) {
            context.blit(RenderPipelines.GUI_TEXTURED, LegacyDisplay.INSTANCE.getMENU_BACKGROUND_TEXTURE(), 0, 0, 0.0F, 0.0F, width, height, 32, 32);
        } else {
            original.call(instance, context, delta);
        }
    }
}