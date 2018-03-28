package com.chenjf.web.listener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * http://www.voidcn.com/article/p-pfavmned-tc.html
 * Created by chenjf on 2017-09-30.
 */
public class Comet4jListener implements ServletContextListener {
    private static final String CHANNEL ="hello";
    public void contextInitialized(ServletContextEvent arg0){
        CometContext cc = CometContext.getInstance();
        cc.registChannel(CHANNEL);//注册应用的channel
        Thread helloAppModule =new Thread(new HelloAppModule(),"Sender App Module");
        helloAppModule.setDaemon(true);
        helloAppModule.start();

    }

    class HelloAppModule implements Runnable {
        public void run(){
            while(true){
                try{
                    Thread.sleep(1000);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                CometEngine engine =CometContext.getInstance().getEngine();
                engine.sendToAll(CHANNEL,Runtime.getRuntime().freeMemory()/1024);
            }
        }
    }

    public void contextDestroyed(ServletContextEvent arg0){

    }
}
