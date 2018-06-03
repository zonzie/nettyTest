package activeserverwithpojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author zonzie
 * @date 2018/4/9 9:27
 */
public class ActiveEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        User m = (User) msg;
        ByteBuf buffer = ctx.alloc().buffer(4);
        byte[] bytes = JSONObject.toJSONBytes(m);
        buffer.writeBytes(bytes);
        ctx.write(buffer,promise);
    }
}
