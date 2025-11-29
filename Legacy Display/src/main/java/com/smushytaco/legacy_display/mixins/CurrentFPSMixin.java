package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(Minecraft.class)
public interface CurrentFPSMixin {
    @Accessor("fps")
    static int getCurrentFPS() { throw new AssertionError(); }
}