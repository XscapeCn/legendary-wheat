package app;

import com.mongodb.client.*;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
//import org.graalvm.compiler.nodes.calc.ObjectEqualsNode;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class Query {
    public Query(MongoDatabase db, String coll, String str) {
        MongoCollection<Document> collection = db.getCollection(coll);
        new ShowVars(db, coll,",");
        Bson filter = buildBson(checkFilter(splitQuery(str)));
        FindIterable<Document> documents = collection.find(filter);
        for (Document doc : documents) {
            Collection<Object> values = doc.values();
            System.out.println(values);
        }
    }

//    public static Bson buildBson(List<Object[]> query) {
//        Bson filter = eq((String) query.get(0)[0], query.get(0)[1]);
//        for (Object[] qu : query) {
//            filter = and(filter, eq((String) qu[0], qu[1]));
//        }
//        return filter;
//    }

    public static Bson buildBson(List<List<Object>> query) {
        Bson filter = eq((String) query.get(0).get(0), query.get(0).get(1));
        for (List<Object> qu : query) {
            filter = and(filter, eq((String) qu.get(0), qu.get(1)));
        }
        return filter;
    }

    public static List<Object[]> splitQuery(String str) {
        String[] qs = str.split(",");
        List<Object[]> eq = new ArrayList<>();
        for (String query : qs) {
            String[] aa = query.split(":");
            eq.add(aa);
        }
        return eq;
    }
// ArrayStoreException, how to fix it?
//    public static List<Object[]> checkFilter(List<Object[]> query){
//        for (Object[] obj : query) {
//            if (NumberUtils.isCreatable((String) obj[1])){
//                obj[1] = Double.valueOf((String) obj[1]);
//            }
//        }
//        return query;
//    }

    public static List<List<Object>> checkFilter(List<Object[]> query){
        List<List<Object>> res = new ArrayList<>();
        for (Object[] obj : query) {
            List<Object> temp = new ArrayList<>();
            temp.add((String) obj[0]);
            if (NumberUtils.isCreatable((String) obj[1])){
                double v = Double.parseDouble((String) obj[1]);
                temp.add(v);
            }else {
                temp.add(obj[1]);
            }
            res.add(temp);
        }
        return res;
    }
//
//    public static void main(String[] args) {
////        boolean creatable = NumberUtils.isCreatable(str);
//        String uri = "mongodb://localhost:27017";
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase db = mongoClient.getDatabase("test");
//            MongoCollection<Document> collection = db.getCollection("new");
////            FindIterable<Document> documents = collection.find(eq("Elevalution(m)", 359.0000));
////            for (Document doc : documents) {
////                Collection<Object> values = doc.values();
////                System.out.println(values);
////            }
//            new Query(db, "BamDatabaseID:TW0055,Elevalution(m):359.0,TreeValidatedGroupbySubspecies:Landrace,Latitude:36.7089");
////            new Query(db, "_id:6124a49802a7b5eb2a9df80c");
//        }
//    }
}