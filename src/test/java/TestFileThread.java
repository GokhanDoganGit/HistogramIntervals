import model.HistogramModel;
import org.junit.jupiter.api.Test;
import services.ReadingFileService;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class TestFileThread {

    /*Test Class for reading two different files by using threads from resources and
    * concat erroneousInputs, samples and histogramModelList and printing them*/
    @Test
    public void testFile(){
        try {
            URL res = getClass().getClassLoader().getResource("test.txt");
            File file = Paths.get(res.toURI()).toFile();
            URL res2 = getClass().getClassLoader().getResource("test2.txt");
            File myObj = Paths.get(res2.toURI()).toFile();
            ReadingFileService readingFile = new ReadingFileService(myObj);
            ReadingFileService readingFile2 = new ReadingFileService(file);
            Thread thread = new Thread(readingFile);
            Thread thread2 = new Thread(readingFile2);
            thread.start();
            thread2.start();
            thread.join();
            thread2.join();
            List<String> erroneousInputs = Stream.concat(readingFile.getErroneousInputs().stream(), readingFile2.getErroneousInputs().stream()).collect(Collectors.toList());
            List<Double> samples = Stream.concat(readingFile.getSamples().stream(), readingFile2.getSamples().stream()).collect(Collectors.toList());
            List<HistogramModel> histogramModelList = Stream.concat(readingFile.getHistogramModelList().stream(), readingFile2.getHistogramModelList().stream()).collect(Collectors.toList());
            System.out.println("ErroneousInputs:" +erroneousInputs);
            System.out.print("HistogramModels: ");
            for (HistogramModel histogramModel:histogramModelList) {
                System.out.print("[" +histogramModel.getX()+","+histogramModel.getY()+")");
            }
            System.out.println("");
            System.out.print("Samples: ");
            for (Double sample:samples) {
                System.out.print(sample);
                System.out.print(",");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
