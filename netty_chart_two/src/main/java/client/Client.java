package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Package:client
 * Description:
 *
 * @author:鲍嘉鑫
 */
public class Client {
    private int port;
    private String localhost;

    private static final int DEFAULT_PORT = 9999;
    private static final String DEFAULT_LOCALHOST = "localhost";

    public Client() {
        this(DEFAULT_PORT,DEFAULT_LOCALHOST);
    }

    public Client(int port, String localhost) {
        this.port = port;
        this.localhost = localhost;
    }

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .remoteAddress(new InetSocketAddress(localhost,port))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
