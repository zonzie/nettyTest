package chatclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zonzie
 * @date 2018/4/9 10:38
 */
public class SimpleChatClient {
    public void run(String host,int port) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer());
            ChannelFuture f = b.connect(host, port).sync();
            Channel channel = f.channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String s = in.readLine();
                if("quit".equals(s)) {
                    break;
                }
                channel.writeAndFlush(s + "\r\n");
//                channel.writeAndFlush("123321占山");
            }
//            f.channel().closeFuture().sync();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleChatClient simpleChatClient = new SimpleChatClient();
        simpleChatClient.run("127.0.0.1",8080);
    }
}
