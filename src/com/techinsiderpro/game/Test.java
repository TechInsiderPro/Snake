package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;
import com.techinsiderpro.events.Handler;

public class Test {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.registerHandler(DirectionChangeRequestEvent.class, new Handler<DirectionChangeRequestEvent>() {
            @Override
            public void dispatch(DirectionChangeRequestEvent event) {
                if (event.getTarget().getDirection() != Direction.getOpposite(event.getRequestedDirection())) {
                    event.getTarget().setDirection(event.getRequestedDirection());
                }
            }
        });

        GridObject object = new GridObject(new Position(1,1), Direction.DOWN);

        dispatcher.dispatch(new DirectionChangeRequestEvent(Direction.UP, object));

        System.out.println(object.getDirection());
    }
}
