package net.teamfruit.clientmonitor.api;

import java.awt.image.BufferedImage;

import javax.annotation.Nullable;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;

/**
 * スクリーンショット要求リクエストを受けた時のイベント
 *
 * @author TeamFruit
 */
public abstract class ScreenShotRequestedEvent extends Event {
	public ScreenShotRequestedEvent() {
	}

	/**
	 * イベントを発生させます。
	 */
	public void post() {
		MinecraftForge.EVENT_BUS.post(this);
	}

	/**
	 * スクリーンショット要求リクエストを受けた時のイベント
	 * <br>
	 * 返答タイプの生成処理の前に呼ばれます。
	 *
	 * @author TeamFruit
	 */
	@Cancelable
	public static class Pre extends ScreenShotRequestedEvent {
		public Pre() {
			super();
		}
	}

	/**
	 * スクリーンショット要求リクエストを受けた時のイベント
	 * <br>
	 * 返答タイプの生成処理の後に呼ばれます。
	 *
	 * @author TeamFruit
	 */
	@Cancelable
	public static class Post extends ScreenShotRequestedEvent {
		/**
		 * 送信予定の画像
		 * <br>
		 * nullの場合は送信されません
		 */
		public @Nullable BufferedImage pending;

		public Post() {
			super();
		}
	}
}
