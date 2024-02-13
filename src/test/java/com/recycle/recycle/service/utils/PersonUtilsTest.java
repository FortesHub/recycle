package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Person;
import com.recycle.recycle.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonUtilsTest {
    @InjectMocks
    PersonUtils personUtils;
    @Mock
    PersonRepository personRepository;
    private List<Person> personList;
    private List<String> personIds;

    @BeforeEach
    void setUp(){
        personList = Arrays.asList(
                new Person("personId123", "John Doe", "123456789",
                        "john.doe@example.com"));
        personIds = Arrays.asList("personId123");
    }

    @DisplayName("Test to get PersonIds and return List Persons")
    @Test
    void givenGetPersonIdsWhenReturnPersonList(){
        when(personRepository.findById(personList.get(0).getPersonId())).thenReturn(Optional.ofNullable(personList.get(0)));
        List<Person> employees = personUtils.getEmployees(personIds);
        verify(personRepository, times(1)).findById(personList.get(0).getPersonId());
        assertEquals(personList.size(), employees.size());
    }
}
