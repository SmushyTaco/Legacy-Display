package com.smushytaco.legacy_display.mixins;
import com.smushytaco.legacy_display.LegacyDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
@Mixin(GuiGraphics.class)
public class DrawContextMixin {
    @ModifyVariable(method = "innerBlit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIIIFFFFI)V", at = @At(value = "HEAD"), argsOnly = true)
    private ResourceLocation hookDrawTexturedQuad(ResourceLocation sprite) {
        if (!LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen()) return sprite;
        if (sprite.equals(Screen.MENU_BACKGROUND)) return LegacyDisplay.INSTANCE.getMENU_BACKGROUND_TEXTURE();
        if (sprite.equals(ScreenAccessor.getINWORLD_MENU_BACKGROUND())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_BACKGROUND_TEXTURE();
        if (sprite.equals(Screen.HEADER_SEPARATOR)) return LegacyDisplay.INSTANCE.getHEADER_SEPARATOR_TEXTURE();
        if (sprite.equals(Screen.FOOTER_SEPARATOR)) return LegacyDisplay.INSTANCE.getFOOTER_SEPARATOR_TEXTURE();
        if (sprite.equals(EntryListWidgetAccessor.getMENU_LIST_BACKGROUND())) return LegacyDisplay.INSTANCE.getMENU_LIST_BACKGROUND_TEXTURE();
        if (sprite.equals(EntryListWidgetAccessor.getINWORLD_MENU_LIST_BACKGROUND())) return LegacyDisplay.INSTANCE.getINWORLD_MENU_LIST_BACKGROUND_TEXTURE();
        if (sprite.equals(CreateWorldScreen.TAB_HEADER_BACKGROUND)) return LegacyDisplay.INSTANCE.getTAB_HEADER_BACKGROUND_TEXTURE();
        return sprite;
    }
}