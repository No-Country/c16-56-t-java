const vistaCliente = document.querySelector('#cliente_vista');
const vistaCarrito = document.querySelector('#carrito_vista');
const vistaMetodosdePago = document.querySelector('#metodos_pago');



const btnCarritoVista = document.querySelector('#btn_carrito');
btnCarritoVista.addEventListener('click', function() {
    vistaCliente.style.display = 'none';
    vistaCarrito.style.display = 'block';
});

const btnVolverCliente = document.querySelector('#btn_flecha');
btnVolverCliente.addEventListener('click', function(){
    vistaCliente.style.display = 'block';
    vistaCarrito.style.display = 'none';
});

const btnMetodosPago = document.querySelector('#btn_comprar');
btnMetodosPago.addEventListener('click', function(){
    vistaMetodosdePago.style.display = 'block';
    vistaCarrito.style.display = 'none';
});

const btnVolverCarrito = document.querySelector('#btn_volver_carrito');
btnVolverCarrito.addEventListener('click', function(){
    vistaMetodosdePago.style.display = 'none';
    vistaCarrito.style.display = 'block';
});