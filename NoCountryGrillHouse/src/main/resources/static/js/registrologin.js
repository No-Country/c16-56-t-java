import { enviarFetch } from './fetch.js';

let registroForm = document.getElementById('registro_form');

registroForm.addEventListener('submit', function (event) {
    event.preventDefault();

    let nombre = document.getElementById('nombre').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let password2 = document.getElementById('password_confirm').value;
    let telefono = document.getElementById('telefono').value;

    let clienteData = {
        nombre: nombre,
        email: email,
        password: password,
        password2: password2,
        telefono: telefono
    }


    enviarFetch('/cliente/registrar', 'POST', clienteData,
        function (data) {
            mostrarMensaje(data, true, 'alert', 'alert-success', 'alerta-exito');
        },
        function (error) {
            mostrarMensaje(error.message, false, 'alert', 'alert-danger', 'alerta-error');
        }
    )

    function mostrarMensaje(mensaje, esExito, ...clases) {
        let mensajeDiv = document.getElementById('mensaje');
        if (!mensajeDiv) {
            mensajeDiv = document.createElement('div');
            mensajeDiv.id = 'mensaje';
        }

        mensajeDiv.role = 'alert';
        mensajeDiv.className = '';

        clases.forEach(clase => {
            mensajeDiv.classList.add(clase);
        });

        if (esExito) {
            limpiarCampos();
            const cerrarRegistro = document.querySelector('#inicio');
            cerrarRegistro.style.display = 'unset';
            const ocultarRegistro1 = document.querySelector('#registro');
            ocultarRegistro1.style.display = 'none';
            mensajeDiv.textContent = 'Se ha registrado correctamente!';
            document.body.insertBefore(mensajeDiv, document.body.firstChild);
            setTimeout(function () {
                mensajeDiv.style.display = 'none';
            }, 4000);
        } else {
            mensajeDiv.textContent = mensaje;
            const primerElemento = registroForm.firstElementChild;
            if (primerElemento) {
                registroForm.insertBefore(mensajeDiv, primerElemento);
            }
        }
    }

});

function limpiarCampos() {
    document.getElementById('nombre').value = '';
    document.getElementById('email').value = '';
    document.getElementById('password').value = '';
    document.getElementById('password_confirm').value = '';
    document.getElementById('telefono').value = '';
}

let loginForm = document.getElementById('login_form');

loginForm.addEventListener('submit', function (event) {
    event.preventDefault();

    let email = document.getElementById('loginEmail').value;
    let password = document.getElementById('loginContrasenia').value;

    let loginData = {
        email: email,
        password: password,
    }


    enviarFetch('/auth/login', 'POST', loginData,
        function (data) {
            localStorage.setItem('token', data.token);

            localStorage.setItem('rol', data.rol);

            if (data.rol == 'CLIENTE') {
                window.location.href = '/cliente';
            } else if (data.rol == 'ADMIN') {
                window.location.href = '/admin';
            } else if (data.rol == 'JEFE_COCINA') {
                window.location.href = '/jefe_cocina';
            } else if (data.rol == 'MESERO') {
                window.location.href = '/mesero';
            }


        },
        function (error) {
            mostrarMensaje(error.message, 'alert', 'alert-danger', 'alerta-error');
        }
    )

    function mostrarMensaje(mensaje, ...clases) {
        let mensajeDiv = document.getElementById('mensaje');
        if (!mensajeDiv) {
            mensajeDiv = document.createElement('div');
            mensajeDiv.id = 'mensaje';
        }

        mensajeDiv.role = 'alert';
        mensajeDiv.className = '';
        mensajeDiv.textContent = mensaje;

        clases.forEach(clase => {
            mensajeDiv.classList.add(clase);
        });

        const primerElemento = loginForm.firstElementChild;
        if (primerElemento) {
            loginForm.insertBefore(mensajeDiv, primerElemento);
        }

    }

});