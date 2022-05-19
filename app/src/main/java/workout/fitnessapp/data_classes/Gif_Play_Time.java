package workout.fitnessapp.data_classes;

public class Gif_Play_Time {
    String gifName;
    String gifUrl;
    String gifTime;

    public Gif_Play_Time(String gifName, String gifUrl, String gifTime) {
        this.gifName = gifName;
        this.gifUrl = gifUrl;
        this.gifTime = gifTime;
    }

    public String getGifName() {
        return gifName;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getGifTime() {
        return gifTime;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public void setGifTime(String gifTime) {
        this.gifTime = gifTime;
    }
}
