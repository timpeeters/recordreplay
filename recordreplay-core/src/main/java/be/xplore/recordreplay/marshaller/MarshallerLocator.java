package be.xplore.recordreplay.marshaller;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MarshallerLocator {
    private final ServiceLoader<Marshaller> loader;
    private final long typeCount;

    public MarshallerLocator() {
        loader = ServiceLoader.load(Marshaller.class);
        typeCount = loader.stream().count();
    }

    public Marshaller load() {
        if (typeCount > 1) {
            throw new IllegalArgumentException(String.format("Multiple marshaller types found %s", loader.stream()
                    .map(marshallerProvider -> marshallerProvider.type().getName())
                    .collect(Collectors.joining(", ", "{", "}"))));
        }
        return loader.findFirst().orElseThrow(noSuchElementException(Marshaller.class));
    }

    public Marshaller load(Class<? extends Marshaller> marshallerType) {
        return loader.stream()
                .filter(marshallerProvider -> marshallerProvider.type().equals(marshallerType))
                .findFirst()
                .orElseThrow(noSuchElementException(marshallerType))
                .get();
    }

    private Supplier<NoSuchElementException> noSuchElementException(Class<? extends Marshaller> marshallerType) {
        return () -> new NoSuchElementException(
                String.format("Marshaller with type '%s' not found on classpath {%s}", marshallerType
                        .getName(), System
                        .getProperty("java.class.path")
                ));
    }
}
