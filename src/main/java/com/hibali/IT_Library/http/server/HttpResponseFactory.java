package com.hibali.IT_Library.http.server;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HttpResponseFactory {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private final OutputStream outputStream;
    private int responseCode;
    private String responseMessage;

    public HttpResponseFactory(OutputStream out, int responseCode, String responseMessage) {
        this.outputStream = out;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    private void respond(int contentLength, String mimeType, byte[] content) {

        try {
            BufferedOutputStream bout = new BufferedOutputStream(this.outputStream);
            OutputStreamWriter out = new OutputStreamWriter(bout, StandardCharsets.UTF_8);
            System.out.println("am here");
            out.write(HTTP_VERSION + " " + this.responseCode + " " + this.responseMessage + "\r\n");
            out.write("server: HichamServer\r\n");
            out.write("date: " + new Date().toString() + "\r\n");
            out.write("content-length: " + contentLength + "\r\n");
            out.write("content-type: " + mimeType + "\r\n");
            out.write("\r\n");
            if (content != null && content.length > 0) {
                out.flush(); // it is neccessary to flush to header before writing the response body
                bout.write(content);
            }
            bout.flush();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stringResponse(String response) {
        byte[] data = response.getBytes(StandardCharsets.UTF_8);
        System.out.println("content legnth is "+data.length);
        this.respond(data.length, "text/plain", data);
    }
}
