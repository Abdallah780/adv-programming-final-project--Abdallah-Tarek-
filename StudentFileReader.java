import java.io.*;
import java.util.ArrayList;

public class StudentFileReader extends Thread {

    private String fileName;
    private ThreadSafeGradeRepository repository;

    public StudentFileReader(String fileName, ThreadSafeGradeRepository repository) {
        this.fileName = fileName;
        this.repository = repository;
    }
    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String studentId = parts[0].trim();
                String name = parts[1].trim();
                ArrayList<Double> grades = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    try {
                        double grade = Double.parseDouble(parts[i].trim());
                        grades.add(grade);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid grade format in file " + fileName + ": " + parts[i]);
                    }
                }
                Student student = new Student(studentId, name);
                for (Double g : grades) {
                    student.addGrade(g);
                }
                GradeCalculatorTask task = new GradeCalculatorTask(student, repository);
                Thread calcThread = new Thread(task);
                calcThread.start();
                calcThread.join();
            }
        } catch (Exception e) {
            System.out.println("Error reading file " + fileName + ": " + e.getMessage());
        }
    }
}