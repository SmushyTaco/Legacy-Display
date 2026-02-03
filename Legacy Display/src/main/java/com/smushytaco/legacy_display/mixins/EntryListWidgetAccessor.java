package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(AbstractSelectionList.class)
public interface EntryListWidgetAccessor {
    @Accessor
    static Identifier getMENU_LIST_BACKGROUND() { throw new AssertionError(); }
    @Accessor
    static Identifier getINWORLD_MENU_LIST_BACKGROUND() { throw new AssertionError(); }
}