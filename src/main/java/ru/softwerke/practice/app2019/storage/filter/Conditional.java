package ru.softwerke.practice.app2019.storage.filter;

public interface Conditional<T> {
    boolean accept(T object);
}
