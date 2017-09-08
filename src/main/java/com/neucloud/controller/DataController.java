package com.neucloud.controller;

import com.neucloud.dao.TimeWindRepository;
import com.neucloud.entity.TimeWind;
import com.neucloud.web.ClientCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by jizhong on 2017/9/7.
 */

@Controller
@RequestMapping("/data")
public class DataController {

    @Resource(name = "timeWindRepository")
    private TimeWindRepository timeWindRepository;

    @Resource(name = "clientCount")
    private ClientCount clientCount;

    @GetMapping("/top1000")
    @ResponseBody
    public List<TimeWind>  first1000Data(){
        return timeWindRepository.findTop1000();
    }

    private int thound = 1000;

    @GetMapping("/nextOne")
    @ResponseBody
    public List<TimeWind> nextOne(int offset){
        return timeWindRepository.findNext(thound + offset);
    }

    @Autowired
    private TaskScheduler scheduler;

    @MessageMapping("/initTrans")
    public void initTrans(){
        if(clientCount.beforePlus() == 0){
            ScheduledFuture scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                }
            }, 7000);
            clientCount.setScheduledFuture(scheduledFuture);
        }
    }



}
