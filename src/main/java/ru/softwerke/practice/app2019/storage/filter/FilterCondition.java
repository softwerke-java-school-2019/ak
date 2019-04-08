package ru.softwerke.practice.app2019.storage.filter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FilterCondition<T, R> implements Conditional<T> {
    private final Function<T, R> extractor;
    private final List<Conditional<R>> conditional;

    public FilterCondition(Function<T, R> extractor, List<Conditional<R>> conditional) {
        this.extractor = extractor;
        this.conditional = conditional;
    }

    public FilterCondition(Function<T, R> extractor, Conditional<R> conditional) {
        this(extractor, Collections.singletonList(conditional));
    }

    public Function<T, R> getExtractor() {
        return extractor;
    }

    public List<Conditional<R>> getConditional() {
        return conditional;
    }

    @Override
    public boolean accept(T object) {
        R value = extractor.apply(object);
        return conditional.stream().allMatch(it -> it.accept(value));
    }
}

