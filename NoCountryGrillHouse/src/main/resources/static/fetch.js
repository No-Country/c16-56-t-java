document.addEventListener('DOMContentLoaded', function () {
    var registroForm = document.getElementById('registro');

    registroForm.addEventListener('submit', function (event) {
        event.preventDefault();

        var clienteData = {
            clienteDto: {
                nombre: document.getElementById('nombre').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value,
                telefono: document.getElementById('telefono').value
            },
            direccionDto: {
                calle: document.getElementById('calle').value,
                numero: document.getElementById('numero').value,
                ciudad: document.getElementById('ciudad').value
            }
        };

        fetch('http://localhost:8080/clientes/registrar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(clienteData)
        })
            .then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error('Error en el servidor');
                }
            })
            .then(function (data) {
                console.log(data);
                mostrarMensaje(data, 'success');
            })
            .catch(function (error) {
                console.log(error);
                mostrarMensaje(error.message || 'Error de conexión', 'error');
            });

        function mostrarMensaje(mensaje, tipo) {
            var mensajeDiv = document.getElementById('mensaje');
            if (!mensajeDiv) {
                mensajeDiv = document.createElement('div');
                mensajeDiv.id = 'mensaje';
                document.body.appendChild(mensajeDiv);
            }
            mensajeDiv.textContent = mensaje.direccion.calle;
            mensajeDiv.className = tipo; // Clase CSS para estilo de mensaje (por ejemplo, 'success' o 'error')
        }
    });
});
