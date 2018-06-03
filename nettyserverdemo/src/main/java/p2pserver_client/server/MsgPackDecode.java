package p2pserver_client.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * 解码工具
 * @author zonzie
 * @date 2018/4/10 19:05
 */
public class MsgPackDecode extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int i = msg.readableBytes();
        byte[] array = new byte[i];
        msg.getBytes(msg.readerIndex(),array,0,i);
        out.add(new MessagePack().read(array,IMMessage.class));
    }
}
