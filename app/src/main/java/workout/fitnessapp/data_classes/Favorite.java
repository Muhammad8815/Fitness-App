package workout.fitnessapp.data_classes;

public class Favorite {
    String favorite;
    String favoriteKey;

    public Favorite(String favorite) {
        this.favorite = favorite;
    }

    public String getFavorite() {
        return favorite;
    }

    public String getFavoriteKey() {
        return favoriteKey;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public void setFavoriteKey(String favoriteKey) {
        this.favoriteKey = favoriteKey;
    }

    public Favorite() {
    }
}
