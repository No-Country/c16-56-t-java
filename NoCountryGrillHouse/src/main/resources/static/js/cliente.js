const vistaCliente = document.querySelector('#cliente_vista');
const vistaCarrito = document.querySelector('#carrito_vista');
const vistaMetodosdePago = document.querySelector('#metodos_pago');
const vistaComprobantes = document.querySelector('#comprobantes');
const vistaMisPedidos = document.querySelector('#mis_pedidos');
const vistaSeguimiento = document.querySelector('#seguimiento_pedidos');
const vistaDetalle = document.querySelector('#detalle_pedido');



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

const btnPagar = document.querySelector('#btn_pagar');
btnPagar.addEventListener('click', function(){
    vistaComprobantes.style.display = 'block';
    vistaMetodosdePago.style.display = 'none';
});

const btnCerrarCompra = document.querySelector('#btn_cerrar_compra');
btnCerrarCompra.addEventListener('click', function(){
    vistaComprobantes.style.display = 'none';
    vistaCliente.style.display = 'block';
});

const btnMisPedidos = document.querySelector('#btn_mispedidos');
btnMisPedidos.addEventListener('click', function(){
    vistaMisPedidos.style.display = 'block';
    vistaCliente.style.display = 'none';
});

const btnVolverCliente1 = document.querySelector('#btn_clientes_volver');
btnVolverCliente1.addEventListener('click', function(){
    vistaCliente.style.display = 'block';
    vistaMisPedidos.style.display = 'none';
});

const btnSeguimiento = document.querySelector('#btn_seguimiento');
btnSeguimiento.addEventListener('click', function(){
    vistaMisPedidos.style.display = 'none';
    vistaSeguimiento.style.display = 'block';
});

const btnCerrarSeguimiento = document.querySelector('#btn_cerrar_seguimiento');
btnCerrarSeguimiento.addEventListener('click', function(){
    vistaSeguimiento.style.display = 'none';
    vistaCliente.style.display = 'block';
});

const btnDetalle = document.querySelector('#btn_detalle');
btnDetalle.addEventListener('click', function(){
    vistaCliente.style.display = 'none';
    vistaDetalle.style.display = 'block';
});

const btnCerrarDetalle = document.querySelector('#cerra-detalle');
btnCerrarDetalle.addEventListener('click', function(){
    vistaDetalle.style.display = 'none';
    vistaCliente.style.display = 'block';
});