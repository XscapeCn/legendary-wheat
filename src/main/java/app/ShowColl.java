package app;

import com.mongodb.client.*;
import org.bson.Document;

public class ShowColl {
    public ShowColl(MongoDatabase db){
        MongoIterable<String> collectionNames = db.listCollectionNames();
        for (String str: collectionNames) {
            System.out.println(str);
        }
    }
}
