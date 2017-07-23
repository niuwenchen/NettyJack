package com.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by JackNiu on 2017/7/22.
 */
/**
*  ChannelInboundHandlerAdapter，需要返回相同的消息给客户端，在服务器执行完写操作之前不能释放读到的消息
 *  写操作是异步的，一旦写操作完成，Netty会自动释放消息
 *  SimpleChannelInboundHandler: 在处理完消息后负责释放资源，调用ByteBuf.release()来释放资源
 *  会在完成channelRead0后释放消息。
 *  客户端: 一read完就需要释放
 *  服务器: read之后，还要执行写操作才能释放，有一个时间上的延迟。
*/

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    final ByteBuf  buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", CharsetUtil.UTF_8));

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器端 channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead方法");
        ByteBuf in = (ByteBuf) msg;

        System.out.println("Server recevied: "+msg+" --> "+ in.toString(CharsetUtil.UTF_8));
        ctx.write(in);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
