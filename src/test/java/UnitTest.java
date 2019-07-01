import org.carrefour.Generator.GenerateData;
import org.carrefour.dao.FileRW;
import org.carrefour.services.TopSaleByStore;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class UnitTest {


    public String filePath( String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return  file.getAbsolutePath();
    }
    /**
     * Test the IO Exception when permission denied or file not found
     * @throws IOException
     */

    FileRW tester = new FileRW() ;



    @Test (expected = IOException.class)

    public void testSplitTransaction() throws IOException {
        tester.splitTransaction("");
    }

    @Test

    public void testGetTopProduct(){
        TopSaleByStore topSaleByStore = new TopSaleByStore();

        String transFilePath = filePath("e80d2d7e-5970-4bbb-8435-35c8b6f2ee07.data");


        String refProductFilePath = filePath("reference_prod_e80d2d7e-5970-4bbb-8435-35c8b6f2ee07_20190619.data");


        Map<String,Double> toppro = topSaleByStore.getTopProduct(transFilePath, refProductFilePath);
        Map<String,Double> map = new HashMap<>();
        map.put("144",3566.06);
        map.put("1180",1967.8);
        map.put("267",2050.91);

        assertEquals(map.get("144"), toppro.get("144"));
        assertEquals(map.get("1180"), toppro.get("1180"));
        assertEquals(map.get("267"), toppro.get("267"));


    }



    }
