package com.hibali.IT_Library.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.hibali.IT_Library.http.server.HttpRequest.HttpRequestBuilder;
import com.hibali.IT_Library.http.server.exceptions.InvalidHttpRequestException;

public class HttpRequestParser {
    
    private InputStream inputStream;
    private final HttpRequestBuilder httpRequestBuilder = HttpRequest.newBuilder();
    public HttpRequestParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public HttpRequest parse() throws InvalidHttpRequestException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            // first line should include the http method, resource path, and http version!
            line = in.readLine();
            getProperties(line);
            // reading the rest of the http header (reading the key value pairs properties)

            while (true) {
                line = in.readLine();
                if (line == null || line.equals("\r\n") || line.isEmpty()) {
                    break;
                }
                //spliting the key value pairs and passing them to the http request builder, very nice si hicham(fkhater lehcen)
                if(line.indexOf(":") < 0){ //cheking for invalid key value pairs
                    throw new InvalidHttpRequestException("Invalid http header : "+line);
                }
                String key = line.substring(0, line.indexOf(":")).trim();
                String value = line.substring(line.indexOf(":")+1).trim();
                this.httpRequestBuilder.header(key,value);
            }
        } catch (IOException e) {
            System.out.println("Something went wrong while reading the resquest header " + e);
        }
        return this.httpRequestBuilder.build();
    }

    private void getProperties(String line) throws InvalidHttpRequestException {
        String[] elements = line.split(" ");
        // Check if there are at least 3 parts: Method, Path, Version
        if (elements.length < 3) {
            throw new InvalidHttpRequestException(
                    "Invalid request line format: insufficient parts. Line: '" + line + "'");
        }
        // building
        this.httpRequestBuilder.method(elements[0]).resourcePath(elements[1]).version(elements[2]);
    }
}
