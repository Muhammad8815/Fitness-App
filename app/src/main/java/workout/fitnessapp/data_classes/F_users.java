package workout.fitnessapp.data_classes;

public class F_users {
    String name;
    String img;
    String key;

    public F_users(String name, String img, String key) {
        this.name = name;
        this.img = img;
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getKey() {
        return key;
    }
}
