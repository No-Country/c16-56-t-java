function obtenerOrdenesPorEstado(estado) {
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };

    ordenData = {
        email: email,
        estadoOrden: estado
    }

    fetch('/orden/listar/estado/mesero', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(ordenData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Error al procesar la solicitud.');
        })
        .then(data => {
            data.forEach(orden => {
                generarOrden(orden);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function generarOrden(orden) {
    var cardDiv = document.createElement("div");
    cardDiv.classList.add("card");

    var unsplashDiv = document.createElement("div");
    unsplashDiv.classList.add("fernando-andrade-unsplash");
    unsplashDiv.style.backgroundImage = `url('${orden.detalles[0].platillo.foto.url}')`;
    unsplashDiv.style.backgroundRepeat = 'no-repeat';
    unsplashDiv.style.backgroundPosition = 'center';

    var flexColumnDiv = document.createElement("div");
    flexColumnDiv.classList.add("flex-column-dfe");

    var marioRuizDiv = document.createElement("div");
    marioRuizDiv.classList.add("mario-ruiz-4");
    var cupcakeDeSpan = document.createElement("span");
    cupcakeDeSpan.classList.add("cupcake-de");
    cupcakeDeSpan.textContent = "Orden N°";
    var fresaSpan = document.createElement("span");
    fresaSpan.classList.add("fresa");
    fresaSpan.textContent = orden.numeroOrden;
    marioRuizDiv.appendChild(cupcakeDeSpan);
    marioRuizDiv.appendChild(fresaSpan);

    var fechaActual = new Date();

    var fechaCreacion = new Date(orden.fechaAlta);

    var diferenciaMilisegundos = fechaActual - fechaCreacion;

    var minutosTranscurridos = Math.floor(diferenciaMilisegundos / (1000 * 60));

    var mensajeTiempo = '';

    if (minutosTranscurridos < 60) {
        if (minutosTranscurridos == 1) {
            mensajeTiempo = "Hace 1 minuto";
        } else {
            mensajeTiempo = `Hace ${minutosTranscurridos} minutos`;
        }
    } else if (minutosTranscurridos < 1440) {
        var horasTranscurridas = Math.floor(minutosTranscurridos / 60);
        if (horasTranscurridas == 1) {
            mensajeTiempo = "Hace 1 hora";
        } else {
            mensajeTiempo = `Hace ${horasTranscurridas} horas`;
        }
    } else {
        var diasTranscurridos = Math.floor(minutosTranscurridos / 1440);
        if (diasTranscurridos == 1) {
            mensajeTiempo = "Hace 1 día";
        } else {
            mensajeTiempo = `Hace ${diasTranscurridos} días`;
        }
    }

    var chefSpan = document.createElement("span");
    chefSpan.classList.add("chef");
    chefSpan.textContent = mensajeTiempo;
    flexColumnDiv.appendChild(marioRuizDiv);
    flexColumnDiv.appendChild(chefSpan);

    if (orden.estadoOrden == 'TERMINADO') {
        var button = document.createElement("button");
        button.classList.add("botones-mobile-5");
        var enProcesoSpan = document.createElement("span");
        enProcesoSpan.classList.add("en-proceso");
        enProcesoSpan.textContent = 'Entregado';
        button.appendChild(enProcesoSpan);
        flexColumnDiv.appendChild(button);
    }

    cardDiv.appendChild(unsplashDiv);
    cardDiv.appendChild(flexColumnDiv);

    if (orden.estadoOrden != 'ENTREGADO') {
        button.addEventListener('click', function () {
            if (orden.estadoOrden == 'TERMINADO') {
                cambiarEstado(orden)
                    .then(data => {
                        var contenedorPrincipal = document.getElementById('contenedor-ordenes');
                        contenedorPrincipal.innerHTML = '';
                        obtenerOrdenesPorEstado('ENTREGADO');
                    })
                    .catch(error => {
                        console.error('Error al cambiar el estado de la orden:', error);
                    });
            }
        });
    }

    const contenedorOrdenes = document.getElementById('contenedor-ordenes');
    contenedorOrdenes.appendChild(cardDiv);

}

function cambiarEstado(orden) {
    return new Promise((resolve, reject) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        };

        const numeroOrden = orden.numeroOrden;

        fetch('/orden/cambiar/estado', {
            method: 'POST',
            headers: headers,
            body: numeroOrden
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error('Error al procesar la solicitud.');
            })
            .then(data => {
                resolve(data);
            })
            .catch(error => {
                reject(error);
            });
    });
}

document.addEventListener('DOMContentLoaded', function () {
    obtenerOrdenesPorEstado('TERMINADO');

    const btnPendiente = document.querySelector('#pendiente');
    btnPendiente.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('contenedor-ordenes');
        contenedorPrincipal.innerHTML = '';
        obtenerOrdenesPorEstado('TERMINADO');
    });

    const btnEntregado = document.querySelector('#entregado');
    btnEntregado.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('contenedor-ordenes');
        contenedorPrincipal.innerHTML = '';
        obtenerOrdenesPorEstado('ENTREGADO');
    });

});