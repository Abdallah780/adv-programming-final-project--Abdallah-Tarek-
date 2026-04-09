import java.util.ArrayList;

public class GradeAnalyzer extends GradeProcessor {
    @Override
    public double processGrades(ArrayList<Double> grades) {
        try {
            validateGrades(grades);
        } catch (InvalidGradeException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        if (grades.size() == 0) {
            System.out.println("No grades to analyze");
            return 0;
        }
        double sum = 0;
        double max = grades.get(0);
        double min = grades.get(0);
        for (int i = 0; i < grades.size(); i++) {
            double g = grades.get(i);
            sum = sum + g;
            if (g > max) {
                max = g;
            }
            if (g < min) {
                min = g;
            }
        }
        double average = sum / grades.size();
        double sdSum = 0;
        for (int i = 0; i < grades.size(); i++) {
            double g = grades.get(i);
            sdSum = sdSum + (g - average) * (g - average);
        }
        double stdDev = Math.sqrt(sdSum / grades.size());
        System.out.println("Highest grade: " +max);
        System.out.println("Lowest grade: " +min);
        System.out.println("Average grade: " +average);
        System.out.println("Standard deviation: " +stdDev);
        return average;
    }
}