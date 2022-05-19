package workout.fitnessapp.data_classes;

public class Trainner_Followers {
    String trainnerFollowers;
    String trainnerFollowersKey;

    public Trainner_Followers(String trainnerFollowers) {
        this.trainnerFollowers = trainnerFollowers;
    }

    public String getTrainnerFollowers() {
        return trainnerFollowers;
    }

    public String getTrainnerFollowersKey() {
        return trainnerFollowersKey;
    }

    public void setTrainnerFollowers(String trainnerFollowers) {
        this.trainnerFollowers = trainnerFollowers;
    }

    public void setTrainnerFollowersKey(String trainnerFollowersKey) {
        this.trainnerFollowersKey = trainnerFollowersKey;
    }

    public Trainner_Followers() {
    }
}
