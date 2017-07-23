##channelHandler
消息的自动释放，ChannelInboundHandler 不会自动释放消息，可以手动释放

    @Sharable
    public class DiscardHandler extends ChannelInboundHandlerAdapter {
    
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ReferenceCountUtil.release(msg);
        }
    
    }
    也可以在read完成之后调用别的方法释放:
    @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            //将所有消息发送到远程，当这个操作完成之后关闭这个channel
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
                    ChannelFutureListener.CLOSE
            );
        }
     
    而SimpleChannelHandler则可以自动释放消息
    
    @Sharable
    public class SimpleDiscardHandler
        extends SimpleChannelInboundHandler<Object> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
            Object msg) {
            // No need to do anything special
        }
    }
    
    