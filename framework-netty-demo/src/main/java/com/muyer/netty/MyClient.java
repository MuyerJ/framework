package com.muyer.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Description: 
 * date: 2021/8/1 23:36
 * @author YeJiang
 * @version
 */
public class MyClient extends ChannelInboundHandlerAdapter {


    /**
     *
     * @param ctx  channel的上下文
     * @param msg  传递的消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive msg: " + msg);
        ctx.channel().writeAndFlush("accept msg:" + msg);
        //关掉一些启动的资源
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client get error :" + cause.getMessage());
    }
}
