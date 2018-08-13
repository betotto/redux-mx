package examples.simple.modules;

import io.vavr.collection.HashMap;
import mx.com.betotto.redux.Action;
import mx.com.betotto.redux.Reducer;

public class Modulo2 {

    private final static String ADD_TODO2 = "ADD_TODO2";

    public static Reducer reducer = (state, action) -> {
        switch (action.type) {
            case ADD_TODO2: {
                return state.put("text2", action.payload.get("text2").getOrNull());
            }
            default: {
                return state;
            }
        }
    };

    public static Action addTodo2(String todoText) {
        return new Action(ADD_TODO2, HashMap.of("text2", todoText));
    }
}
