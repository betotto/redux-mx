package mx.com.betotto.redux;

import io.vavr.Tuple3;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class Store {

    private final static Logger log = LoggerFactory.getLogger(Store.class);

    private boolean isDispatching = false;
    private Reducer reducer;
    private Map<String, Object> state;
    private Vector<Tuple3<Integer, Boolean, Consumer>> listeners = Vector.empty();
    private Integer listenerId = 0;

    public Store (Reducer reducer, Map<String, Object> preloadedState) {
        this.reducer = reducer;
        this.state = preloadedState;
    }

    public Map<String, Object> getState() {
        Map<String, Object> result = null;
        if (isDispatching) {
            log.error("You may not call store.getState() while the reducer is executing.");
        } else {
            result = state;
        }
        return result;
    }

    public Runnable subscribe(Consumer<Map<String, Object>> listener) {
        final Tuple3<Integer, Boolean, Consumer> tupleListener = new Tuple3(++listenerId, true, listener);
        if (isDispatching) {
            log.error("You may not call store.subscribe() while the reducer is executing.");
        } else {
            listeners = listeners.append(tupleListener);
        }
        return () -> {
            if (isDispatching) {
                log.error("You may not unsubscribe from a store listener while the reducer is executing.");
                throw new RuntimeException("You may not unsubscribe from a store listener while the reducer is executing.");
            }
            listeners = listeners.filter(t -> t._1 != tupleListener._1);
        };
    }

    public Action dispatch(Action action) {
        Action result = null;
        if (isDispatching) {
            log.error("Reducers may not dispatch actions");
            throw new RuntimeException("Reducers may not dispatch actions");
        } else {
            isDispatching = true;
            state = reducer.reduce(state, action);
            listeners.forEach(l -> l._3.accept(state));
            isDispatching = false;
            result = action;
        }
        return result;
    }

    public static Reducer combineReducer(HashMap<String, Reducer> reducers) {
        return (state, action) -> {
            for(String module : reducers.keySet()) {
                state = state.put(module, reducers.get(module).get().reduce((HashMap<String, Object>)state.get(module).get(), action));
            }
            return state;
        };
    }
}
