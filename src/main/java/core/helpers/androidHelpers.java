package core.helpers;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

import static core.utilities.globalValues.*;

public class androidHelpers {

    /**
     * Generate a valid Unique Id value and saved it in the uniqueId global variable.
     */
    public static void generateUniqueId() {
        uniqueId = RandomStringUtils.randomAlphanumeric(32);
    }

    /**
     * Generate a valid UUI code and saved it in the uuiId global variable.
     */
    public static void generateUUICode() {
        UUID uuid = UUID.randomUUID();
        uuiId = uuid.toString();
    }
}
