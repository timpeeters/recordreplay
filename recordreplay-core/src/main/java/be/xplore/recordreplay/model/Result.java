package be.xplore.recordreplay.model;

public class Result {

    private final double distance;
    private Stub stub;

    public Result(double distance) {
        this.distance = distance;
    }

    public Result(double distance, Stub stub) {
        this.distance = distance;
        this.stub = stub;
    }

    public double getDistance() {
        return distance;
    }

    public Stub getStub() {
        return stub;
    }
}
