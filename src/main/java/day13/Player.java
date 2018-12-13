package day13;

import day10.Coordinate;

public class Player {

    private Coordinate coordinate;
    private char currentDirection;
    private Direction lastDirectionAtIntersection;

    public Player(Coordinate coordinate, char currentDirection, Direction lastDirectionAtIntersection) {
        this.coordinate = coordinate;
        this.currentDirection = currentDirection;
        this.lastDirectionAtIntersection = lastDirectionAtIntersection;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public char getCurrentDirection() {
        return currentDirection;
    }

    public Direction getLastDirectionAtIntersection() {
        return lastDirectionAtIntersection;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setCurrentDirection(char currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setLastDirectionAtIntersection(Direction lastDirectionAtIntersection) {
        this.lastDirectionAtIntersection = lastDirectionAtIntersection;
    }
}
