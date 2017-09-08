package com.neucloud.web;

import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by wangcj on 2017/7/4.
 */

@Component("clientCount")
public class ClientCount {

    private int count = 0;

    private ScheduledFuture scheduledFuture;

    public int beforePlus(){
        return count++;
    }

    public int afterPlus(){
        return ++count;
    }

    public int getCount(){
        return count;
    }

    public int beforeMinus(){
        return count--;
    }

    public int afterMinus(){
        return --count;
    }

    public void reset(){
        count = 0;
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    public static void main(String[] args){
        ClientCount clientCount = new ClientCount();
        System.out.println(clientCount.beforePlus() == 0);
        System.out.println(clientCount.afterPlus() == 2);
    }
}
