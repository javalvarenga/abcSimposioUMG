package simposio.studentForm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simposio.studentForm.Clases.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}