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
 *  https://blog.csdn.net/qq_37909508/article/details/91127882
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
        /* 1.初始化启动器 */
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        /* 2.通过group()方法把 boss线程和work线程设置进去 */
        serverBootstrap.group(boosGroup, workGroup);
        /*
         * 3.设置channel工厂
         *       传入 ReflectiveChannelFactory#newChannel --> (Channel)this.clazz.getConstructor().newInstance()
         * */
        serverBootstrap.channel(NioServerSocketChannel.class);
        /* handler处理服务端逻辑：Handler添加、channel被注册 等等 */
        //.handler("")
        //.childOption()
        //.childAttr()
        /*
         * 4.设置pipeline
         *    childHandler处理连接的处理：TCP属性、接收缓冲区、发送缓冲区 等等
         * */
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
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
        /*
         * 5.初始化和注册
         *    bind --> dobind  -->
         *      initAndRegister 调用channelFactory初始化channel实例
         *      ChannelFuture 异步IO的结果：
         *          所有IO都是异步的,意味着调用就会立马返回,就不能保证IO操作是已经完成的,并且返回一个IO操作状态的结果
         *          两种状态：完成状态和未完成状态
         *              新的I/O操作是一种初始化未完成的状态
         *              如果I/O操作是完成finish状态(成功执行、或是失败、或是取消)，该future会被标记这一些指定的信息，例如返回的失败原因。
         *          它允许你去增加ChannelFutureListenner，以至于你能够在I/O操作完成后，接收到通知
         */
        ChannelFuture channelFuture = serverBootstrap.bind(IP, port).sync();

        //注册一个监听器，等待关闭事件
        channelFuture.channel().closeFuture(). sync();
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
