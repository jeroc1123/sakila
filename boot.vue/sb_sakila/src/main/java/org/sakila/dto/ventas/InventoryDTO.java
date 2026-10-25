package org.sakila.dto.ventas;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Integer inventoryId;
    private Timestamp lastUpdate;
    private Short filmId;
    private Byte storeId;
}
