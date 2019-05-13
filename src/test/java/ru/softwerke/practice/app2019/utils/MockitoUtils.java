package ru.softwerke.practice.app2019.utils;

import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.util.function.Predicate;

public class MockitoUtils {

    public static <T> T argThat(Predicate<T> predicate) {
        return Mockito.argThat(new ArgumentMatcher<T>() {
            @Override
            public boolean matches(Object argument) {
                T v = (T) argument;
                return predicate.test(v);
            }
        });
    }
}
