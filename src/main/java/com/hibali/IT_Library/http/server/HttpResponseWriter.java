package com.hibali.IT_Library.http.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//this class is responsible for wrting the http response on the socket output stream
public class HttpResponseWriter {
    private final HttpResponse httpResponse;
    private OutputStream socketOut;

    public HttpResponseWriter(HttpResponse httpResponse, OutputStream socketOutputStream) {
        this.httpResponse = httpResponse;
        this.socketOut = socketOutputStream;
    }

    public void write() {
        try {
            // buffering the output and wrape it in a reader
            BufferedOutputStream out = new BufferedOutputStream(this.socketOut);
            OutputStreamWriter writer = new OutputStreamWriter(this.socketOut, StandardCharsets.UTF_8);

            // write the first header line (http version, response code and response
            // message)
            String httpVersion = this.httpResponse.getHttpVersion();
            int responseCode = this.httpResponse.getResponseCode();
            String responseMessage = this.httpResponse.getResponseMessage();
            writer.write(httpVersion + " " + responseCode + " " + responseMessage + "\r\n");

            // writing the rest of the header
            Map<String, String> headers = this.httpResponse.getHeaders();
            for (String key : headers.keySet()) {
                writer.write(key + ": " + headers.get(key) + "\r\n");
            }
            writer.write("\r\n"); // end of header

            if (this.httpResponse.getData() != null && this.httpResponse.getData().length > 0) {
                writer.flush(); // send/flush the header first before starting with the body
                out.write(this.httpResponse.getData());
            }
            out.flush();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
