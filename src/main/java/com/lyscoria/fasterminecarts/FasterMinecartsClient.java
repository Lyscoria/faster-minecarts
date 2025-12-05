package com.lyscoria.fasterminecarts;

import com.lyscoria.fasterminecarts.network.ClientNetwork;
import net.fabricmc.api.ClientModInitializer;

public class FasterMinecartsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetwork.register();
    }
}