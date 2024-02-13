package com.recycle.recycle.service.utils;

import com.recycle.recycle.domain.Address;
import com.recycle.recycle.domain.AddressComposite;
import com.recycle.recycle.dto.AddressCompositeDTO;
import com.recycle.recycle.dto.AddressDTO;
import com.recycle.recycle.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressUtilsTest {
    @InjectMocks
    AddressUtils addressUtils;
    @Mock
    AddressRepository addressRepository;
    private Address address;
    private AddressComposite addressComposite;
    private AddressCompositeDTO addressCompositeDTO;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        addressComposite = new AddressComposite("rue des johns", "3", "j4k4j4");
        address = new Address(addressComposite, "Saint Jean Sur Richelieu", "Canada");
        addressCompositeDTO = new AddressCompositeDTO("rue des johns", "3", "j4k4j4");
        addressDTO = new AddressDTO(addressCompositeDTO, "Saint Jean Sur Richelieu", "Canada");
    }

    @DisplayName("Test for get Address and return Address if exist")
    @Test
    void givenAddressWhenGetAddressIfExist() {
        when(addressRepository.findByAddressComposite(addressComposite)).thenReturn(address);
        Address foundAddress = addressUtils.getAddress(address);
        verify(addressRepository, times(1)).findByAddressComposite(addressComposite);
        assertEquals(foundAddress, address);
    }
}
