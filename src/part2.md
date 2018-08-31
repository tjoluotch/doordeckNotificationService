Operational Constrains
--------------------------

The way i have created the service is through using the web MVC 
design pattern, and REST principles. The operational constraints of this are

- **Loose Coupling**
: The Notification ``Notification.java`` is a Data structure that is independent of 
the Logic that manipulates the Notification ``NotificationController.java``

The Controller class holds all the methods that handle creation and 
manipulation of a Notification. These are made available to the client
through the ``@RequestMapping`` tag and ``value`` parameter which holds the URI
for the resource.

Both methods that execute the logic for their particular URI take
a parameter which gives them access to data that is used for the response.

The API endpoints are both returned as a ``ResponseEntity<>``, one being of
type Notification, and the other being of type ``ArrayList<Notification>``.

The ``notifByUser()`` method is dependant on the ``SortByTimeStamp.java`` 
class.

What is going to Fail and how are those failures resolved
---------------------------------------------------------
-**Incorrect type entry in notification data structure**
: The entry of an incorrect type in the request body
 ```{"user_id": int, "timestamp": int, "message": String} ```.
 An example of this could be an non integer character in `user_id`
 or `timestamp`.
 
 *resolution* : a bad request error message is returned to the client
`````` 
{
       "timestamp": "2018-08-22T14:43:21.039+0000",
       "status": 400,
       "error": "Bad Request",
       "message": "JSON parse error: Unexpected character (',' (code 44)) in numeric value: Exponent indicator not followed by a digit; nested exception is com.fasterxml.jackson.core.JsonParseException: Unexpected character (',' (code 44)) in numeric value: Exponent indicator not followed by a digit\n at [Source: (PushbackInputStream); line: 2, column: 16]",
       "path": "/notifications"
   } 
   ```````
   
 -**Requesting a user's last N notifications when they haven't created 
 any Notifications**
 
 *resolution*: this will return an empty 1 dimensional array
 ``[]``
 
 -**A User creates a Notification while at the same time that same user's last N
  notifications are being requested**
  
  *resolution*: this returns the empty 1 dimensional array ``[]`` first
  then create the notification.
  
Roughly how many users will this solution Handle
---------------------------------------------------
From the load tests carried out using Jmeter, this solution can handle 
just under 5,000 users. *See load test.png for proof of this.

How Many notifications per second
------------------------------------
1038 notifications per second. *See load test.png for proof of this.
   
  