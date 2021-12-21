package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Deque;
@Mixin(ClientWorld.class)
public interface ChunkUpdatersMixin {
    @Accessor
    Deque<Runnable> getChunkUpdaters();
}