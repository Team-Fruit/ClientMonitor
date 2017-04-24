package net.teamfruit.clientmonitor.api;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;

/**
 * Modリスト要求リクエストを受けた時のイベント
 *
 * @author TeamFruit
 */
public abstract class ModListRequestedEvent extends Event {
	/**
	 * プレイヤーの名前
	 */
	public @Nonnull String playerName;

	public ModListRequestedEvent(final @Nonnull String playerName) {
		this.playerName = playerName;
	}

	/**
	 * イベントを発生させます。
	 */
	public void post() {
		MinecraftForge.EVENT_BUS.post(this);
	}

	/**
	 * Modリスト要求リクエストを受けた時のイベント
	 * <br>
	 * 返答タイプの生成処理の前に呼ばれます。
	 *
	 * @author TeamFruit
	 */
	@Cancelable
	public static class Pre extends ModListRequestedEvent {
		public Pre(final @Nonnull String playerName) {
			super(playerName);
		}
	}

	/**
	 * Modリスト要求リクエストを受けた時のイベント
	 * <br>
	 * 返答タイプの生成処理の後に呼ばれます。
	 *
	 * @author TeamFruit
	 */
	@Cancelable
	public static class Post extends ModListRequestedEvent {
		/**
		 * 送信予定のテキスト
		 * <br>
		 * nullの場合は送信されません
		 */
		public @Nullable List<String> pending;

		public Post(final @Nonnull String playerName) {
			super(playerName);
		}
	}
}
