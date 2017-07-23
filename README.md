## Netty

    1. 客户端连接到服务器
    2. 建立连接后，发送或接受数据
    3. 服务器处理所有的客户端连接
    
    编写服务器
    1. 配置服务器功能， 线程，端口等
    2. 实现服务器处理程序，包含业务逻辑
    
    通过ServerBootStrap对象启动服务器。
    过程：
        (1) 指定接受和处理新连接对象    NioEventLoopGroup，Netty内部通过线程处理数据，EventLoopGroup管理和调度，实际上是一个线程池
        (2) 指定通道类型                NioServerSocketChannel，服务器和客户端得以传输信息的通过，维护连接或IO操作
        (3) 设置InetSocketAddress       设置主线程，并启动
        (4) childhandler                处理所有的连接请求。
        
        

