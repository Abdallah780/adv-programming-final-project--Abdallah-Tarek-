import java.util.ArrayList;

public class Student {
    private String studentId;
    private String name;
    private ArrayList<Double> grades;
    public Student(String name, String studentId) throws InvalidDataException
    {
        setName(name);
        setStudentId(studentId);
        this.grades=new ArrayList<>();
    }
    public void setName(String name) throws InvalidDataException
    {
        if(name == null || name.trim().isEmpty())
        {
            throw new InvalidDataException("Invalid Name");
        }
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public void setStudentId(String studentId) throws InvalidDataException
    {
        if(studentId == null || studentId.trim().isEmpty() )
        {
            throw new InvalidDataException("Inavalid Id");
        }
        this.studentId= studentId;
    }
    public String getStudentId()
    {
        return studentId;
    }
    public String getLetterGrade() {
        return getLetter();
    }
    public ArrayList<Double> getGrades() {
        return grades;
    }
    public void addGrade(double grade) throws InvalidDataException
    {
        if(grade <0 ||grade >100)
        {
            throw new InvalidDataException("Invalid Grade");
        }
        grades.add(grade);
    }
    public double calculateAverage()
    {
        double sum = 0;
        for(double g:grades)
        {
            sum +=g;
        }
        return sum / grades.size();
    }
    public String getLetter()
    {
        double avg = calculateAverage();
        if (avg >= 90) {
            return "A";
        }
        else if (avg >=80 && avg <=89) {
            return "B";
        }
        else if(avg >=70)
        {
            return "C";
        }
        else if(avg >=60)
        {
            return "D";
        }
        else
        {
            return "F";
        }
    }
    @Override
    public String toString()
    {
        return "Name: " + name +
                "\nStudent Id:" + studentId +
                "\nGrades: " + grades +
                "\nAverage: " + calculateAverage() +
                "\n Letter: " + getLetter();

    }
}


