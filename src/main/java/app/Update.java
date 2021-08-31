package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import org.apache.poi.ss.usermodel.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.io.*;
import java.util.*;

public class Update {
    List<String> field = new ArrayList<>();
    List<List<Object>> res = new ArrayList<>();
    List<Object> temp = new ArrayList<>();
    MongoCollection<Document> collection;

    public Update(MongoDatabase db, String coll, String str){
        this.collection = db.getCollection(coll);
        readUpdate(str);
        checkVar();

        for (List<Object> obj: res) {
            System.out.println("=========");
            for (Object a: obj) {
                System.out.println(a);
            }
        }
        insertToDb();
    }

    private void insertToDb() {
        List<Document> documents = new ArrayList<>();
        for (List<Object> docu: res) {
            Document doc = new Document();
            for (int i = 0; i < docu.size(); i++) {
                doc.append(field.get(i), docu.get(i));
            }
            documents.add(doc);
        }

        InsertManyResult result = collection.insertMany(documents);
        List<ObjectId> insertedIds = new ArrayList<>();
        result.getInsertedIds().values()
                .forEach(doc -> insertedIds.add(doc.asObjectId().getValue()));
        System.out.println("Inserted documents with the following ids: " + insertedIds);
    }

    public void readUpdate(String file){
        try {
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            Workbook wb = WorkbookFactory.create(new File(file));
            Sheet sheet = wb.getSheetAt(0);

            int count = 0;

            for (Row cells : sheet) {
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = cells.cellIterator();
                if (count != 0) {
                    insert(cellIterator);
                } else {
                    insertField(cellIterator);
                }
                count++;
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public void checkVar(){
        //check if some value such as "GID" existed in the db
        Document first = this.collection.find().first();
        assert first != null;
        Set<String> keySet = first.keySet();
        String[] keys = keySet.toArray(new String[0]);
        String[] field = new String[keys.length-1];
        System.arraycopy(keys, 1, field, 0, keys.length - 1);
        for (int i = 0; i < this.field.size(); i++) {
            if ( ! field[i].equals((String) this.field.get(i))){
                System.out.println("Please check the columns in your excel, does it equal to the excel downloaded from DownTable? ");
                System.exit(1);
            }
        }
    }

    public void insert(Iterator<Cell> cellIterator){
        Cell cell;
        while (cellIterator.hasNext()) {
            cell = cellIterator.next();
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    temp.add(cell.getBooleanCellValue());
//                            data.append(cell.getBooleanCellValue() + ",");
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    temp.add(cell.getNumericCellValue());
//                            data.append(cell.getNumericCellValue() + ",");
                    break;
                case Cell.CELL_TYPE_STRING:
                    temp.add(cell.getStringCellValue());
//                            data.append(cell.getStringCellValue() + ",");
                    break;
                case Cell.CELL_TYPE_BLANK:
                    temp.add("");
//                            data.append("" + ",");
                    break;
                default:
                    temp.add(cell);
//                            data.append(cell + ",");
            }
        }
        res.add(temp);
        temp = new ArrayList<>();
    }

    public void insertField(Iterator<Cell> cellIterator){
        Cell cell;
        while (cellIterator.hasNext()) {
            cell = cellIterator.next();
            field.add(cell.getStringCellValue());
        }
    }

//    public static void main(String[] args) {
//
//        String uri = "mongodb://localhost:27017";
//        try (MongoClient mongoClient = MongoClients.create(uri)) {
//            MongoDatabase db = mongoClient.getDatabase("test");
//            String coll = "germGeo";
//            String file = "D:/Desktop/GFGsheet.xlsx";
//            new Update(db, coll, file);
//        }
//    }
}