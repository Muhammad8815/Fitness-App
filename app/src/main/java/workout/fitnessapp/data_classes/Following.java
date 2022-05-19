package workout.fitnessapp.data_classes;

public class Following {
    String following;
    String followingKey;

    public Following(String following) {
        this.following = following;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowingKey() {
        return followingKey;
    }

    public void setFollowingKey(String followingKey) {
        this.followingKey = followingKey;
    }

    public Following() {
    }
}
