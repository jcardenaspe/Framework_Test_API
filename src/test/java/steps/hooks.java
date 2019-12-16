package steps;

import core.utilities.constants;
import core.utilities.loggerSetup;
import cucumber.api.*;
import cucumber.api.java.After;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class hooks {

    private loggerSetup Logger = new loggerSetup();
    private StringBuilder steps = new StringBuilder();
    private String projectName, testCaseId, finalErrorMessage = "";
    private org.json.simple.JSONObject parseResults = null;
    private static boolean featureFlag = false;

    @After
    public void after(final Scenario scenario) throws IOException, ParseException {
        if (scenario.isFailed()){
            this.getStepsErrorMessage(scenario);
        }
        if (!this.getResultJsonFileAsJSONObject()) {
            createResultFileFrom0(scenario);
        } else {
            if (scenario.getName().equals(parseResults.get("test_case_name"))) {
                reWriteResultFile(scenario);
            } else {
                this.validateResults(scenario);
                this.deleteResultFile();
                this.createResultFileFrom0(scenario);
            }
        }
        if(!featureFlag) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    getResultJsonFileAsJSONObject();
                    validateResults(scenario);
                    deleteResultFile();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }));
            featureFlag = true;
        }
    }

    /**
     * Validate the results of one executed test case
     * @param scenario Scenario
     * @throws IOException IOException
     */
    private void validateResults(Scenario scenario) throws IOException {
        StringBuilder messages = new StringBuilder();
        ArrayList<Boolean> results = (ArrayList<Boolean>) parseResults.get("results");
        this.getProjectName(scenario);
        this.updateGherkin(scenario);
        this.getTestCaseId();
        if (results.contains(true)) {
            JSONArray errorMessages = new JSONArray(parseResults.get("error_messages").toString());
            for (Object errorMessage : errorMessages) {
                String messageText = (String) errorMessage;
                if (!messageText.equals("")) {
                    messages.append(messageText).append("\n");
                }
            }
            // Report in testrail the status test with the error message generated
            System.out.println(projectName + messages.toString());
        } else {
            System.out.println("Good Job :D");
            // Report in testrail the status test with a successful message
        }
    }

    /**
     * Get the content of the result json file to re write it with the new results
     * @param scenario Scenario
     * @throws IOException IOException
     */
    private void reWriteResultFile(Scenario scenario) throws IOException {
        this.getGherkin(scenario);
        JSONArray errorMessages = new JSONArray(parseResults.get("error_messages").toString());
        errorMessages.put(finalErrorMessage);
        JSONArray results = new JSONArray(parseResults.get("results").toString());
        results.put(scenario.isFailed());
        JSONObject testCaseName = new JSONObject();
        testCaseName.put("results", results);
        testCaseName.put("test_case_name", parseResults.get("test_case_name"));
        testCaseName.put("error_messages", errorMessages);
        testCaseName.put("gherkin_text", steps.toString());
        if (this.deleteResultFile()) {
            FileWriter file = new FileWriter(constants.RESULT_FILE_PATH);
            file.write(testCaseName.toString());
            file.flush();
            file.close();
        }
    }

    /**
     * If the result file is not created, this is created
     * @param scenario Scenario
     * @throws IOException IOException
     * @throws ParseException ParseException
     */
    private void createResultFileFrom0(Scenario scenario) throws IOException {
        this.getGherkin(scenario);
        JsonObject jsonp = Json.createObjectBuilder()
                        .add("test_case_name", scenario.getName())
                        .add("results" , Json.createArrayBuilder()
                                .add(scenario.isFailed()))
                        .add("error_messages", Json.createArrayBuilder()
                                .add(finalErrorMessage))
                        .add("gherkin_text", steps.toString())
                .build();
        FileWriter file = new FileWriter(constants.RESULT_FILE_PATH);
        file.write(jsonp.toString());
        file.flush();
        file.close();
    }

    /**
     * Update the Gherkin text in Testrail of the executed feature
     * @param scenario Scenario
     * @throws IOException IOException
     */
    private void updateGherkin(Scenario scenario) throws IOException {
        this.getTestCaseId();
        // Update Gherkin value in Testrail
    }

    /**
     * Extract the error message of a step if this is failing
     * @param scenario Scenario
     * @return String
     */
    private void getStepsErrorMessage(Scenario scenario) {
        StringBuilder errorMessage = new StringBuilder();
        Field field = FieldUtils.getField(((Scenario) scenario).getClass(), "stepResults", true);
        field.setAccessible(true);
        try {
            ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
            errorMessage.append(":( \n");
            for (Result result : results) {
                if (result.getError() != null) {
                    errorMessage.append(result.getErrorMessage());
                }
            }
        } catch (Exception e) {
            Logger.writerLogger(Level.ERROR, hooks.class.toString(), String.format("Error getting test status: %s", e));
        }
        finalErrorMessage = errorMessage.toString();
    }

    /**
     * Get the gherkin text of the test case executed
     * @param scenario Scenario
     */
    private void getGherkin(Scenario scenario) {
        Field field = FieldUtils.getField(((Scenario) scenario).getClass(), "testCase", true);
        field.setAccessible(true);
        try {
            TestCase testCase = (TestCase) field.get(scenario);
            for (TestStep step : testCase.getTestSteps()) {
                PickleStepTestStep stepTestStep = (PickleStepTestStep) step;
                String text = stepTestStep.getStepText() + "\n";
                steps.append(text);
            }
        } catch (Exception e) {
        }
    }

    /**
     * Get the project name of the feature tag list
     * @param scenario Scenario
     */
    private void getProjectName(Scenario scenario) {
        ArrayList<String> tags = new ArrayList<String>(scenario.getSourceTagNames());
        projectName = tags.get(0).substring(1);
    }

    /**
     * Extract the test case id of the test case na,e
     */
    private void getTestCaseId() {
        String regularExpression1=".*?";
        String regularExpression2="((?:[a-z][a-z]*[0-9]+[a-z0-9]*))";
        Pattern pattern = Pattern.compile(regularExpression1 + regularExpression2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(parseResults.get("test_case_name").toString());
        if (matcher.find()) {
            testCaseId= matcher.group(1).substring(1);
        }
    }

    /**
     * Get the content of the result json file
     * @return JSONObject
     * @throws IOException IOException
     * @throws ParseException ParseException
     */
    private boolean getResultJsonFileAsJSONObject() throws IOException, ParseException {
        boolean created = true;
        JSONParser jsonParser = new JSONParser();
        File tempFile = new File(constants.RESULT_FILE_PATH);
        if (tempFile.exists()) {
            FileReader reader = new FileReader(constants.RESULT_FILE_PATH);
            Object obj = jsonParser.parse(reader);
            parseResults = (org.json.simple.JSONObject) obj;
        } else {
            created = false;
        }
        return created;
    }

    /**
     * Delete the result json file
     * @return Boolean
     */
    private Boolean deleteResultFile() {
        File resultJsonFile = new File(constants.RESULT_FILE_PATH);
        return resultJsonFile.delete();
    }
}
