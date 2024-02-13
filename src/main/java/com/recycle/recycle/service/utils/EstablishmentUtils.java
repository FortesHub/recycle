package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Establishment;
import com.recycle.recycle.repository.EstablishmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class EstablishmentUtils {
    private EstablishmentRepository establishmentRepository;
    private String establishmentNotFound = "Establishment Not Found!";

    @Autowired
    public void establishmentUtils(EstablishmentRepository establishmentRepository){
        this.establishmentRepository = establishmentRepository;
    }

    public List<Establishment> getEstablishments(List<String> establishmentIds) {
        List<Establishment> establishments = new ArrayList<>();
        if (establishmentIds != null) {
            for (String establishmentId : establishmentIds) {
                Establishment establishment = establishmentRepository.findById(establishmentId)
                        .orElseThrow(() -> new EntityNotFoundException(establishmentNotFound));
                establishments.add(establishment);
            }
        }
        return establishments;
    }
}
