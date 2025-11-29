package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(AbstractSelectionList.class)
public interface EntryListWidgetAccessor {
    @Accessor
    static ResourceLocation getMENU_LIST_BACKGROUND() { throw new AssertionError(); }
    @Accessor
    static ResourceLocation getINWORLD_MENU_LIST_BACKGROUND() { throw new AssertionError(); }
}