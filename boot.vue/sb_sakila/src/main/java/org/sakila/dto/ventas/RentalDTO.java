package org.sakila.dto.ventas;

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
public class RentalDTO {
    private Integer rentalId;
    private Timestamp lastUpdate;
    private Date rentalDate;
    private Date returnDate;
    private Short customerId;
    private Integer inventoryId;
    private Byte staffId;
}
