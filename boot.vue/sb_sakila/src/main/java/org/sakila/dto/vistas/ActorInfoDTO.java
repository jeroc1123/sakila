package org.sakila.dto.vistas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorInfoDTO {
    private Short actorId;
    private String filmInfo;
    private String firstName;
    private String lastName;
}
