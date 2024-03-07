const vistaCliente = document.querySelector('#cliente_vista');
const vistaCarrito = document.querySelector('#carrito_vista');
const vistaMetodosdePago = document.querySelector('#metodos_pago');
const vistaComprobantes = document.querySelector('#comprobantes');
const vistaMisPedidos = document.querySelector('#mis_pedidos');
const vistaSeguimiento = document.querySelector('#seguimiento_pedidos');
const vistaDetalle = document.querySelector('#detalle_pedido');

function listaDePlatillos(url, nombre) {
    if (nombre != null) {
        url += `?nombre=${nombre}`;
    }

    const token = localStorage.getItem('token');

    const headers = {
        'Authorization': `Bearer ${token}`
    };

    fetch(url, {
        method: 'GET',
        headers: headers
    })
        .then(response => response.json())
        .then(data => {

            let contenedorPlatillos = '';
            if (nombre == 'Comida') {
                contenedorPlatillos = document.getElementById('contenedor-comidas');
            } else if (nombre == 'Postre') {
                contenedorPlatillos = document.getElementById('contenedor-postres');
            } else if (nombre == 'Bebida') {
                contenedorPlatillos = document.getElementById('contenedor-bebidas');
            }


            data.forEach(async producto => {
                var menuItemContainer = document.createElement('div');
                menuItemContainer.classList.add('menu-item');

                var classicBurgerDiv = document.createElement('div');
                classicBurgerDiv.classList.add('classic-burger');

                var cocaColaSpan = document.createElement('span');
                cocaColaSpan.textContent = producto.nombre;
                cocaColaSpan.classList.add('coca-cola');

                var sodaSpan = document.createElement('span');
                sodaSpan.textContent = producto.categoria.nombre;
                sodaSpan.classList.add('soda');

                var heartIconDiv = document.createElement('div');
                heartIconDiv.classList.add('heart-icon');

                const enFavoritos = await estaEnFavoritos(producto);
                if (enFavoritos) {
                    heartIconDiv.classList.add('favorito');
                }

                heartIconDiv.addEventListener('click', async function () {
                    if (await estaEnFavoritos(producto)) {
                        heartIconDiv.classList.toggle('favorito');
                        agregarEliminarFavoritos(producto, '/cliente/favoritos/eliminar');
                    } else {
                        heartIconDiv.classList.toggle('favorito');
                        agregarEliminarFavoritos(producto, '/cliente/favoritos/agregar');
                    }
                });

                var beverageDiv = document.createElement('div');
                beverageDiv.classList.add('beverage');
                if (producto.foto == null) {
                    beverageDiv.style.backgroundImage = `url('/images/userdefult/perfiluser.png')`;
                } else {
                    beverageDiv.style.backgroundImage = `url('${producto.foto.url}')`;
                }
                beverageDiv.style.backgroundRepeat = 'no-repeat';
                beverageDiv.style.backgroundPosition = 'center';

                var cartIconDiv = document.createElement('div');
                cartIconDiv.classList.add('cart-icon');

                var iconDiv = document.createElement('div');
                iconDiv.classList.add('icon-55');

                var priceSpan = document.createElement('span');
                priceSpan.textContent = `$${producto.precio.toFixed(2)}`;
                priceSpan.classList.add('price-56');

                classicBurgerDiv.appendChild(cocaColaSpan);
                menuItemContainer.appendChild(classicBurgerDiv);
                menuItemContainer.appendChild(sodaSpan);
                menuItemContainer.appendChild(heartIconDiv);
                menuItemContainer.appendChild(beverageDiv);
                cartIconDiv.appendChild(iconDiv);
                menuItemContainer.appendChild(cartIconDiv);
                menuItemContainer.appendChild(priceSpan);

                menuItemContainer.addEventListener('click', function (event) {
                    if (!event.target.closest('.heart-icon') && !event.target.closest('.cart-icon')) {
                        llenarDetalle(producto);

                        vistaCliente.style.display = 'none';
                        vistaDetalle.style.display = 'block';
                    }
                });

                menuItemContainer.addEventListener('click', function (event) {
                    if (event.target.closest('.cart-icon')) {
                        crearProductoEnCarrito(producto);
                    }
                });

                contenedorPlatillos.appendChild(menuItemContainer);
            });
        })
        .catch(error => {
            console.error('Error al obtener la lista de platillos:', error);
        });
}

