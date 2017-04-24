package net.teamfruit.clientmonitor;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
	@Override
	@SideOnly(Side.CLIENT)
	public void preInit(final @Nonnull FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void init(final @Nonnull FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void postInit(final @Nonnull FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void serverStarting(final @Nonnull FMLServerStartingEvent event) {
		super.serverStarting(event);
	}
}