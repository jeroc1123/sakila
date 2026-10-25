package org.sakila.dto.peliculas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmTextDTO {
    private Short filmId;
    private String description;
    private String title;
}
