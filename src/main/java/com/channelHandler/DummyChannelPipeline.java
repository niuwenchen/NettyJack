package com.channelHandler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelPipeline;

/**
 * Created by JackNiu on 2017/7/23.
 */

public class DummyChannelPipeline extends DefaultChannelPipeline {
    public static final ChannelPipeline DUMMY_INSTANCE = new DummyChannelPipeline(null);
    public DummyChannelPipeline(Channel channel) {
        super(channel);
    }
}