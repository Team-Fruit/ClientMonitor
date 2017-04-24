package net.teamfruit.clientmonitor;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcheli.MCH_OStream;
import mcheli.multiplay.MCH_PacketLargeData;
import mcheli.multiplay.MCH_PacketModList;
import net.minecraft.client.shader.Framebuffer;
import net.teamfruit.clientmonitor.api.ModListRequestedEvent;
import net.teamfruit.clientmonitor.api.ScreenShotRequestedEvent;
import net.teamfruit.clientmonitor.requested.DefaultMLRequested;
import net.teamfruit.clientmonitor.requested.DefaultSSRequested;
import net.teamfruit.clientmonitor.requested.ModListRequested;
import net.teamfruit.clientmonitor.requested.ScreenShotRequested;

@CoreInvoke
@SideOnly(Side.CLIENT)
public class MCH_MultiplayClient {
	private static @Nonnull ModListRequested modListRequested = new DefaultMLRequested();
	private static @Nonnull ScreenShotRequested screenShotRequested = new DefaultSSRequested();

	private static MCH_OStream dataOutputStream;

	@CoreInvoke
	public static void startSendImageData() {
		Log.log.warn("sending screenshot is requested!");
		try {
			final ScreenShotRequestedEvent.Pre eventPre = new ScreenShotRequestedEvent.Pre();
			eventPre.post();
			if (!eventPre.isCanceled()) {
				final ScreenShotRequestedEvent.Post eventPost = new ScreenShotRequestedEvent.Post();
				eventPost.pending = screenShotRequested.onRequested();
				eventPost.post();
				if (!eventPost.isCanceled())
					if (eventPost.pending!=null) {
						dataOutputStream = new MCH_OStream();
						ImageIO.write(eventPost.pending, "png", dataOutputStream);
					}
			}
		} catch (final Exception arg7) {
			Log.log.error("Failed to send image: ", arg7);
		}

	}

	@CoreInvoke
	public static void sendScreenShot(final int displayWidth, final int displayHeight, final Framebuffer framebufferMc) {
	}

	@CoreInvoke
	public static void readImageData(final DataOutputStream dos) throws IOException {
		Log.log.info("loading screenshot image to send.");
		dataOutputStream.write(dos);
	}

	@CoreInvoke
	public static void sendImageData() {
		if (dataOutputStream!=null) {
			MCH_PacketLargeData.send();
			Log.log.info("send screenshot.");
			if (dataOutputStream.isDataEnd())
				dataOutputStream = null;
		}
	}

	@CoreInvoke
	public static double getPerData() {
		return dataOutputStream==null ? -1.0D : (double) dataOutputStream.index/(double) dataOutputStream.size();
	}

	@CoreInvoke
	public static void readModList(final String playerName) {
	}

	@CoreInvoke
	public static void sendModsInfo(final String playerName, final int id) {
		Log.log.warn("sending modlist is requested!");

		// final MCH_Config arg9999 = MCH_MOD.config;
		// if (MCH_Config.DebugLog) {
		// 	modList.clear();
		// 	readModList(playerName);
		// }

		final ModListRequestedEvent.Pre eventPre = new ModListRequestedEvent.Pre(playerName);
		eventPre.post();
		if (!eventPre.isCanceled()) {
			final ModListRequestedEvent.Post eventPost = new ModListRequestedEvent.Post(playerName);
			eventPost.pending = modListRequested.onRequested(playerName);
			eventPost.post();
			if (!eventPost.isCanceled())
				if (eventPost.pending!=null) {
					MCH_PacketModList.send(eventPost.pending, id);
					Log.log.info("send modlist.");
				}
		}
	}
}