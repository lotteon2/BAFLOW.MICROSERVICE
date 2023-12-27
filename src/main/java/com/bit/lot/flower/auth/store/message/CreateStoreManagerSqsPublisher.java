package com.bit.lot.flower.auth.store.message;

import bloomingblooms.domain.notification.NotificationData;
import bloomingblooms.domain.notification.NotificationKind;
import bloomingblooms.domain.notification.NotificationURL;
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
  @Value("${cloud.aws.sqs.newcomer-queue.url}")
  private String queueUrl;

  public void publish() throws JsonProcessingException {

    PublishNotificationInformation newStoreManagerInfo = PublishNotificationInformation.getData(
        NotificationURL.NEWCOMER,NotificationKind.NEWCOMER);

    NotificationData<Void> notificationData = NotificationData.notifyData(newStoreManagerInfo);

    SendMessageRequest sendMessageRequest = new SendMessageRequest(
        queueUrl, objectMapper.writeValueAsString(notificationData));

      sqs.sendMessage(sendMessageRequest);

  }
}

