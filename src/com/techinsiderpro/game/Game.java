package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Dispatcher dispatcher;
    private List<GridObject> gridObjectContainer;

    public Game(int width, int height) {
        dispatcher = new Dispatcher();
        gridObjectContainer = new ArrayList<>();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public List<GridObject> getGridObjectContainer() {
        return gridObjectContainer;
    }
}
