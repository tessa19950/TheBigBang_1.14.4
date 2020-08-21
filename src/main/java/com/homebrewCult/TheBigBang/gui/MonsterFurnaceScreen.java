package com.homebrewCult.TheBigBang.gui;

import com.homebrewCult.TheBigBang.TheBigBang;
import com.homebrewCult.TheBigBang.inventory.MonsterFurnaceContainer;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class MonsterFurnaceScreen extends AbstractFurnaceScreen<MonsterFurnaceContainer> {

	public static final ResourceLocation MONSTER_FURNACE = new ResourceLocation(TheBigBang.MODID, "textures/gui/monster_furnace.png"); 
	
	public MonsterFurnaceScreen(MonsterFurnaceContainer container, PlayerInventory playerInventory, ITextComponent titleIn) {
		super(container, new FurnaceRecipeGui(), playerInventory, titleIn, MONSTER_FURNACE);
	}
}
