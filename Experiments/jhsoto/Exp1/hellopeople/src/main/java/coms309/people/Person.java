package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String major;

    private String college;

    private String email;

    private int ID;

    private String classification;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String major, String college, String email, int ID, String classification){
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
        this.college = college;
        this.email = email;
        this.ID = ID;
        this.classification = classification;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return this.major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getClassification() {
        return this.classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return firstName + " "
               + lastName + " "
               + major + " "
               + college + " "
                + email + " "
                + ID + " "
                + classification;
    }
}
