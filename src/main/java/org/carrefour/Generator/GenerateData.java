package org.carrefour.Generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import java.util.Calendar;


public class GenerateData {




    static int NUBMER_OF_TRX = 500000;
    static Integer NUMBER_OF_DAYS = 7;

    static List<String> days  = null;

    static DateFormat df = new SimpleDateFormat("yyyyMMdd");


    static List<String>  aa = new ArrayList<String>();

    static Integer NUMBER_OF_PRODUCT = 2000;
    static int NUBMER_OF_MAGASIN = 1200;
    static File refMag = null;
    static FileWriter fw = null;
    static String PATH = "/home/lafi/Testcarrefour/";
    static String PREFIX_MAG = "reference_prod_";
    static String EXT = ".data";
    static String SEPARATOR = "|";

    static List<String> getDays() throws ParseException {
        if(days == null) {
            days = new ArrayList();
            String toDay = "20190613";
            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < NUMBER_OF_DAYS; i++) {
                cal.setTime(df.parse(toDay));
                cal.add(Calendar.DATE, i);
                cal.setTime(df.parse(toDay));
                cal.add(Calendar.DATE, i);
                days.add(df.format(cal.getTime()));
            }
        }
        return days;
    }

    static BigDecimal randomPrice(int range) {
        BigDecimal max = new BigDecimal(range);
        BigDecimal randFromDouble = new BigDecimal(Math.random());
        BigDecimal actualRandomDec = randFromDouble.multiply(max);
        actualRandomDec = actualRandomDec
                .setScale(2, BigDecimal.ROUND_DOWN);
        return actualRandomDec;
    }

    public static void generate() throws IOException, ParseException {

        //
        for (int i = 0; i < NUBMER_OF_MAGASIN; i++) {

            String magasinId = UUID.randomUUID().toString();

            for (String day: getDays()) {

                refMag = new File(PATH + PREFIX_MAG+ magasinId+ "_"+ day+ EXT);
                fw = new FileWriter(refMag);


                StringBuffer str = null;

                for (int j = 1; j <= NUMBER_OF_PRODUCT; j++) {
                    str = new StringBuffer();
                    str.append(j)
                            .append(SEPARATOR)
                            .append(randomPrice(100))
                            .append("\n");
                    fw.write(str.toString());
                    if(j%1000 == 0) {
                        fw.flush();

                    }
                }
                fw.close();
            }

        }
        //

    }


}
