package workout.fitnessapp.data_classes;

public class PicNameUrl {
    String picName;
    String picurl;
    String ppid;

    public void setPpid(String ppid) {
        this.ppid = ppid;
    }

    public String getPpid() {
        return ppid;
    }

    public PicNameUrl(String picName, String picurl) {
        this.picName = picName;
        this.picurl = picurl;
    }

    public String getPicName() {
        return picName;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public PicNameUrl() {
    }
}
