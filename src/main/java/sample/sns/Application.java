package sample.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.cache.ElastiCacheAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;

// 未使用機能に対し EC2 metadata を取得しようとするので、明示的に無効化する。
@SpringBootApplication(exclude = {
		ElastiCacheAutoConfiguration.class,
		ContextInstanceDataAutoConfiguration.class
})
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
//		app.setAdditionalProfiles("local");
		app.run(args);
	}

}
