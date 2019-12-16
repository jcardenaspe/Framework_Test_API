package core.helpers;

import io.cucumber.datatable.DataTable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class requestHelpers {

    private static String parameterName, parameterValue;

    /**
     * Create a body request from a DataTable
     *
     * @param dataTable DataTable
     * @return JSONObject
     * @throws JSONException
     */
    public static JSONObject createBodyRequest(DataTable dataTable) throws JSONException {
        JSONObject body = new JSONObject();
        for (Map<String, String> parameter : convertDataTableToMap(dataTable)) {
            parameterName = parameter.get("parameterName");
            parameterValue = parameter.get("parameterValue");
            body.put(parameterName, parameterValue);
        }
        return body;
    }

    /**
     * Create a path parameters request from a DataTable
     *
     * @param dataTable DataTable
     * @return Map<String, String>
     */
    public static Map<String, String> createPathParameters(DataTable dataTable) {
        HashMap<String, String> pathParameters = new HashMap<String, String>();
        for (Map<String, String> parameter : convertDataTableToMap(dataTable)) {
            parameterName = parameter.get("parameterName");
            parameterValue = parameter.get("parameterValue");
            pathParameters.put(parameterName, parameterValue);
        }
        return pathParameters;
    }

    /**
     * Convert a DataTable to List Map of strings.
     *
     * @param dataTable DataTable
     * @return List<Map < String, String>>
     */
    private static List<Map<String, String>> convertDataTableToMap(DataTable dataTable) {
        return dataTable.asMaps(String.class, String.class);
    }
}
