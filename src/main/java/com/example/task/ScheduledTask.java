package com.example.task;

import com.example.service.TestService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName ScheduledTask
 * @Description: 计划任务
 * @Author 刘苏义
 * @Date 2023/5/10 20:27
 * @Version 1.0
 */
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   //2.开启定时任务
public class ScheduledTask {
    @Resource
    TestService testService;
    //@Scheduled(cron = "0/1 * * * * ?")
    @Scheduled(fixedDelay =1000)
    public void run() throws IOException {
        testService.sendMsg();
    }
}
