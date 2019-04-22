package ru.softwerke.practice.app2019.controller.rest;

import org.junit.Test;
import ru.softwerke.practice.app2019.model.Client;
import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;


public class ModelValidatorTest {
    private Client client;

    @Test
    public void validateGoodClient() {
        LocalDate date = ParsingUtil.getLocalDate("12.12.2018");
        client = new Client("Ng", "Ng", "Ng", date);
        ModelValidator.validateEntity(client);
    }

    @Test(expected = WebApplicationException.class)
    public void validateBadClientWithLastNameNull() {
        client = new Client("Ng", null, "Ng", LocalDate.now());
        ModelValidator.validateEntity(client);
    }

    @Test(expected = WebApplicationException.class)
    public void validateBadClientWithDateNull() {
        client = new Client("Ng", "N", "Ng", null);
        ModelValidator.validateEntity(client);
    }

    @Test(expected = WebApplicationException.class)
    public void validateBadClientWithWrongFirstName() {
        client = new Client("g", "N", "Ng", LocalDate.now());
        ModelValidator.validateEntity(client);
    }

    @Test(expected = WebApplicationException.class)
    public void validateBadClientWithTooLongFirstName() {
        StringBuffer firatName = new StringBuffer(101);
        firatName.append("A");
        for (int i = 0; i < 100; i++){
            firatName.append("b");
        }
        client = new Client(firatName.toString(), "N", "Ng", LocalDate.now());
        ModelValidator.validateEntity(client);
    }

    @Test(expected = WebApplicationException.class)
    public void validateBadClientWithTooShortFirstName() {
        client = new Client("", "N", "Ng", LocalDate.now());
        ModelValidator.validateEntity(client);
    }



}