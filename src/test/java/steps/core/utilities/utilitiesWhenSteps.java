package steps.core.utilities;

import cucumber.api.java.en.When;
import core.helpers.androidHelpers;
import core.helpers.generalHelpers;
import core.helpers.personFakeHelper;
import org.apache.log4j.Level;
import org.json.JSONObject;
import steps.core.requests.requestGeneralWhenSteps;
import core.utilities.generalUtilities;
import core.utilities.loggerSetup;

import static core.utilities.globalValues.*;

public class utilitiesWhenSteps {

    private loggerSetup Logger = new loggerSetup();
    private core.helpers.personFakeHelper personFakeHelper = new personFakeHelper();
    private generalHelpers helpers = new generalHelpers();
    private generalUtilities generalUtilities = new generalUtilities();

    @When("^I generate a unique id$")
    public void generateUniqueId() {
        androidHelpers.generateUniqueId();
        Logger.writerLogger(Level.INFO, requestGeneralWhenSteps.class.toString(), String.format("Unique Id Generated: %s", uniqueId));
    }

    @When("^I generate a UUI id$")
    public void generateUUId() {
        androidHelpers.generateUUICode();
        Logger.writerLogger(Level.INFO, requestGeneralWhenSteps.class.toString(), String.format("UUI Id Generated: %s", uuiId));
    }

    @When("^I generate a email$")
    public void createEmail() {
        emailAddress = personFakeHelper.getEmail();
        Logger.writerLogger(Level.INFO, requestGeneralWhenSteps.class.toString(), String.format("Email Address Generated: %s", emailAddress));
    }

    @When("^I generate a random email with the \"([^\"]*)\" domain$")
    public void createEmailRandom(String domain) {
        emailAddress = helpers.createEmailRandom(domain);
        Logger.writerLogger(Level.INFO, requestGeneralWhenSteps.class.toString(), String.format("Email Address Generated: %s", emailAddress));
    }

    @When("^I generate fake values of a person$")
    public void createFakeData() {
        personFakeHelper.generatePersonalFakeInformation();
        personFakeJSON = new JSONObject(personFakeInformation);
        Logger.writerLogger(Level.INFO, requestGeneralWhenSteps.class.toString(), String.format("Person Fake data Generated: %s", personFakeJSON.toString()));
    }
    
}
