package com.lyscoria.fasterminecarts.mixin;

import com.lyscoria.fasterminecarts.MaxSpeedAccessor;
import com.lyscoria.fasterminecarts.network.MinecartControllerUpdatePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HangingSignBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class AbstractMinecartEntityMixin extends Entity implements MaxSpeedAccessor {

    public AbstractMinecartEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private double maxSpeed = 0.4;

    @Override
    public double getCurMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void setCurMaxSpeed(double speed) {
        maxSpeed = speed;
    }

    @Override
    public void setController(MinecartController new_controller) {
        if (controller instanceof ExperimentalMinecartController && new_controller instanceof DefaultMinecartController
            || controller instanceof DefaultMinecartController && new_controller instanceof ExperimentalMinecartController)
            controller = new_controller;
    }

    @Shadow
    @Mutable
    @Final
    private MinecartController controller;

    @Shadow
    public abstract BlockPos getRailOrMinecartPos();

    @Shadow
    public abstract boolean isOnRail();

    @Inject(method = "tick", at = @At("HEAD"))
    void _tick(CallbackInfo ci) {
        World world = this.getEntityWorld();
        if (world.isClient()) return;
        if (this.hasPassengers() && this.isOnRail()) {
            BlockPos minecartPos = this.getRailOrMinecartPos();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -2; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        BlockPos pos = minecartPos.add(dx, dy, dz);
                        BlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (!(block instanceof SignBlock || block instanceof WallSignBlock
                                || block instanceof HangingSignBlock || block instanceof WallHangingSignBlock))
                            continue;
                        checkSign(pos);
                    }
                }
            }
        }
        if (controller instanceof ExperimentalMinecartController) {
            sendControllerUpdateToClients(1, maxSpeed);
        } else if (controller instanceof DefaultMinecartController) {
            sendControllerUpdateToClients(0, maxSpeed);
        }
    }

    @Unique
    private void checkSign(BlockPos pos) {
        AbstractMinecartEntity self = (AbstractMinecartEntity) (Object) this;
        BlockEntity blockEntity = this.getEntityWorld().getBlockEntity(pos);
        Text[] texts;
        if (blockEntity instanceof SignBlockEntity signBlockEntity) {
            texts = signBlockEntity.getText(true).getMessages(false);
        } else if (blockEntity instanceof HangingSignBlockEntity hangingSignBlockEntity) {
            texts = hangingSignBlockEntity.getText(true).getMessages(false);
        } else {
            return;
        }
        if (texts.length > 0) {
            String line1 = texts[0].getString();
            if (line1.equals("*Original*")) {
                maxSpeed = 0.4;
                if (controller instanceof ExperimentalMinecartController) {
                    controller = new DefaultMinecartController(self);
                }
            }
            if (line1.equals("*NewSpeed*")) {
                String line2 = texts[1].getString();
                if (line2.isEmpty()) return;
                try {
                    maxSpeed = Double.parseDouble(line2) / 20.0;
                    if (controller instanceof DefaultMinecartController) {
                        controller = new ExperimentalMinecartController(self);
                    }
                } catch (NumberFormatException e) {
                    return;
                }
            }
        }
    }

    @Unique
    private void sendControllerUpdateToClients(int controllerType, double maxSpeed) {
        if (this.getEntityWorld() instanceof ServerWorld serverWorld) {
            var payload = new MinecartControllerUpdatePayload(this.getId(), controllerType, maxSpeed);
            for (ServerPlayerEntity player : serverWorld.getPlayers()) {
                ServerPlayNetworking.send(player, payload);
            }
        }
    }
}