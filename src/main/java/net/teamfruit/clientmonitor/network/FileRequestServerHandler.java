package net.teamfruit.clientmonitor.network;

import java.io.File;
import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedFile;
import net.teamfruit.clientmonitor.Log;

public class FileRequestServerHandler extends SimpleChannelInboundHandler<FileRequestProtocol> {

	private File f;

	@Override
	public void channelRead0(final ChannelHandlerContext ctx, final FileRequestProtocol fileRequest) throws IOException {
		Log.log.info("Server new FileRequest "+fileRequest);
		this.f = new File(fileRequest.getFilePath());
		fileRequest.setFileSize(this.f.length());
		ctx.writeAndFlush(fileRequest);

		// directly make your chunkedFile there instead of creating a sub handler
		final ChunkedFile chunkedFile = new ChunkedFile(this.f);
		ctx.writeAndFlush(chunkedFile);// need a specific handler
		// Don't create such an handler: new ChunkedFileServerHandler(ctx,f);
	}
}