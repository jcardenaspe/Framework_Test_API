package core.utilities;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class generalUtilities {

    /**
     * Get the source from a JSON file saved in the Resources folder and return the content as a JSONObject
     *
     * @param jsonFilePath String
     * @return JSONObject
     */
    public JSONObject getJsonFileAsJSONObject(String jsonFilePath) {
        InputStream inputStream = getClass().getResourceAsStream(jsonFilePath);
        if (inputStream == null) {
            throw new Error("file is not found!");
        } else {
            return new JSONObject(new JSONTokener(inputStream));
        }
    }
}
