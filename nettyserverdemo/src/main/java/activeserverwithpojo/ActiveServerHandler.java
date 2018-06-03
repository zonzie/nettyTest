package activeserverwithpojo;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zonzie
 * @date 2018/4/9 9:24
 */
public class ActiveServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User("张三", "123");
        ChannelFuture channelFuture = ctx.writeAndFlush(user);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
}
