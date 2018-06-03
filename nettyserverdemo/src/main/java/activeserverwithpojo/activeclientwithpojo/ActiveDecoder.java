package activeserverwithpojo.activeclientwithpojo;

import activeserverwithpojo.User;
import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zonzie
 * @date 2018/4/9 9:57
 */
public class ActiveDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4) {
            return;
        }

//        JSON.parseObject(,User.class);
//        out.add();
    }
}
