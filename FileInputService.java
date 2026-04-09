import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileInputService {
    public ArrayList<Student> readStudentFile(String filename) {
        ArrayList<Student> students = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String studentId = parts[0].trim();
                String name = parts[1].trim();
                Student student = new Student(studentId, name);
                for (int i = 2; i < parts.length; i++) {
                    try {
                        double grade = Double.parseDouble(parts[i].trim());
                        student.addGrade(grade);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid grade format: " + parts[i]);
                    }
                }
                students.add(student);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (Exception e) {
            System.out.println("Error reading file " + filename + ": " + e.getMessage());
        }
        return students;
    }
    public ArrayList<Student> readMultipleFiles(String[] filenames) {
        ArrayList<Student> allStudents = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();
        ArrayList<ArrayList<Student>> threadResults = new ArrayList<>();
        for (String file : filenames) {
            ArrayList<Student> studentsFromThread = new ArrayList<>();
            threadResults.add(studentsFromThread);
            Thread t = new Thread(() -> {
                ArrayList<Student> list = readStudentFile(file);
                synchronized (studentsFromThread) {
                    studentsFromThread.addAll(list);
                }
            });
            threads.add(t);
            t.start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        for (ArrayList<Student> list : threadResults) {
            allStudents.addAll(list);
        }
        return allStudents;
    }
}