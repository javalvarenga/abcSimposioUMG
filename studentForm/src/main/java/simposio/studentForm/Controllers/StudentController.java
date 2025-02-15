package simposio.studentForm.Controllers;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import simposio.studentForm.Clases.Student;
import simposio.studentForm.StudentRepository;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping("/")
    public String redirectToIndex() {
        return "forward:/index.html";  // Redirige a index.html
    }


    @Autowired
    private StudentRepository studentRepository;

    // Guardar estudiante
    @PostMapping("/create2")
    public Student createStudent2(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PostMapping("/create")
    public Object createStudent(@RequestBody Map<String, Object> studentData) {

        // Extraer los parámetros del Map de forma flexible
        String firstName = (String) studentData.get("firstName");
        String secondName = (String) studentData.get("secondName");
        String firstSurname = (String) studentData.get("firstSurname");
        String secondSurname = (String) studentData.get("secondSurname");
        String phone = (String) studentData.get("phone");
        String firstFieldCarnet = (String) studentData.get("firstFieldCarnet");
        String secondFieldCarnet = (String) studentData.get("secondFieldCarnet");
        String thirdFieldCarnet = (String) studentData.get("thirdFieldCarnet");
        String email = (String) studentData.get("email");
        LocalDate registerDate = Optional.ofNullable((String) studentData.get("registerDate"))
                .map(LocalDate::parse)
                .orElse(LocalDate.now());
        LocalDate birthdate = LocalDate.parse((String) studentData.get("birthdate"));

        // Procesar los datos para completar los campos necesarios
        String completedName = String.join(" ", firstName, secondName, firstSurname, secondSurname);
        String completedCarnet = String.join("-",firstFieldCarnet , secondFieldCarnet , thirdFieldCarnet);

        // Crear el objeto Student
        Student student = new Student(completedName, phone, completedCarnet, email, false, registerDate, birthdate);

        // Guardar el estudiante en la base de datos
        return studentRepository.save(student);
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Map<String, Object> studentData) {
        return studentRepository.findById(studentId).map(existingStudent -> {

            String studentName = (String) studentData.get("studentName");
            if (studentName != null && !studentName.trim().isEmpty()) {
                existingStudent.setStudentName(studentName.trim());
            }

            // Construcción del carnet concatenando los tres valores con "-"
            String carnet = (String) studentData.get("carnet");
            if (carnet != null && !carnet.trim().isEmpty()) {
                existingStudent.setCarnet(carnet.trim());
            }


            // Actualizar otros campos solo si se proporcionan
            Optional.ofNullable((String) studentData.get("phone")).ifPresent(existingStudent::setPhone);
            Optional.ofNullable((String) studentData.get("email")).ifPresent(existingStudent::setEmail);

            // Manejar registerDate y birthdate solo si son proporcionados
            Optional.ofNullable((String) studentData.get("registerDate"))
                    .map(LocalDate::parse)
                    .ifPresent(existingStudent::setRegisterDate);
            Optional.ofNullable((String) studentData.get("birthdate"))
                    .map(LocalDate::parse)
                    .ifPresent(existingStudent::setBirthdate);

            // Guardar cambios en la base de datos
            studentRepository.save(existingStudent);
            return ResponseEntity.ok(existingStudent);
        }).orElseGet(() -> ResponseEntity.notFound().build()); // Retornar 404 si no existe
    }

    // Obtener todos los estudiantes
    @GetMapping("/getAllStudents")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // Obtener un estudiante por ID
    @GetMapping("/getStudent/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable long studentId) {
        return studentRepository.findById(studentId)
                .map(ResponseEntity::ok)  // Devuelve el estudiante con código 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build()); // Retorna 404 si no se encuentra
    }



    // Eliminar estudiante
    @DeleteMapping("/delete/{studentId}")
    public void deleteStudent(@PathVariable long studentId) {
        studentRepository.deleteById(studentId);
    }

}

