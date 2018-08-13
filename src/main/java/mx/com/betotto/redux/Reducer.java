package mx.com.betotto.redux;

import io.vavr.collection.Map;

@FunctionalInterface
public interface Reducer {

    Map<String, Object> reduce(Map<String, Object> state, Action action);
}
