package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(EntryListWidget.class)
public interface EntryListWidgetAccessor {
    @Accessor
    static Identifier getMENU_LIST_BACKGROUND_TEXTURE() { throw new AssertionError(); }
    @Accessor
    static Identifier getINWORLD_MENU_LIST_BACKGROUND_TEXTURE() { throw new AssertionError(); }
}