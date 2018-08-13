package examples.simple;

import examples.simple.modules.Modulo1;
import examples.simple.modules.Modulo2;
import io.vavr.collection.HashMap;
import mx.com.betotto.redux.Reducer;
import mx.com.betotto.redux.Store;

public class Main {

    private static HashMap<String, Object> initialState = HashMap.of("reducer1", HashMap.of("text", ""),"reducer2", HashMap.of("text2", ""));

    public static void main(String... args) {


        Reducer appReducer = Store.combineReducer(HashMap.of(
                "reducer1", Modulo1.reducer,
                "reducer2", Modulo2.reducer));

        Store store = new Store(appReducer, initialState);

        Runnable unsubcribe = store.subscribe(s -> System.out.println(s));

        store.dispatch(Modulo1.addTodo("Hello"));

        store.dispatch(Modulo2.addTodo2("Hello"));

        unsubcribe.run();

        store.dispatch(Modulo2.addTodo2("Hello3"));
    }
}
