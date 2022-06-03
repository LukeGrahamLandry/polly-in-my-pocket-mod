package ca.lukegrahamlandry.pollyinmypocket;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.Registry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;

public class ExampleMod implements ModInitializer {

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, Constants.HELD_PET_ITEM_RL, new HeldPetItem(new Item.Properties()));

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (HeldPetItem.wantsToPickup(player, hand) && HeldPetItem.tryPickup(player, entity)) return InteractionResult.CONSUME;
            return InteractionResult.PASS;
        });
    }
}
