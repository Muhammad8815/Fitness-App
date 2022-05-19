package workout.fitnessapp.data_classes;

public class User_Information {

    String fullName;
    String Profile;
    String cover;
    String dobDay;
    String dobMonth;
    String dobYear;
    String userIs;
    String address;
    String email;
    String pid;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public User_Information(String fullName, String dobDay, String dobMonth, String dobYear, String userIs, String address, String email) {
        this.fullName = fullName;
        this.dobDay = dobDay;
        this.dobMonth = dobMonth;
        this.dobYear = dobYear;
        this.userIs = userIs;
        this.address = address;
        this.email = email;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        this.Profile = profile;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDobDay() {
        return dobDay;
    }

    public String getDobMonth() {
        return dobMonth;
    }

    public String getDobYear() {
        return dobYear;
    }

    public String getUserIs() {
        return userIs;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDobDay(String dobDay) {
        this.dobDay = dobDay;
    }

    public void setDobMonth(String dobMonth) {
        this.dobMonth = dobMonth;
    }

    public void setDobYear(String dobYear) {
        this.dobYear = dobYear;
    }

    public void setUserIs(String userIs) {
        this.userIs = userIs;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User_Information() {
    }
}
