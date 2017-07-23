package com.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by JackNiu on 2017/7/23.
 */
/*
* 使用OIO 解决并发问题
*
* 开启选择器来处理channel
* 给selector注册每一个socket
*等待新的事件被处理，阻塞事件
*
* 反正是太麻烦
* */
public class PlainNioServer {
    public void server(int port) throws Exception{
        System.out.println("Listening for connection on port:"+ port);
        ServerSocketChannel serverSocketChannel;
        Selector selector;

        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket ss = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address); // 将socket绑定到这个端口，也就是用这个socket监听这个端口联结
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi\r\n".getBytes());

        while(true){
            try{
                selector.select();
            }catch (IOException ex){
                ex.printStackTrace();
                break;
            }

            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next() ;
                iterator.remove();
                try{
                    if (key.isAcceptable()){
                        ServerSocketChannel server =(ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accept connection from "+client);
                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE| SelectionKey.OP_READ,msg.duplicate());
                    }
                    if (key.isWritable()){
                        SocketChannel client = (SocketChannel)key.channel();
                        ByteBuffer buffer  = (ByteBuffer) key.attachment();
                        while(buffer.hasRemaining()){
                            if (client.write(buffer) ==0){
                                break;
                            }
                        }
                        client.close();
                    }
                }catch (IOException ex){
                    key.cancel();
                    try{
                        key.channel().close();
                    }catch (IOException cex){}
                }
            }
        }
    }
}
