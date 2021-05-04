package com.smushytaco.legacy_display.mixin_logic
import net.minecraft.client.render.WorldRenderer
import net.minecraft.client.render.chunk.ChunkBuilder
import com.smushytaco.legacy_display.mixins.ChunksToRebuildMixin
object MixinSyntacticSugar {
    val WorldRenderer.chunksToRebuild: Set<ChunkBuilder.BuiltChunk>
        get() = (this as ChunksToRebuildMixin).chunksToRebuild
}