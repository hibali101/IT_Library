package com.hibali.IT_Library.http.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.hibali.IT_Library.Application.ApplicationContext;
import com.hibali.IT_Library.controllers.BaseController;
import com.hibali.IT_Library.http.Routes;
import com.hibali.IT_Library.http.Routes.ControllerMethodIdentity;
import com.hibali.IT_Library.http.Routes.RouteKey;

//this class is responsible for routing incoming request to controllers methods 
//from which it gets the data to return as a response
public class Router { 
    private final HttpRequest httpRequest;
    private final ApplicationContext applicationContext;

    public Router(HttpRequest request, ApplicationContext applicationContext) {
        this.httpRequest = request;
        this.applicationContext = applicationContext;
    }

    public String resolve() { // returns string for now
        String path = this.httpRequest.getRessourcePath();
        String methodName = this.httpRequest.getMethod();

        for (Map.Entry<RouteKey, ControllerMethodIdentity> entry : Routes.mapper.entrySet()) {

            Routes.RouteKey routeKey = entry.getKey();
            // if method dont match just continue to the next entry
            if (!routeKey.getMethod().equals(methodName)) {
                continue;
            }
            // test if the template path matches the actual resource path, also extract any
            // params if any
            Optional<LinkedHashMap<String, String>> params = matchPath(routeKey.getPath(), path);
            if (params.isPresent()) {
                // if their is any params i need to call a controller instance and get data back
                ControllerMethodIdentity cmi = entry.getValue();
                Class<? extends BaseController> controllerClass = cmi.getControllerClass();
                Method method = cmi.getTargetMethod();
                BaseController controller = applicationContext.getController(controllerClass);
                try {
                    Object[] args = prepareMethodArguments(method, params.get());
                    Object result = method.invoke(controller, args);
                    if(result instanceof String){
                        return (String) result;
                    }
                } catch (RouterException | IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
        return "hello";
    }

    // this method will retrieve an array of the arguments accepted by the
    // controller method
    private Object[] prepareMethodArguments(Method targetMethod, LinkedHashMap<String, String> pathParams) throws RouterException {
        Parameter[] parametres = targetMethod.getParameters();
        Object[] args = new Object[parametres.length];
        for (int i = 0; i < parametres.length; i++) {
            //getting the controller method parametres and search for their values in the pathParams(already returned from the match method below)
            Parameter methodParam = parametres[i];
            Class<?> methodParamType = methodParam.getType();
            String methodParamName = methodParam.getName();
            String pathParamValue = pathParams.get(methodParamName);

            if(pathParamValue == null){
                throw new RouterException("the parametre extracted from the incoming request ressource path"+
                        " doesnt match the argument name in the controller method!\r\n"+
                        "you must also compile java code with javac -parametres App.java or add it in maven configuration");   
            }

            if (methodParamType == Integer.class || methodParamType == int.class) {
                try{
                    args[i] = Integer.parseInt(pathParamValue);
                }catch(NumberFormatException e){
                    throw new RouterException("value of "+methodParamName+"is not an int"+e);
                }
            } else if (methodParamType == String.class) {
                args[i] = pathParamValue;
            }
        }
        return args;
    }

    // this function will return a map that has keys as controller method
    // parametres names and value as the value of this parametre extracted from the
    // ressource path
    // if empty is returned means no match between the ressource path and the
    // template path(template path is the path in the routes class like /books/{id})
    private Optional<LinkedHashMap<String, String>> matchPath(String templatePath, String actualPath) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>(); // linked to perserve order of insertion
        String[] templateParts = templatePath.split("/");
        String[] actualPathParts = actualPath.split("/");

        if (templateParts.length != actualPathParts.length) {
            return Optional.empty();
        }

        for (int i = 0; i < templateParts.length; i++) {
            String templatePart = templateParts[i];
            String actualPart = actualPathParts[i];

            if (templatePart.startsWith("{") && templatePart.endsWith("}")) {

                String paramName = templatePart.substring(1, templatePart.indexOf("}"));
                params.put(paramName, actualPart);
                continue; // no need to verify if parts are not equal IMPORTANT
            }

            if (!templatePart.equals(actualPart)) {
                return Optional.empty(); // no match
            }
        }
        return Optional.ofNullable(params); // btw in this function if no params were found then it will return an empty
                                            // hash map,which means ITS A MATCH but with no params
    }
}
