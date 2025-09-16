package edu.escuelaing.app.http;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP response with methods to set status, headers, and body.
 */
public class HttpResponse {
    private int statusCode;
    private String statusMessage;
    private Map<String, String> headers;
    private String body;

    /**
     * Creates a new HttpResponse with default values.
     */
    public HttpResponse() {
        this.statusCode = 200;
        this.statusMessage = "OK";
        this.headers = new HashMap<>();
        this.body = "";

        // Set default headers
        headers.put("Content-Type", "text/html; charset=UTF-8");
        headers.put("Server", "CustomWebFramework/1.0");
    }

    /**
     * Sets the HTTP status code and message.
     *
     * @param code    the status code
     * @param message the status message
     */
    public void setStatus(int code, String message) {
        this.statusCode = code;
        this.statusMessage = message;
    }

    /**
     * Sets a response header.
     *
     * @param name  the header name
     * @param value the header value
     */
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    /**
     * Sets the response body.
     *
     * @param body the response body
     */
    public void setBody(String body) {
        this.body = body != null ? body : "";
        setHeader("Content-Length", String.valueOf(this.body.getBytes(StandardCharsets.UTF_8).length));
    }

    /**
     * Sets the content type header.
     *
     * @param contentType the content type
     */
    public void setContentType(String contentType) {
        setHeader("Content-Type", contentType);
    }

    /**
     * Writes the response to the output stream.
     *
     * @param outputStream the output stream to write to
     */
    public void write(OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8);

        // Write status line
        writer.printf("HTTP/1.1 %d %s\r\n", statusCode, statusMessage);

        // Write headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            writer.printf("%s: %s\r\n", header.getKey(), header.getValue());
        }

        // Write empty line
        writer.print("\r\n");

        // Write body
        writer.print(body);
        writer.flush();
    }

    /**
     * Gets the status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Gets the status message.
     *
     * @return the status message
     */
    public String getStatusMessage() {
        return statusMessage;
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
     * Gets the response body.
     *
     * @return the response body
     */
    public String getBody() {
        return body;
    }
}