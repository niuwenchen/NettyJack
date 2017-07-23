## Transport
每一个channel都有一个ChannelPipeline 和一个ChannelConfig 
ChannelConfig 负责配置并设置存储配置，并允许在运行期间更新它们。
ChannelPipeline 保存了所有的handler实例。
访问指定的ChannelPipeline和ChannelConfig，你可以继续操作Channel。

    envetloop, pipeline,isActive, localAddress, remoteAddress, write

注意，一般意义上的数据操作是在handler中完成的， ChannelHandlerContext，也可以在直接在对应的channel中操作。
    
    ChannelFuture future = b.connect().sync();
    //            future.addListener(new ChannelFutureListener() {
    //                public void operationComplete(ChannelFuture channelFuture) throws Exception {
    //                    if (channelFuture.isSuccess()){
    //                        ByteBuf buf = Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8);
    //                        channelFuture.channel().writeAndFlush(buf);
    //                    }
    //                }
    //            });
    这段代码就是直接在对应的channel中执行的，会在消息的后面跟一个Hello字符串。
    

Netty包含的传输实现

    NIO，OIO，Local，Embedded(允许在没有真正网络的传输中使用ChannelHandler，测试)