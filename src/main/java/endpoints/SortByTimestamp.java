package endpoints;

import java.util.Comparator;

public class SortByTimestamp implements Comparator<Notification> {

    //sort Notifications by Timestamp in descending order
    public int compare(Notification a, Notification b){
        return b.getTimestamp() - a.getTimestamp();
    }
}
