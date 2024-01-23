package com.bit.lot.flower.auth.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ImageRequestDto {

  @JsonProperty("images")
  private Image[] images;

  @JsonProperty("lang")
  private String lang;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("resultType")
  private String resultType;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("version")
  private String version;

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Builder
  public static class Image {

    @JsonProperty("format")
    private String format;

    @JsonProperty("name")
    private String name;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("url")
    private String url;


  }
}
