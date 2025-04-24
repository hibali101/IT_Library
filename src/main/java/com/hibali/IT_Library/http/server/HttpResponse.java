package com.hibali.IT_Library.http.server;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record HttpResponse(String httpVersion, int responseCode,
        Map<String, String> headers, byte[] data) {

    // static final definition of supported httpversions, response codes, and
    // response messages
    private static final List<String> SUPPORTED_HTTP_VERSIONS = List.of("HTTP/1.0", "HTTP/1.1");
    // keeping it simple a si hicham (fkhater lehcen)
    private static final Map<Integer, String> SUPPORTED_RESPONSE_CODE_MESSAGES = Map.ofEntries(
            Map.entry(200, "OK"),
            Map.entry(300, "Multiple Choices"),
            Map.entry(301, "Moved Permanently"),
            Map.entry(302, "Found"),
            Map.entry(400, "Bad Request"),
            Map.entry(404, "Not Found"),
            Map.entry(405, "Method Not Allowed"),
            Map.entry(500, "Internal Server Error"),
            Map.entry(501, "Not Implemented"));
    // checking constructor arguments that needs to be either supported or required
    public HttpResponse {
        if (!SUPPORTED_HTTP_VERSIONS.contains(httpVersion) || httpVersion == null) {
            throw new IllegalArgumentException("Invalid http version " + httpVersion);
        }
        if (SUPPORTED_RESPONSE_CODE_MESSAGES.get(responseCode) == null) {
            throw new IllegalArgumentException("Invalid http response code, or not supported " + responseCode);
        }
        if (headers == null) {
            throw new IllegalArgumentException(
                    "No headers provided to the response, must provide at least the default headers, see the response builder");
        }
        // when constrcuted i dont want you to miss with the headers,
        // so your reference now is yours but modifing it wont change the response
        // headers
        headers = Collections.unmodifiableMap(new HashMap<>(headers));
        data = (data == null) ? null : data.clone();
    }

    public String getResponseMessage() {
        return SUPPORTED_RESPONSE_CODE_MESSAGES.get(this.responseCode);
    }

    // nested class to be called without intantiating the record xd fkhater lhcen
    // again
    public static class HttpResponseBuilder {
        private String httpVersion;
        private int responseCode;
        private Map<String, String> headers = new HashMap<>();
        private byte[] data;

        public HttpResponseBuilder version(String version) {
            this.httpVersion = version;
            return this;
        }

        public HttpResponseBuilder responseCode(int responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public HttpResponseBuilder header(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public HttpResponseBuilder defaultHeaders() {
            this.headers.put("server", "hichamserv");
            this.headers.put("content-type", "application/json");
            this.headers.put("date", new Date().toString());
            if (this.data != null && this.data.length > 0) {
                this.headers.put("content-length", String.valueOf(this.data.length));
            }
            return this;
        }

        public HttpResponseBuilder data(byte[] data) {
            this.data = data;
            return this;
        }

        public HttpResponse build() throws InvalidHttpResponseException{
            try{
                return new HttpResponse(httpVersion, responseCode, headers, data);
            }catch(IllegalArgumentException e){
                throw new InvalidHttpResponseException(e.getMessage());
            }
        }
    }
}
