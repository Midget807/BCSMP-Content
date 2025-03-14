package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    public void domainExpansion$stopMoving(long window, int button, int action, int mods, CallbackInfo ci) {
        if (DEModUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    public void domainExpansion$stopMoving(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (DEModUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
}
