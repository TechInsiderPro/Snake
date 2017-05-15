package com.techinsiderpro.game;

public class ArrayGridObjectContainer extends GridObjectContainer {
    private GridObject[][] gridObjects;

    public ArrayGridObjectContainer(int width, int height) {
        gridObjects = new GridObject[height][width];
    }

    @Override
    public boolean add(GridObject gridObject) {
        if (isEmptyAt(gridObject.getPosition())) {
            gridObjects[gridObject.getPosition().getY()][gridObject.getPosition().getX()] = gridObject;
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(GridObject gridObject) {
        return removeGridObjectAt(gridObject.getPosition());
    }

    @Override
    public void setPositionTo(GridObject gridObject) {
        removeGridObjectAt(new Position(gridObject.getPosition().getX(), gridObject.getPosition().getY()));
        addGridObject(gridObject);
    }

    @Override
    public GridObject getGridObjectAt(Position position) {
        return gridObjects[position.getY()][position.getX()];
    }

    @Override
    public boolean removeGridObjectAt(Position position) {
        gridObjects[position.getY()][position.getX()] = null;
    }

    @Override
    public boolean isEmptyAt(Position position) {
        return getGridObjectAt(position) == null;
    }
}
