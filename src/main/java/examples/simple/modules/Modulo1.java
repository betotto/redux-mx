package examples.simple.modules;

import io.vavr.collection.HashMap;
import mx.com.betotto.redux.Action;
import mx.com.betotto.redux.Reducer;

public class Modulo1 {

    private final static String ADD_TODO = "ADD_TODO";

    public static Reducer reducer = (state, action) -> {
        switch (action.type) {
            case ADD_TODO: {
                return state.put("text", action.payload.get("text").getOrNull());
            }
            default: {
                return state;
            }
        }
    };

    public static Action addTodo(String todoText) {
        return new Action(ADD_TODO, HashMap.of("text", todoText));
    }
}
