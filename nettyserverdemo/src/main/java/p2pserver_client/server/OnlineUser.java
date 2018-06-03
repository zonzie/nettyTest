package p2pserver_client.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

/**
 * 在线用户表
 * @author zonzie
 * @date 2018/4/10 19:17
 */
public class OnlineUser {
    /**
     * 所有的在线用户
     */
    private static HashMap<Integer,ChannelHandlerContext> onlineUser = new HashMap<>();

    public static void put(Integer uid,ChannelHandlerContext ctx) {
        onlineUser.put(uid,ctx);
    }

    public static void remove(Integer uid) {
        onlineUser.remove(uid);
    }

    public static ChannelHandlerContext get(Integer uid) {
        return onlineUser.get(uid);
    }
}
