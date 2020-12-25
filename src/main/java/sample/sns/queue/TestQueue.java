package sample.sns.queue;

import org.springframework.stereotype.Component;

@Component
public class TestQueue extends Queue {

	public TestQueue() {
		super("test", "test");
	}
}
