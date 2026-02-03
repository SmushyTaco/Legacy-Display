package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor
    static Identifier getINWORLD_MENU_BACKGROUND() { throw new AssertionError(); }
}