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
public class LanguageDTO {
    private Byte languageId;
    private Timestamp lastUpdate;
    private String name;
}
