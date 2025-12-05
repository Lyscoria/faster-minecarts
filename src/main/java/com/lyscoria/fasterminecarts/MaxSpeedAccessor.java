package com.lyscoria.fasterminecarts;

import net.minecraft.entity.vehicle.MinecartController;

public interface MaxSpeedAccessor {
    double getCurMaxSpeed();
    void setCurMaxSpeed(double speed);
    void setController(MinecartController controller);
}
