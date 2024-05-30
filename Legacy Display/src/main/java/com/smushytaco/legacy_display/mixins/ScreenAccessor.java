package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor
    static Identifier getINWORLD_MENU_BACKGROUND_TEXTURE() { throw new AssertionError(); }
}