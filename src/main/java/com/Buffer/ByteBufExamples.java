package com.Buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Created by JackNiu on 2017/7/23.
 */
public class ByteBufExamples {
    private final static Random random = new Random();
    // 创建一个ByteBuf
    private static  final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static  final ByteBuf DIRECT_BYTE_BUF_FROM_SOMEWHERE = Unpooled.directBuffer(1024);
    public static void main(String[] args) {
//        heapBuffer();
//        directBuffer();
        byte[] header=new byte[128];
        for (int i=0;i<header.length;i++){
            header[i]=(byte) i;
        }

        byte[] message=new byte[128];
        for (int i=0;i<message.length;i++){
            message[i]=(byte) i;
        }

//        byteBufferComposite(ByteBuffer.wrap(header),ByteBuffer.wrap(message));
        byteBufCopy();
    }


    public  static void heapBuffer(){
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;

        for (int i = 0; i < heapBuf.capacity(); i ++) {
           heapBuf.writeByte(random.nextInt());
        }


        if (heapBuf.hasArray()){
            System.out.println(heapBuf.readableBytes());
            byte[] array=heapBuf.array();
            System.out.println(heapBuf.arrayOffset());
            int offset = heapBuf.arrayOffset()+heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println(offset+"->"+length+"-->"+array.length);
//            handleArray(array,offset,length);
        }
    }

    // direct access data
    public static void directBuffer() {
        ByteBuf directBuf = DIRECT_BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        System.out.println(directBuf.hasArray());
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            System.out.println(length);
            byte[] array = new byte[length];

            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println(directBuf.readerIndex());
            handleArray(array, 0, length);

        }
    }

    /**
     * Listing 5.3 Composite buffer pattern using ByteBuffer
     */
    public static void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        // Use an array to hold the message parts
        ByteBuffer[] message =  new ByteBuffer[]{ header, body };

        // Create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer message2 =
                ByteBuffer.allocate(header.remaining() + body.remaining());
        message2.put(header);
        message2.put(body);
        message2.flip();
        System.out.println(message2);
        System.out.println(message2.toString());
//        for (int i=0;i<message2.capacity();i++){
//
//        }
    }


    /**
     * Listing 5.4 Composite buffer pattern using CompositeByteBuf
     */
    public static void byteBufComposite() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE; // can be backing or direct
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;   // can be backing or direct
        messageBuf.addComponents(headerBuf, bodyBuf);
        messageBuf.addComponents(headerBuf, bodyBuf);

        messageBuf.addComponents(headerBuf, bodyBuf);
        messageBuf.addComponents(headerBuf, bodyBuf);
        System.out.println(messageBuf.readableBytes());
        messageBuf.removeComponent(0); // remove the header
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString());
        }
    }

    /**
     * Listing 5.5 Accessing the data in a CompositeByteBuf
     */
    public static void byteBufCompositeArray() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer(128);
        int length = compBuf.readableBytes();
        System.out.println(length);
        byte[] array = new byte[length];
        compBuf.getBytes(compBuf.readerIndex(), array);
        handleArray(array, 0, array.length);
    }



    private static void handleArray(byte[] array, int offset, int len) {
        System.out.println(offset+"->"+len);
        for (int i = 0; i < array.length; i ++) {
            byte b = array[i];
            System.out.println((char) b);

        }

    }

    /**
     * Listing 5.6 Access data
     */
    public static void byteBufRelativeAccess() {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere

        while (buffer.writableBytes() >= 4) {
            buffer.writeInt(random.nextInt());
        }


//        for (int i = 0; i < buffer.capacity(); i++) {
//            byte b = buffer.getByte(i);
//            System.out.println(b);
//        }
        System.out.println(buffer.isReadable());
        System.out.println(buffer.readerIndex());
        System.out.println(buffer.readableBytes());
        System.out.println(buffer.writerIndex());
        while (buffer.isReadable()) {
            System.out.print(buffer.readByte()+",");
//            buffer.readByte();
        }
        System.out.println(buffer.readerIndex());
        System.out.println(buffer.readableBytes());

    }

    /**
     * Listing 5.10 Slice a ByteBuf
     */
    public static void byteBufSlice() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf in = (ByteBuf) buf;
        System.out.println(in.toString(utf8));
        ByteBuf sliced = buf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte)'J');
        System.out.println(buf.getByte(0) == sliced.getByte(0));
    }
    /*
    * copy 和slice 是有不同表现的
    * copy 不共享，源改变之后，copyd  是不会改变的
    * slice 共享， 源改变之后，sliced 是会随之改变
    * */

    public static void byteBufCopy() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 15);
        System.out.println(copy.toString(utf8));
        buf.setByte(0, (byte)'J');
        System.out.println(buf.getByte(0) ==copy.getByte(0));
    }


}
