package com.bcsmp.bcsmp_content.mixin.domain_expansion.client;

import com.bcsmp.bcsmp_content.main.domain_expansion.util.DEModUtil;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    public void domainExpansion$stopMoving(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (DEModUtil.shouldLockPlayerMovement(MinecraftClient.getInstance().player) && !MinecraftClient.getInstance().isPaused()) {
            KeyBinding.unpressAll();
            ci.cancel();
        }
    }
}
