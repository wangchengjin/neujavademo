package com.neucloud.web;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT;

/**
 * Created by wangcj on 2017/9/8.
 */

@Component("presenceChannelInterceptor")
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    @Resource(name = "clientCount")
    private ClientCount clientCount;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);

        // ignore non-STOMP messages like heartbeat messages
        if (sha.getCommand() == null) {
            return;
        }
        if (sha.getCommand() == DISCONNECT) {
            if (clientCount.afterMinus() < 0) clientCount.reset();
            if (clientCount.getCount() == 0) {
                clientCount.getScheduledFuture().cancel(true);
                clientCount.setScheduledFuture(null);
            }

        }
    }

}
