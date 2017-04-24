package net.teamfruit.clientmonitor;

import javax.annotation.Nonnull;

public class Reference {
	public static final @Nonnull String MODID = "clientmonitor";
	public static final @Nonnull String NAME = "ClientMonitor";
	public static final @Nonnull String VERSION = "${version}";
	public static final @Nonnull String FORGE = "${forgeversion}";
	public static final @Nonnull String MINECRAFT = "${mcversion}";
	public static final @Nonnull String PROXY_SERVER = "net.teamfruit.clientmonitor.CommonProxy";
	public static final @Nonnull String PROXY_CLIENT = "net.teamfruit.clientmonitor.ClientProxy";
	public static final @Nonnull String GUI_FACTORY = "net.teamfruit.clientmonitor.gui.config.ConfigGuiFactory";
}
