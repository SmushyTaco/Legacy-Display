package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @WrapOperation(method = "renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackgroundTexture(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;IIFFII)V"))
    private void hookRenderDarkening(GuiGraphics context, ResourceLocation texture, int x, int y, float u, float v, int width, int height, Operation<Void> original) {
        Screen screen = (Screen) (Object) this;
        original.call(context, LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen() && !(screen instanceof PauseScreen || screen instanceof OptionsScreen) ? Screen.MENU_BACKGROUND : texture, x, y, u, v, width, height);
    }
}