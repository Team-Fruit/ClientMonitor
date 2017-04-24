package net.teamfruit.clientmonitor;

import java.awt.Color;

import javax.annotation.Nonnull;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.teamfruit.clientmonitor.api.ModListRequestedEvent;
import net.teamfruit.clientmonitor.api.ScreenShotRequestedEvent;
import net.teamfruit.clientmonitor.network.RequestMessage;
import net.teamfruit.clientmonitor.network.RequestMessage.RequestType;

public class CoreHandler {
	public final @Nonnull Config configHandler = Config.getConfig();

	public void init() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onConfigChanged(final @Nonnull ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		this.configHandler.onConfigChanged(eventArgs);
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onScreenShotShield(final @Nonnull ScreenShotRequestedEvent.Post event) {
		if (Config.getConfig().notifyChat.get())
			ChatBuilder.create("clientmonitor.notification.chat.screenshot.message").useTranslation().chatClient();
		if (Config.getConfig().notifySound.get())
			FMLClientHandler.instance().getClient().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("clientmonitor", "notification.sound"), 1.0F));
		if (Config.getConfig().notifyEffect.get())
			this.effectStrength = .75f;
		if (Config.getConfig().notifyOverlay.get()) {
			this.text = I18n.format("clientmonitor.notification.overlay.screenshot.message");
			this.textStrength = 1f;
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onModListShield(final @Nonnull ModListRequestedEvent.Post event) {
		if (Config.getConfig().notifyChat.get())
			ChatBuilder.create("clientmonitor.notification.chat.modlist.message").useTranslation().chatClient();
		if (Config.getConfig().notifySound.get())
			FMLClientHandler.instance().getClient().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("clientmonitor", "notification.sound"), 1.0F));
		if (Config.getConfig().notifyEffect.get())
			this.effectStrength = .75f;
		if (Config.getConfig().notifyOverlay.get()) {
			this.text = I18n.format("clientmonitor.notification.overlay.modlist.message");
			this.textStrength = 1f;
		}
	}

	private float effectStrength = 0f;
	private @Nonnull String text = "";
	private float textStrength = 0f;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDraw(final @Nonnull RenderGameOverlayEvent.Post event) {
		if (event.type==ElementType.EXPERIENCE) {
			if (this.effectStrength>0f) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				final Minecraft mc = FMLClientHandler.instance().getClient();
				final Tessellator t = Tessellator.instance;
				t.startDrawingQuads();
				t.setColorRGBA_F(1f, 1f, 1f, this.effectStrength);
				t.addVertex(0f, 0f, 0f);
				t.addVertex(0f, mc.displayHeight, 0f);
				t.addVertex(mc.displayWidth, mc.displayHeight, 0f);
				t.addVertex(mc.displayWidth, 0f, 0f);
				t.draw();
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				this.effectStrength -= .015f;
			}
			if (this.textStrength>0f)
				if ((int) (System.currentTimeMillis()/500)%2==0) {
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					final Minecraft mc = FMLClientHandler.instance().getClient();
					mc.fontRenderer.drawStringWithShadow(this.text, 5, 5, new Color(1f, 1f, 1f, this.textStrength).getRGB());
				}
			this.textStrength -= .001f;
		}
	}

	@SubscribeEvent
	public void onTick(final @Nonnull ClientTickEvent event) {
		if (event.phase==Phase.END)
			debugKey();
	}

	private boolean debugKey;

	private void debugKey() {
		if (Keyboard.isKeyDown(Keyboard.KEY_I)&&Keyboard.isKeyDown(Keyboard.KEY_O)&&Keyboard.isKeyDown(Keyboard.KEY_P)) {
			if (!this.debugKey)
				debug();
			this.debugKey = true;
		} else
			this.debugKey = false;
	}

	private void debug() {
		ClientMonitor.proxy.network.sendToServer(new RequestMessage(RequestType.ModList));
	}
}
