package app;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.*;
import java.util.Date;

public class ReadLog {
    public String user;
    public String command;
    public ReadLog(MongoDatabase db, String[] args){
        this.user = getUser();
        this.command = getCommand(args);
        addToDb(db);
    }

    public String getUser(){
        String cmd = "pwd";
        String str = null;
        String res = null;
        try{
            String [] cmdArray ={"/bin/bash","-c", cmd};
            Process p = Runtime.getRuntime().exec(cmdArray,null);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            str = br.readLine();
            br.close();
            p.waitFor();

            String[] split = str.split("/");
            if (split.length >= 4){
                res = split[3];
            }else {
                res = split[1];
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return res;
    }

    public String getCommand(String[] args){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < args.length; i++) {
            sb.append(args[i]);
        }
        return (sb.toString());
    }

    public void addToDb(MongoDatabase db){
        String uri = "mongodb://localhost:27017";
        MongoCollection<Document> collection = db.getCollection("log");

        try {
            Date date = new Date();
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("user", this.user)
                    .append("log", this.command)
                    .append("data", date.toString()));
        } catch (MongoException ignored) {}
    }
}
