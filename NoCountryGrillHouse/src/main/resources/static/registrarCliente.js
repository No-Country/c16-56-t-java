import { enviarFetch } from './enviarFetch';

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

        enviarFetch('/clientes/registrar', 'POST', clienteData,
            function (data) {
                mostrarMensaje(data, 'success');
            },
            function (error) {
                mostrarMensaje(error, 'error');
            }
        )

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
