import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" Student Grade Processing System");
        System.out.println("========================================");

        createSampleFiles();

        System.out.println("\n[1] Reading student files...");
        FileInputService inputService = new FileInputService();

        String[] files = {
                "Student1.txt",
                "Student2.txt",
                "Student3.txt"
        };

        ArrayList<Student> allStudents = inputService.readMultipleFiles(files);
        System.out.println("Total students loaded: " + allStudents.size());

        Course course;
        try {
            course = new Course("CS001", "Advanced Programming");
            for (Student s : allStudents) {
                course.addStudent(s);
            }
        } catch (InvalidDataException e) {
            System.out.println("Course error: " + e.getMessage());
            return;
        }

        System.out.println("\n[2] Validating and analyzing grades...");
        GradeAnalyzer analyzer = new GradeAnalyzer();

        for (Student s : course.getStudents()) {
            try {
                analyzer.validateGrades(s.getGrades());
                double avg = analyzer.processGrades(s.getGrades());

                System.out.println("----------------------------------------");
                System.out.println("Student: " + s.getName());
                System.out.println("Average from analyzer: " + String.format("%.2f", avg));

            } catch (InvalidGradeException e) {
                System.out.println("Invalid grade for " + s.getName() + ": " + e.getMessage());
            }
        }

        System.out.println("\n[3] Running grade calculator threads...");
        ThreadSafeGradeRepository repository = new ThreadSafeGradeRepository();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (Student s : course.getStudents()) {
            executor.submit(new GradeCalculatorTask(s, repository));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Some tasks did not finish in time.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\n[4] Calculation Results:");
        for (Student s : course.getStudents()) {
            String result = repository.getAllResults().get(s.getStudentId());
            if (result != null) {
                System.out.println(result);
            }
        }

        System.out.println("\n[5] Generating reports...");
        new File("reports").mkdirs();

        ReportGenerator reporter = new ReportGenerator();
        reporter.generateSummaryReport(course, "reports/summary_report.txt");
        reporter.generateGradeDistribution(course, "reports/grade_distribution.txt");
        reporter.generateErrorLog("System started successfully.", "reports/error_log.txt");

        writeFullGradeReport(course, "reports/grade_report.txt");

        System.out.println("\n[6] Full Course Report:");
        System.out.println(course.generateGradeReport());

        System.out.println("\n[7] Demonstrating resource manager...");
        ResourceManager manager = new ResourceManager();
        manager.methodNoDeadlock();

        System.out.println("\nDone. Reports saved inside the 'reports' folder.");
    }

    private static void createSampleFiles() {
        try {
            PrintWriter w1 = new PrintWriter("Student1.txt");
            w1.println("2311,Ahmed Tarek,83,79,88,91");
            w1.println("2312,Mahmoud Adel,76,81,73,85");
            w1.println("2313,Sara Hossam,92,95,90,94");
            w1.close();

            PrintWriter w2 = new PrintWriter("Student2.txt");
            w2.println("2314,Yassin Omar,67,72,70,69");
            w2.println("2315,Malak Wael,89,84,91,87");
            w2.println("2316,Seif Mostafa,58,64,61,66");
            w2.close();

            PrintWriter w3 = new PrintWriter("Student3.txt");
            w3.println("2317,Rana Samy,97,93,96,98");
            w3.println("2318,Adham Nabil,74,77,71,79");
            w3.println("2319,Laila Sherif,86,88,84,90");
            w3.close();

            System.out.println("Sample files created successfully.");
        } catch (IOException e) {
            System.out.println("Could not create sample files: " + e.getMessage());
        }
    }

    private static void writeFullGradeReport(Course course, String outputFile) {
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.println(course.generateGradeReport());
        } catch (IOException e) {
            System.out.println("Error writing full grade report: " + e.getMessage());
        }
    }
}