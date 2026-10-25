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
public class StoreDTO {
    private Byte storeId;
    private Timestamp lastUpdate;
    private Short addressId;
    private Byte managerStaffId;
}
