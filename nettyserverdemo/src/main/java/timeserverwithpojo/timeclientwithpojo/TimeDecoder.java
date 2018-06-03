package timeserverwithpojo.timeclientwithpojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import timeserverwithpojo.UnixTIme;

import java.util.List;

/**
 * @author zonzie
 * @date 2018/4/8 19:45
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4) {
            return;
        }
        out.add(new UnixTIme(in.readUnsignedInt()));
    }
}
