package com.techinsiderpro.game;

public interface GridObjectContainer {
    void addGridObject(GridObject gridObject);

    void removeGridObject(GridObject gridObject);

    void setPositionTo(GridObject gridObject);

    GridObject getGridObjectAt(Position position);

    void removeGridObjectAt(Position position);

    boolean isEmptyAt(Position position);
}
