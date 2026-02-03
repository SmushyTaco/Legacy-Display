package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(Screen.class)
public abstract class ScreenMixin {
    @WrapOperation(method = "renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackgroundTexture(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/Identifier;IIFFII)V"))
    private void hookRenderDarkening(GuiGraphics context, Identifier texture, int x, int y, float u, float v, int width, int height, Operation<Void> original) {
        Screen screen = (Screen) (Object) this;
        original.call(context, LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen() && !(screen instanceof PauseScreen || screen instanceof OptionsScreen || screen instanceof ShareToLanScreen || screen instanceof AdvancementsScreen || screen instanceof ConfirmLinkScreen || screen instanceof SkinCustomizationScreen || screen instanceof SoundOptionsScreen) ? Screen.MENU_BACKGROUND : texture, x, y, u, v, width, height);
    }
}