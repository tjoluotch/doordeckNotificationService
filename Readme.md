The Code for the Service can be found at:
-
**codingchallenge/src/main/java/endpoints** .

The answers for part 2 can be found at:
-

**codingchallenge/src/part2.md**

with a screenshot of the acompanying load test at

**codingchallenge/src/load test.png** .

the service is extended to allow for editing a notification, this is done through:
-


```PUT /notifications/edit/<notification_id>```
which accepts the following data structure in JSON:

 ```
     {
         "message": String,
         "timestamp": int
     }
 ``` 
 and returns the following:
 ```
 {
     "id": int,
     "user_id": int,
     "timestamp": int,
     "message": String,
     "read": boolean
 }
```

The service is extended to allow for reading a single notification, this is done through:
-
```PUT /notifications/read/<notification_id>```

which returns the following with the boolean value as true:
``` 
{
     "id": int,
     "user_id": int,
     "timestamp": int,
     "message": String,
     "read": boolean
}

```

The service is extended to allow for reading all of a particular User's notifications, this is done through:
-
```PUT /notifications/read/by_user/<user_id>```

which returns a JSON list of the last N Notifications by that particular user with read value being true.

Persistence Layer and Access Patterns
-
I created a persistence layer in ```PersistenceLayer.java``` which implements 
the interface ```AccessPatterns.java```

This solution achieves loose coupling as all the code concerning where or how the data is stored is in 
```PersistenceLayer.java``` with 8 methods providing patterns regarding Data Access.

