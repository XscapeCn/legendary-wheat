package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class DownTable {
    public DownTable(MongoDatabase db, String coll){
        MongoCollection<Document> collection = db.getCollection(coll);
        Document first = collection.find().first();
        Set<String> keySet = first.keySet();
        String[] keys = keySet.toArray(new String[0]);
        String[] field = new String[keys.length-1];
        System.arraycopy(keys, 1, field, 0, keys.length - 1);

        Object[] values = first.values().toArray();
        Object[] value = new Object[values.length-1];
        System.arraycopy(values, 1, value, 0, values.length - 1);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("example");

        XSSFRow row;
        Map<String, Object[]> example = new TreeMap<String, Object[]>();

        example.put("1", field);
        example.put("2", value);

        int rowid = 0;
        Set<String> keyid = example.keySet();

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = example.get(key);
            int cellid = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(String.valueOf(obj));
            }
        }
        // .xlsx is the format for Excel Sheets...
        // writing the workbook into the file...
        try{
            FileOutputStream out = new FileOutputStream(new File("D:/Desktop/UpdateTable.xlsx"));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test");
//            MongoCollection<Document> collection = db.getCollection(this.collection);
            new DownTable(db, "germGeo");
        }
    }
}