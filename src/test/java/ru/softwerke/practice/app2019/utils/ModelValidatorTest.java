package ru.softwerke.practice.app2019.utils;

import org.junit.jupiter.api.Test;
import ru.softwerke.practice.app2019.model.Client;
import ru.softwerke.practice.app2019.utils.ModelValidator;
import ru.softwerke.practice.app2019.utils.ParsingUtil;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ModelValidatorTest {
    private Client client;

    @Test
    public void validateGoodClient() {
        LocalDate date = ParsingUtil.getLocalDate("12.12.2018");
        client = new Client("Ng", "Ng", "Ng", date);
        assertTrue(validate(client));
    }

    @Test
    public void validateBadClientWithLastNameNull() {
        client = new Client("Ng", null, "Ng", LocalDate.now());
        assertFalse(validate(client));
    }

    @Test
    public void validateBadClientWithDateNull() {
        client = new Client("Ng", "N", "Ng", null);
        assertFalse(validate(client));
    }

    @Test
    public void validateBadClientWithWrongFirstName() {
        client = new Client("ASdg6", "N", "Ng", LocalDate.now());
        assertFalse(validate(client));
    }

    @Test
    public void validateBadClientWithTooLongFirstName() {
        StringBuffer firatName = new StringBuffer(101);
        firatName.append("A");
        for (int i = 0; i < 100; i++){
            firatName.append("b");
        }
        client = new Client(firatName.toString(), "N", "Ng", LocalDate.now());
        assertFalse(validate(client));
    }

    @Test
    public void validateBadClientWithTooShortFirstName() {
        client = new Client("", "N", "Ng", LocalDate.now());
        assertFalse(validate(client));
    }

    private static boolean validate(Object object) {
        try {
            ModelValidator.validateEntity(object);
            return true;
        } catch (WebApplicationException ex) {
            return false;
        }
    }
}