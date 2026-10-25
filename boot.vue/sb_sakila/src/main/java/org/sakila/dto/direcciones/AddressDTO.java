package org.sakila.dto.direcciones;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Short addressId;
    private String address;
    private String address2;
    private Short cityId;
    private String district;
    private Timestamp lastUpdate;
    private byte[] location;
    private String phone;
    private String postalCode;
}
