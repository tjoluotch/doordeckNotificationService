package endpoints;

import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class NotificationController {

    //Id AtomicInteger declared and initialised, AtomicInteger is used in order to access incrementAndGet() method.
    private final AtomicInteger id = new AtomicInteger();

    // declaration of Persistence layer
    PersistenceLayer pl = new PersistenceLayer();

    @RequestMapping(
                    value = "/notifications",
                    method = RequestMethod.POST)
    public ResponseEntity<Notification> createNotification(@RequestBody Map<String, Object> payload){

        // clear body values
        pl.clearBodyValues();

        // Declare and initialise empty notification object which will be returned
        Notification notification = new Notification();
        // Take payload Map values and put them in ArrayList as it is a sub class of Collection interface.
        //ArrayList bodyValues = new ArrayList(payload.values());
        pl.setBodyValues(new ArrayList<>(payload.values()));

        //First Converting Object into String type in order to use parsetInt() method.
        //String u_id = bodyValues.get(0).toString();
        String u_id = pl.getBodyValues().get(0).toString();

        // convert string into int userId
        int userId = Integer.parseInt(u_id.trim());

        //First Converting Object into String type in order to use parsetInt() method.
        //String t_stamp = bodyValues.get(1).toString();
        String t_stamp = pl.getBodyValues().get(1).toString();

        // convert string into int timestamp
        int timestamp = Integer.parseInt(t_stamp.trim());

        //convert Object into string variable called message
        //String message = bodyValues.get(2).toString();
        String  message = pl.getBodyValues().get(2).toString();

        // set notification id
        notification.setId(id.incrementAndGet());
        // set notification user_id
        notification.setUser_id(userId);
        // set notification timestamp
        notification.setTimestamp(timestamp);
        // set notification message
        notification.setMessage(message);

        // Add notification to ArrayList ----------------> implemented in Access Pattern/Persistence Layer
        pl.addNotificationToAllNotificationsStore(notification);

        // Return notification created with 200 status.
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @RequestMapping(
                    value = "/notifications/by_user/{user_id}",
                    method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Notification>> notifByUser(@PathVariable String user_id){

        // declare and initialise u_id variable which takes user_id type string and changes it to type int.
         int u_id = Integer.parseInt(user_id.trim());

         // ----------------> implemented in Access Pattern/Persistence Layer
         // clear data structure so that its only got notifications from current user_id request. -----> Impl in Access Pattern/Persistence Layer
        pl.clearNotificationsByUser();

        pl.loopAllNotificationsAddSpecificUser(u_id);

                // -----------> implemented in Access Pattern/Persistence Layer * using getter that returns ArrList
                Collections.sort(pl.getNotificationsByUser(), new SortByTimestamp());


                return new ResponseEntity<>(pl.getNotificationsByUser(), HttpStatus.OK);

    }

    @RequestMapping(
                    value = "notifications/edit/{notif_id}",
                    method = RequestMethod.PUT)
    public ResponseEntity<Object> editNotification(@RequestBody Map<String, Object> payload,
                                                         @PathVariable String notif_id){

        // with persistence layer clear body values before use
        pl.clearBodyValues();

        // Take payload Map values and put them in ArrayList as it is a sub class of Collection interface.
        //ArrayList bodyValues = new ArrayList(payload.values());
        pl.setBodyValues(new ArrayList<>(payload.values()));

        // edited message convert to string

        String edited_message = pl.getBodyValues().get(0).toString();

        //First Converting Object into String type in order to use parsetInt() method.
        //String t_stamp = bodyValues.get(1).toString();
        String t_stamp = pl.getBodyValues().get(1).toString();

        // convert string into int edited timestamp
        int edited_timestamp = Integer.parseInt(t_stamp.trim());

        // turned notification id into type int
        int notification_id = Integer.parseInt(notif_id.trim());

        return pl.loopAllNotificationsEditNotificationById(notification_id, edited_message, edited_timestamp);
    }

    @RequestMapping(
                    value = "notifications/read/{notif_id}",
                    method = RequestMethod.PUT)
    public ResponseEntity<Object> readNotification(@PathVariable String notif_id){
        // turned notification id into type int
        int notification_id = Integer.parseInt(notif_id.trim());

        return pl.loopAllNotificationsReadNotification(notification_id);
    }

    @RequestMapping(
                    value = "notifications/read/by_user/{user_id}",
                    method = RequestMethod.PUT)
    public ResponseEntity<Object> readAllUserNotifications(@PathVariable String user_id){
        // declare and initialise u_id variable which takes user_id type string and changes it to type int.
        int u_id = Integer.parseInt(user_id.trim());
        // clear data structure so that its only got notifications from current user_id request.
        pl.clearNotificationsByUser();

        // Loop through all notifications
        pl.loopAllNotificationsReadByUser(u_id);

        return new ResponseEntity<>(pl.getNotificationsByUser(), HttpStatus.OK);
    }

}
