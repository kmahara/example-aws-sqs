package sample.sns.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

public abstract class Queue {
	private final String topicName;
	private final String queueName;

	@Autowired
	private AmazonSNS sns;

	@Autowired
	private AmazonSQS sqs;

	private String topicArn;
	private String queueUrl;
	private String subscriptionArn;

	public Queue(String topicName, String queueName) {
		this.topicName = topicName;
		this.queueName = queueName;
	}

	public void create() {
		CreateTopicResult topicResult = sns.createTopic(topicName);
		CreateQueueResult queueResult = sqs.createQueue(queueName);

		topicArn = topicResult.getTopicArn();
		queueUrl = queueResult.getQueueUrl();

		subscriptionArn = Topics.subscribeQueue(sns, sqs, topicArn, queueUrl);
	}

	public void destroy() {
		if (subscriptionArn == null) {
			create();
		}

		sns.unsubscribe(subscriptionArn);
		sqs.deleteQueue(queueUrl);
		sns.deleteTopic(topicArn);

		topicArn = null;
		queueUrl = null;
		subscriptionArn = null;
	}

	public void send(String message) {
		if (subscriptionArn == null) {
			create();
		}

		sns.publish(topicArn, message);
	}

	public Object receive() {
		if (subscriptionArn == null) {
			create();
		}

		ReceiveMessageResult receiveMessage = sqs.receiveMessage(new ReceiveMessageRequest(queueUrl)
				.withWaitTimeSeconds(1));

		List<Message> messages = receiveMessage.getMessages();

		List<Object> list = new ArrayList<>();
		for (Message message : messages) {
			String body = message.getBody();
			Map<String, String> attributes = message.getAttributes();
			Map<String, MessageAttributeValue> messageAttributes = message.getMessageAttributes();
			String receiptHandle = message.getReceiptHandle();

			Map<String, Object> map = new HashMap<>();
			map.put("body", body);
			map.put("attr", attributes);
			map.put("messageAttr",  messageAttributes);
			map.put("receiptHandle",  receiptHandle);
			list.add(map);

			sqs.deleteMessage(queueUrl, receiptHandle);
		}

		return list;
	}
}
