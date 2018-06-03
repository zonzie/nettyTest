package p2pserver_client.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import p2pserver_client.server.IMConfig;
import p2pserver_client.server.IMMessage;
import p2pserver_client.server.MsgPackEncode;

import java.util.Scanner;

/**
 * @author zonzie
 * @date 2018/4/10 19:53
 */
public class Client implements Runnable,IMConfig {
    public static int UID = 8889;
    public static int toID = 8888;
    private ClientHandler clientHandler = new ClientHandler();

    public void start() {
        new Client().start();
        runServerCMD();
    }

    public void sendMsg(IMMessage msg) {
        clientHandler.sendMsg(msg);
    }

    private void runServerCMD() {
        IMMessage message = new IMMessage(
                APP_IM,
                CLIENT_VERSION,
                UID,
                TYPE_MSG_TEXT,
                toID,
                MSG_EMPTY
        );
        Scanner scanner = new Scanner(System.in);
        do {
            message.setMsg(scanner.nextLine());
        } while (clientHandler.sendMsg(message));
    }

    @Override
    public void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65536,0,2,0,2))
                                    .addLast("msgpack decoder",new LengthFieldPrepender(2))
                                    .addLast("msgpack encoder",new MsgPackEncode())
                                    .addLast(clientHandler);
                        }
                    });
            ChannelFuture f = b.connect(SERVER_HOST, SERVER_PORT).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
