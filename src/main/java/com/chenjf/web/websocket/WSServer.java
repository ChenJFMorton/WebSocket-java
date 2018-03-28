package com.chenjf.web.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by chenjf on 2018-01-16.
 */

//ws://127.0.0.1:8087/Demo1/ws/张三
@ServerEndpoint("/ws/{user}")
public class WSServer {

    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final Map<String,Object> connections = new HashMap<>();

    private final String nickname;
    private Session session;

    public WSServer() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }

    private String currentUser;

    //连接打开时执行
    @OnOpen
    public void onOpen(@PathParam("user") String user, Session session) {
        currentUser = user;
        System.out.println("Connected ... " + session.getId());

        this.session = session;
        connections.put(nickname, this);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }

    //收到消息时执行
//    @OnMessage
//    public String onMessage(String message, Session session) {
//        System.out.println(currentUser + "：" + message);
//        return currentUser + "：" + message;
//    }

    //收到消息时执行
    @OnMessage
    public void onMessage(String message, Session session) {
        // Never trust the client
        String filteredMessage = String.format("%s: %s",
                nickname, HTMLFilter.filter(message.toString()));
        broadcast(filteredMessage);
    }

    //连接关闭时执行
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    //连接错误时执行
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    /**
     * 消息发送方法
     * @param msg
     */
    private static void broadcast(String msg) {
        if(msg.indexOf("Guest0")!=-1){
            sendUser(msg);
        } else{
            sendAll(msg);
        }
    }

    /**
     * 向所有用户发送
     * @param msg
     */
    public static void sendAll(String msg){
        for (String key : connections.keySet()) {
            WSServer client = null ;
            try {
                client = (WSServer) connections.get(key);
                synchronized (client) {
                    client.session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                connections.remove(client);
                try {
                    client.session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                        client.nickname, "has been disconnected.");
                broadcast(message);
            }
        }
    }



    /**
     * 向指定用户发送消息
     * @param msg
     */
    public static void sendUser(String msg){
        WSServer c = (WSServer)connections.get("Guest0");
        try {
            c.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            connections.remove(c);
            try {
                c.session.close();
            } catch (IOException e1) {
                // Ignore
            }
            String message = String.format("* %s %s",
                    c.nickname, "has been disconnected.");
            broadcast(message);
        }
    }
}
