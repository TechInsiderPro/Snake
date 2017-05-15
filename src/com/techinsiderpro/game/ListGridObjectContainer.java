package com.techinsiderpro.game;

import java.util.ArrayList;
import java.util.List;

public class ListGridObjectContainer implements GridObjectContainer {
    private List<GridObject> gridObjects;

    public ListGridObjectContainer() {
        gridObjects = new ArrayList<>();
    }

    public void addGridObject(GridObject gridObject) {
        gridObjects.add(gridObject);
    }

    public void removeGridObject(GridObject gridObject) {
        gridObjects.remove(gridObject);
    }

    public void setPositionTo(GridObject gridObject) {
        removeGridObjectAt(gridObject.getPosition());
        addGridObject(gridObject);
    }

    public GridObject getGridObjectAt(Position position) {
        for (GridObject gridObject : gridObjects) {
            if (gridObject.getPosition().equals(position)) {
                return gridObject;
            }
        }

        return null;
    }

    public void removeGridObjectAt(Position position) {
        GridObject gridObjectAtPosition = null;

        for (GridObject gridObject : gridObjects) {
            if (gridObject.getPosition().equals(position)) {
                gridObjectAtPosition = gridObject;
                break;
            }
        }

        removeGridObject(gridObjectAtPosition);
    }

    @Override
    public boolean isEmptyAt(Position position) {
        return getGridObjectAt(position) == null;
    }
}
