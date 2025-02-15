document.addEventListener("DOMContentLoaded", function() {
    fetchStudents();
});


const calculateAge = (birthdate) => {
    if (!birthdate) return 0;
  
    const birthDate = new Date(birthdate);
    const currentDate = new Date();
  
    let age = currentDate.getFullYear() - birthDate.getFullYear();
    
    // Verifica si ya pasó el cumpleaños este año
    const monthDiff = currentDate.getMonth() - birthDate.getMonth();
    const dayDiff = currentDate.getDate() - birthDate.getDate();
  
    // Si el cumpleaños aún no ha pasado este año, resta 1
    if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
      age--;
    }
  
    return age;
  };

function fetchStudents() {
    fetch("/students/getAllStudents")
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("studentsTable");
            tableBody.innerHTML = ""; // Limpiar tabla antes de agregar nuevos datos
            

            data.forEach(student => {
                const row = document.createElement("tr");
                const age = calculateAge(student?.birthdate);

                row.innerHTML = `
                    <td>${student.studentId}</td>
                    <td>${student.studentName}</td>
                    <td>${age}</td>
                    <td>${student.phone}</td>
                    <td>${student.carnet}</td>
                    <td>${student.email}</td>
                    <td>${student.paymentStatus ? "Pagado" : "Pendiente"}</td>
                    <td>${student.registerDate}</td>
                    <td>${student.birthdate}</td>
                    <td>
                        <button id="edit-btn" onclick="editStudent(${student.studentId})">Editar</button>
                        <button id="delete-btn" onclick="deleteStudent(${student.studentId})">Eliminar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error al obtener los estudiantes:", error));
}

function editStudent(studentId) {
    // Redirige a editStudent.html pasando el ID como query param
    window.location.href = `/views/editStudent.html?studentId=${studentId}`;
}

function deleteStudent(studentId) {
    if (!confirm("¿Estás seguro de que deseas eliminar este estudiante?")) return;

    fetch(`/students/delete/${studentId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) throw new Error("Error al eliminar el estudiante");
        return response.json();
    })
    .then(() => {
        alert("Estudiante eliminado correctamente");
        fetchStudents(); // Refrescar la tabla
    })
    .catch(error => console.error("Error al eliminar estudiante:", error));
}
