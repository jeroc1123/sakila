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
public class CityDTO {
    private Short cityId;
    private String city;
    private Short countryId;
    private Timestamp lastUpdate;
}
