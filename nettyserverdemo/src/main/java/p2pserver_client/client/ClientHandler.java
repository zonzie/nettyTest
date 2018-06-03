package p2pserver_client.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import p2pserver_client.server.IMConfig;
import p2pserver_client.server.IMMessage;

/**
 * @author zonzie
 * @date 2018/4/10 19:54
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements IMConfig {
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("成功连接服务器");
        this.ctx = ctx;
        IMMessage message = new IMMessage(
                APP_IM,
                CLIENT_VERSION,
                1,
                TYPE_CONNECT,
                SERVER_ID,
                MSG_EMPTY
        );
        sendMsg(message);
    }

    public boolean sendMsg(IMMessage message) {
        System.out.println("client:" + message);
        ctx.channel().writeAndFlush(message);
        return !"q".equals(message.getMsg());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMMessage m = (IMMessage) msg;
        System.out.println(m.getUid() + ":" + m.getMsg());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("与服务器断开连接:" + cause.getMessage());
        ctx.close();
    }
}
