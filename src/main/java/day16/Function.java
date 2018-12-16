package day16;

import java.util.List;

@FunctionalInterface
public interface Function<T> {
    List<T> apply(T A, T B, T C, List<T> registersBefore);
}