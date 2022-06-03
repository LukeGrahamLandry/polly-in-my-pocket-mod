package ca.lukegrahamlandry.pollyinmypocket;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class ModRegistries {
    public static void init(IEventBus modEventBus){
        ITEMS.register(modEventBus);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    public static final RegistryObject<Item> PET = ITEMS.register(Constants.HELD_PET_ITEM_RL.getPath(), () -> new HeldPetItem(new Item.Properties()){
        @Override
        public void initializeClient(Consumer<IItemRenderProperties> consumer) {
            BlockEntityWithoutLevelRenderer renderer = new HeldPetRenderer(null, null);
            consumer.accept(new IItemRenderProperties() {

                @Override
                public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                    return renderer;
                }
            });
        }
    });

}
