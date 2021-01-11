package services;

import java.util.List;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class VarianceCalculationService implements Runnable {

    /*Variables*/
    private List<Double> sample;
    private double mean;
    private double variance;

    /*Constructors*/
    public VarianceCalculationService() {
    }

    public VarianceCalculationService(List<Double> sample) {
        this.sample = sample;
    }

    /*Method that calculate variance of the samples*/
    @Override
    public void run() {
        double sampleToplam=0;
        if(sample.size()>0){
            for (Double sample:sample) {
                sampleToplam = sampleToplam + sample;
            }
            mean = (mean * (sample.size() - 1) + sampleToplam) / sample.size();
            for (Double sample : sample) {
                variance = variance + Math.pow((sample - mean), 2);
            }
            variance = variance / (sample.size() - 1);
        }
        System.out.println(variance);
    }

    /*Getter*/
    public double getVariance() {
        return variance;
    }
}
