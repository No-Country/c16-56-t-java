const vistaCliente = document.querySelector('#cliente_vista');
const vistaCarrito = document.querySelector('#carrito_vista');


const btnCarritoVista = document.querySelector('#btn_carrito');
btnCarritoVista.addEventListener('click', function() {
    vistaCliente.style.display = 'none';
    vistaCarrito.style.display = 'block';
});