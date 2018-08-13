package mx.com.betotto.redux;

import io.vavr.collection.HashMap;

public class Action {
    public final String type;
    public final HashMap<String, Object> payload;

    public Action() {
        throw new RuntimeException("Type required");
    }

    public Action(String type) {
        this.type = type;
        payload = null;
    }

    public Action(String type, HashMap<String, Object> payload) {
        this.type = type;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return String.format("{ type=%s, payload={%s} }", type, payload != null ? payload.toString() : "null");
    }
}

