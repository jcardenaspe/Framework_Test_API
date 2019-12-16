package steps.core;

import core.utilities.restAssuredExtension;
import cucumber.api.java.en.Given;

public class givenGeneralSteps {

    @Given("^I set the \"([^\"]*)\" base URL$")
    public void setBaseURL(String URL) {
        new restAssuredExtension(URL);
    }

}
