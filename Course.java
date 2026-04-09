import java.util.ArrayList;

public class Course {
    private String courseCode;
    private String courseName;
    private ArrayList<Student> students;

    public Course(String courseCode, String courseName) throws InvalidDataException {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new InvalidDataException("Course code cannot be null or empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new InvalidDataException("Course name cannot be null or empty");
        }
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student s) throws InvalidDataException {
        if (s == null) {
            throw new InvalidDataException("Student cannot be null");
        }
        students.add(s);
    }

    public double getClassAverage() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student s : students) {
            sum += s.calculateAverage();
        }
        return sum / students.size();
    }

    public Student getTopStudent() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.calculateAverage() > top.calculateAverage()) {
                top = s;
            }
        }
        return top;
    }

    public String generateGradeReport() {
        StringBuilder report = new StringBuilder();

        report.append("Course: ").append(courseName)
                .append(" (").append(courseCode).append(")\n\n");

        for (Student s : students) {
            report.append("ID: ").append(s.getStudentId())
                    .append(", Name: ").append(s.getName())
                    .append(", Avg: ").append(String.format("%.2f", s.calculateAverage()))
                    .append(", Grade: ").append(s.getLetterGrade())
                    .append("\n");
        }

        report.append("\nClass Average: ")
                .append(String.format("%.2f", getClassAverage()));

        Student top = getTopStudent();
        if (top != null) {
            report.append("\nTop Student: ")
                    .append(top.getName())
                    .append(" (").append(String.format("%.2f", top.calculateAverage())).append(")");
        }

        return report.toString();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }
}