function generarContenedoresTodos() {

    var contenedorPrincipal = document.getElementById('vista-platillos');

    var flexRowE = document.createElement('div');
    flexRowE.classList.add('flex-row-e');
    flexRowE.innerHTML = `
    <span class="hamburguesas">Hamburguesas</span>
    <div id="ver-todo-comidas" class="boton-14"><span class="ver-todo-15">Ver todo</span></div>
`;
    contenedorPrincipal.appendChild(flexRowE);

    var flexRowD = document.createElement('div');
    flexRowD.classList.add('flex-row-d');
    flexRowD.innerHTML = `
    <div id="contenedor-comidas" class="hamburguesas-1a"></div>
`;
    contenedorPrincipal.appendChild(flexRowD);

    var flexRowA38 = document.createElement('div');
    flexRowA38.classList.add('flex-row-a-38');
    flexRowA38.innerHTML = `
    <span class="postres">Postres</span>
    <div id="ver-todo-postres" class="boton-39"><span class="ver-todo-3a">Ver todo</span></div>
`;
    contenedorPrincipal.appendChild(flexRowA38);

    var contenedorPostres = document.createElement('div');
    contenedorPostres.id = 'contenedor-postres';
    contenedorPostres.classList.add('postre-3b');
    contenedorPrincipal.appendChild(contenedorPostres);

    var flexRowPlatillo = document.createElement('div');
    flexRowPlatillo.classList.add('flex-row-platillo');
    flexRowPlatillo.innerHTML = `
    <span class="beverages">Bebidas</span>
    <div id="ver-todo-bebidas" class="button"><span class="see-all">Ver todo</span></div>
`;
    contenedorPrincipal.appendChild(flexRowPlatillo);

    var contenedorBebidas = document.createElement('div');
    contenedorBebidas.id = 'contenedor-bebidas';
    contenedorBebidas.classList.add('beverages-54');
    contenedorPrincipal.appendChild(contenedorBebidas);
}

async function obtenerDatos(url) {
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`
    };

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: headers
        });
        return await response.json();
    } catch (error) {
        throw new Error('Error al obtener los datos:', error);
    }
}

async function obtenerDatosFavoritos(url) {
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`
    };

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: headers,
            body: email
        });
        return await response.json();
    } catch (error) {
        throw new Error('Error al obtener los datos:', error);
    }
}

async function mostrarComidas(url, bandera = true) {
    try {
        let data = '';
        if (bandera) {
            data = await obtenerDatos(url);
        } else {
            data = await obtenerDatosFavoritos(url);
        }

        var contenedorPrincipal = document.getElementById('vista-platillos');

        for (let i = 0; i < data.length; i += 2) {
            var filaContenedor = document.createElement('div');
            filaContenedor.classList.add('fila');

            var producto1 = data[i];
            var menuItem1 = await crearMenuItem(producto1);
            filaContenedor.appendChild(menuItem1);

            if (i + 1 < data.length) {
                var producto2 = data[i + 1];
                var menuItem2 = await crearMenuItem(producto2);
                filaContenedor.appendChild(menuItem2);
            }

            contenedorPrincipal.appendChild(filaContenedor);
        }
    } catch (error) {
        console.error('Error al obtener la lista de platillos:', error);
    }
}

