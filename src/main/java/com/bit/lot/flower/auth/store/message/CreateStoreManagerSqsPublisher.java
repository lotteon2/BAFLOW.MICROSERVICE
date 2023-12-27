package com.bit.lot.flower.auth.store.message;

import bloomingblooms.domain.notification.NotificationData;
import bloomingblooms.domain.notification.NotificationKind;
import bloomingblooms.domain.notification.PublishNotificationInformation;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateStoreManagerSqsPublisher {

  private final AmazonSQS sqs;
  private final ObjectMapper objectMapper;
  private final String message = "님이 가입 문의를 신청하였습니다.";
  @Value("${cloud.aws.sqs.newcomer-queue.url}")
  private String queueUrl;

  public void publish(String name) throws JsonProcessingException {

    PublishNotificationInformation newStoreManagerInfo = PublishNotificationInformation.builder()
        .content(name + message).notificationKind(
            NotificationKind.NEWCOMER).notificationUrl(queueUrl).build();

    NotificationData<String> notificationData = NotificationData.notifyData(newStoreManagerInfo);

    SendMessageRequest sendMessageRequest = new SendMessageRequest(
        queueUrl, objectMapper.writeValueAsString(notificationData));

      sqs.sendMessage(sendMessageRequest);

  }
}

