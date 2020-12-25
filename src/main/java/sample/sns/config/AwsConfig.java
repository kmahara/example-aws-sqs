package sample.sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsConfig {
	@Value("${app.aws.endpoint}")
	private String endpoint;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Bean
	public AwsClientBuilder.EndpointConfiguration endpointConfiguration() {
		System.out.println("aws endpoint: " + endpoint);
		return new AwsClientBuilder.EndpointConfiguration(endpoint, region);
	}

	@Bean
	public AmazonSNS amazonSNS(AwsClientBuilder.EndpointConfiguration endpointConfiguration,
			AWSCredentialsProvider credentialsProvider) {
		AmazonSNS sns = AmazonSNSClientBuilder.standard()
				.withEndpointConfiguration(endpointConfiguration)
				.withCredentials(credentialsProvider)
				.build();
		return sns;
	}

	public AmazonSQS amazonSQS(AwsClientBuilder.EndpointConfiguration endpointConfiguration,
			AWSCredentialsProvider credentialsProvider) {
		AmazonSQSAsync sns = AmazonSQSAsyncClientBuilder.standard()
				.withEndpointConfiguration(endpointConfiguration)
				.withCredentials(credentialsProvider)
				.build();
		return sns;
	}

}