async function crearMenuItem(producto) {
    var menuItemContainer = document.createElement('div');
    menuItemContainer.classList.add('menu-item');

    var classicBurgerDiv = document.createElement('div');
    classicBurgerDiv.classList.add('classic-burger');

    var cocaColaSpan = document.createElement('span');
    cocaColaSpan.textContent = producto.nombre;
    cocaColaSpan.classList.add('coca-cola');

    var sodaSpan = document.createElement('span');
    sodaSpan.textContent = producto.categoria.nombre;
    sodaSpan.classList.add('soda');

    var heartIconDiv = document.createElement('div');
    heartIconDiv.classList.add('heart-icon');

    const enFavoritos = await estaEnFavoritos(producto);
    if (enFavoritos) {
        heartIconDiv.classList.add('favorito');
    }

    heartIconDiv.addEventListener('click', async function () {
        if (await estaEnFavoritos(producto)) {
            heartIconDiv.classList.toggle('favorito');
            agregarEliminarFavoritos(producto, '/cliente/favoritos/eliminar');
        } else {
            heartIconDiv.classList.toggle('favorito');
            agregarEliminarFavoritos(producto, '/cliente/favoritos/agregar');
        }
    });

    var beverageDiv = document.createElement('div');
    beverageDiv.classList.add('beverage');
    if (producto.foto == null) {
        beverageDiv.style.backgroundImage = `url('/images/userdefult/perfiluser.png')`;
    } else {
        beverageDiv.style.backgroundImage = `url('${producto.foto.url}')`;
    }
    beverageDiv.style.backgroundRepeat = 'no-repeat';
    beverageDiv.style.backgroundPosition = 'center';

    var cartIconDiv = document.createElement('div');
    cartIconDiv.classList.add('cart-icon');

    var iconDiv = document.createElement('div');
    iconDiv.classList.add('icon-55');

    var priceSpan = document.createElement('span');
    priceSpan.textContent = `$${producto.precio.toFixed(2)}`;
    priceSpan.classList.add('price-56');

    classicBurgerDiv.appendChild(cocaColaSpan);
    menuItemContainer.appendChild(classicBurgerDiv);
    menuItemContainer.appendChild(sodaSpan);
    menuItemContainer.appendChild(heartIconDiv);
    menuItemContainer.appendChild(beverageDiv);
    cartIconDiv.appendChild(iconDiv);
    menuItemContainer.appendChild(cartIconDiv);
    menuItemContainer.appendChild(priceSpan);

    menuItemContainer.addEventListener('click', function (event) {
        if (!event.target.closest('.heart-icon') && !event.target.closest('.cart-icon')) {
            llenarDetalle(producto);

            vistaCliente.style.display = 'none';
            vistaDetalle.style.display = 'block';
        }
    });

    menuItemContainer.addEventListener('click', function (event) {
        if (event.target.closest('.cart-icon')) {
            crearProductoEnCarrito(producto);
        }
    });

    return menuItemContainer;
}

function llenarDetalle(producto) {
    const detallePedido = document.getElementById('detalle_pedido');

    const postreDetalle = detallePedido.querySelector('.postre-detalle');
    const texto3Detalle = detallePedido.querySelector('.text-3-detalle');
    const minsDetalle = detallePedido.querySelector('.mins-detalle');
    const priceDetalle = detallePedido.querySelector('.price-detalle');
    const descripcionDetalle = detallePedido.querySelector('.delicious-dessert-detalle');
    const imagenDetalle = detallePedido.querySelector('.fernando-andrade-unsplash-detalle');

    postreDetalle.textContent = producto.categoria.nombre;
    texto3Detalle.textContent = producto.nombre;
    minsDetalle.textContent = `${producto.tiempoEstimado} Mins`;
    priceDetalle.textContent = `$${producto.precio}`;
    descripcionDetalle.textContent = producto.descripcion;
    if (producto.foto == null) {
        imagenDetalle.style.backgroundImage = `url('/images/userdefult/perfiluser.png')`;
    } else {
        imagenDetalle.style.backgroundImage = `url('${producto.foto.url}')`;
    }
    imagenDetalle.style.backgroundRepeat = 'no-repeat';
    imagenDetalle.style.backgroundPosition = 'center';

    const btnAgregar = document.querySelector('.add-to-cart-button-detalle');
    btnAgregar.addEventListener('click', function () {
        crearProductoEnCarrito(producto);
    });
}

function estaEnFavoritos(producto) {
    return new Promise((resolve, reject) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Authorization': `Bearer ${token}`
        };

        fetch('/cliente/favoritos', {
            method: 'POST',
            headers: headers,
            body: email
        })
            .then(response => response.json())
            .then(data => {
                resolve(data.some(favorito => favorito.id === producto.id));
            })
            .catch(error => {
                reject(error);
            });
    });
}

function agregarEliminarFavoritos(producto, url) {
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };

    let userData = {
        email: email,
        id: producto.id
    }

    fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Error al procesar la solicitud.');
        })
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const productosEnCarrito = [];

