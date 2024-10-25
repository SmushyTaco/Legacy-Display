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
    @ModifyVariable(method = "drawTexturedQuad", at = @At(value = "HEAD"), argsOnly = true)
    private Identifier hookDrawTexturedQuad(Identifier sprite) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen()) return sprite;
        if (sprite.equals(Screen.MENU_BACKGROUND_TEXTURE)) return LegacyDisplay.INSTANCE.getMENU_BACKGROUND_TEXTURE();
        if (sprite.equals(ScreenAccessor.getINWORLD_MENU_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_BACKGROUND_TEXTURE();
        if (sprite.equals(Screen.HEADER_SEPARATOR_TEXTURE)) return LegacyDisplay.INSTANCE.getHEADER_SEPARATOR_TEXTURE();
        if (sprite.equals(Screen.FOOTER_SEPARATOR_TEXTURE)) return LegacyDisplay.INSTANCE.getFOOTER_SEPARATOR_TEXTURE();
        if (sprite.equals(EntryListWidgetAccessor.getMENU_LIST_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getMENU_LIST_BACKGROUND_TEXTURE();
        if (sprite.equals(EntryListWidgetAccessor.getINWORLD_MENU_LIST_BACKGROUND_TEXTURE())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_LIST_BACKGROUND_TEXTURE();
        if (sprite.equals(CreateWorldScreen.TAB_HEADER_BACKGROUND_TEXTURE)) return LegacyDisplay.INSTANCE.getTAB_HEADER_BACKGROUND_TEXTURE();
        return sprite;
    }
}