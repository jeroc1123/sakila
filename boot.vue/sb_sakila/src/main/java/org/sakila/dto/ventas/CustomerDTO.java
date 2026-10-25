package org.sakila.dto.ventas;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Short customerId;
    private BigInteger active;
    private Date createDate;
    private String email;
    private String firstName;
    private String lastName;
    private Timestamp lastUpdate;
    private Short addressId;
    private Byte storeId;
}
