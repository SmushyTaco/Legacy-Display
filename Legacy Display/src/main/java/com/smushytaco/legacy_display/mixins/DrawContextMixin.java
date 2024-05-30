package com.smushytaco.legacy_display.mixins;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(DrawContext.class)
public class DrawContextMixin {
    @ModifyVariable(method = "drawTexturedQuad(Lnet/minecraft/util/Identifier;IIIIIFFFF)V", at = @At(value = "HEAD"), argsOnly = true)
    private Identifier hookDrawTexture(Identifier texture) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen()) return texture;
        if (texture.equals(Screen.MENU_BACKGROUND_TEXTURE)) return LegacyDisplay.INSTANCE.getMENU_BACKGROUND_TEXTURE();
        if (texture.equals(ScreenAccessor.getINWORLD_MENU_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_BACKGROUND_TEXTURE();
        if (texture.equals(Screen.HEADER_SEPARATOR_TEXTURE)) return LegacyDisplay.INSTANCE.getHEADER_SEPARATOR_TEXTURE();
        if (texture.equals(Screen.FOOTER_SEPARATOR_TEXTURE)) return LegacyDisplay.INSTANCE.getFOOTER_SEPARATOR_TEXTURE();
        if (texture.equals(EntryListWidgetAccessor.getMENU_LIST_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getMENU_LIST_BACKGROUND_TEXTURE();
        if (texture.equals(EntryListWidgetAccessor.getINWORLD_MENU_LIST_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_LIST_BACKGROUND_TEXTURE();
        if (texture.equals(CreateWorldScreen.TAB_HEADER_BACKGROUND_TEXTURE)) return LegacyDisplay.INSTANCE.getTAB_HEADER_BACKGROUND_TEXTURE();
        return texture;
    }
}