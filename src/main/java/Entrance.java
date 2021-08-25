import com.mongodb.client.MongoClient;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Entrance {

    public static void main(String[] args) {
//        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("new");
            Document doc = collection.find().first();
//            if (doc != null) {
//                System.out.println("_id: " + doc.getObjectId("_id")
//                        + ", name: " + doc.getString("name")
//                        + ", dateOfDeath: " + doc.getDate("dateOfDeath"));
//                doc.getList("novels", Document.class).forEach((novel) -> {
//                    System.out.println("title: " + novel.getString("title")
//                            + ", yearPublished: " + novel.getInteger("yearPublished"));
//                });
//            }
            System.out.println(doc.toJson());



//            Bson filter = Filters.and(Filters.gt("qty", 3), Filters.lt("qty", 9));
//            collection.find(filter).forEach(doc -> System.out.println(doc.toJson()));
        }
    }
}


