import java.util.ArrayList;

public abstract class GradeProcessor {

    public abstract double processGrades(ArrayList<Double> grades);

    public void validateGrades(ArrayList<Double> grades) throws InvalidGradeException {
        if (grades == null) {
            throw new InvalidGradeException("Grades list cannot be null");
        }
        for (Double grade : grades) {
            if (grade == null || grade < 0 || grade > 100) {
                throw new InvalidGradeException("Invalid grade: " + grade);
            }
        }
    }
}