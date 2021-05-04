package com.smushytaco.legacy_display
import kotlin.math.floor
object DoubleFloorExtensionFunction {
    val Double.floor: Double
        get() = floor(this)
}