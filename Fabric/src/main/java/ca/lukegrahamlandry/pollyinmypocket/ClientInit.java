package ca.lukegrahamlandry.pollyinmypocket;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.core.Registry;

public class ClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BuiltinItemRendererRegistry.INSTANCE.register(Registry.ITEM.get(Constants.HELD_PET_ITEM_RL), new HeldPetRenderer(null, null)::renderByItem);
    }
}
