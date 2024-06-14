package org.example.backend.store;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInformationVo {

    private int storeId;
    private String menuName;
    private int menuPrice;
    private String menuImage;
    private int visibilityStatus;
}
