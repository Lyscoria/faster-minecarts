package com.lyscoria.fasterminecarts.mixin;

import com.lyscoria.fasterminecarts.MaxSpeedAccessor;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperimentalMinecartController.class)
public abstract class ExperimentalMinecartControllerMixin extends MinecartController {

    protected ExperimentalMinecartControllerMixin(AbstractMinecartEntity minecart) {
        super(minecart);
    }

    @Inject(method = "getMaxSpeed", at = @At("HEAD"), cancellable = true)
    public void _getMaxSpeed(ServerWorld serverWorld, CallbackInfoReturnable<Double> cir) {
        if (this.minecart instanceof MaxSpeedAccessor accessor) {
            cir.setReturnValue(accessor.getCurMaxSpeed());
        }
    }
}
