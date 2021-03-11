package com.homebrewCult.TheBigBang.entities.render;

import com.homebrewCult.TheBigBang.TheBigBang;
import com.homebrewCult.TheBigBang.entities.GenesisBeamEntity;
import com.homebrewCult.TheBigBang.entities.model.DragonCrusherLayerModel;
import com.homebrewCult.TheBigBang.entities.model.GenesisLayerModel;
import com.homebrewCult.TheBigBang.entities.model.HurricaneLayerModel;
import com.homebrewCult.TheBigBang.entities.model.HyperBodyLayerModel;
import com.homebrewCult.TheBigBang.entities.model.MonsterMagnetLayerModel;
import com.homebrewCult.TheBigBang.entities.model.ThreatenLayerModel;
import com.homebrewCult.TheBigBang.init.ModItems;
import com.homebrewCult.TheBigBang.items.HeliosItem;
import com.homebrewCult.TheBigBang.items.LamaStaffItem;
import com.homebrewCult.TheBigBang.items.OmegaSpearItem;
import com.homebrewCult.TheBigBang.items.ScorpioItem;
import com.homebrewCult.TheBigBang.items.ZardItem;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BigBangPlayerLayer<T extends LivingEntity> extends LayerRenderer<T, PlayerModel<T>> {

	public static final ResourceLocation HURRICANE_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hurricane.png");
	public static final ResourceLocation GENESIS_ANGEL_0_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/genesis_angel_0.png");
	public static final ResourceLocation GENESIS_ANGEL_1_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/genesis_angel_1.png");
	public static final ResourceLocation HYPER_BODY_0_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hyper_body_0.png");
	public static final ResourceLocation HYPER_BODY_1_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hyper_body_1.png");
	public static final ResourceLocation HYPER_BODY_2_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hyper_body_2.png");
	public static final ResourceLocation HYPER_BODY_3_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hyper_body_3.png");
	public static final ResourceLocation HYPER_BODY_4_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/hyper_body_4.png");
	public static final ResourceLocation DRAGON_CRUSHER_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/dragon_crusher.png");
	public static final ResourceLocation THREATEN_0_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/threaten_0.png");
	public static final ResourceLocation THREATEN_1_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/threaten_1.png");
	public static final ResourceLocation THREATEN_2_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/threaten_2.png");
	public static final ResourceLocation THREATEN_3_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/threaten_3.png");
	public static final ResourceLocation MONSTER_MAGNET_0_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_0.png");
	public static final ResourceLocation MONSTER_MAGNET_1_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_1.png");
	public static final ResourceLocation MONSTER_MAGNET_2_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_2.png");
	public static final ResourceLocation MONSTER_MAGNET_3_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_3.png");
	public static final ResourceLocation MONSTER_MAGNET_4_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_4.png");
	public static final ResourceLocation MONSTER_MAGNET_5_TEXTURE_LOCATION = new ResourceLocation(TheBigBang.MODID, "textures/particle/monster_magnet_5.png");
	
	private static final HyperBodyLayerModel HYPER_BODY_MODEL = new HyperBodyLayerModel();
	private static final GenesisLayerModel GENESIS_MODEL = new GenesisLayerModel();
	private static final HurricaneLayerModel HURRICANE_MODEL = new HurricaneLayerModel();
	private static final DragonCrusherLayerModel DRAGON_CRUSHER_MODEL = new DragonCrusherLayerModel();
	private static final ThreatenLayerModel THREATEN_MODEL = new ThreatenLayerModel();
	private static final MonsterMagnetLayerModel MONSTER_MAGNET_MODEL = new MonsterMagnetLayerModel();
	
	private static final ResourceLocation PULL = new ResourceLocation("pull");
	
	public BigBangPlayerLayer(IEntityRenderer<T, PlayerModel<T>> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(T entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		PlayerEntity player = (PlayerEntity)entityIn;
		ItemStack stack = player.getHeldItemMainhand();
		if(stack.getItem().equals(ModItems.VAULTER_2000) && stack.getItem().properties.containsKey(PULL)) {
			float pull = stack.getItem().getPropertyGetter(PULL).call(stack, player.world, player);
			if(pull > 0) {
				this.renderHurricane(player, pull, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		} else if(stack.getItem().equals(ModItems.LAMA_STAFF)) {
			CompoundNBT nbt = stack.getOrCreateTag();
			if(nbt.contains(LamaStaffItem.SPELL_TIME_KEY)) {
				this.renderGenesisAngel(player, nbt, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		} else if(stack.getItem().equals(ModItems.OMEGA_SPEAR)) {
			CompoundNBT nbt = stack.getOrCreateTag();
			if(nbt.contains(OmegaSpearItem.HYPER_BODY_TIME_KEY)) {
				this.renderHyperBody(player, nbt, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		} else if(stack.getItem().equals(ModItems.SCORPIO)) {
			CompoundNBT nbt = stack.getOrCreateTag();
			if(nbt.contains(ScorpioItem.DRAGON_CRUSHER_TIME_KEY)) {
				this.renderDragonCrusher(player, nbt, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		} else if(stack.getItem().equals(ModItems.HELIOS)) {
			CompoundNBT nbt = stack.getOrCreateTag();
			if(nbt.contains(HeliosItem.THREATEN_TIME_KEY)) {
				this.renderThreaten(player, nbt, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		} else if(stack.getItem().equals(ModItems.ZARD)) {
			CompoundNBT nbt = stack.getOrCreateTag();
			if(nbt.contains(ZardItem.MONSTER_MAGNET_TIME_KEY)) {
				this.renderMonsterMagnet(player, nbt, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleFactor);
			}
		}
	}

	public void renderHurricane(PlayerEntity player, float pull, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		float hurricanePct = MathHelper.clamp((pull - 1) / 2, 0, 1);
		GlStateManager.pushMatrix();
		this.bindTexture(HURRICANE_TEXTURE_LOCATION);
		float heightOffset = 0;
		GlStateManager.translatef(0f, heightOffset, 0f);
		float scale = 0.0625F;
		GlStateManager.scalef(scale, scale, scale);
		int j = 15728880 % 65536;
		int k = 15728880 / 65536;
		GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, hurricanePct); 
		HURRICANE_MODEL.render(pull);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	public void renderGenesisAngel(PlayerEntity player, CompoundNBT nbt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		int genesisTime = player.ticksExisted - nbt.getInt(LamaStaffItem.SPELL_TIME_KEY);
		if(genesisTime > 0 && genesisTime < 45) {
			GlStateManager.pushMatrix();
			if(genesisTime > 30) {
				this.bindTexture(GENESIS_ANGEL_1_TEXTURE_LOCATION);
			} else {
				this.bindTexture(GENESIS_ANGEL_0_TEXTURE_LOCATION);
			}
			float heightOffset = -1.5f + (genesisTime / 45F) * -1.5f;
			GlStateManager.translatef(0f, heightOffset, 0.2f);
			float scale = 0.0625F;
			GlStateManager.scalef(scale, scale, scale);
			int j = 15728880 % 65536;
			int k = 15728880 / 65536;
			GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, MathHelper.sin((genesisTime / 45F) * (float)Math.PI)); 
			GENESIS_MODEL.render(genesisTime + partialTicks);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	public void renderHyperBody(PlayerEntity player, CompoundNBT nbt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		int hyperBodyTime = player.ticksExisted - nbt.getInt(OmegaSpearItem.HYPER_BODY_TIME_KEY);
		if(hyperBodyTime < 30) {
			GlStateManager.pushMatrix();
			if(hyperBodyTime > 28) {
				this.bindTexture(HYPER_BODY_4_TEXTURE_LOCATION);
			} else if(hyperBodyTime > 26) {
				this.bindTexture(HYPER_BODY_3_TEXTURE_LOCATION);
			} else if(hyperBodyTime > 24) {
				this.bindTexture(HYPER_BODY_2_TEXTURE_LOCATION);
			} else if(hyperBodyTime > 20) {
				this.bindTexture(HYPER_BODY_1_TEXTURE_LOCATION);
			} else {
				this.bindTexture(HYPER_BODY_0_TEXTURE_LOCATION);
			}
			GlStateManager.translatef(0f, 0f, 0f);
			float scale = 0.0625F;
			float width = MathHelper.clamp((float)hyperBodyTime + partialTicks, 0f, 5f) / 5f;
			GlStateManager.scalef(scale * width, scale, scale);
			GlStateManager.enableBlend();
			if(hyperBodyTime > 22) {
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F - ((hyperBodyTime - 20) * 0.1F));
			}   
			HYPER_BODY_MODEL.render(hyperBodyTime + partialTicks);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			if(hyperBodyTime == 12) {
				for(int x = 0; x < 3; ++x) {
					for(int y = 0; y < 3; ++y) {
						for(int z = 0; z < 3; ++z) {
							double p0 = player.posX + (x * 0.1d);
							double p1 = player.posY + (y * 0.8d);
							double p2 = player.posZ + (z * 0.1d);
							double s0 = (player.world.rand.nextDouble() - 0.5d) * 0.5d;
							double s1 = (player.world.rand.nextDouble() - 0.5d) * 0.5d;
							double s2 = (player.world.rand.nextDouble() - 0.5d) * 0.5d;
							player.world.addParticle(ParticleTypes.POOF, p0, p1, p2, s0, s1, s2);
						}
					}
				}
			}
		}
	}
	
	public void renderDragonCrusher(PlayerEntity player, CompoundNBT nbt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		int dragonTime = player.ticksExisted - nbt.getInt(ScorpioItem.DRAGON_CRUSHER_TIME_KEY);
		if(dragonTime > 0 && dragonTime < 45) {
			GlStateManager.pushMatrix();
			this.bindTexture(DRAGON_CRUSHER_TEXTURE_LOCATION);
			GlStateManager.translatef(0f, 0f, 0f);
			float scale = 0.0625F;
			GlStateManager.scalef(scale, scale, scale);
			int j = 15728880 % 65536;
			int k = 15728880 / 65536;
			GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, MathHelper.sin((dragonTime / 45F) * (float)Math.PI)); 
			DRAGON_CRUSHER_MODEL.render(dragonTime + partialTicks);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	public void renderThreaten(PlayerEntity player, CompoundNBT nbt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		int threatenTime = player.ticksExisted - nbt.getInt(HeliosItem.THREATEN_TIME_KEY);
		if(threatenTime > 0 && threatenTime < 45) {
			GlStateManager.pushMatrix();
			if(threatenTime > 30) {
				this.bindTexture(THREATEN_3_TEXTURE_LOCATION);
			} else if (threatenTime > 20) {
				this.bindTexture(THREATEN_2_TEXTURE_LOCATION);
			} else if (threatenTime > 10) {
				this.bindTexture(THREATEN_1_TEXTURE_LOCATION);
			} else {
				this.bindTexture(THREATEN_0_TEXTURE_LOCATION);
			}
			GlStateManager.translatef(0f, 0f, 0f);
			float scale = 0.0625F;
			GlStateManager.scalef(scale, scale * MathHelper.clamp((float)threatenTime / 20f, 0f, 1f), scale);
			int j = 15728880 % 65536;
			int k = 15728880 / 65536;
			//GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
			//GlStateManager.enableBlend();
			//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F); 
			THREATEN_MODEL.render(threatenTime + partialTicks);
			//GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}
	
	public void renderMonsterMagnet(PlayerEntity player, CompoundNBT nbt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		int magnetTime = player.ticksExisted - nbt.getInt(ZardItem.MONSTER_MAGNET_TIME_KEY);
		if(magnetTime > 0 && magnetTime < 45) {
			GlStateManager.pushMatrix();
			if(magnetTime > 30) {
				this.bindTexture(MONSTER_MAGNET_1_TEXTURE_LOCATION);
			} else {
				this.bindTexture(MONSTER_MAGNET_0_TEXTURE_LOCATION);
			}
			GlStateManager.translatef(0f, 0f, 0f);
			float scale = 0.0625F;
			GlStateManager.scalef(scale, scale, scale);
			int j = 15728880 % 65536;
			int k = 15728880 / 65536;
			GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float)j, (float)k);
			//GlStateManager.enableBlend();
			//GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_ALPHA);
			//GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F); 
			MONSTER_MAGNET_MODEL.render(magnetTime + partialTicks);
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}

}
