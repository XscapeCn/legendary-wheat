package app;

import java.io.File;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DownTable {





    public DownTable(MongoDatabase db, String coll) throws IOException {
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
        Set<String> keys = first.keySet();
        Collection<Object> values = first.values();
        List<String> set = new ArrayList<>();
        for (Object val:values
             ) {
            set.add((String) val);
        }

        Set<String> setti = new HashSet<String>(set);




        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

        XSSFRow row;
        Map<String, Object[]> Sheet1 = new TreeMap<String, Object[]>();

        String[] var = keys.toArray(new String[0]);
        String[] varr = new String[var.length-1];
        for (int i = 1; i < var.length; i++) {
            varr[i-1] = var[i];
        }

        String[] val = setti.toArray(new String[0]);
        String[] vall = new String[val.length-1];
        for (int i = 1; i < val.length; i++) {
            vall[i-1] = val[i];
        }



        Sheet1.put("1", varr );
        Sheet1.put("2", vall );


        int rowid = 0;


        Set<String> keyid = Sheet1.keySet();



        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = Sheet1.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }

        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        FileOutputStream out = new FileOutputStream(
                new File("D:/Desktop/GFGsheet.xlsx"));

        workbook.write(out);
        out.close();






    }

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test");
//            MongoCollection<Document> collection = db.getCollection(this.collection);
            new DownTable(db, "log");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
