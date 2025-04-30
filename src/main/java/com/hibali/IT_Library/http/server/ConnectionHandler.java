package com.hibali.IT_Library.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import com.hibali.IT_Library.Application.ApplicationContext;
import com.hibali.IT_Library.http.server.exceptions.InvalidHttpRequestException;
import com.hibali.IT_Library.http.server.exceptions.InvalidHttpResponseException;
import com.hibali.IT_Library.http.server.responses.BaseResponse;
import com.hibali.IT_Library.http.server.responses.HttpResponseFactory;

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
    private final ApplicationContext applicationContext;
    public ConnectionHandler(Socket socket,ApplicationContext context) {
        this.socket = socket;
        this.applicationContext = context;
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
                // we give the request to the router class to see how it should be routed to a controller method
                //had ster ktebto mais mazal madert had lkhedma 
                //how about giving the router class a data class routes that maps urls end points to controllers + specifique methods like laravel
                //Router class should maybe to have a method that returns an HttpResponse to be send
                Router router = new Router(request, applicationContext);
                //router method will return a response (not http response, IResponse are returned but the underlying controller)
                BaseResponse response = router.resolve();
                HttpResponse httpResponse = HttpResponseFactory.fromResponse(response);
                //instantiate the writer and write the response
                HttpResponseWriter httpResponseWriter = new HttpResponseWriter(httpResponse, out);
                httpResponseWriter.write();
            } catch (InvalidHttpRequestException | InvalidHttpResponseException e) { //after fix this router.resolve should throws its exception and be cached here
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
