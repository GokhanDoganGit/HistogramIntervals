package services;

import model.HistogramModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author Gökhan Doğan on Ara, 2020
 */
public class ReadingFileService implements Runnable{

    /*Variables*/
    private File file;
    private List<String> erroneousInputs = new ArrayList<>();
    private List<HistogramModel> histogramModelList = new ArrayList<>();
    private List<Double> samples = new ArrayList<>();

    /*Constructor*/
    public ReadingFileService(File file) {
        this.file = file;
    }

    /*Getters*/
    public List<String> getErroneousInputs() {
        return erroneousInputs;
    }

    public List<HistogramModel> getHistogramModelList() {
        return histogramModelList;
    }

    public List<Double> getSamples() {
        return samples;
    }

    /*Method that getting files and fill the samples and intervals
    * if the datas are in the file is valid
    * else fill erroneousInputs
    * */
    @Override
    public void run() {
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.startsWith("[")) {
                    data = data.substring(1, data.length() - 1);
                    String[] split = data.split(",");
                    HistogramModel histogramModel = new HistogramModel(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
                    if(histogramModelList.size()>=1){
                        boolean isValid = checkValid(histogramModelList,histogramModel);
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
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Data in the File is MISFORMATTED.");
        }
    }

    /*Method that checks the datas from file is valid or not*/
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
