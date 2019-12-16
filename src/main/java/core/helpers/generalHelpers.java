package core.helpers;

import com.github.javafaker.Faker;

public class generalHelpers {

    private Faker faker = new Faker();

    public String createEmailRandom(String domain) {
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        // Random rnd = new Random();
        // while (salt.length() < 10) {
        // int index = (int) (rnd.nextFloat() * string.length());
        // salt.append(string.charAt(index));
        // }

        return salt.append(faker.name().username()).append("@").append(domain).append(".com").toString();
        // return salt.append("@").append(domain).toString();

    }

}
