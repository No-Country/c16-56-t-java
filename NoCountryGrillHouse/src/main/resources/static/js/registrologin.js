import { enviarFetch } from './fetch.js';

var registroForm = document.getElementById('registro_form');

registroForm.addEventListener('submit', function (event) {
    event.preventDefault();

    var clienteData = {
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        password2: document.getElementById('password_confirm').value,
        telefono: document.getElementById('telefono').value
    }


    enviarFetch('/clientes/registrar', 'POST', clienteData,
        function (data) {
            console.log(data);
            mostrarMensaje(data, 'success');
        },
        function (error) {
            console.log(error);
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
        mensajeDiv.textContent = mensaje.body;
        mensajeDiv.className = tipo; // Clase CSS para estilo de mensaje (por ejemplo, 'success' o 'error')
    }
});


