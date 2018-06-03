package p2pserver_client.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.Scanner;

/**
 * @author zonzie
 * @date 2018/4/10 19:35
 */
public class Server implements Runnable,IMConfig {

    private ServerHandler serverHandler = new ServerHandler();

    public static void main(String[] args) {
        new Server().start();
    }

    private void start() {
        new Thread(this).start();
        runServerCMD();
    }

    private void runServerCMD() {
        // 服务端主动推动消息
        int toID = 1;
        IMMessage message = new IMMessage(
                APP_IM,
                CLIENT_VERSION,
                SERVER_ID,
                TYPE_MSG_TEXT,
                toID,
                MSG_EMPTY
        );
        Scanner scanner = new Scanner(System.in);
        do {
            message.setMsg(scanner.nextLine());
        } while (serverHandler.sendMsg(message));
    }

    @Override
    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65536,0,2,0,2));
                            ch.pipeline().addLast("msgpack decoder",new MsgPackDecode());
                            ch.pipeline().addLast("frameDecoder",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder",new MsgPackEncode());
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f = b.bind(SERVER_PORT).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
