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



    Options options = new Options();
    HelpFormatter optionFormat = new HelpFormatter();
    String introduction = this.createIntroduction();
    String app = null;

    String inputFile = null;
    String outputFileDirS=null;
    String QCmethod = null;
    String readsNumber = null;

    public Entrance (String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase db = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = db.getCollection("new");
            Document doc = collection.find().first();
            this.createOptions();
            this.retrieveParameters (args, db);
        }
    }

    public void createOptions() {
        options = new Options();
        options.addOption("a", true, "App. e.g. -a Parsing");
        options.addOption("c", true, "collection. e.g. -c germplasm");
        options.addOption("q", true, "query format. e.g. -q ploidy:6,SeedStorage:1");


        options.addOption("f", true, "Parameter file path of an app. e.g. parameter_Alignment.txt");
        options.addOption("i", true, "-inputFile /User/bin/");
        options.addOption("o", true, "-outputFileDirS /User/bin/");
        options.addOption("s", true, "-sampleInformationFileS /User/bin/");
        options.addOption("l", true, "-library /User/bin/");
        options.addOption("app", true, "-anno /User/bin/");
        options.addOption("t", true, "-t 32");
        options.addOption("m",true,"method mean or median");
        options.addOption("r",true,"readsNumber");
    }


    public void retrieveParameters(String[] args, MongoDatabase db) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            app = line.getOptionValue("a");

            if( line.hasOption( "i" ) ) {
                inputFile =line.getOptionValue("i");
            }
            if( line.hasOption( "c" ) ) {
                collection =line.getOptionValue("c");
            }
            if( line.hasOption( "q" ) ) {
                query =line.getOptionValue("q");
            }

        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        if (app == null) {
            System.out.println("App does not exist");
            this.printIntroductionAndUsage();
            System.exit(0);
        }
        if (app.equals(AppNames.ShowVars.getName())) {
            new ShowVars(db, this.collection);
        }
        else if (app.equals(AppNames.ShowColl.getName())) {
            String[] news ={this.inputFile,this.outputFileDirS,this.QCmethod,this.readsNumber};
            new ShowColl(db);
        }
        else if (app.equals(AppNames.Query.getName())) {
            String[] news ={this.inputFile,this.outputFileDirS,this.QCmethod,this.readsNumber};
            new Query(db, this.query);
        }
        else if (app.equals(AppNames.Download.getName())) {
            String[] news ={this.inputFile,this.outputFileDirS,this.QCmethod,this.readsNumber};
            new Download(db, this.collection);
        }
        else {
            System.out.println("App does not exist");
            this.printIntroductionAndUsage();
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
        sb.append("It uses two options to run its apps. \"-a\" is used to select an app. \"-f\" is used to provide a parameter file of an app.\n");
        sb.append("e.g. The basic function of it shows below: ");
        sb.append("java -Xmx100g -jar SiPAS-tools.jar -a Parsing -f parameter_parsing.txt > log.txt &\n");
        sb.append("\nAvailable apps in SiPAS-tools include,\n");
        for (int i = 0; i < AppNames.values().length; i++) {
            sb.append(AppNames.values()[i].getName()).append("\n");
        }
        sb.append("\nPlease visit https://github.com/PlantGeneticsLab/SiPAS-tools for details.\n");
        return sb.toString();
    }

    public static void main (String[] args) {

        new Entrance(args);
    }

}











