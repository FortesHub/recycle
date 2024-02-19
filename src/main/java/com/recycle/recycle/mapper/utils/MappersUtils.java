package com.recycle.recycle.mapper.utils;

import com.recycle.recycle.domain.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MappersUtils {
    default List<String> mapEstablishments(List<Establishment> establishments) {
        return establishments.stream()
                .map(Establishment::getEstablishmentId)
                .toList();
    }

    default List<String> mapEmployees(List<Person> employees) {
        return employees.stream()
                .map(Person::getPersonId)
                .toList();
    }

    default List<String> mapMachines(List<Machine> machines) {
        return machines.stream()
                .map(Machine::getMachineId)
                .toList();
    }

    default List<String> mapContainers(List<Material> containers) {
        return containers.stream()
                .map(Material::getMaterialId)
                .toList();
    }
}
