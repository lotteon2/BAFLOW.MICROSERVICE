package com.bit.lot.flower.auth.store.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ImageResponseDto {

  @JsonProperty("version")
  private String version;

  @JsonProperty("requestId")
  private String requestId;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("images")
  private List<ImageInfo> images;


  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Builder
  public static class ImageInfo {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("inferResult")
    private String inferResult;

    @JsonProperty("message")
    private String message;

    @JsonProperty("validationResult")
    private ValidationResult validationResult;

    @JsonProperty("fields")
    private List<FieldInfo> fields;


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class ValidationResult {

      @JsonProperty("result")
      private String result;


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class FieldInfo {

      @JsonProperty("valueType")
      private String valueType;

      @JsonProperty("boundingPoly")
      private BoundingPoly boundingPoly;

      @JsonProperty("inferText")
      private String inferText;

      @JsonProperty("inferConfidence")
      private double inferConfidence;

      @AllArgsConstructor
      @NoArgsConstructor
      @Getter
      @Builder
      public static class BoundingPoly {

        @JsonProperty("vertices")
        private List<Vertex> vertices;


        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Builder
        public static class Vertex {

          @JsonProperty("x")
          private double x;

          @JsonProperty("y")
          private double y;

        }
      }
    }
  }
}
