package com.bit.lot.flower.auth.store.service;

import com.bit.lot.flower.auth.store.dto.ImageRequestDto;
import com.bit.lot.flower.auth.store.dto.ImageResponseDto;
import com.bit.lot.flower.auth.store.dto.ImageResponseDto.ImageInfo.FieldInfo;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class NaverClovaOCRService implements
    RequestBusinessNumberFromImageService {

  private final RestTemplate restTemplate;
  @Value("${ocr.naver.secret}")
  private String ocrSecret;

  @Override
  public String getBusinessNumber(String imageUrl) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    headers.set("X-OCR-SECRET", ocrSecret);
    ImageRequestDto requestDto = createImageRequestDtoByImageUrl(imageUrl);

    HttpEntity<ImageRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

    String apiUrl = "https://5uwmtf47oq.apigw.ntruss.com/custom/v1/27880/634611cfef823f53c9ee0a045d3cbfddfa75b4154fbb56e2b30019466f39fedc/general";


    ImageResponseDto response = restTemplate.postForObject(apiUrl, requestEntity,
        ImageResponseDto.class);

    return getBusinessNumberFromResponse(response);


  }

  private String getBusinessNumberFromResponse(ImageResponseDto responseDto) {

    Optional<FieldInfo> businessNumberfield = responseDto.getImages().stream()
        .flatMap(imageInfo -> imageInfo.getFields().stream())
        .filter(fieldInfo -> fieldInfo.getInferText().contains("-"))
        .findFirst();

    if (businessNumberfield.isPresent()) {
      return businessNumberfield.get().getInferText();
    }

    throw new IllegalArgumentException("사업자 등록 번호를 찾을 수 없습니다.");
  }

  private ImageRequestDto createImageRequestDtoByImageUrl(String imageUrl) {
    ImageRequestDto.Image[] image = {createImage(imageUrl)};
    return ImageRequestDto.builder().images(image).lang("ko").requestId("string")
        .resultType("string")
        .version("V1").timestamp(System.currentTimeMillis()).build();
  }

  private ImageRequestDto.Image createImage(String imageUrl) {
    return ImageRequestDto.Image.builder().data(null).format("jpg").name("medium").url(imageUrl)
        .build();


  }

}
