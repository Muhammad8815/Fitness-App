package workout.fitnessapp.data_classes;

public class Trainners {
    String trainner;
    String trainnerKey;

    public Trainners(String trainner) {
        this.trainner = trainner;
    }

    public String getTrainner() {
        return trainner;
    }

    public String getTrainnerKey() {
        return trainnerKey;
    }

    public void setTrainner(String trainner) {
        this.trainner = trainner;
    }

    public void setTrainnerKey(String trainnerKey) {
        this.trainnerKey = trainnerKey;
    }

    public Trainners() {
    }
}
