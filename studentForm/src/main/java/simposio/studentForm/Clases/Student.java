package simposio.studentForm.Clases;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long studentId;
    private String studentName;
    private String phone;
    private String carnet;
    private String email;
    private boolean paymentStatus;
    private LocalDate registerDate;
    private LocalDate birthdate;


    public Student(){}

    // Constructor con parámetros (opcional, para facilitar la creación de estudiantes)
    public Student(String studentName, String phone, String carnet, String email,boolean paymentStatus, LocalDate registerDate, LocalDate birthdate) {
        this.studentName = studentName;
        this.phone = phone;
        this.carnet = carnet;
        this.email = email;
        this.paymentStatus = paymentStatus;
        this.registerDate = registerDate;
        this.birthdate = birthdate;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
        this.registerDate = registerDate;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


}