package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by JackNiu on 2017/7/23.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接到服务器被调用");
        ctx.write(Unpooled.copiedBuffer("Netty scokets", CharsetUtil.UTF_8));
    }



    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("Client received: "+ ByteBufUtil.hexDump(byteBuf.readBytes(byteBuf.readableBytes())));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
