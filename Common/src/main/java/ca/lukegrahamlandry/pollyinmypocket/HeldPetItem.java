package ca.lukegrahamlandry.pollyinmypocket;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class HeldPetItem extends Item {
    public HeldPetItem(Properties $$0) {
        super($$0);
    }

    public static boolean wantsToPickup(Player player, InteractionHand hand) {
        return !player.level.isClientSide() && player.isShiftKeyDown() && player.getItemInHand(hand).isEmpty() && hand == InteractionHand.MAIN_HAND;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        if (!world.isClientSide()){
            ItemStack stack = ctx.getItemInHand();
            BlockPos pos = ctx.getClickedPos();
            Direction direction = ctx.getClickedFace();
            BlockState state = world.getBlockState(pos);

            BlockPos spawnPosition;
            if (state.getCollisionShape(world, pos).isEmpty()) {
                spawnPosition = pos;
            } else {
                spawnPosition = pos.relative(direction);
            }


            Entity pet = readEntity(stack, world);
            if (pet == null) return InteractionResult.FAIL;
            pet.setPos(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ());
            world.addFreshEntity(pet);
            stack.shrink(1);
            world.gameEvent(ctx.getPlayer(), GameEvent.ENTITY_PLACE, pos);
        }

        return InteractionResult.SUCCESS;
    }

    public static Entity readEntity(ItemStack stack, Level world) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(ENTITY_NBT_KEY)) return null;
        CompoundTag entityTag = tag.getCompound(ENTITY_NBT_KEY);
        String typeID = entityTag.getString("id");
        if (EntityType.byString(typeID).isEmpty()) return null;
        EntityType entityType = EntityType.byString(typeID).get();
        Entity pet = entityType.create(world);
        pet.load(entityTag);
        return pet;
    }

    public static String ENTITY_NBT_KEY = "entity";

    public static TagKey<EntityType<?>> CAN_PICKUP_ENTITY = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, new ResourceLocation(Constants.MOD_ID, "pickup"));

    public static boolean tryPickup(Player player, Entity target){
        boolean isOwner = !(target instanceof TamableAnimal) || player.getUUID().equals(((TamableAnimal) target).getOwnerUUID());
        if (target.getType().is(CAN_PICKUP_ENTITY) && isOwner){
            CompoundTag entityTag = new CompoundTag();
            target.save(entityTag);

            ItemStack stack = new ItemStack(Registry.ITEM.get(Constants.HELD_PET_ITEM_RL));
            CompoundTag stackTag = new CompoundTag();
            stackTag.put(ENTITY_NBT_KEY, entityTag);

            stack.setTag(stackTag);

            target.discard();

            boolean result = player.addItem(stack);
            if (!result) player.drop(stack, false);
            return true;
        }
        return false;
    }

}
