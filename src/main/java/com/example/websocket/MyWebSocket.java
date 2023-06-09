package com.example.websocket;

/**
 * @ClassName MyWebSocket
 * @Description:
 * @Author 刘苏义
 * @Date 2023/5/10 20:18
 * @Version 1.0
 */
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/websocket")
public class MyWebSocket {
    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @paramsession
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        MySessionCollection.sessions.add(session);
        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入！当前在线人数为"+ webSocketSet.size());
        try {
            sendMessage("连接成功，当前时间：" + new java.sql.Timestamp(System.currentTimeMillis()));
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
         //从set中删除
        webSocketSet.remove(this);
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @parammessage 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        // 群发消息
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @paramsession
     * @paramerror
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @parammessage
     * @throwsIOException
     */
    public void sendMessage(Session session,String message) throws IOException {
        session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }
    /**
     * 发送消息
     *
     * @parammessage
     * @throwsIOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }
    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
