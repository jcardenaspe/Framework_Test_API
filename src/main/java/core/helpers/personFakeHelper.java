package core.helpers;

import com.github.javafaker.Faker;
import core.utilities.globalValues;

public class personFakeHelper {

    private Faker faker = new Faker();

    /**
     * Generate a fake full name.
     *
     * @return String
     */
    public String getFullName() {
        return faker.name().fullName();
    }

    /**
     * Generate a fake first name
     *
     * @return String
     */
    public String getFirstName() {
        return faker.name().firstName();
    }

    /**
     * Generate a fake Last name
     *
     * @return String
     */
    public String getLastName() {
        return faker.name().lastName();
    }

    /**
     * Generate a fake email address
     *
     * @return String
     */
    public String getEmail() {
        return faker.internet().safeEmailAddress();
    }

    /**
     * Generate a fake phone number
     *
     * @return String
     */
    public String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    /**
     * Generate a fake middle name
     *
     * @return String
     */
    public String getMiddleName() {
        return faker.name().nameWithMiddle();
    }

    /**
     * Generate a fake id number
     *
     * @return String
     */
    public String getIdNumber() {
        return faker.idNumber().invalid();
    }

    /**
     * Generate a personal fake information using the personFakeInformation class.
     */
    public void generatePersonalFakeInformation() {
        personFakeInformation personFakeInformation = new personFakeInformation();

        personFakeInformation.setEmail(this.getEmail());
        personFakeInformation.setLastName(this.getLastName());
        personFakeInformation.setFirstName(this.getFirstName());
        personFakeInformation.setMiddleName(this.getMiddleName());
        personFakeInformation.setPhone(this.getPhoneNumber());
        personFakeInformation.setIdDocumentNumber(this.getIdNumber());

        globalValues.personFakeInformation = personFakeInformation;
    }
}
