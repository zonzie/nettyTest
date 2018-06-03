package p2pserver_client.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zonzie
 * @date 2018/4/10 19:22
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端handler创建...");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        System.out.println("有客户端连接:" + ctx.channel().remoteAddress().toString());
    }

    public boolean sendMsg(IMMessage msg) {
        System.out.println("服务器推送消息:" + msg);
        ctx.writeAndFlush(msg);
        return !"q".equals(msg.getMsg());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器接收到的消息:" + msg);
        IMMessage message = (IMMessage) msg;
        if(OnlineUser.get(message.getReceiveId()) == null) {
            OnlineUser.put(message.getUid(),ctx);
        }
        ChannelHandlerContext c = OnlineUser.get(message.getReceiveId());
        if(c == null) {
            message.setMsg("对方不在线!");
            OnlineUser.get(message.getUid()).writeAndFlush(message);
        } else {
            c.writeAndFlush(message);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("与客户端断开连接:" + cause.getMessage());
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
