package edu.escuelaing.app.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request with methods to parse and access request data.
 */
public class HttpRequest {
    private String method;
    private String path;
    private String httpVersion;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private String body;

    /**
     * Creates a new HttpRequest by parsing the input stream.
     *
     * @param inputStream the input stream from the socket
     * @throws IOException if reading from stream fails
     */
    public HttpRequest(InputStream inputStream) throws IOException {
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
        parseRequest(inputStream);
    }

    /**
     * Parses the HTTP request from the input stream.
     *
     * @param inputStream the input stream to parse
     * @throws IOException if reading fails
     */
    private void parseRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        // Parse request line
        String requestLine = reader.readLine();
        if (requestLine != null) {
            String[] parts = requestLine.split(" ");
            if (parts.length >= 3) {
                method = parts[0];
                parsePathAndQuery(parts[1]);
                httpVersion = parts[2];
            }
        }

        // Parse headers
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                String headerName = line.substring(0, colonIndex).trim();
                String headerValue = line.substring(colonIndex + 1).trim();
                headers.put(headerName.toLowerCase(), headerValue);
            }
        }

        // Parse body if present
        String contentLength = headers.get("content-length");
        if (contentLength != null && !contentLength.isEmpty()) {
            int length = Integer.parseInt(contentLength);
            if (length > 0) {
                char[] bodyChars = new char[length];
                reader.read(bodyChars, 0, length);
                body = new String(bodyChars);
            }
        }
    }

    /**
     * Parses the path and query parameters from the URL.
     *
     * @param url the URL to parse
     */
    private void parsePathAndQuery(String url) {
        int queryIndex = url.indexOf('?');
        if (queryIndex == -1) {
            path = url;
        } else {
            path = url.substring(0, queryIndex);
            String queryString = url.substring(queryIndex + 1);
            parseQueryParams(queryString);
        }
    }

    /**
     * Parses query parameters from the query string.
     *
     * @param queryString the query string to parse
     */
    private void parseQueryParams(String queryString) {
        if (queryString != null && !queryString.isEmpty()) {
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int equalIndex = pair.indexOf('=');
                if (equalIndex > 0) {
                    String key = URLDecoder.decode(pair.substring(0, equalIndex), StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(pair.substring(equalIndex + 1), StandardCharsets.UTF_8);
                    queryParams.put(key, value);
                }
            }
        }
    }

    /**
     * Gets the HTTP method.
     *
     * @return the HTTP method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Gets the request path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the HTTP version.
     *
     * @return the HTTP version
     */
    public String getHttpVersion() {
        return httpVersion;
    }

    /**
     * Gets all headers.
     *
     * @return the headers map
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Gets a specific header value.
     *
     * @param name the header name
     * @return the header value or null if not found
     */
    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    /**
     * Gets all query parameters.
     *
     * @return the query parameters map
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * Gets the value of a query parameter.
     *
     * @param name the parameter name
     * @return the parameter value
     */
    public String getQueryParam(String name) {
        return queryParams.get(name);
    }

    /**
     * Gets the request body.
     *
     * @return the request body
     */
    public String getBody() {
        return body;
    }
}