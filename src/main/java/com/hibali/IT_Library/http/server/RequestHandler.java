package com.hibali.IT_Library.http.server;

public interface RequestHandler { //just definition of contract of any requestHandler implementation
    public HttpResponse handle(HttpRequest request);
}
