package com.smushytaco.legacy_display.mixin_logic
import com.smushytaco.legacy_display.mixins.ChunkUpdatersMixin
import net.minecraft.client.world.ClientWorld
import java.util.*
object MixinSyntacticSugar {
    val ClientWorld.chunkUpdaters: Deque<Runnable>
        get() = (this as ChunkUpdatersMixin).chunkUpdaters
}