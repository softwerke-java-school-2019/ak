package ru.softwerke.practice.app2019.service;

import ru.softwerke.practice.app2019.storage.filter.sorting.SortConditional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientFilter {
    public final static ClientFilter EMPTY = new ClientFilter();

    private UUID id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDateFrom;
    private LocalDate birthDateTo;
    private List<SortConditional> sortConditionals = new ArrayList<>();
    private int count;
    private int pageNumber ;

    public UUID getId() {
        return id;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPatronymic() { return patronymic; }

    public LocalDate getBirthDateFrom() {
        return birthDateFrom;
    }

    public LocalDate getBirthDateTo() { return birthDateTo; }

    public List<SortConditional> getSortConditionals() { return sortConditionals; }

    public int getCount() {
        return count;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public  ClientFilter withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public  ClientFilter withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public  ClientFilter withPatronymic(String patronymic){
        this.patronymic = patronymic;
        return this;
    }

    public ClientFilter withBirthDateFrom(LocalDate birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
        return this;
    }

    public ClientFilter withBirthDateTo(LocalDate birthDateTo) {
        this.birthDateTo = birthDateTo;
        return this;
    }

    public ClientFilter withSortParams(List<SortConditional> sortConditionals){
        this.sortConditionals = sortConditionals;
        return this;
    }

    public ClientFilter withCount(int count){
        this.count = count;
        return this;
    }

    public ClientFilter withPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
        return this;
    }
}
