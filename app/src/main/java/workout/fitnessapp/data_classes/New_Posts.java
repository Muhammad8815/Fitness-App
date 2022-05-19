package workout.fitnessapp.data_classes;

public class New_Posts {
    String Post,ImgUrl,uuid;

    public New_Posts(String post, String imgUrl) {
        Post = post;
        ImgUrl = imgUrl;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPost() {
        return Post;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public New_Posts() {
    }

    public void setPost(String post) {
        Post = post;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
