package org.sakila.dto.ventas;

import java.math.BigDecimal;
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
public class PaymentDTO {
    private Long paymentId;
    private BigDecimal amount;
    private Timestamp lastUpdate;
    private Date paymentDate;
    private Short customerId;
    private Integer rentalId;
    private Byte staffId;
}
