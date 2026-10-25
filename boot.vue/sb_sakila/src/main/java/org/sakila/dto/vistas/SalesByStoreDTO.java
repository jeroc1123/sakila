package org.sakila.dto.vistas;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesByStoreDTO {
    private String manager;
    private String store;
    private BigDecimal totalSales;
}
