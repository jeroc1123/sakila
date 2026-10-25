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
public class SalesByFilmCategoryDTO {
    private String category;
    private BigDecimal totalSales;
}
