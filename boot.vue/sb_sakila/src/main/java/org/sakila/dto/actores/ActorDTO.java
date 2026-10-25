package org.sakila.dto.actores;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorDTO {
    private Short actorId;
    private String firstName;
    private String lastName;
    private Timestamp lastUpdate;
}
