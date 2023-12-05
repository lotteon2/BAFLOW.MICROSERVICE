package com.bit.lot.flower.auth.common.valueobject;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public abstract class BaseId<T> {

  private T value;



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseId<?> baseId = (BaseId<?>) o;
    return value.equals(baseId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}