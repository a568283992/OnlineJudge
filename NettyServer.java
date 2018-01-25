package cn.maven.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyServer {
    private static final int port = 8080;
    public void start() throws InterruptedException {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)//设置nio类型的Channel
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {//有连接时创建一个channel
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //pipeline管理channel中的handler，在channel队列中添加一个handler处理业务
                    socketChannel.pipeline().addLast(new ServerHandler());
                }
            });
            ChannelFuture future = serverBootstrap.bind(port).sync();// 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
            System.out.println(NettyServer.class.getName()+"started and listen on "+future.channel().localAddress());
            future.channel().closeFuture().sync();// 应用程序会一直等待，直到channel关闭
        }catch (Exception e){

        }finally {
            boosGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServer().start();
    }
}
