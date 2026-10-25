package org.sakila.dto.vistas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffListDTO {
    private String address;
    private String city;
    private String country;
    private Byte id;
    private String name;
    private String phone;
    private byte sid;
    private String zip_code;
}