function crearProductoEnCarrito(producto) {
    const carritoContainer = document.querySelector('#cards-carrito');

    const index = productosEnCarrito.findIndex(item => item.platillo.id === producto.id);

    if (index !== -1) {
        return;
    }

    let cantidad = 1;

    const card = document.createElement('div');
    card.classList.add('card-7');

    const cerrarIcono = document.createElement('div');
    cerrarIcono.classList.add('icono-cerrar-8');
    cerrarIcono.innerHTML = '<div class="icon-9"></div>';
    card.appendChild(cerrarIcono);

    const columnaFlex = document.createElement('div');
    columnaFlex.classList.add('flex-column-a');

    const cupcake = document.createElement('div');
    cupcake.classList.add('cupcake-de-fresa-a');
    cupcake.innerHTML = `<span class="cupcake-de">${producto.nombre}</span>`;
    columnaFlex.appendChild(cupcake);

    const precioSpan = document.createElement('span');
    precioSpan.classList.add('price-c');
    precioSpan.textContent = `$${producto.precio}`;
    columnaFlex.appendChild(precioSpan);

    const cantidadDiv = document.createElement('div');
    cantidadDiv.classList.add('aumentar-cantidad-d');

    const botonMenos = document.createElement('div');
    botonMenos.classList.add('boton-menos-e');
    botonMenos.innerHTML = '<div class="icono-menos-f"><div class="icon-10"></div></div>';
    cantidadDiv.appendChild(botonMenos);

    const cantidadSpan = document.createElement('span');
    cantidadSpan.classList.add('quantity-11');
    cantidadSpan.textContent = cantidad;
    cantidadDiv.appendChild(cantidadSpan);

    const botonMas = document.createElement('div');
    botonMas.classList.add('boton-mas-12');
    botonMas.innerHTML = '<div class="icono-mas-13"><div class="icon-14"></div></div>';
    cantidadDiv.appendChild(botonMas);

    columnaFlex.appendChild(cantidadDiv);
    card.appendChild(columnaFlex);

    const imagenDiv = document.createElement('div');
    imagenDiv.classList.add('fernando-andrade-unsplash');
    if (producto.foto) {
        imagenDiv.style.backgroundImage = `url('${producto.foto.url}')`;
    }
    imagenDiv.style.backgroundRepeat = 'no-repeat';
    imagenDiv.style.backgroundPosition = 'center';
    card.appendChild(imagenDiv);

    productosEnCarrito.push({ platillo: producto, cantidad: cantidad });

    card.addEventListener('click', function (event) {
        const cardClicked = event.currentTarget;
        const cantidadSpan = cardClicked.querySelector('.quantity-11');

        if (event.target.closest('.boton-menos-e')) {
            const index = productosEnCarrito.findIndex(item => item.platillo === producto);
            if (index !== -1) {
                if (productosEnCarrito[index].cantidad > 1) {
                    productosEnCarrito[index].cantidad--;
                    cantidadSpan.textContent = productosEnCarrito[index].cantidad;
                    calcularTotal();
                }
            }
        }
    });

    card.addEventListener('click', function (event) {
        const cardClicked = event.currentTarget;
        const cantidadSpan = cardClicked.querySelector('.quantity-11');

        if (event.target.closest('.boton-mas-12')) {
            const index = productosEnCarrito.findIndex(item => item.platillo === producto);
            if (index !== -1) {
                productosEnCarrito[index].cantidad++;
                cantidadSpan.textContent = productosEnCarrito[index].cantidad;
                calcularTotal();
            }
        }
    });

    cerrarIcono.addEventListener('click', function (event) {
        const card = event.currentTarget.parentElement;

        const index = productosEnCarrito.findIndex(item => item.platillo.id === producto.id);

        if (index !== -1) {
            productosEnCarrito.splice(index, 1);
            card.remove();
            calcularTotal();
            actualizarNumeroCarrito();
        }
    });

    carritoContainer.appendChild(card);
    calcularTotal();
    actualizarNumeroCarrito();
}

function actualizarNumeroCarrito() {
    const carritoIcono = document.querySelector('.cantidad-carrito');
    const cantidadTotal = productosEnCarrito.reduce((total, producto) => total + producto.cantidad, 0);
    carritoIcono.textContent = cantidadTotal;
}

function calcularTotal() {
    let subtotal = 0;

    productosEnCarrito.forEach(item => {
        subtotal += item.platillo.precio * item.cantidad;
    });

    const impuestos = 0;
    const costoEnvio = 0;

    const total = subtotal + impuestos + costoEnvio;

    document.querySelector('.subtotal-price').textContent = `$${subtotal.toFixed(2)}`;
    document.querySelector('.total-price').textContent = `$${total.toFixed(2)}`;
}

