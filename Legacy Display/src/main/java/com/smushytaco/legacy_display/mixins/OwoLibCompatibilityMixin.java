package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import io.wispforest.owo.config.ui.ConfigScreen;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.ui.core.Surface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(value = ConfigScreen.class, remap = false)
public abstract class OwoLibCompatibilityMixin {
    @WrapOperation(method = "build(Lio/wispforest/owo/ui/container/FlowLayout;)V", at = @At(value = "INVOKE", target = "Lio/wispforest/owo/ui/container/FlowLayout;surface(Lio/wispforest/owo/ui/core/Surface;)Lio/wispforest/owo/ui/core/ParentComponent;"))
    private ParentComponent hookBuild(FlowLayout instance, Surface surface, Operation<ParentComponent> original) {
        return original.call(instance, LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen() ? LegacyDisplay.INSTANCE.getOWO_LIB_LEGACY_BACKGROUND() : surface);
    }
}