package com.bit.lot.flower.auth.social.http.valueobject;

import com.bit.lot.flower.auth.common.valueobject.BaseId;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
public class UserId extends BaseId<UUID> {

}
