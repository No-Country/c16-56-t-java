function enviarFetch(url, metodo, datos, exitoCallback, errorCallback) {
    fetch(url, {
        method: metodo,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error en el servidor');
            }
        })
        .then(function (data) {
            exitoCallback(data);
        })
        .catch(function (error) {
            errorCallback(error.message || 'Error de conexión');
        });
}

export { enviarFetch };

