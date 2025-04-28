package com.hibali.IT_Library.http;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

import com.hibali.IT_Library.controllers.BaseController;
import com.hibali.IT_Library.controllers.BookController;

public final class Routes {
    public static final Map<RouteKey,ControllerMethodIdentity> mapper = Map.of(
        new RouteKey("GET", "/books/{id}"), new ControllerMethodIdentity(BookController.class, "getById")
    );// "/books/12"

    public static class RouteKey {
        private String method;
        private String path;

        public RouteKey(String method, String path) {
            this.method = method;
            this.path = path;
        }
        //getters
        public String getMethod(){
            return this.method;
        }
        public String getPath(){
            return this.path;
        }

        //hash code and equals
        @Override
        public int hashCode() {
            return Objects.hash(method,path);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            RouteKey other = (RouteKey) obj;
            if (method == null) {
                if (other.method != null)
                    return false;
            } else if (!method.equals(other.method))
                return false;
            if (path == null) {
                if (other.path != null)
                    return false;
            } else if (!path.equals(other.path))
                return false;
            return true;
        }
    }

    public static class ControllerMethodIdentity {
        private Class<? extends BaseController> controllerClass;
        private Method targetMethod;

        public ControllerMethodIdentity(Class<? extends BaseController> controllerClass, String targetMethod) {
            this.controllerClass = controllerClass;
            try {
                this.targetMethod = controllerClass.getMethod(targetMethod, Integer.class);
            } catch (NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }

        public Class<? extends BaseController> getControllerClass() {
            return controllerClass;
        }

        public Method getTargetMethod() {
            return targetMethod;
        }
        
    }
}
