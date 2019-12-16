package core.utilities;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class restAssuredExtension {

    public static RequestSpecification Request;
    public static ResponseOptions<Response> Response;

    /**
     * Set a basic base
     * @param baseURL String
     */
    public restAssuredExtension(String baseURL) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL);
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpec = builder.build();
        Request = RestAssured.given(requestSpec);
    }

    /**
     * Set a base with authentication
     * @param baseURL String
     * @param user String
     * @param password String
     */
    public restAssuredExtension(String baseURL, String user, String password) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseURL);
        builder.setContentType(ContentType.URLENC);
        PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
        auth.setUserName(user);
        auth.setPassword(password);
        builder.setAuth(auth);
        RequestSpecification requestSpec = builder.build();
        Request = RestAssured.given(requestSpec);
    }

    public static void GetOpsWithPathParameter(String url, Map<String, String> pathParams) {
        //Act
        Request.pathParams(pathParams);
        try {
            Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static ResponseOptions<Response> GetOps(String url) {
        try {
            return Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> GetOpsWithToken(String url, String apiKey) throws URISyntaxException {
        Request.header("Authorization", "Basic " + apiKey);
        return Request.get(url);
    }

    public static ResponseOptions<Response> PUTOpsWithBodyAndPathParams(String url, Map<String, String> body, Map<String, String> pathParams) {
        Request.pathParams(pathParams);
        Request.body(body);
        return Request.put(url);
    }

    public ResponseOptions<Response> GetOpsQueryParams(String url, String queryParams) {
        try {
            Request.queryParam(queryParams);
            return Request.get(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResponseOptions<Response> PostOpsWithBodyAndPathParams(String url, Map<String, String> pathParams, Map<String, String> body) {
        Request.pathParams(pathParams);
        Request.body(body);
        return Request.post(url);
    }

    public static ResponseOptions<Response> doPostOpsWithBody(String url, JSONObject body) {
        Request.body(body.toString());
        Response = Request.post(url);
        return Response;
    }

    public static ResponseOptions<Response> doPostWithFormData(String url, List<Map<String, String>> formParams) {
        for (Map<String, String> formParam: formParams) {
            Request.formParam(formParam.get("paramName"), formParam.get("paramValue"));
        }
        return Response = Request.post(url);
    }

    public static ResponseOptions<Response> doPost(String url) {
        return Response = Request.post(url);
    }

    public static ResponseOptions<Response> DeleteOpsWithPathParams(String url, Map<String, String> pathParams) {
        Request.pathParams(pathParams);
        return Request.delete(url);
    }

    public static ResponseOptions<Response> doGetWithPathParams(String url, Map<String, String> pathParams) {
        Request.pathParams(pathParams);
        return Response = Request.get(url);
    }

    public static ResponseOptions<Response> doGet(String url) {
        return Response = Request.get(url);
    }

    public static ResponseOptions<Response> doGetWithHeaders(String url, Header headers) {
        Request.header(headers);
        return Response = Request.get(url);
    }

}
