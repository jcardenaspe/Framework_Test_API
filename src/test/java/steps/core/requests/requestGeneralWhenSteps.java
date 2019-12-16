package steps.core.requests;

import core.utilities.constants;
import core.utilities.globalValues;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import core.utilities.restAssuredExtension;

import java.util.List;
import java.util.Map;

import static core.helpers.requestHelpers.*;

public class requestGeneralWhenSteps {

    @When("^I perform a POST request to the \"([^\"]*)\" path with body$")
    public void performPostWithBody(String urlPath, DataTable table) throws JSONException {
        JSONObject body = createBodyRequest(table);
        restAssuredExtension.doPostOpsWithBody(urlPath, body);
    }

    @When("^I perform a POST request to the \"([^\"]*)\" path$")
    public void performPost(String urlPath) {
        restAssuredExtension.doPost(urlPath);
    }

    @When("^I perform a GET request to the \"([^\"]*)\" path$")
    public void performGet(String urlPath) {
        restAssuredExtension.doGet(urlPath);
    }

    @When("^I perform a GET request to the \"([^\"]*)\" path with the following params:")
    public void performGetWithParams(String urlPath, DataTable table) throws JSONException {
        Map<String, String> params = createPathParameters(table);
        restAssuredExtension.doGetWithPathParams(urlPath, params);
    }

    @When("^I perform a POST operation to the \"([^\"]*)\" path with the following form data:$")
    public void generateTokenClientType (String urlPath, DataTable formData) throws JSONException {
        List<Map<String, String>> formParams = formData.asMaps(String.class, String.class);
        restAssuredExtension.doPostWithFormData(urlPath, formParams);
    }
}
