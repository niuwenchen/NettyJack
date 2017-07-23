package com.jackniu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * Created by JackNiu on 2017/7/23.
 */
public class ChannelOperationExamples {
    private static final Channel CHANNEL_FROM_SOMWHERE= new NioSctpChannel();

    // 线程安全的，可以被多个不同的线程操作。
    public static void writingToChannel(){
        final Channel channel = CHANNEL_FROM_SOMWHERE;
        ByteBuf buf = Unpooled.copiedBuffer("Your data", CharsetUtil.UTF_8);
        ChannelFuture cf= channel.writeAndFlush(buf);
        cf.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("Write successful");
                }else{
                    System.err.println("Write error");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    //多个不同的线程操作
    public static void writingToChannelFromManyThreads() {
        final Channel channel = CHANNEL_FROM_SOMWHERE; // Get the channel reference from somewhere
        final ByteBuf buf = Unpooled.copiedBuffer("your data",CharsetUtil.UTF_8);
        Runnable writer = new Runnable() {
            public void run() {
                channel.write(buf.duplicate());
            }
        };
        Executor executor = Executors.newCachedThreadPool();

        // write in one thread
        executor.execute(writer);

        // write in another thread
        executor.execute(writer);

    }
}
