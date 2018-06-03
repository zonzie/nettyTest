package heartbeat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * @author zonzie
 * @date 2018/4/10 14:53
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HeartBeat", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if(event.state() == IdleState.READER_IDLE) {
                type = "read idle";
            } else if(event.state() == IdleState.READER_IDLE){
                type = "write idle";
            } else if(event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
            }
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            System.out.println(ctx.channel().remoteAddress() + "超时类型" + type);
        } else {
            super.userEventTriggered(ctx,evt);
        }
    }
}
