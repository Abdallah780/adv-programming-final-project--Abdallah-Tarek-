import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {

    public void generateSummaryReport(Course course, String outputFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("=========================================");
            writer.println("          GRADE SUMMARY REPORT");
            writer.println("=========================================");
            writer.println("Course: " + course.getCourseCode() + " - " + course.getCourseName());
            writer.println("Total Students: " + course.getStudents().size());
            double classAverage = course.getClassAverage();
            writer.printf("Class Average: %.1f\n\n", classAverage);
            Map<String, Integer> gradeCounts = getGradeCounts(course);
            int totalStudents = course.getStudents().size();
            String[] grades = {"A", "B", "C", "D", "F"};
            writer.println("Grade Distribution:");
            for (String grade : grades) {
                int count = gradeCounts.getOrDefault(grade, 0);
                double percent = totalStudents == 0 ? 0 : (count * 100.0) / totalStudents;
                writer.printf("%s: %d students (%.0f%%)\n", grade, count, percent);
            }
            Student topStudent = course.getTopStudent();
            if (topStudent != null) {
                double topAvg = topStudent.calculateAverage();
                writer.printf("\nTop Student: %s - %s (%.1f)\n",
                        topStudent.getStudentId(),
                        topStudent.getName(),
                        topAvg);
            }
            writer.println("=========================================");
        } catch (IOException e) {
            System.out.println("Error writing summary report: " + e.getMessage());
        }
    }
    public void generateGradeDistribution(Course course, String outputFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            Map<String, Integer> gradeCounts = getGradeCounts(course);
            int totalStudents = course.getStudents().size();

            writer.println("Grade Distribution Histogram:");
            for (Map.Entry<String, Integer> entry : gradeCounts.entrySet()) {
                String grade = entry.getKey();
                int count = entry.getValue();
                double percent = totalStudents == 0 ? 0 : (count * 100.0) / totalStudents;

                writer.printf("%s: %d students (%.0f%%) ", grade, count, percent);

                int stars = (int) (percent / 2);
                for (int i = 0; i < stars; i++) {
                    writer.print("*");
                }
                writer.println();
            }

        } catch (IOException e) {
            System.out.println("Error writing grade distribution: " + e.getMessage());
        }
    }

    public void generateErrorLog(String errorMessage, String logFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
            writer.println(errorMessage);
        } catch (IOException e) {
            System.out.println("Error writing error log: " + e.getMessage());
        }
    }
    private Map<String, Integer> getGradeCounts(Course course) {
        Map<String, Integer> gradeCounts = new HashMap<>();
        gradeCounts.put("A", 0);
        gradeCounts.put("B", 0);
        gradeCounts.put("C", 0);
        gradeCounts.put("D", 0);
        gradeCounts.put("F", 0);
        for (Student s : course.getStudents()) {
            String letter = s.getLetter();
            gradeCounts.put(letter, gradeCounts.get(letter) + 1);
        }

        return gradeCounts;
    }
}