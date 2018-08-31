package endpoints;

public class Notification {

    private  int id;
    private  int user_id;
    private  int timestamp;
    private  String message;
    private boolean read;

    public Notification(){
    }

    public Notification(int id, int user_id, int timestamp, String message, Boolean read) {
        this.id = id;
        this.user_id = user_id;
        this.timestamp = timestamp;
        this.message = message;

    }
    // accessor methods for variables
    public int getId() { return id; }

    public int getUser_id(){
        return user_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getRead(){ return read;}

    //Mutator methods for variables
    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRead(Boolean read) { this.read = read; }
}
