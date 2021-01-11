import model.HistogramModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class TestHistogram {

    /*Test class for checking the data in the file and printing the erroneousInputs
    * If the data in the file is misformatted it continues with the right ones or
    * enter exception and printing that data is misformatted*/
    @Test
    public void testFile(){
        try {
            List<HistogramModel> histogramModelList = new ArrayList<>();
            List<Double> samples = new ArrayList<>();
            List<String> erroneousInputs = new ArrayList<>();
            Histogram histogram = new Histogram();
            URL res = getClass().getClassLoader().getResource("test.txt");
            File myObj = Paths.get(res.toURI()).toFile();
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.startsWith("[")) {
                    data = data.substring(1, data.length() - 1);
                    String[] split = data.split(",");
                    HistogramModel histogramModel = new HistogramModel(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                    if(histogramModelList.size()>=1){
                        boolean isValid = histogram.checkValid(histogramModelList,histogramModel);
                        if(isValid){
                            histogramModelList.add(histogramModel);
                        }
                    }
                    else if(histogramModelList.size()==0){
                        histogramModelList.add(histogramModel);
                    }

                }
                else if(data.equalsIgnoreCase("")){
                    erroneousInputs.add(data);
                    continue;
                }
                else if (Character.isDigit(data.charAt(0)) && data.matches(".*\\d.*")) {
                    samples.add(Double.parseDouble(data));
                }
                else{
                    erroneousInputs.add(data);
                    continue;
                }
            }
            myReader.close();
            System.out.println(erroneousInputs);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }catch (NumberFormatException e){
            System.out.println("Data in the File is MISFORMATTED.");
        }
    }
}
