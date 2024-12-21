package com.quantum_leap.api.service;

import com.quantum_leap.api.domain.address.Address;
import com.quantum_leap.api.domain.event.Event;
import com.quantum_leap.api.domain.event.EventRequestDTO;
import com.quantum_leap.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;

    public Address createAddress (EventRequestDTO data, Event event){
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.uf());
        address.setEvent(event);

        return repository.save(address);
    }
}
