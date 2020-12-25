package sample.sns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.sns.queue.TestQueue;

@RestController
public class TestController {

	@Autowired
	private TestQueue queue;

	@GetMapping("/create")
	public String create() {
		queue.create();
		return "create";
	}

	@GetMapping("/destroy")
	public String destroy() {
		queue.destroy();
		return "destroy";
	}

	@GetMapping("/send")
	public String send(@RequestParam(defaultValue = "body") String message) {
		queue.send(message);
		return "send";
	}

	@GetMapping("/receive")
	public Object receive() {
		return queue.receive();
	}
}
