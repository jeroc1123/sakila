package org.sakila.dto.peliculas;

import java.math.BigDecimal;
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
public class FilmDTO {
    private Short filmId;
    private String description;
    private Timestamp lastUpdate;
    private BigInteger length;
    private String rating;
    private Date releaseYear;
    private BigInteger rentalDuration;
    private BigDecimal rentalRate;
    private BigDecimal replacementCost;
    private String specialFeatures;
    private String title;
    private Byte languageId;
    private Byte originalLanguageId;
}
