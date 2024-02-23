function enviarFetch(url, metodo, datos, exitoCallback, errorCallback, token = null) {
    const headers = {
        'Content-Type': 'application/json',
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    fetch(url, {
        method: metodo,
        headers: headers,
        body: JSON.stringify(datos)
    })
        .then(function (response) {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(errorData => {
                    const nombrePropiedad = Object.keys(errorData)[0];
                    const mensajeError = errorData[nombrePropiedad];
                    throw new Error(mensajeError || 'Error en el servidor');
                });
            }
        })
        .then(function (data) {
            exitoCallback(data);
        })
        .catch(function (error) {
            errorCallback(error);
        });
}

export { enviarFetch };


