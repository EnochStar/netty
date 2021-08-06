package server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * Package:server
 * Description:
 *
 * @author:鲍嘉鑫
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * @param ctx
     * @param msg
     * @throws Exception
     * 对于每个传入的消息都要调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received:" + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    /**
     * @param ctx
     * @throws Exception
     * 通知ChannelInboundHandler最后一次对channelRead的调用是当前批量读取的最后一条消息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将未决消息冲刷到远程节点，并关闭该Channel
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
