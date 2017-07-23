package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * Created by JackNiu on 2017/7/22.
 */
public class EchoServer {
    private  final int port;
    public EchoServer(int port){
        this.port=port;
    }
    public void start() throws  Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            //SererBootstrap
            ServerBootstrap server= new ServerBootstrap();
            server.group(group).channel(NioServerSocketChannel.class).localAddress(port)
                    .childHandler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new EchoServerHandler());
//                            System.out.println(channel.eventLoop());
//                            System.out.println(channel.pipeline());
//                            System.out.println(channel.isActive());
//                            System.out.println(channel.localAddress());
//                            System.out.println(channel.remoteAddress());
//                            channel.write(Unpooled.copiedBuffer("Jack!!!", CharsetUtil.UTF_8));


                        }
                    });
            //绑定服务器，等待服务器关闭
            ChannelFuture futuer= server.bind().sync();
            System.out.println(EchoServer.class.getName()+" started and listen on\" "+futuer.channel().localAddress()+"\"");
            futuer.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
    public static void  main(String[] args)throws  Exception{
        new EchoServer(65535).start();
    }

}
