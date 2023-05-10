package com.example.service;

import com.example.websocket.MySessionCollection;
import com.example.websocket.MyWebSocket;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName testService
 * @Description:
 * @Author 刘苏义
 * @Date 2023/5/10 20:22
 * @Version 1.0
 */
@Service
public class TestService {
    @Resource
    MyWebSocket myWebSocket;

    @Async("tesk")
    public void sendMsg() throws IOException {

        if(MyWebSocket.webSocketSet.size()>0)
        {
            for (MyWebSocket s:MyWebSocket.webSocketSet) {
                System.out.println("当前线程" + Thread.currentThread().getName());
                s.sendMessage(new Date().toString());
            }
        }
//        if(MySessionCollection.sessions.size()>0)
//        {
//            for (Session s:MySessionCollection.sessions) {
//                System.out.println("当前线程" + Thread.currentThread().getName() + ":" + s.getAsyncRemote());
//                myWebSocket.sendMessage(s,new Date().toString());
//            }
//        }
    }
}
