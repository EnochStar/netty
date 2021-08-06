import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package:PACKAGE_NAME
 * Description:
 *
 * @author:鲍嘉鑫
 */
public class AbsIntegerEncoderTest {
    @Test
    public void testEncoded() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        // 创建一个 EmbeddedChanel，并安装一个要测试的 AbsIntegerEncoder
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        // 写入 ByteBuf，调用 readOutbound() 方法将会产生数据
        System.out.println(channel.writeOutbound(buf));
        System.out.println(channel.finish());

        for (int i = 1; i < 10; i++) {
            int temp = channel.readOutbound();
            System.out.println(temp);
        }
        System.out.println(channel.readOutbound() == null);
    }
}