package coms309.student;

public class Student {
    private Integer id;
    private String studentName;
    private String major;

    public Student(){

    }

    public Student(Integer id, String studentName, String major){
        this.id = id;
        this.studentName = studentName;
        this.major = major;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setStudentName(String studentName){
        this.studentName = studentName;
    }
    public String getStudentName(){
        return this.studentName;
    }

    public void setMajor(String major){
        this.major = major;
    }
    public String getMajor(){
        return this.major;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + studentName + " - " + major + "\n";
    }
}
