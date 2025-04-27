package com.hibali.IT_Library.http.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hibali.IT_Library.utilities.LibLogger;
public final class HttpServer {
    //server configuration
    private static final LibLogger LOGGER = LibLogger.getSevereLogger();
    public static final int PORT = 8080;
    public static final int MAX_WAITING_QUEUE = 5000;
    public static final int MAX_THREADS = 400;
    
    //private constructor so far
    private HttpServer(){}

    //server start from this method
    public static void start(){
        ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);
        try(ServerSocket serverSocket = new ServerSocket(PORT, MAX_WAITING_QUEUE)){
            System.out.println("Server listening on "+serverSocket.getLocalSocketAddress());
            while(true){
                try{
                    Socket connectionSocket = serverSocket.accept();
                    ConnectionHandler handler = new ConnectionHandler(connectionSocket);
                    pool.submit(handler);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            LOGGER.log("SERVER SOCKET FAILED TO OPEN! "+e.getMessage());
        }
    }
}
