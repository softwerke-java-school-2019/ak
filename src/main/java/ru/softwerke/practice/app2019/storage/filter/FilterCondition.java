package ru.softwerke.practice.app2019.storage.filter;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FilterCondition<T, R> implements Conditional<T> {
    private final Function<T, R> extractor;
    private final List<Conditional<R>> conditional;

    private FilterCondition(Function<T, R> extractor, List<Conditional<R>> conditional) {
        this.extractor = extractor;
        this.conditional = conditional;
    }

    private FilterCondition(Function<T, R> extractor, Conditional<R> conditional) {
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

    public static <T, R extends Comparable<? super R>> FilterConditionalBuilder<T, R> on(Function<T, R> extractor) {
        return new FilterConditionalBuilder<>(extractor);
    }

    public static class FilterConditionalBuilder<T, R extends Comparable<? super R>> {
        private final Function<T, R> extractor;

        private FilterConditionalBuilder(Function<T, R> extractor) {
            this.extractor = extractor;
        }

        public FilterCondition<T, R> cond(Conditional<R> conditional) {
            return new FilterCondition<>(extractor, conditional);
        }

        public FilterCondition<T, R> conds(List<Conditional<R>> conditionals) {
            return new FilterCondition<>(extractor, conditionals);
        }

        public FilterCondition<T, R> eq(R value) {
            return cond(ComparisonConditional.eq(value));
        }

        public FilterCondition<T, R> inRange(R from, R to) {
            return cond(new RangeConditional<>(from, to));
        }
    }
}

