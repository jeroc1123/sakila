package org.sakila.dto.ventas;

import java.math.BigInteger;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffDTO {
    private Byte staffId;
    private BigInteger active;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastUpdate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private byte[] picture;
    private String username;
    private Short addressId;
    private Byte storeId;
}
