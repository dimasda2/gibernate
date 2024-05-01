package org.example.app.utils;

import java.util.HashMap;
import java.util.Map;

public class ContactValidator {

    public Map<String, String> validateContactData(Map<String, String> data) {

        Map<String, String> errors = new HashMap<>();

        if (data.containsKey("id") & AppValidator.isIdValid(data.get("id")))
            errors.put("id", Constants.WRONG_ID_MSG);

        if (data.containsKey("name")) {
            if (data.get("name") != null & data.get("name").isEmpty())
                errors.put("name", Constants.INPUT_REQ_MSG);
        }

        if (data.containsKey("email") & AppValidator.isEmailValid(data.get("email")))
            errors.put("email", Constants.WRONG_EMAIL_MSG);

        return errors;
    }
}