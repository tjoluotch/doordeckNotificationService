package endpoints;

import org.springframework.http.ResponseEntity;

public interface AccessPatterns {

    public void addNotificationToAllNotificationsStore(Notification notification);

    public void addNotificationToAllNotificationsByUser(Notification notification);

    public void clearNotificationsByUser();

    public void loopAllNotificationsAddSpecificUser(int user_id);

    public void clearBodyValues();

    public ResponseEntity<Object> loopAllNotificationsEditNotificationById(int notification_id, String edited_message,
                                                                           int edited_timestamp);

    public ResponseEntity<Object> loopAllNotificationsReadNotification(int notification_id);

    public void loopAllNotificationsReadByUser(int user_id);
}
