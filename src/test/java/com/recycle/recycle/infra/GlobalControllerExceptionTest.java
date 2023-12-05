package com.recycle.recycle.infra;

import com.recycle.recycle.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GlobalControllerExceptionTest {
    @InjectMocks
    GlobalControllerException globalControllerException = new GlobalControllerException();
    @Mock
    DataIntegrityViolationException dataIntegrityViolationException;
    @Mock
    MethodArgumentNotValidException methodArgumentNotValidException;
    @Mock
    EntityNotFoundException entityNotFoundException;
    @Mock
    BindingResult bindingResult;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void threatDuplicateEntry() {
        when(dataIntegrityViolationException.getMessage()).thenReturn("Already exist");
        ResponseEntity<ExceptionDTO> response = globalControllerException.threatDuplicateEntry(dataIntegrityViolationException);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Already exist", response.getBody().message());
        assertEquals(409, response.getStatusCodeValue());
    }

    @Test
    void handleEntityNotFoundException() {
        when(entityNotFoundException.getMessage()).thenReturn("Not Found");
        ResponseEntity<ExceptionDTO> response = globalControllerException.handleEntityNotFoundException(entityNotFoundException);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not Found", response.getBody().message());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testHandleValidationException() {
        when(bindingResult.getFieldErrors())
                .thenReturn(Collections.singletonList(new FieldError("objectName", "fieldName", "should not be empty")));
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(methodArgumentNotValidException.getMessage()).thenReturn("Validation error");
        ResponseEntity<Object> response = globalControllerException.handleValidationException(methodArgumentNotValidException);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Object responseBody = response.getBody();
        assertTrue(responseBody instanceof Map);
        assertTrue(((Map<?, ?>) responseBody).containsKey("fieldName"));
        assertEquals("should not be empty", ((Map<?, ?>) responseBody).get("fieldName"));
    }
}