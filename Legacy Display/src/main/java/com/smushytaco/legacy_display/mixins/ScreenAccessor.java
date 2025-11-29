package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor
    static ResourceLocation getINWORLD_MENU_BACKGROUND() { throw new AssertionError(); }
}