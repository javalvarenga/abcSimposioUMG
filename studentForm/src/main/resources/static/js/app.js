  // Handle form submission via JavaScript and fetch API
  document.getElementById("studentForm").addEventListener("submit", function (e) {
    e.preventDefault();  // Prevenir el comportamiento predeterminado del formulario

    let formData = new FormData(this);  // Crear un objeto FormData con los datos del formulario
    let studentData = {};
    formData.forEach((value, key) => {
        studentData[key] = value;  // Convertir los datos del formulario en un objeto
    });

    // Enviar una solicitud POST con los datos del estudiante en formato JSON
    fetch("http://localhost:8080/students/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(studentData)  // Convertir los datos del formulario a JSON
    })
    .then(response => response.json())
    .then(data => {
        alert("Estudiante guardado con éxito");
        window.location.href = "/";
    })
    .catch(error => {
        console.error("Error:", error);
    });
});