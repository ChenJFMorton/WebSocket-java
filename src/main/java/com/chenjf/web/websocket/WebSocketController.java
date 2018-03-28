package com.chenjf.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenjf on 2018-01-16.
 */
@Controller
@RequestMapping("/webSocket")
@MessageMapping("foo")
public class WebSocketController {
    @MessageMapping("handle")
    @SendTo("/topic/greetings")
    public String handle(String name) {
        //...
        return "handle2";
    }
}
