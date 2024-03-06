package no_country_grill_house.utils;

import java.io.Serializable;
import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class CustomIdentifierGenerator implements IdentifierGenerator {

    // private final ClienteRepository clienteRepository;

    // public CustomIdentifierGenerator(ClienteRepository clienteRepository) {
    // this.clienteRepository = clienteRepository;
    // }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        Long id;
        Random random = new Random();
        // do {
        // id = random.nextLong(899999) + 100001;
        id = random.nextLong(899999) + 100001;
        // } while (!isIDUniqueInDatabase(id));
        return id;
    }

    // private boolean isIDUniqueInDatabase(Long id) {
    // Cliente cliente = clienteRepository.findById(id).get();
    // return cliente == null;
    // }
}