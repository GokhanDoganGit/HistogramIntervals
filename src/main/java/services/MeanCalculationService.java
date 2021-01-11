package services;

import java.util.List;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class MeanCalculationService implements Runnable{

    /*Variables*/
    private List<Double> sample;
    private double mean;

    /*Constructors*/
    public MeanCalculationService() {
    }

    public MeanCalculationService(List<Double> sample) {
        this.sample = sample;
    }

    /*Method that calculate mean of the samples*/
    @Override
    public void run() {
        double sampleToplam=0;
        if(sample.size()>0){
            for (Double sample:sample) {
                sampleToplam = sampleToplam + sample;
            }
            mean = (mean * (sample.size() - 1) + sampleToplam) / sample.size();
            System.out.println(mean);
        }
    }

    /*Getter*/
    public double getMean() {
        return mean;
    }
}
