import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Package:PACKAGE_NAME
 * Description:
 *
 * @author:鲍嘉鑫
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFixedLengthFrameDecoder() throws IllegalAccessException {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));
        assertTrue(embeddedChannel.finish());

        // read messages
        ByteBuf read = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        read = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(buf.readSlice(3), read);
        read.release();

        assertNull(embeddedChannel.readInbound());
        buf.release();
    }
}