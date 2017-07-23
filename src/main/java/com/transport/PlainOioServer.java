package com.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by JackNiu on 2017/7/23.
 */
/*
* 不使用Netty io，工作没问题，但是阻塞模式有很大的问题
* 可以使用异步网络处理所有的并发连接，但问题在于NIO和OIO是完全不同的API
*
*
* */

public class PlainOioServer {
    public void  server(int port) throws Exception{
        final ServerSocket socket = new ServerSocket(port);
        try{
            while(true){
                //接受请求
                final Socket client = socket.accept();
                System.out.println("Accepted connection from:"+client);
                // 创建一个新的线程去处理连接程序
                 new Thread(new Runnable() {
                     public void run() {
                         OutputStream out;
                         try{
                             out= client.getOutputStream();
                             out.write("Hi\r\n".getBytes());
                             out.flush();
                             client.close();
                         }catch(IOException e){
                            try{
                                client.close();
                            }catch (Exception e1){
                                e1.printStackTrace();
                            }
                         }
                     }
                 }).start();


            }
        }catch (Exception e){
            e.printStackTrace();
            socket.close();
        }
    }
}
