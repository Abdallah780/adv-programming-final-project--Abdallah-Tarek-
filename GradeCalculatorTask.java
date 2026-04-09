public class GradeCalculatorTask implements Runnable {

    private Student student;
    private ThreadSafeGradeRepository repository;

    public GradeCalculatorTask(Student student, ThreadSafeGradeRepository repository) {
        this.student = student;
        this.repository = repository;
    }

    @Override
    public void run() {
        double average = student.calculateAverage();
        String letterGrade = student.getLetterGrade();

        String result = "ID: " + student.getStudentId() +
                ", Name: " + student.getName() +
                ", Avg: " + String.format("%.2f", average) +
                ", Grade: " + letterGrade;

        repository.addResult(student.getStudentId(), result);
    }
}