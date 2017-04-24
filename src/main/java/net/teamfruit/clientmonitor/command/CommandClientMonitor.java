package net.teamfruit.clientmonitor.command;

import javax.annotation.Nonnull;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.teamfruit.clientmonitor.ClientMonitor;
import net.teamfruit.clientmonitor.Log;
import net.teamfruit.clientmonitor.command.base.RootCommand;
import net.teamfruit.clientmonitor.command.base.SubCommand;
import net.teamfruit.clientmonitor.network.RequestMessage;
import net.teamfruit.clientmonitor.network.RequestMessage.RequestType;

public class CommandClientMonitor extends RootCommand {
	public final static @Nonnull CommandClientMonitor instance = new CommandClientMonitor();

	private CommandClientMonitor() {
		super("clientmonitor", "clientmonitor", "monitor");
		addChildCommand(new CommandSendSS());
		addChildCommand(new CommandSendML());
	}

	public static class CommandSendSS extends SubCommand {
		public CommandSendSS() {
			super("sendss");
			setPermLevel(PermLevel.ADMIN);
		}

		@Override
		public void processSubCommand(final ICommandSender sender, final String[] args) {
			if (args.length>0) {
				Log.log.info("Send sendss");
				ClientMonitor.proxy.network.sendTo(new RequestMessage(RequestType.ScreenShot), CommandBase.getPlayer(sender, args[0]));
			}
		}
	}

	public static class CommandSendML extends SubCommand {
		public CommandSendML() {
			super("modlist");
			setPermLevel(PermLevel.ADMIN);
		}

		@Override
		public void processSubCommand(final ICommandSender sender, final String[] args) {
			if (args.length>0) {
				Log.log.info("Send modlist");
				ClientMonitor.proxy.network.sendTo(new RequestMessage(RequestType.ModList), CommandBase.getPlayer(sender, args[0]));
			}
		}
	}
}
