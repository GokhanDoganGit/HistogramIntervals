import model.HistogramModel;
import services.MeanCalculationService;
import services.ReadingFileService;
import services.VarianceCalculationService;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class Histogram {
    /*Main Class that only calls for reading File*/
    public static void main(String[] args) {

        readFile();

    }

    /*Read File Method is getting the file from the resources and sent to Reading File Services by using thread
    * Then data which is received from this service sent the sortAndFound() method
    * */
    private static void readFile() {
        try {
            URL res = Histogram.class.getClassLoader().getResource("intervalsSamples.txt");
            File myObj = Paths.get(res.toURI()).toFile();
            ReadingFileService readingFile = new ReadingFileService(myObj);
            Thread thread = new Thread(readingFile);
            thread.start();
            thread.join();
            sortAndFound(readingFile.getHistogramModelList(),readingFile.getSamples());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /*Sorting and find the samples in intervals and
    * sent to datas to printing method*/
    private static void sortAndFound(List<HistogramModel> histogramModelList,List<Double> samples) throws InterruptedException {
        HashMap<HistogramModel, Integer> map = new HashMap<>();
        Collections.sort(samples);
        List<HistogramModel> sortedList = histogramModelList.stream().sorted(Comparator.comparing(HistogramModel::getX)).collect(Collectors.toList());
        List<Double> notFoundList = new ArrayList<>();
        double mean = 0;
        double variance = 0;

        int i = 0;
        int found = 0;
        List<Double> foundList = new ArrayList<>();

        /*Main code block that checking samples are in the right intervals
        * Min loop is considered and sent samples to mean and variance calculation services with threads */
        for (int j=0; samples.size()>j;j++){
            while (sortedList.size()>i) {
                if (samples.get(j) >= sortedList.get(i).getX() && samples.get(j) < sortedList.get(i).getY()) {
                    found++;
                    foundList.add(samples.get(j));
                    map.put(sortedList.get(i), found);
                    MeanCalculationService meanCalculation = new MeanCalculationService(foundList);
                    Thread threadMean = new Thread(meanCalculation);
                    threadMean.start();
                    threadMean.join();
                    mean = meanCalculation.getMean();
                    VarianceCalculationService varianceCalculation = new VarianceCalculationService(foundList);
                    Thread threadVariance = new Thread(varianceCalculation);
                    threadVariance.start();
                    threadVariance.join();
                    variance = varianceCalculation.getVariance();
                    break;
                }
                else if(samples.get(j)>= sortedList.get(i).getY()){
                    found = 0;
                    if(!map.containsKey(sortedList.get(i))){
                        map.put(sortedList.get(i), found);
                    }
                    i++;
                    continue;
                }
                else if(samples.get(j)<= sortedList.get(i).getX()){
                    break;
                }
            }
        }
        /*Purpose for outliers*/
        for (Double sample : samples) {
            if (!foundList.contains(sample)) {
                notFoundList.add(sample);
            }
        }

        printMethod(map,notFoundList,mean,variance);
    }

    /*Only purpose is printing*/
    private static void printMethod(HashMap<HistogramModel, Integer> map,List<Double> notFoundList,double mean,double variance) {
        for (Map.Entry<HistogramModel, Integer> entry : map.entrySet()) {
            HistogramModel key = entry.getKey();
            int value = entry.getValue();
            System.out.println("[" + key.getX() + "," + key.getY() + ")" + ":" + value);
        }
        NumberFormat formatter = new DecimalFormat("#0.000");
        System.out.println("Outliers:" + notFoundList);
        System.out.println("Sample Mean:" + formatter.format(mean));
        System.out.println("Sample Variance:" + formatter.format(variance));
    }

    /*Checking the model is valid or not*/
    public static boolean checkValid(List<HistogramModel> list, HistogramModel histogramModel) {
        if(histogramModel.getX()>histogramModel.getY()){
            return false;
        }
        for (HistogramModel validhistogramModel:list) {
            if((histogramModel.getX()>validhistogramModel.getX() && histogramModel.getX()<validhistogramModel.getY()) ||
                    (histogramModel.getY()>validhistogramModel.getX() && histogramModel.getY()<validhistogramModel.getY())){
                return false;
            }
        }
        return true;
    }

}
