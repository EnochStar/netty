import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Package:PACKAGE_NAME
 * Description:
 *
 * @author:鲍嘉鑫
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        while (in.readableBytes() >= 4) {
            int value = Math.abs(in.readInt());
            list.add(value);
        }
    }
}
