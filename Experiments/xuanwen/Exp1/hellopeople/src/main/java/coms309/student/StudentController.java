package coms309.student;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    List<Student> studentList = new ArrayList<>();

    /**
     * GET: to get the inputs / data
     * show the list of students
     */
    @GetMapping("/student")
    public String getStudentList(){
        return studentList.toString();
    }

    /**
     * find student info based on their id
     * @param id student id
     * @return info of the student
     */
    @GetMapping("/student/{id}")
    public @ResponseBody Student getStudent(@PathVariable Integer id){
        for(int i=0; i<studentList.size(); i++){
            if(studentList.get(i).getId().equals(id)){
                return studentList.get(i);
            }
        }
        return null;
    }

    /**
     * POST: create data
     * add new students
     */
    @PostMapping("/student")
    public @ResponseBody String addStudent(@RequestBody Student student){
        System.out.println(student);
        studentList.add(student);
        return "Added student (" + student.getId() + ") " + student.getStudentName();
    }

    /**
     * PUT: update data
     * find student based on their id, update with new input
     */
    @PutMapping("/student/{id}")
    public @ResponseBody String updateStudent(@PathVariable Integer id, @RequestBody Student s){
        for(int i=0; i<studentList.size(); i++){
            if(studentList.get(i).getId().equals(id)){
                studentList.set(i, s);
                return studentList.toString();
            }
        }
        return "Student not found";

    }

    /**
     * DELETE: delete data
     * find student based on their id and remove the student
     */
    @DeleteMapping("/student/{id}")
    public @ResponseBody String deleteStudent(@PathVariable Integer id, @RequestBody Student s){
        Student target = getStudent(id);
        studentList.remove(target);
        return studentList.toString();
    }
}
