package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(MinecraftClient.class)
public interface CurrentFPSMixin {
    @Accessor("currentFps")
    static int getCurrentFPS() { throw new AssertionError(); }
}