function calcularTotalValor() {
    let subtotal = 0;

    productosEnCarrito.forEach(item => {
        subtotal += item.platillo.precio * item.cantidad;
    });

    const impuestos = 0;
    const costoEnvio = 0;

    const total = subtotal + impuestos + costoEnvio;

    return total;
}

function crearTarjetaDePago(nombreProducto, precioProducto, imagenUrl, cantidad) {
    const contenedorTarjetasPago = document.getElementById('productos-metodos');

    const cardPago = document.createElement('div');
    cardPago.classList.add('card-pagos');

    const imagenDiv = document.createElement('div');
    imagenDiv.classList.add('fernando-andrade-unsplash-pagos');
    imagenDiv.style.backgroundImage = `url('${imagenUrl}')`;
    imagenDiv.style.backgroundRepeat = 'no-repeat';
    imagenDiv.style.backgroundPosition = 'center';
    cardPago.appendChild(imagenDiv);

    const flexColumn = document.createElement('div');
    flexColumn.classList.add('flex-column-db-pagos');

    const nombreProductoDiv = document.createElement('div');
    nombreProductoDiv.classList.add('cupcake-de-fresa-pagos');

    const fresaSpan = document.createElement('span');
    fresaSpan.classList.add('fresa-pagos');
    fresaSpan.textContent = ` x ${cantidad}`;

    const nombreSpan = document.createElement('span');
    nombreSpan.classList.add('cupcake-pagos');
    nombreSpan.textContent = nombreProducto;;

    nombreProductoDiv.appendChild(nombreSpan);
    nombreProductoDiv.appendChild(fresaSpan);

    flexColumn.appendChild(nombreProductoDiv);

    const precioSpan = document.createElement('span');
    precioSpan.classList.add('price-pagos');
    precioSpan.textContent = precioProducto;

    flexColumn.appendChild(precioSpan);

    cardPago.appendChild(flexColumn);

    contenedorTarjetasPago.appendChild(cardPago);
}

function generarOrden(data) {
    mostrarCargando();
    return new Promise((resolve, reject) => {
        const token = localStorage.getItem('token');
        const headers = {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        };

        fetch('/orden/registrar', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(data)
        })
            .then(response => {
                setTimeout(() => {
                    ocultarCargando();
                    vistaComprobantes.style.display = 'block';
                    vistaMetodosdePago.style.display = 'none';
                    llenarComprobante();
                    resolve(response);
                }, 2000);
            })
            .catch(error => {
                console.error('Error al generar la orden:', error);
                ocultarCargando();
                reject(error);
            });
    });
}

function vaciarCarrito() {
    productosEnCarrito.length = 0;

    const carritoContainer = document.querySelector('#cards-carrito');
    carritoContainer.innerHTML = '';

    const carritoIcono = document.querySelector('.cantidad-carrito');
    carritoIcono.textContent = '0';

    calcularTotal();
}

function mostrarCargando() {
    const overlay = document.getElementById('loading-overlay');
    overlay.style.display = 'flex';
}

function ocultarCargando() {
    const overlay = document.getElementById('loading-overlay');
    overlay.style.display = 'none';
}

function llenarComprobante() {

    const randomCode = Math.floor(10000000 + Math.random() * 90000000);

    const total = calcularTotalValor().toFixed(2);

    document.querySelector('.subtotal-amount-comprobante').textContent = `$${total}`;
    document.querySelector('.code-number-comprobante').textContent = `${randomCode.toString().substring(0, 4)} ${randomCode.toString().substring(4)}`;
    document.querySelector('.dollar-comprobante').textContent = `$${total}`;
}

