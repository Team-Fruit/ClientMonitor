package net.teamfruit.clientmonitor.network;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.teamfruit.clientmonitor.Log;

public class RequestMessage implements IMessage {
	private RequestType type;

	@Deprecated
	public RequestMessage() {
	}

	public RequestMessage(final RequestType type) {
		this.type = type;
	}

	@Override
	public void fromBytes(final ByteBuf buf) {
		this.type = RequestType.fromID(ByteBufUtils.readUTF8String(buf)); // this class is very useful in general for writing more complex objects
	}

	@Override
	public void toBytes(final ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, this.type.getID());
	}

	public static class Handler implements IMessageHandler<RequestMessage, IMessage> {
		@Override
		public IMessage onMessage(final RequestMessage message, final MessageContext ctx) {
			Log.log.info(String.format("Received %s", message.type.getID()));
			return null; // no response in this case
		}
	}

	private static final @Nonnull Map<String, RequestType> map = Maps.newHashMap();

	public static enum RequestType {
		ScreenShot("sendss"),
		ModList("modlist"),
		;

		private @Nonnull String id;

		private RequestType(final @Nonnull String id) {
			this.id = id;
			map.put(id, this);
		}

		public @Nonnull String getID() {
			return this.id;
		}

		public static @Nonnull String getID(final RequestType type) {
			return type.getID();
		}

		public static @Nullable RequestType fromID(final String id) {
			return map.get(id);
		}
	}
}