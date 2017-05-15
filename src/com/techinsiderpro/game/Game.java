package com.techinsiderpro.game;

import com.techinsiderpro.events.Dispatcher;

public class Game {

    private Dispatcher dispatcher;
    private GridObjectContainer gridObjectContainer;

    public Game(int width, int height) {
        dispatcher = new Dispatcher();
        gridObjectContainer = new ArrayGridObjectContainer(width, height);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public GridObjectContainer getGridObjectContainer() {
        return gridObjectContainer;
    }
}
