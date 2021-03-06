package com.homebrewCult.TheBigBang;

import com.homebrewCult.TheBigBang.init.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.homebrewCult.TheBigBang.config.BigBangConfigSetup;
import com.homebrewCult.TheBigBang.network.BigBangPacketHandler;
import com.homebrewCult.TheBigBang.util.ClientProxy;
import com.homebrewCult.TheBigBang.util.IProxy;
import com.homebrewCult.TheBigBang.util.ServerProxy;
import com.homebrewCult.TheBigBang.world.ModWorldGen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(TheBigBang.MODID)
public final class TheBigBang {
	
	public static final String MODID = "thebigbang";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	
	public TheBigBang() {
		LOGGER.debug("Constructing the Big Bang.");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BigBangConfigSetup.SERVER_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BigBangConfigSetup.CLIENT_CONFIG);
		BigBangConfigSetup.LoadConfig(BigBangConfigSetup.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve("thebigbang-client.toml").toString());
		BigBangConfigSetup.LoadConfig(BigBangConfigSetup.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve("thebigbang-server.toml").toString());
		
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModRecipeTypes.RECIPES.register(eventBus);
		ModParticleTypes.PARTICLE_TYPES.register(eventBus);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		proxy.Init();
		BigBangPacketHandler.packetHandlerInit();
		ModWorldGen.worldGenInit();
	}
	
	private void clientRegistries(final FMLClientSetupEvent event) {
		ModBlocks.registerBlockRenderers(event);
		ModEntities.registerEntityRenders();
		ModLayers.registerLayers();
	}
	
	public static void print (String message) {
		LOGGER.debug("[The Big Bang Info] " + message);
	}
}
