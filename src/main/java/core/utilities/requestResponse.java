package core.utilities;

import io.restassured.response.ResponseBody;

public class requestResponse {

    /**
     * Get the status code of the response.
     *
     * @return int.
     */
    public int getStatusCode() {
        return restAssuredExtension.Response.getStatusCode();
    }

    /**
     * Get the body of the response.
     *
     * @return ResponseBody.
     */
    public ResponseBody getBodyResponse() {
        return restAssuredExtension.Response.getBody();
    }

    /**
     * Get the body of the response as String.
     *
     * @return String.
     */
    public String getBodyResponseAsString() {
        return restAssuredExtension.Response.getBody().asString();
    }

    /**
     * Get the Content Type of the response.
     *
     * @return String.
     */
    public String getContentType() {
        return restAssuredExtension.Response.getContentType().trim();
    }

    /**
     * Get the time of the response.
     *
     * @return long.
     */
    public long getResponseTime() {
        return restAssuredExtension.Response.time();
    }

}
