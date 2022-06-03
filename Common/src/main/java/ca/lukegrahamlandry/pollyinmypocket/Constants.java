package ca.lukegrahamlandry.pollyinmypocket;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Constants {
	public static final String MOD_ID = "pollyinmypocket";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation HELD_PET_ITEM_RL = new ResourceLocation(MOD_ID, "held_pet");
}