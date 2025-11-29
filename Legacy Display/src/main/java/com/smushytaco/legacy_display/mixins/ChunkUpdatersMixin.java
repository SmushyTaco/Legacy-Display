package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Deque;
@Mixin(ClientLevel.class)
public interface ChunkUpdatersMixin {
    @Accessor
    Deque<Runnable> getLightUpdateQueue();
}