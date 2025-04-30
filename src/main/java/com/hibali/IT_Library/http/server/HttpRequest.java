package com.hibali.IT_Library.http.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hibali.IT_Library.http.server.exceptions.InvalidHttpRequestException;

public record HttpRequest(
        String method,
        String resourcePath, // Renamed from ressourcePath for standard spelling
        String httpVersion,
        Map<String, String> headers, // Use Map interface
        byte[] data // Request body data
) {

    // Static final lists for supported methods and versions
    private static final List<String> SUPPORTED_METHODS = List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS",
            "PATCH");
    private static final List<String> SUPPORTED_HTTP_VERSIONS = List.of("HTTP/1.0", "HTTP/1.1");

    public HttpRequest {
        // Validation - check for null or invalid values upon construction
        if (method == null || !SUPPORTED_METHODS.contains(method)) {
            throw new IllegalArgumentException("Invalid or unsupported HTTP method: " + method);
        }
        if (resourcePath == null || resourcePath.isEmpty()) {
            if (resourcePath == null)
                throw new IllegalArgumentException("Resource path cannot be null");
        }
        if (httpVersion == null || !SUPPORTED_HTTP_VERSIONS.contains(httpVersion)) {
            throw new IllegalArgumentException("Unsupported http version or absent: " + httpVersion);
        }
        if (headers == null) {
            throw new IllegalArgumentException("Headers map cannot be null");
        }
        headers = Collections.unmodifiableMap(new HashMap<>(headers));
        data = (data == null) ? null : data.clone();
    }

    //getters 
    public String getHeader(String name) { //get a signle header by name if exists
        return headers.get(name);
    }
    public String getRessourcePath(){
        return this.resourcePath;
    }
    public String getHttpVersion(){
        return this.httpVersion;
    }
    public Map<String,String> getHeaders(){
        return this.headers;
    }
    public String getMethod(){
        return this.method;
    }

    public String toString() {
        return "Method : " + this.method + ", ResourcePath: " + this.resourcePath + ", HttpVersion: " + this.httpVersion
                +
                "\r\nheaders: \r\n" + this.headers.toString();
    }

    public static class HttpRequestBuilder {
        // Mutable fields to hold the state during building
        private String method;
        private String resourcePath;
        private String httpVersion;
        private Map<String, String> headers = new HashMap<>();
        private byte[] data;

        // Builder methods - they set state on the builder and return the builder
        // ('this')
        public HttpRequestBuilder method(String method) {
            this.method = method;
            return this; // Return builder for chaining
        }

        public HttpRequestBuilder resourcePath(String resourcePath) {
            this.resourcePath = resourcePath;
            return this;
        }

        public HttpRequestBuilder version(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public HttpRequestBuilder header(String name, String value) {
            // Add individual headers
            if (name != null && value != null) { // Basic check
                this.headers.put(name, value);
            }
            return this;
        }

        public HttpRequestBuilder data(byte[] data) {
            this.data = data;
            return this;
        }

        // The build() method - creates the final, immutable HttpRequest record
        public HttpRequest build() throws InvalidHttpRequestException {
            try {
                return new HttpRequest(
                        this.method, this.resourcePath, this.httpVersion, this.headers, this.data);
            } catch (IllegalArgumentException e) {
                throw new InvalidHttpRequestException(e.getMessage());
            }
        }
    }

    // --- Static factory method to get a new builder instance ---
    public static HttpRequestBuilder newBuilder() {
        return new HttpRequestBuilder();
    }
}
