package be.xplore.recordreplay.util;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ClassLocator<T> {

    private final Class<T> type;
    private final ServiceLoader<T> loader;
    private final long typeCount;

    public ClassLocator(Class<T> type) {
        this.type = type;
        this.loader = ServiceLoader.load(type);
        this.typeCount = loader.stream().count();
    }

    public T load() {
        if (typeCount > 1) {
            throw new IllegalArgumentException(String.format("Multiple class-types found %s", loader.stream()
                    .map(classProvider -> classProvider.type().getName())
                    .collect(Collectors.joining(", ", "{", "}"))));
        }
        return loader.findFirst().orElseThrow(noSuchElementException(type));
    }

    public T load(Class<? extends T> type) {
        return loader.stream()
                .filter(classProvider -> classProvider.type().equals(type))
                .findFirst()
                .orElseThrow(noSuchElementException(type))
                .get();
    }

    private Supplier<NoSuchElementException> noSuchElementException(Class<? extends T> type) {
        return () -> new NoSuchElementException(
                String.format("Class with type '%s' not found on classpath {%s}", type
                        .getName(), System
                        .getProperty("java.class.path")
                ));
    }

}
