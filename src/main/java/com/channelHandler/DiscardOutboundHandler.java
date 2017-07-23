package com.channelHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by JackNiu on 2017/7/23.
 */
public class DiscardOutboundHandler  extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx,
                      Object msg, ChannelPromise promise) {
        ReferenceCountUtil.release(msg);
        //通知channelPromise已经处理了消息。
        promise.setSuccess();
    }


}
