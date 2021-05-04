package com.smushytaco.legacy_display.mixins;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(TitleScreen.class)
public interface TitleScreenAccessors {
    @Accessor
    RotatingCubeMapRenderer getBackgroundRenderer();
    @Accessor
    boolean getDoBackgroundFade();
    @Accessor
    long getBackgroundFadeStart();
}