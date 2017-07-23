package com.jackniu.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by JackNiu on 2017/7/23.
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port){
        this.port=port;
    }
    public void start() throws Exception{
        EventLoopGroup group= new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            // 绑定到服务器并且等待绑定完成，sync 是启用阻塞方式，
            // 为什么要启用阻塞服务，只有当绑定之后才能继续别的操作
            ChannelFuture future=b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    "started and listen on" + future.channel().localAddress());
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception{
        new EchoServer(8080).start();
    }
}
