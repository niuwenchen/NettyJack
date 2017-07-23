##缓冲
ByteBuf  字节容器
ByteBufHolder
    
    Netty使用reference-counting(引用计数)的时候知道安全释放Buf和其他资源。虽然知道Netty
    有效的使用引用计数都是自动完成的。这允许Netty使用池和其他技巧来加快速度和保持内存利用率在正常水平，
    不需要做任何事情来实现这一点。
   
    ByteBuf-字节数据容器
    字节码数据传输。ByteBuf是一个经过优化的数据容器，一个用于读，一个用于写。
    
    
    Unpooled
    创建缓冲区的工具类
    CompositeBuf compBuf = Unpooled.compositeBuffer();
    // 创建堆缓冲区
    ByteBuf heapBuf = Unpooled.buffer(8)
    // 创建直接缓冲区
    ByteBuf directBuf = Unpooled.directBuffer(16)
    