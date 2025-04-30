package com.hibali.IT_Library.http.server.responses;

import com.hibali.IT_Library.http.server.HttpResponse;
import com.hibali.IT_Library.http.server.HttpResponse.HttpResponseBuilder;
import com.hibali.IT_Library.http.server.exceptions.InvalidHttpResponseException;

public class HttpResponseFactory {
    public static HttpResponse fromResponse(BaseResponse response) throws InvalidHttpResponseException {
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();
        httpResponseBuilder.defaultHeaders();
        if (response.getContentType() != null && response.getData().length > 0) { // check content type and length if
                                                                                  // present add them to the header
            httpResponseBuilder.header("content-type", response.getContentType())
                    .header("content-length", String.valueOf(response.getData().length));
            // also add data because its present
            httpResponseBuilder.data(response.getData());
        }
        //response code and version
        httpResponseBuilder.responseCode(response.getResponseCode());
        httpResponseBuilder.version(HttpResponse.SUPPORTED_HTTP_VERSIONS.get(1)); //1 is for HTTP/1.1
        return httpResponseBuilder.build();
    }
}
