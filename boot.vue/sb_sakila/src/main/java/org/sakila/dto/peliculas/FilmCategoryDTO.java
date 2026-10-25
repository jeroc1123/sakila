package org.sakila.dto.peliculas;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmCategoryDTO {
    private Timestamp lastUpdate;
    private Byte categoryId;
    private Short filmId;
}
