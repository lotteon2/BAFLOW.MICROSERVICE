package com.bit.lot.flower.auth.store.valueobject;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
public class StoreId  extends BaseId<Long> {
    @JsonProperty("storeId")
    public Long getStoreIdValue() {
        return this.getValue();
    }
}
