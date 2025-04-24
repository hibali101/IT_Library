package com.hibali.IT_Library.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

//this dude here (or class as they call it) is responsible for getting the input/output streams
//from the socket then serve them to whowhever needs them, it is als responsible for closing 
//the spocket when its done
//my idea here is that users of the streams should not close them,
//but i dont know yet how to force this rule!   
//the chain of classes is like this :
////parse the request arriving from input then return an httpRequest record or fail
/// give this request to a router class (not yet implemented)
/// this router class is responsible for givin back an HttpResponse record
/// a responseWriter class will send the response
/// this dude will close the connection and its done
public class ConnectionHandler implements Callable<Void> {
    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public Void call() {
        try {
            //getting the in/out socket streams
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            try {
                //HttpRequestParser is the main reader of the request
                HttpRequestParser requestParser = new HttpRequestParser(in);
                //HttpRequest is a holder class of the request (a record to be specific not a mere class)
                HttpRequest request = requestParser.parse();
                // we give the request to the router class to see how it should be router to a controller method
                //had ster ktebto mais mazal madert had lkhedma 
                //how about giving the router class a data class routes that maps urls end points to controllers + specifique methods like laravel
                //Router class should maybe to have a method that returns an HttpResponse to be send
                HttpResponseFactory responseFactory = new HttpResponseFactory(out, 200, "OK");
                responseFactory.stringResponse(request.toString());
            } catch (InvalidHttpRequestException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //closing the connection
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
