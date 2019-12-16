package steps.core.requests;

import cucumber.api.java.en.Then;
import org.apache.log4j.Level;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import core.utilities.requestResponse;
import core.utilities.loggerSetup;
import core.utilities.generalUtilities;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class requestGeneralThenSteps {

    private requestResponse response = new requestResponse();
    private generalUtilities generalUtilities = new generalUtilities();
    private loggerSetup Logger = new loggerSetup();

    @Then("^The response should contain the ([^\"]*) status code")
    public void validateStatusCodeResponse(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
        Logger.writerLogger(Level.INFO, requestGeneralThenSteps.class.toString(), String.format("The status Code is %s", response.getStatusCode()));
    }

    @Then("^The response should contain the ([^\"]*) content type")
    public void validateContentType(String contentType) {
        assertEquals(contentType, response.getContentType());
    }

    @Then("^The response time should be (less|greater) than ([^\"]*) milliseconds")
    public void validateResponseTime(String validatorType, long responseTime) {
        if (validatorType.equals("greater")) {
            assertThat(" Greater response time: ", response.getResponseTime(), greaterThan(responseTime));
        } else {
            assertThat("Less response time: ", response.getResponseTime(), lessThan(responseTime));
        }
    }

    @Then("^The schema of the response should be equal to the \"([^\"]*)\" file")
    public void validateSchemaResponse(String jsonSchemaPath) {
        JSONObject jsonSchema = generalUtilities.getJsonFileAsJSONObject(jsonSchemaPath);
        SchemaLoader loader = SchemaLoader.builder()
                .schemaJson(jsonSchema)
                .draftV7Support()
                .build();
        Schema schema = loader.load().build();
        JSONObject jsonResponse = new JSONObject(response.getBodyResponseAsString());
        schema.validate(jsonResponse);
    }

    @Then("^The JSON response should be equal to the \"([^\"]*)\" file")
    public void validateJsonResponse(String jsonPath) {
        JSONObject jsonObject = generalUtilities.getJsonFileAsJSONObject(jsonPath);
        JSONObject jsonResponse = new JSONObject(response.getBodyResponseAsString());
        JSONAssert.assertEquals(jsonObject, jsonResponse, true);
    }

    @Then("^The JSON response should be equal to the ([^\"]*) file")
    public void validateJsonResponseWithTable(String jsonPath) {
        JSONObject jsonObject = generalUtilities.getJsonFileAsJSONObject(jsonPath);
        JSONObject jsonResponse = new JSONObject(response.getBodyResponseAsString());
        JSONAssert.assertEquals(jsonObject, jsonResponse, true);
    }
}
