package app;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Update {
    List<String> field = new ArrayList<>();
    List<String> res = new ArrayList<>();
    public Update(MongoDatabase db, String coll, String str){
        readUpdate(str);
        MongoCollection<Document> collection = db.getCollection(coll);




    }

    public void readUpdate(String file){
        String str;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((str = br.readLine()) != null){
                String[] split = str.split(":");
                this.field.add(split[0]);
                this.res.add(split[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}