package workout.fitnessapp.data_classes;

public class Followers {
    String followers;
    String followerKey;

    public Followers(String followers) {
        this.followers = followers;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowerKey() {
        return followerKey;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public void setFollowerKey(String followerKey) {
        this.followerKey = followerKey;
    }

    public Followers() {
    }
}
