package be.xplore.fakes.util;

public class Assert {
    public static <T> T notNull(T t) {
        if (t == null){
            throw new IllegalArgumentException("argument is null!");
        }
        return t;
    }
}
