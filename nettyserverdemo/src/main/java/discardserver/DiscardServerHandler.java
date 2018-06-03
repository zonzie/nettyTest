package discardserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @author zonzie
 * @date 2018/4/8 15:54
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 丢弃收到的数据 discardServer
//        ((ByteBuf) msg).release();
        // 打印数据 让discardServer 打印数据
        ByteBuf in = (ByteBuf) msg;
        try {
//            while (in.isReadable()) {
//                System.out.println((char) in.readByte());
//                System.out.flush();
//            }
            // 循环可以被替代
            System.out.println(in.toString(CharsetUtil.US_ASCII));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
