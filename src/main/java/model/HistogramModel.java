package model;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class HistogramModel {
    /*Variables*/
    private double x;
    private double y;

    /*Constructor*/
    public HistogramModel(double x,double y){
        this.x = x;
        this.y = y;
    }

    /*Getters*/
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
