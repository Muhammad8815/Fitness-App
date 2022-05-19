package workout.fitnessapp.data_classes;

public class Message_Class {
    String text,personuid,Email;

    public Message_Class(String text, String personuid, String email) {
        this.text = text;
        this.personuid = personuid;
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getText() {
        return text;
    }

    public String getPersonuid() {
        return personuid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPersonuid(String personuid) {
        this.personuid = personuid;
    }

    public Message_Class() {
    }
}
