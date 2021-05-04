package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import java.util.Set;
@Mixin(WorldRenderer.class)
public interface ChunksToRebuildMixin {
    @Accessor
    Set<ChunkBuilder.BuiltChunk> getChunksToRebuild();
}