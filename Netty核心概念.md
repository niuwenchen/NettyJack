## Netty核心概念
一个Netty程序开始于Bootstrap类，Bootstrap类是Netty提供的一个可以通过简单配置来设置会引导程序的一个很重要的类。
Netty中设计了Handlers来处理特定的“event”和设置Netty中的事件，从而来处理多个协议和数据。
事件可以描述成一个非常通用的方法，可以自定义一个handler，处理各种事情。

经常实现一个ChannelInboundHandler的类，ChannelInboundHandler是用来接受消息，就是业务处理逻辑。当程序需要返回消息时
可以在ChannelInboundHandler的write/flush数据 ????  如何返回消息

Netty连接客户端或绑定服务器需要知道如何发送或接收消息，通过不同类型的handlers来做的，多个Handlers怎么配置?
Netty提供了ChannelInitializer类来配置Handlers。ChannelInitializer是通过ChannelPipline来添加ChannelHandler的,
ChannelInitializer自身也是一个ChannelHandler，在添加完其他的handlers之后会自动从ChannelPipeline中删除自己。

Netty的所有IO操作都是异步执行的，例如你连接一个主机默认是异步完成的；写入/发送消息也同样是异步完成的。也就是说操作
不会直接执行，而是会等一会执行，因为你不知道返回的操作结果是成功还是失败，但是需要有检查是否成功的方法或是注册监听来通知。
Netty使用Futures的ChannelFuture来达到这种目的。注册一个监听，当操作成功或者失败时会通知，Channelfuture封装的是一个操作相关的信息，
操作被执行时会立刻返回ChannelFuture。

典型的Channelfuture是一个占位符

    可以使用ChannelHandler做下面事情：
    1. 传输数据时，将数据从一种格式转换为另一种格式
    2. 异常通知
    3. Channel变为有效或无效时获取通知
    4. Channel被注册或从EventLoop中注销时获取通知
    5. 获取用户特定事件.
    
BootStrap和ServerBotstrap，创建一个client或者server需要的程序

    ServerBotstrap 绑定到本地  2   以本地环境作为服务器
    Bootstrap      绑定到远程  1
### 3.4Channel Handlers and DataFlow
 outbound(出站): 从用户到远程
 inbound(入站):   从远程到用户
 总感觉是反的。
 
 
 