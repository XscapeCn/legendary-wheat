import com.mongodb.client.MongoClient;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import app.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;


public class Entrance{
    String collection = null;
    String query = null;
    String database = "germplasm";
    Options options = new Options();
    String introduction = this.createIntroduction();
    String app = null;

    public Entrance (String[] args) {
        this.createOptions();
        this.retrieveParameters (args);

        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase(database);
//            MongoCollection<Document> collection = db.getCollection(this.collection);
            runApp(db, args);
        }
    }

    public void createOptions() {
        options = new Options();
        options.addOption("a", true, "App. e.g. -a Parsing");
        options.addOption("c", true, "collection. e.g. -c inventory");
        options.addOption("d", true, "database. e.g. -d germplasm");
        options.addOption("q", true, "query format. e.g. -q ploidy:6,SeedStorage:1");
    }

    public void runApp(MongoDatabase db,String[] args){
        if (app == null) {
            System.out.println("App does not exist");
            this.printIntroductionAndUsage();
            System.exit(0);
        }
        if (app.equals(AppNames.ShowVars.getName())) {
            new ShowVars(db, this.collection);
        }
        else if (app.equals(AppNames.ShowColl.getName())) {
            new ShowColl(db);
        }
        else if (app.equals(AppNames.DownTable.getName())) {
            new DownTable(db, this.collection);
        }
        else if (app.equals(AppNames.Query.getName())) {
            new Query(db,this.collection,this.query);
        }
        else if (app.equals(AppNames.Download.getName())) {
            new Download(db, this.collection);
        }
        else {
            System.out.println("App does not exist");
            this.printIntroductionAndUsage();
            System.exit(0);
        }
        new ReadLog(db, args);
    }

    public void retrieveParameters(String[] args) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter hf = new HelpFormatter();
        try {
            CommandLine line = parser.parse(options, args);
            app = line.getOptionValue("a");

            if( line.hasOption( "d" ) ) {
                database =line.getOptionValue("d");
            }
            if( line.hasOption( "c" ) ) {
                collection =line.getOptionValue("c");
            }
            if( line.hasOption( "q" ) ) {
                query =line.getOptionValue("q");
            }
        }
        catch(Exception e) {
            hf.printHelp("LW", options, true);
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void printIntroductionAndUsage() {
        System.out.println("Incorrect options input. Program stops.");
        System.out.println(introduction);
    }

    public String createIntroduction() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nLegendary-wheat is designed to make wheat germplasm contraction easier.\n");
        sb.append("It uses at least options to run its apps. \"-a\" is used to select an app.\n");
        sb.append("e.g. The basic function of it shows below: ");
        sb.append("java -Xmx100g -jar LW.jar -a Query -q GID:ABD00001\n");
        sb.append("\nAvailable apps in LW include,\n");
        for (int i = 0; i < AppNames.values().length; i++) {
            sb.append(AppNames.values()[i].getName()).append("\n");
        }
        sb.append("\nPlease visit https://github.com/XscapeCn/legendary-wheat for details.\n");
        return sb.toString();
    }

    public static void main (String[] args) {
        new Entrance(args);
    }
}











