package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @WrapOperation(method = "renderDarkening(Lnet/minecraft/client/gui/DrawContext;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderBackgroundTexture(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;IIFFII)V"))
    private void hookRenderDarkening(DrawContext context, Identifier texture, int x, int y, float u, float v, int width, int height, Operation<Void> original) {
        Screen screen = (Screen) (Object) this;
        original.call(context, LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen() && !(screen instanceof GameMenuScreen || screen instanceof OptionsScreen) ? Screen.MENU_BACKGROUND_TEXTURE : texture, x, y, u, v, width, height);
    }
}