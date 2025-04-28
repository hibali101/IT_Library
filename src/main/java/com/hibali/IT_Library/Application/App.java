package com.hibali.IT_Library.Application;

import com.hibali.IT_Library.http.server.HttpServer;

public class App {
    public static void main(String[] args) {
        ApplicationContext appContext = new ApplicationContext();
        HttpServer server = new HttpServer(appContext);
        server.start();
    }
}
