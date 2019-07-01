// top 100 vente par magasain par jour ( quatite de chaque produit vendue par magasin)


// filter magaisan id in the transacation file




import org.carrefour.Generator.GenerateData;
import org.carrefour.dao.FileRW;
import org.carrefour.services.TopSaleByStore;

import java.io.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import java.util.stream.Collectors;


public class Main {

    public static String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        System.out.println("Current time of the day using Date - 12 hour format: " + formattedDate);
        return  formattedDate ;
    }



    public static void main(String[] args) throws IOException, ParseException {


        if(args.length != 5){
            System.out.println("missed arguments");
            System.exit(0);
        }



        // Result Dir
        String transPath = args[0]; //"/home/lafi/IdeaProjects/newData/transaction/transactions_20190613.data";
        String refDir = args[1];//"/home/lafi/IdeaProjects/newData/ref/";

        String outputPath = args[2];//"/home/lafi/IdeaProjects/newData/1200Mag/";

        String topHundredDir = args[3]; //"/home/lafi/IdeaProjects/newData/topHundredMag/";
        String topHundredCa = args[4];//"/home/lafi/IdeaProjects/newData/topHundredCA/";




        String currentInit = getCurrentTimeUsingDate();
        //GenerateData.generate();


        // Split Transaction file for each Magasain
        Map<String, FileWriter> splitTra = new HashMap<String, FileWriter>();
        FileRW fileRW = new FileRW();

        fileRW.splitTransaction(transPath, outputPath);



        Map<String, Double> sales = new HashMap<>();
        // First Question
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.FRENCH);

        String dateInString = "2019/06/14";
        Date date = formatter.parse(dateInString);

        // Get Transaction files for each Mag

        List<String> transactionByMag = fileRW.listFileName(outputPath);



        // For each Mag Get Top 100 product starting from "2019/06/14"
        TopSaleByStore topSaleByStore = new TopSaleByStore();

        for(String trans: transactionByMag){
            String magID = trans.substring(trans.lastIndexOf("/") + 1).replaceFirst("[.][^.]+$", "");
            sales.put(magID,0.);
            //System.out.println("Top 100 product for the mag:" + magID);
            for(int i = 13; i < 20; i++ ){
                String refByMag = refDir + "/reference_prod_" + magID + "_201906" + Integer.toString(i) + ".data";

                Map<String,Double> topProduct = topSaleByStore.getTopProduct(trans, refByMag);
                Map<String,Double> topHundredProduct = topProduct.entrySet().
                        stream().
                        limit(100).
                        collect(Collectors.
                                toMap(Map.Entry::getKey,Map.Entry::getValue,
                                        (oldValue, newValue)->oldValue,LinkedHashMap::new));
                System.out.println("Transaction:" + trans + System.lineSeparator());
                System.out.println(refByMag + System.lineSeparator());
                System.out.println("Top 100 product:" + topHundredProduct + System.lineSeparator());
                fileRW.writeFileToDisk(topHundredProduct,topHundredDir + "top_100_ventes_" + magID +"_201906" + Integer.toString(i) + ".data" );

                sales.put(magID,sales.get(magID) + topProduct.values().stream().reduce(0.0,Double::sum));
                //System.out.println(topHundredProduct.values());
            }


        }
        // Seccond Question
       sales = sales.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue)->oldValue,LinkedHashMap::new));
                sales.entrySet().
                stream().
                sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).
                limit(100).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue, newValue)->oldValue,LinkedHashMap::new));
        System.out.println("Top 100 CA :" + sales);
        // Write Top CA
        for(String key: sales.keySet()){
            Writer writer = new FileWriter(topHundredCa + "top_100_ca_" + key + "_20190613-J7.data");
            writer.write(sales.get(key).toString() + System.lineSeparator());
            writer.flush();
            writer.close();
        }


        String finalTime = getCurrentTimeUsingDate() ;

        System.out.println("Init  Job OK timer :  " + currentInit);

        System.out.println("End Job OK timer :  "+getCurrentTimeUsingDate());
    }
}






