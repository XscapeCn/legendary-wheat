package app;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.*;

public class ShowVars {
    public Set<String> keys;

    public ShowVars(MongoDatabase db, String coll){
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
//        List<String> filedKeys = new ArrayList<>();
        keys = first.keySet();
        StringBuilder sb = new StringBuilder();

        for (String key : keys) {
            sb.append(key).append("\t");
        }
        System.out.println(sb.toString());
    }


//    public static void main(String[] args) {
//        String uri = "mongodb://localhost:27017";
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase db = mongoClient.getDatabase("test");
////            MongoCollection<Document> collection = db.getCollection("new");
//            new ShowVars(db, "germGeo");
//        }
//    }
}
