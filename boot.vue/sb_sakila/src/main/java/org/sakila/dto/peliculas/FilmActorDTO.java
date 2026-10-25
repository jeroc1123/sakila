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
public class FilmActorDTO {
    private Timestamp lastUpdate;
    private Short actorId;
    private Short filmId;
}
