package edu.escuelaing.app.core;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import edu.escuelaing.app.annotations.GetMapping;
import edu.escuelaing.app.annotations.PostMapping;
import edu.escuelaing.app.annotations.RequestParam;
import edu.escuelaing.app.annotations.RestController;
import edu.escuelaing.app.http.HttpRequest;
import edu.escuelaing.app.http.HttpResponse;

/**
 * Handles routing of HTTP requests to appropriate controller methods using
 * reflection.
 * This class is responsible for scanning controllers and mapping requests to
 * methods.
 */
public class RequestHandler {
    private Map<String, RouteInfo> getRoutes;
    private Map<String, RouteInfo> postRoutes;

    /**
     * Creates a new RequestHandler.
     */
    public RequestHandler() {
        this.getRoutes = new HashMap<>();
        this.postRoutes = new HashMap<>();
    }

    /**
     * Registers a controller class for route mapping.
     *
     * @param controllerClass the controller class to register
     */
    public void registerController(Class<?> controllerClass) {
        if (!controllerClass.isAnnotationPresent(RestController.class)) {
            throw new IllegalArgumentException("Class must be annotated with @RestController");
        }

        RestController controllerAnnotation = controllerClass.getAnnotation(RestController.class);
        String basePath = controllerAnnotation.value();

        try {
            Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();

            for (Method method : controllerClass.getDeclaredMethods()) {
                registerMethodRoutes(method, controllerInstance, basePath);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate controller: " + controllerClass.getName(), e);
        }
    }

    /**
     * Registers routes for a specific method.
     *
     * @param method             the method to register
     * @param controllerInstance the controller instance
     * @param basePath           the base path from the controller
     */
    private void registerMethodRoutes(Method method, Object controllerInstance, String basePath) {
        if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping mapping = method.getAnnotation(GetMapping.class);
            String fullPath = basePath + mapping.value();
            RouteInfo routeInfo = new RouteInfo(controllerInstance, method);
            getRoutes.put(fullPath, routeInfo);
        }

        if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping mapping = method.getAnnotation(PostMapping.class);
            String fullPath = basePath + mapping.value();
            RouteInfo routeInfo = new RouteInfo(controllerInstance, method);
            postRoutes.put(fullPath, routeInfo);
        }
    }

    /**
     * Handles an incoming HTTP request.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     */
    public void handle(HttpRequest request, HttpResponse response) {
        String method = request.getMethod();
        String path = request.getPath();

        Map<String, RouteInfo> routes = "GET".equals(method) ? getRoutes : postRoutes;
        RouteInfo routeInfo = routes.get(path);

        if (routeInfo == null) {
            response.setStatus(404, "Not Found");
            response.setBody("<html><body><h1>404 - Page Not Found</h1></body></html>");
            return;
        }

        try {
            Object result = invokeMethod(routeInfo, request);
            if (result != null) {
                response.setBody(result.toString());
                if (result instanceof String && ((String) result).startsWith("{")) {
                    response.setContentType("application/json");
                }
            }
        } catch (Exception e) {
            response.setStatus(500, "Internal Server Error");
            response.setBody(
                    "<html><body><h1>500 - Internal Server Error</h1><p>" + e.getMessage() + "</p></body></html>");
        }
    }

    /**
     * Invokes a controller method with appropriate parameters.
     *
     * @param routeInfo the route information
     * @param request   the HTTP request
     * @return the method result
     * @throws Exception if invocation fails
     */
    private Object invokeMethod(RouteInfo routeInfo, HttpRequest request) throws Exception {
        Method method = routeInfo.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.getType() == HttpRequest.class) {
                args[i] = request;
            } else if (parameter.isAnnotationPresent(RequestParam.class)) {
                RequestParam annotation = parameter.getAnnotation(RequestParam.class);
                String paramName = annotation.value();
                String defaultValue = annotation.defaultValue();

                String paramValue = request.getQueryParam(paramName);
                if (paramValue == null && !defaultValue.isEmpty()) {
                    paramValue = defaultValue;
                }

                args[i] = convertParameter(paramValue, parameter.getType());
            } else {
                args[i] = null;
            }
        }

        return method.invoke(routeInfo.getController(), args);
    }

    /**
     * Converts a string parameter to the appropriate type.
     *
     * @param value      the string value
     * @param targetType the target type
     * @return the converted value
     */
    private Object convertParameter(String value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        if (targetType == String.class) {
            return value;
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value);
        }

        return value;
    }

    /**
     * Inner class to hold route information.
     */
    private static class RouteInfo {
        private final Object controller;
        private final Method method;

        /**
         * Creates a new RouteInfo.
         *
         * @param controller the controller instance
         * @param method     the method to invoke
         */
        public RouteInfo(Object controller, Method method) {
            this.controller = controller;
            this.method = method;
        }

        /**
         * Gets the controller instance.
         *
         * @return the controller
         */
        public Object getController() {
            return controller;
        }

        /**
         * Gets the method to invoke.
         *
         * @return the method
         */
        public Method getMethod() {
            return method;
        }
    }
}