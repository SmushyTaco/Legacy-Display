package com.smushytaco.legacy_display.mixins;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.smushytaco.legacy_display.LegacyDisplay;
import io.wispforest.owo.config.ui.ConfigScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.Surface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
@Mixin(value = ConfigScreen.class, remap = false)
public abstract class OwoLibCompatibilityMixin {
    @WrapWithCondition(method = "build(Lio/wispforest/owo/ui/container/FlowLayout;)V", at = @At(value = "INVOKE", target = "Lio/wispforest/owo/ui/container/FlowLayout;surface(Lio/wispforest/owo/ui/core/Surface;)Lio/wispforest/owo/ui/core/ParentComponent;"))
    private boolean hookBuildOne(FlowLayout instance, Surface surface) { return !LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen(); }
    @WrapOperation(method = "build(Lio/wispforest/owo/ui/container/FlowLayout;)V", at = @At(value = "INVOKE", target = "Lio/wispforest/owo/ui/container/FlowLayout;childById(Ljava/lang/Class;Ljava/lang/String;)Lio/wispforest/owo/ui/core/Component;", ordinal = 1))
    private Component hookBuildTwo(FlowLayout instance, Class<ButtonComponent> aClass, String s, Operation<Component> original, FlowLayout rootComponent) {
        if (LegacyDisplay.INSTANCE.getConfig().getEnableLegacyDirtScreen()) rootComponent.surface(LegacyDisplay.INSTANCE.getOWO_LIB_LEGACY_BACKGROUND());
        return original.call(instance, aClass, s);
    }
}