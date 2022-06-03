package ca.lukegrahamlandry.pollyinmypocket;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Iterator;

@Mod(Constants.MOD_ID)
public class ExampleMod {
    public ExampleMod() {
        ModRegistries.init(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ServerEvents {
        @SubscribeEvent
        public static void grab(PlayerInteractEvent.EntityInteract event){
            if (HeldPetItem.wantsToPickup(event.getPlayer(), event.getHand())){
                 HeldPetItem.tryPickup(event.getPlayer(), event.getTarget());
            }
        }
    }
}