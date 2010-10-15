package com.youdevise.test.narrative;

import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A assertion of the final state of the system for a narrative
 * @param <T> The type of tool that the Actor uses
 */
public class Then<T> {
    private final Actor<T> actor;

    /**
     * Type safe matching.
     * @param <D> The type of data that is going to be checked
     */
    public class TypedMatcher<D> {
        private final Extractor<D, T> expected;
        private final Then<T> outer;

        public TypedMatcher(Extractor<D, T> expected, Then<T> outer) {
            this.expected = expected;
            this.outer = outer;
        }
        
        public TypedMatcher<D> should_be(Matcher<? super D> matcher) {
            return should(matcher);
        }

        public TypedMatcher<D> should_have(Matcher<? super D> matcher) {
            return should(matcher);
        }

        public TypedMatcher<D> should(Matcher<? super D> matcher) {
            assertThat(actor.grabUsing(expected), matcher);
            return this;
        }

        public Then<T> andAlso() {
            return outer;
        }

        public <X> TypedMatcher<X> and_also_expects_that(Extractor<X, T> next_expected) {
            return outer.expects_that(next_expected);
        }
    }

    private Then(Actor<T> actor) {
        this.actor = actor;
    }

    public <D> TypedMatcher<D> expects_that(Extractor<D, T> expected) {
        return new TypedMatcher<D>(expected, this);
    }

    public static <T> Then<T> the(Actor<T> actor) {
        return new Then<T>(actor);
    }
}