function generarPedidos() {
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };

    fetch('/orden/listar/cliente', {
        method: 'POST',
        headers: headers,
        body: email
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Error al procesar la solicitud.');
        })
        .then(data => {
            data.forEach(orden => {
                generarOrdenMisPedidos(orden);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function generarOrdenMisPedidos(orden) {
    const contenedorPedidos = document.getElementById('contenedor-ordenes');

    const nuevoPedidoDiv = document.createElement('div');
    nuevoPedidoDiv.classList.add('section-2-pedidos');

    const imgPedidoDiv = document.createElement('div');
    imgPedidoDiv.classList.add('img-3-pedidos');
    imgPedidoDiv.style.backgroundImage = `url('${orden.detalles[0].platillo.foto.url}')`;
    imgPedidoDiv.style.backgroundRepeat = 'no-repeat';
    imgPedidoDiv.style.backgroundPosition = 'center';
    nuevoPedidoDiv.appendChild(imgPedidoDiv);

    const completoSpan = document.createElement('span');
    completoSpan.classList.add('text-8-pedidos');
    completoSpan.textContent = orden.estadoOrden;
    nuevoPedidoDiv.appendChild(completoSpan);

    const boxPedidoDiv = document.createElement('div');
    boxPedidoDiv.classList.add('box-pedidos');
    nuevoPedidoDiv.appendChild(boxPedidoDiv);

    const fechaHoraString = orden.fechaAlta;

    var fechaHora = new Date(fechaHoraString);

    var dia = fechaHora.getDate() < 10 ? '0' + fechaHora.getDate() : fechaHora.getDate();
    var mes = (fechaHora.getMonth() + 1) < 10 ? '0' + (fechaHora.getMonth() + 1) : (fechaHora.getMonth() + 1); // Los meses van de 0 a 11 en JavaScript, por lo que se suma 1
    var anio = fechaHora.getFullYear();
    var horas = fechaHora.getHours() < 10 ? '0' + fechaHora.getHours() : fechaHora.getHours();
    var minutos = fechaHora.getMinutes() < 10 ? '0' + fechaHora.getMinutes() : fechaHora.getMinutes();
    var segundos = fechaHora.getSeconds() < 10 ? '0' + fechaHora.getSeconds() : fechaHora.getSeconds();

    var fechaFormateada = `${dia}/${mes}/${anio} ${horas}:${minutos}:${segundos}`;

    const cupcakeTextSpan = document.createElement('span');
    cupcakeTextSpan.classList.add('text-9-pedidos');
    cupcakeTextSpan.textContent = fechaFormateada;
    boxPedidoDiv.appendChild(cupcakeTextSpan);

    let total = 0;

    orden.detalles.forEach(item => {
        total += item.platillo.precio * item.cantidad;
    });

    const totalConvertido = total.toFixed(2);

    const precioSpan = document.createElement('span');
    precioSpan.classList.add('text-b-pedidos');
    precioSpan.textContent = `Total: $${totalConvertido}`;
    nuevoPedidoDiv.appendChild(precioSpan);

    nuevoPedidoDiv.addEventListener('click', function () {
        const contenedorSeguimiento = document.getElementById('contenedor-seguimiento');
        contenedorSeguimiento.innerHTML = '';
        orden.detalles.forEach(item => {
            crearElementoCardSeguimiento(item.platillo, item.cantidad);
        })
        vistaMisPedidos.style.display = 'none';
        vistaSeguimiento.style.display = 'block';
    });

    contenedorPedidos.appendChild(nuevoPedidoDiv);
}

function crearElementoCardSeguimiento(platillo, cantidad) {
    const contenedorSeguimiento = document.getElementById('contenedor-seguimiento');

    const cardSeguimiento = document.createElement('div');
    cardSeguimiento.classList.add('card-seguimiento');

    const imagen = document.createElement('div');
    imagen.classList.add('fernando-andrade-unsplash-seguimiento');
    imagen.style.backgroundImage = `url('${platillo.foto.url}')`;
    imagen.style.backgroundRepeat = 'no-repeat';
    imagen.style.backgroundPosition = 'center';

    const columnaFlex = document.createElement('div');
    columnaFlex.classList.add('flex-column-ee-seguimiento');

    const cupcakeContainer = document.createElement('div');
    cupcakeContainer.classList.add('cupcake-de-fresa-seguimiento');

    const tipoCupcakeSpan = document.createElement('span');
    tipoCupcakeSpan.classList.add('cupcake-de-seguimiento');
    tipoCupcakeSpan.textContent = platillo.nombre;

    const saborCupcakeSpan = document.createElement('span');
    saborCupcakeSpan.classList.add('fresa-seguimiento');
    saborCupcakeSpan.textContent = ` x ${cantidad}`;

    cupcakeContainer.appendChild(tipoCupcakeSpan);
    cupcakeContainer.appendChild(saborCupcakeSpan);

    const precioSpan = document.createElement('span');
    precioSpan.classList.add('dollar-sign-seguimiento');
    precioSpan.textContent = `$${platillo.precio.toFixed(2)}`;

    columnaFlex.appendChild(cupcakeContainer);
    columnaFlex.appendChild(precioSpan);

    cardSeguimiento.appendChild(imagen);
    cardSeguimiento.appendChild(columnaFlex);

    contenedorSeguimiento.appendChild(cardSeguimiento);
}

document.addEventListener('DOMContentLoaded', function () {
    generarContenedoresTodos();
    listaDePlatillos('/platillo/listar/categoria', 'Comida');
    listaDePlatillos('/platillo/listar/categoria', 'Postre');
    listaDePlatillos('/platillo/listar/categoria', 'Bebida');

    const btnTodos = document.querySelector('#btn-todos');
    btnTodos.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        generarContenedoresTodos();
        listaDePlatillos('/platillo/listar/categoria', 'Comida');
        listaDePlatillos('/platillo/listar/categoria', 'Postre');
        listaDePlatillos('/platillo/listar/categoria', 'Bebida');
    });

    const btnComida = document.querySelector('#btn-comida');
    btnComida.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=comida');
    });

    const btnPostre = document.querySelector('#btn-postre');
    btnPostre.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=postre');
    });

    const btnBebida = document.querySelector('#btn-bebida');
    btnBebida.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=bebida');
    });

    const btnFavoritos = document.querySelector('#btn-favoritos');
    btnFavoritos.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/cliente/favoritos', false);
    });

    const btnVerComidas = document.querySelector('#ver-todo-comidas');
    btnVerComidas.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=comida');
    });

    const btnVerPostres = document.querySelector('#ver-todo-postres');
    btnVerPostres.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=postre');
    });

    const btnVerBebidas = document.querySelector('#ver-todo-bebidas');
    btnVerBebidas.addEventListener('click', function () {
        var contenedorPrincipal = document.getElementById('vista-platillos');
        contenedorPrincipal.innerHTML = '';
        mostrarComidas('/platillo/listar/categoria?nombre=bebida');
    });

    const btnCarritoVista = document.querySelector('#btn_carrito');
    btnCarritoVista.addEventListener('click', function () {
        vistaCliente.style.display = 'none';
        vistaCarrito.style.display = 'block';
    });

    const btnVolverCliente = document.querySelector('#btn_flecha');
    btnVolverCliente.addEventListener('click', function () {
        vistaCliente.style.display = 'block';
        vistaCarrito.style.display = 'none';
    });

    const btnMetodosPago = document.querySelector('#btn_comprar');
    btnMetodosPago.addEventListener('click', function () {
        vistaMetodosdePago.style.display = 'block';
        vistaCarrito.style.display = 'none';
    });

    const btnVolverCarrito = document.querySelector('#btn_volver_carrito');
    btnVolverCarrito.addEventListener('click', function () {
        vistaMetodosdePago.style.display = 'none';
        vistaCarrito.style.display = 'block';
    });

    const btnCerrarCompra = document.querySelector('#btn_cerrar_compra');
    btnCerrarCompra.addEventListener('click', function () {
        vistaComprobantes.style.display = 'none';
        vistaCliente.style.display = 'block';
    });

    const btnMisPedidos = document.querySelector('#btn_mispedidos');
    btnMisPedidos.addEventListener('click', function () {
        vistaMisPedidos.style.display = 'block';
        vistaCliente.style.display = 'none';
        const contenedorPedidos = document.getElementById('contenedor-ordenes');
        contenedorPedidos.innerHTML = '';
        generarPedidos();
    });

    const btnVolverCliente1 = document.querySelector('#btn_clientes_volver');
    btnVolverCliente1.addEventListener('click', function () {
        vistaCliente.style.display = 'block';
        vistaMisPedidos.style.display = 'none';
    });

    const btnCerrarSeguimiento = document.querySelector('#btn_cerrar_seguimiento');
    btnCerrarSeguimiento.addEventListener('click', function () {
        vistaSeguimiento.style.display = 'none';
        vistaMisPedidos.style.display = 'block';
    });

    const btnVolverInicio = document.querySelector('.ir-al-inicio-comprobante');
    btnVolverInicio.addEventListener('click', function () {
        vistaComprobantes.style.display = 'none';
        vistaCliente.style.display = 'block';
    });

    const btnIrSeguimiento = document.querySelector('.botones-mobile-comprobante');
    btnIrSeguimiento.addEventListener('click', function () {
        vistaComprobantes.style.display = 'none';
        vistaMisPedidos.style.display = 'block';
        const contenedorPedidos = document.getElementById('contenedor-ordenes');
        contenedorPedidos.innerHTML = '';
        generarPedidos();
    });

    const btnCerrarDetalle = document.querySelector('#cerra-detalle');
    btnCerrarDetalle.addEventListener('click', function () {
        vistaDetalle.style.display = 'none';
        vistaCliente.style.display = 'block';
    });

    const switchInput = document.getElementById('btn-switch');
    const reservaMesaContainer = document.getElementById('reservaMesaContainer');
    const reservaMesaContainer2 = document.getElementById('reservaMesaContainer2');

    switchInput.addEventListener('change', function () {
        if (this.checked) {
            reservaMesaContainer.classList.remove('oculto');
            reservaMesaContainer2.classList.remove('oculto');
        } else {
            reservaMesaContainer.classList.add('oculto');
            reservaMesaContainer2.classList.add('oculto');
        }
    });

    const botonMenos = document.querySelector('.boton-menos');
    const botonMas = document.querySelector('.boton-mas');
    const cantidadPersonasSpan = document.querySelector('.numero-1');

    botonMenos.addEventListener('click', function () {
        let cantidadActual = parseInt(cantidadPersonasSpan.textContent);
        if (cantidadActual > 1) {
            cantidadActual--;
            cantidadPersonasSpan.textContent = cantidadActual;
        }
    });

    botonMas.addEventListener('click', function () {
        let cantidadActual = parseInt(cantidadPersonasSpan.textContent);
        cantidadActual++;
        cantidadPersonasSpan.textContent = cantidadActual;
    });

    const btnComprar = document.querySelector('#btn_comprar');
    btnComprar.addEventListener('click', function () {
        productosEnCarrito.forEach(item => {
            const producto = item.platillo;
            const cantidad = item.cantidad;
            const nombreProducto = producto.nombre;
            const precioProducto = `$${producto.precio}`;
            const imagenUrl = producto.foto.url;

            crearTarjetaDePago(nombreProducto, precioProducto, imagenUrl, cantidad);
        });
        const total = calcularTotalValor().toFixed(2);
        const total1 = document.querySelector('#total-1');
        const total2 = document.querySelector('#total-2');
        const total3 = document.querySelector('#total-3');
        const total4 = document.querySelector('#total-4');
        total1.textContent = `$${total}`;
        total2.textContent = `$${total}`;
        total3.textContent = `$${total}`;
        total4.textContent = `$${total}`;
    });

    const btnEfectivo = document.querySelector('#btnEfectivo');
    btnEfectivo.addEventListener('click', function () {
        let cantidadPersonasNumerico = 0;
        const switchInput = document.getElementById('btn-switch');
        if (switchInput.checked) {
            const cantidadPersonasSpan = document.querySelector('.numero-1');
            cantidadPersonasNumerico = parseInt(cantidadPersonasSpan.textContent);
        }

        const ordenData = {
            email: email,
            cantidadPersonas: cantidadPersonasNumerico,
            detalles: productosEnCarrito
        }

        generarOrden(ordenData)
            .then(() => {
                vaciarCarrito();
            })
            .catch(error => {
                console.error('Error al generar la orden:', error);
            });
    });

    const btnPaypal = document.querySelector('#btnPaypal');
    btnPaypal.addEventListener('click', function () {

        let cantidadPersonasNumerico = 0;
        const switchInput = document.getElementById('btn-switch');
        if (switchInput.checked) {
            const cantidadPersonasSpan = document.querySelector('.numero-1');
            cantidadPersonasNumerico = parseInt(cantidadPersonasSpan.textContent);
        }

        const ordenData = {
            email: email,
            cantidadPersonas: cantidadPersonasNumerico,
            detalles: productosEnCarrito
        }

        generarOrden(ordenData)
            .then(() => {
                vaciarCarrito();
            })
            .catch(error => {
                console.error('Error al generar la orden:', error);
            });
    });

});