package app;

import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import java.util.Collection;
import java.util.Set;

public class Download {
    public Set<String> keys;

    public Download(MongoDatabase db, String coll){
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
        assert first != null;
        keys = first.keySet();
        StringBuilder sb = new StringBuilder();

        for (String key : keys) {
            sb.append(key).append(",");
        }
        System.out.println(sb.toString());

        FindIterable<Document> iterDoc = collection.find();
        for (Document doc:iterDoc) {
            Collection<Object> values = doc.values();
            for (Object obj : values) {
                System.out.print(obj + ",");
            }
            System.out.print("\n");
        }
    }

    public Download(MongoDatabase db, String coll, String gex, String query, String val){
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
//        List<String> filedKeys = new ArrayList<>();
        assert first != null;
        keys = first.keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(gex);
        }
        System.out.println(sb.toString());
        FindIterable<Document> iterDoc = collection.find(eq(query, val));
        for (Document doc:iterDoc) {
            Collection<Object> values = doc.values();
            for (Object obj : values) {
                System.out.print(obj + gex);
            }
            System.out.print("\n");
        }
    }

    public FindIterable<Document> getFound(MongoDatabase db, String coll){
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
        assert first != null;
        keys = first.keySet();
        FindIterable<Document> iterDoc = collection.find();
        return iterDoc;
    }

    public void subPrint(){
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(",");
        }
        System.out.println(sb.toString());
    }

    public void subPrint(String gex){
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append(key).append(gex);
        }
        System.out.println(sb.toString());
    }

    public void print(FindIterable<Document> iterDoc, Set<String> keys){


        for (Document doc:iterDoc) {
            Collection<Object> values = doc.values();
            for (Object obj : values) {
                System.out.print(obj + ",");
            }
            System.out.print("\n");
        }
    }

    public void print(FindIterable<Document> iterDoc, String gex){
        for (Document doc:iterDoc) {
            Collection<Object> values = doc.values();
            for (Object obj : values) {
                System.out.print(obj + gex);
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test");
//            MongoCollection<Document> collection = db.getCollection("new");
            new Download(db, "new");
        }
    }
}
