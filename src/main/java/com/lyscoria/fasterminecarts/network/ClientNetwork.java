package com.lyscoria.fasterminecarts.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetwork {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(
                MinecartControllerUpdatePayload.ID, (payload, context) -> {
                    context.client().execute(() -> {
                        var world = context.client().world;
                        if (world != null) {
                            var entity = world.getEntityById(payload.entityId());
                            if (entity instanceof net.minecraft.entity.vehicle.AbstractMinecartEntity minecart) {
                                var controller = payload.createController(minecart);
                                if (minecart instanceof com.lyscoria.fasterminecarts.MaxSpeedAccessor accessor) {
                                    accessor.setController(controller);
                                    accessor.setCurMaxSpeed(payload.maxSpeed());
                                }
                            }
                        }
                    });
                }
        );
    }
}