package com.hibali.IT_Library.http.server;

import java.util.Map;
import java.util.Optional;

import com.hibali.IT_Library.http.Routes;
import com.hibali.IT_Library.http.Routes.ControllerMethodIdentity;
import com.hibali.IT_Library.http.Routes.RouteKey;

public class Router {
    private final HttpRequest httpRequest;

    public Router(HttpRequest request) {
        this.httpRequest = request;
    }

    public HttpResponse resolve(){
        String path = this.httpRequest.getRessourcePath();
        String method = this.httpRequest.getMethod();

        for (Map.Entry<RouteKey,ControllerMethodIdentity> entry : Routes.mapper.entrySet()){
            
            Routes.RouteKey routeKey = entry.getKey();
            //if method dont match just continue to the next entry
            if(!routeKey.getMethod().equals(method)){
                continue;
            }

        }
    }
    //this function will return a map that has keys as controller method 
    //parametres names and value as the value of this parametre extracted from the ressource path
    //if empty is returned means no match between the ressource path and the 
    //template path(template path is the path in the routes class like /books/{id})
    private Optional<Map<String,String>> matchPath(String templatePath, String actualPath){
        Optional<Map<String,String>> params;

        String[] templateParts = templatePath.split("/");
        String[] actualPathParts = actualPath.split("/");

        if(templateParts.length != actualPathParts.length){
            return Optional.empty();
        }

        for(int i = 0; i< templateParts.length; i++){
            String templatePart = templateParts[i];
            String actualPart = actualPathParts[i];
        }
    }
}
