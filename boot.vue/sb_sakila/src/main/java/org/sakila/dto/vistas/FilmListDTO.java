package org.sakila.dto.vistas;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmListDTO {
    private Short fid;
    private String actors;
    private String category;
    private String description;
    private BigInteger length;
    private BigDecimal price;
    private String rating;
    private String title;
}
