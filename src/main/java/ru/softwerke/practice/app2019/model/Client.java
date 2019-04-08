package ru.softwerke.practice.app2019.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.softwerke.practice.app2019.storage.Unique;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Client implements Unique {
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String PATRONYMIC_FIELD = "patronymic";
    private static final String BIRTH_DATE_FIELD = "birthDate";

    @JsonProperty(ID_FIELD)
    private UUID id;

    @JsonProperty(FIRST_NAME_FIELD)
    private final String firstName;

    @JsonProperty(LAST_NAME_FIELD)
    private final String lastName;

    @JsonProperty(PATRONYMIC_FIELD)
    private final String patronymic;

    @JsonProperty(BIRTH_DATE_FIELD)
    @JsonSerialize(using = DateSeriailizer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate birthDate;

    @JsonCreator
    public Client(
            @NotNull @JsonProperty(FIRST_NAME_FIELD) String firstName,
            @NotNull @JsonProperty(LAST_NAME_FIELD) String lastName,
            @NotNull @JsonProperty(PATRONYMIC_FIELD) String patronymic,
            @NotNull @JsonProperty(BIRTH_DATE_FIELD) LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id &&
                firstName.equals(client.firstName) &&
                lastName.equals(client.lastName) &&
                patronymic.equals(client.patronymic) &&
                birthDate.equals(client.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, patronymic, birthDate);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}





