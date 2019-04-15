package ru.softwerke.practice.app2019.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.softwerke.practice.app2019.model.date.DateDeserializer;
import ru.softwerke.practice.app2019.model.date.DateSerializer;
import ru.softwerke.practice.app2019.storage.Unique;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;
import ru.softwerke.practice.app2019.storage.filter.sorting.SortableFieldProvider;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

public class Client implements Unique {
    private static final String ID_FIELD = "id";
    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String PATRONYMIC_FIELD = "patronymic";
    private static final String BIRTH_DATE_FIELD = "birthDate";

    public static final SortableFieldProvider<Client> FIELD_PROVIDER = new ClientSortableFieldProvider();


    @JsonProperty(ID_FIELD)
    private UUID id;

    @JsonProperty(FIRST_NAME_FIELD)
    private final String firstName;

    @JsonProperty(LAST_NAME_FIELD)
    private final String lastName;

    @JsonProperty(PATRONYMIC_FIELD)
    private final String patronymic;

    @JsonProperty(BIRTH_DATE_FIELD)
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private final LocalDate birthDate;

    @JsonCreator
    public Client(
            @NotNull @JsonProperty(value = FIRST_NAME_FIELD, required = true) String firstName,
            @NotNull @JsonProperty(value = LAST_NAME_FIELD, required = true) String lastName,
            @NotNull @JsonProperty(value = PATRONYMIC_FIELD, required = true) String patronymic,
            @NotNull @JsonProperty(value = BIRTH_DATE_FIELD, required = true) LocalDate birthDate) {
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

    public static class ClientSortableFieldProvider implements SortableFieldProvider<Client> {

        private ClientSortableFieldProvider() {
        }

        @Override
        public Comparator<Client> getSortConditional(SortConditional sortConditional) {
            switch (sortConditional.getField()) {
                case FIRST_NAME_FIELD:
                    return Comparator.comparing(Client::getFirstName);
                case LAST_NAME_FIELD:
                    return Comparator.comparing(Client::getLastName);
                case PATRONYMIC_FIELD:
                    return Comparator.comparing(Client::getPatronymic);
                case BIRTH_DATE_FIELD:
                    return Comparator.comparing(Client::getBirthDate);
                default:
                    throw new IllegalArgumentException("Unexpected sort param " + sortConditional.getField());
            }
        }
    }
}





