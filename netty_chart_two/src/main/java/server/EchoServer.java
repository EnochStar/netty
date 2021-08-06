package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Package:server
 * Description:
 *
 * @author:鲍嘉鑫
 */
public class EchoServer {
    private static final int DEFAULT_PORT = 9999;

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public EchoServer() {
        this(DEFAULT_PORT);
    }

    public void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 用于接收和处理连接
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 每当有一个连接接入 就创建一个channel 然后将一个EchoServerHandler实例添加到该channel的pipeline中
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定，采用sync阻塞等待直到完成
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listening for connections on " + future.channel().localAddress());

            //sync即等待异步执行完成，如果没有sync 那么main线程执行到这句后 由于是异步操作 会直接走到finally
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
        EchoServer echoServer = new EchoServer();
        echoServer.start();
    }
}
