package com.lyscoria.fasterminecarts.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import net.minecraft.entity.vehicle.ExperimentalMinecartController;
import net.minecraft.entity.vehicle.MinecartController;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;

import static com.lyscoria.fasterminecarts.FasterMinecarts.MOD_ID;

public record MinecartControllerUpdatePayload(int entityId, int controllerType, double maxSpeed) implements CustomPayload {
    public static final Id<MinecartControllerUpdatePayload> ID =
            new CustomPayload.Id<>(Identifier.of(MOD_ID, "minecart_controller_update"));

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static final PacketCodec<PacketByteBuf, MinecartControllerUpdatePayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER, MinecartControllerUpdatePayload::entityId,
                    PacketCodecs.INTEGER, MinecartControllerUpdatePayload::controllerType,
                    PacketCodecs.DOUBLE, MinecartControllerUpdatePayload::maxSpeed,
                    MinecartControllerUpdatePayload::new
            );

    /*

    public static final PacketCodec<PacketByteBuf, MinecartControllerUpdatePayload> CODEC =
            PacketCodec.of(
                    MinecartControllerUpdatePayload::write,
                    MinecartControllerUpdatePayload::read
            );

    public static MinecartControllerUpdatePayload read(PacketByteBuf buf) {
        return new MinecartControllerUpdatePayload(
                buf.readInt(),
                buf.readInt(),
                buf.readDouble()
        );
    }

    public void write(PacketByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(controllerType);
        buf.writeDouble(maxSpeed);
    }

    */

    public MinecartController createController(AbstractMinecartEntity minecart) {
        if (controllerType == 1) {
            return new ExperimentalMinecartController(minecart);
        } else {
            return new DefaultMinecartController(minecart);
        }
    }

}