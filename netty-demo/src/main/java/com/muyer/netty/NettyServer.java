package com.muyer.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Description:
 *  ServerBootStrap#bind --> #doBind
 *                 #initAndRegister 初始化和注册
 *                      this.ChannelFactory#newChannel 创建服务端channel
 *                      ServerBootStrap#init
 *                          pipeline.addLast(
 *                          //添加连接器
 *                          //ch 传入进来的channel
 *                          //currentChildGroup     work线程组  ==> 连接进来的处理事件交给 work线程
 *                          //currentChildHandler   channelHandler
 *                          //currentChildOptions   连接属性
 *                          //currentChildAttrs     特定个性化属性
 *                          new ChannelHandler[]{
 *                              new ServerBootstrapAcceptor(ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs)});
 *
 *                              ServerBootstrapAcceptor -- > NIOServerSocketChannel
 *
 * date: 2021/7/31 22:20
 * @author YeJiang
 * @version
 */
public class NettyServer {

    private static final String IP = "127.0.0.1";
    private static final int port = 6666;
    /* 线程池大小：操作系统 2倍核数 */
    private static final int biz_group_size = Runtime.getRuntime().availableProcessors() * 2;
    private static final int biz_thread_size = 100;
    /* 初始化boss线程组 */
    private static final EventLoopGroup boosGroup = new NioEventLoopGroup(biz_group_size);
    /* 初始化work线程 */
    private static final EventLoopGroup workGroup = new NioEventLoopGroup(biz_thread_size);

    public static void start() throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /**
         * 1.通过group()方法把 boss线程和work线程设置进去
         * 2.
         *
         */
        serverBootstrap.group(boosGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                /* handler处理服务端逻辑：Handler添加、channel被注册 等等 */
                //.handler("")
                //.childOption()
                //.childAttr()
                /* childHandler处理连接的处理：TCP属性、接收缓冲区、发送缓冲区 等等 */
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        //从channel中拿出pipeline
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                        /* 自定义的一定要写在最后 */
                        pipeline.addLast(new TCPServerHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(IP, port).sync();

        //注册一个监听器，等待关闭事件
        channelFuture.channel().closeFuture().sync();
        System.out.println("Server start");
    }

    public static void shutdown() {
        boosGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("启动server");

        NettyServer.start();


    }
}
