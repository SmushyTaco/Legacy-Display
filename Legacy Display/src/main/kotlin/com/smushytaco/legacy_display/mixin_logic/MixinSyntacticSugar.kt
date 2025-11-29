package com.smushytaco.legacy_display.mixin_logic
import com.smushytaco.legacy_display.mixins.ChunkUpdatersMixin
import net.minecraft.client.multiplayer.ClientLevel
import java.util.*
object MixinSyntacticSugar {
    val ClientLevel.chunkUpdaters: Deque<Runnable>
        get() = (this as ChunkUpdatersMixin).lightUpdateQueue
}