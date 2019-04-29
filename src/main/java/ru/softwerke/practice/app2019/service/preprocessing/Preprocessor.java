package ru.softwerke.practice.app2019.service.preprocessing;

public interface Preprocessor<T> {
    public T process(T value);
}
