package no_country_grill_house.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no_country_grill_house.mappers.ClienteMapper;
import no_country_grill_house.mappers.MesaMapper;
import no_country_grill_house.mappers.MeseroMapper;
import no_country_grill_house.mappers.OrdenMapper;
import no_country_grill_house.mappers.PlatilloMapper;
import no_country_grill_house.mappers.ReservaMapper;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Mesa;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.dtos.DetalleDto;
import no_country_grill_house.models.dtos.DetalleOrdenDto;
import no_country_grill_house.models.dtos.GenerarOrdenDto;
import no_country_grill_house.models.dtos.OrdenDto;
import no_country_grill_house.models.dtos.ReservaDto;
import no_country_grill_house.services.ClienteServiceImpl;
import no_country_grill_house.services.DetalleOrdenServiceImpl;
import no_country_grill_house.services.MesaServiceImpl;
import no_country_grill_house.services.MeseroServiceImpl;
import no_country_grill_house.services.OrdenServiceImpl;
import no_country_grill_house.services.PlatilloServiceImpl;
import no_country_grill_house.services.ReservaServiceImpl;

@RestController
@RequestMapping("/orden")
public class OrdenController {
    @Autowired
    private OrdenServiceImpl ordenServiceImpl;

    @Autowired
    OrdenMapper ordenMapper;

    @Autowired
    private ReservaServiceImpl reservaServiceImpl;

    @Autowired
    private ReservaMapper reservaMapper;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private MeseroMapper meseroMapper;

    @Autowired
    private MeseroServiceImpl meseroServiceImpl;

    @Autowired
    private MesaServiceImpl mesaServiceImpl;

    @Autowired
    private MesaMapper mesaMapper;

    @Autowired
    private DetalleOrdenServiceImpl detalleOrdenServiceImpl;

    @Autowired
    private PlatilloServiceImpl platilloServiceImpl;

    @Autowired
    private PlatilloMapper platilloMapper;

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ordenServiceImpl.findAll());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }
    }

    /*
     * @GetMapping("/{id}")
     * public ResponseEntity<?> find(@PathVariable Integer id) {
     * try {
     * return new ResponseEntity<>("GetOne Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     */

    @PostMapping("/registrar")
    public ResponseEntity<?> create(@RequestBody GenerarOrdenDto generarOrdenDto) {
        try {
            Cliente cliente = clienteMapper.toCliente(clienteServiceImpl.findByEmail(generarOrdenDto.getEmail()));
            OrdenDto ordenDto = new OrdenDto();
            if (generarOrdenDto.getCantidadPersonas() != 0) {
                Mesa mesa = mesaMapper.toMesa(mesaServiceImpl.findByCantidad(generarOrdenDto.getCantidadPersonas()));
                ReservaDto reservaDto = new ReservaDto();
                ReservaDto reservaDtoGuardada = null;
                if (mesa != null) {
                    reservaDto.setMesa(mesa);
                    reservaDto.setCliente(cliente);
                    reservaDtoGuardada = reservaServiceImpl.create(reservaDto);
                }
                ordenDto.setMesa(mesa);
                ordenDto.setReserva(reservaMapper.toReserva(reservaDtoGuardada));
            }

            ordenDto.setCliente(cliente);

            OrdenDto ordenDtoGuardada = ordenServiceImpl.create(ordenDto);

            for (DetalleDto detalle : generarOrdenDto.getDetalles()) {
                Integer cantidad = detalle.getCantidad();
                Platillo platillo = platilloMapper
                        .toPlatillo(platilloServiceImpl.findById(detalle.getPlatillo().getId()));
                DetalleOrdenDto detalleOrdenDto = new DetalleOrdenDto();
                detalleOrdenDto.setCantidad(cantidad);
                detalleOrdenDto.setPlatillo(platillo);
                detalleOrdenDto.setOrden(ordenMapper.toOrden(ordenDtoGuardada));
                detalleOrdenServiceImpl.create(detalleOrdenDto);
            }
            String exitoMessage = "La orden se creó correctamente!";
            Map<String, Object> exitoResponse = new HashMap<>();
            exitoResponse.put("message", exitoMessage);
            return ResponseEntity
                    .ok().body(exitoResponse);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @PostMapping("/listar/cliente")
    public ResponseEntity<?> listarPorCliente(@RequestBody String email) {
        try {
            Cliente cliente = clienteMapper.toCliente(clienteServiceImpl.findByEmail(email));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ordenServiceImpl.obtenerOrdenesConDetallesPorCliente(cliente));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @PostMapping("/listar/mesero")
    public ResponseEntity<?> listarPorMesero(@RequestBody String email) {
        try {
            Mesero mesero = meseroMapper.toMesero(meseroServiceImpl.findByEmail(email));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ordenServiceImpl.obtenerOrdenesConDetallesPorMesero(mesero));
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }

    @GetMapping("/listar/detalle")
    public ResponseEntity<?> listarConDetalle() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ordenServiceImpl.obtenerOrdenesConDetalles());
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", errorMessage);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);
        }
    }
    /*
     * @PutMapping()
     * public ResponseEntity<?> update(@RequestBody Dto dto) {
     * try {
     * ResponseEntity
     * return new ResponseEntity<>("Update Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * @DeleteMapping("/{id}")
     * public ResponseEntity<?> delete(@PathVariable Integer id) {
     * try {
     * ResponseEntity
     * return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     */
}
