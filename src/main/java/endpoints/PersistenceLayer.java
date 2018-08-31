package endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

public class PersistenceLayer implements AccessPatterns {

    // Data store where all notifications created in the service layer are stored
    private ArrayList<Notification> notificationList = new ArrayList<>();
    // Data store where all notifications for a particular user are stored.
    private ArrayList<Notification> notificationsByUser = new ArrayList<>();
    // Data store which stores all the body values for a particular request - remember to clear at the start of each request that uses using new values
    private ArrayList<Object> bodyValues = new ArrayList<>();

    public PersistenceLayer(){

    }

    public ArrayList<Notification> getNotificationList() {
        return notificationList;
    }

    public ArrayList<Notification> getNotificationsByUser() {
        return notificationsByUser;
    }

    public ArrayList<Object> getBodyValues() {
        return bodyValues;
    }

    public void setBodyValues(ArrayList<Object> bodyValues) {
        this.bodyValues = bodyValues;
    }

    @Override
    public void addNotificationToAllNotificationsStore(Notification notification) {
        notificationList.add(notification);
    }

    @Override
    public void addNotificationToAllNotificationsByUser(Notification notification) {
        notificationsByUser.add(notification);
    }

    @Override
    public void clearNotificationsByUser() {
        notificationsByUser.clear();
    }

    @Override
    public void loopAllNotificationsAddSpecificUser(int user_id) {
            for(Notification notification : notificationList){

                if (user_id == notification.getUser_id()){
                    this.addNotificationToAllNotificationsByUser(notification);
                }

            }

    }



    @Override
    public void clearBodyValues() {
        bodyValues.clear();
    }

    @Override
    public ResponseEntity<Object> loopAllNotificationsEditNotificationById(int notification_id,String edited_message,
                                                                           int edited_timestamp) {

        Notification current;

        for(int i = 0; i < notificationList.size(); ++i){
            current = notificationList.get(i);

            // if current notification's id in the arrayList is equal to the notification id from the path
            // edit meassage and timestamp of that notification with setters,
            // then return the new Notification with a Success message
            if (current.getId() == notification_id){
                current.setMessage(edited_message);
                current.setTimestamp(edited_timestamp);

                return new ResponseEntity<>(current, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>("No notification stored with that id, try again", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> loopAllNotificationsReadNotification(int notification_id) {

        Notification current;

        for (int i = 0; i < notificationList.size(); ++i){
            current = notificationList.get(i);

            // If notification id from path variable is the same as the current Notification's id
            // then use mutator method setRead() to true and then return the new Notification
            if (notification_id == current.getId()){
                current.setRead(true);

                return new ResponseEntity<>(current, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("No notification stored with that id, try again", HttpStatus.NOT_FOUND);
    }

    @Override
    public void loopAllNotificationsReadByUser(int user_id) {
        // Loop through all notifications
        for(Notification notification : notificationList){
                /* condition to check if the user_id entered in the URI is the same as the
                 current notification's user id
                 then add to the ArrayList notificationsByUser and change its status to read using the
                 mutator method setRead() */
            if (user_id == notification.getUser_id()){
                notificationsByUser.add(notification);
                notification.setRead(true);
            }

        }

    }

}
