package com.smushytaco.legacy_display
import com.llamalad7.mixinextras.MixinExtrasBootstrap
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint
@Suppress("UNUSED")
object LegacyDisplayPreLaunch: PreLaunchEntrypoint { override fun onPreLaunch() { MixinExtrasBootstrap.init() } }