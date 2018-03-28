package com.chenjf.web.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by chenjf on 2017-09-28.
 */
public class SocketServer {

    public static void main(String[] args) {
        ServerSocket myService;
        Socket clientSocket = null;
        try {
            myService = new ServerSocket(7676);
            Socket socket = myService.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
