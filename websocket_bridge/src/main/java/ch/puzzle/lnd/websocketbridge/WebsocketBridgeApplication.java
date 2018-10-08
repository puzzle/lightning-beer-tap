package ch.puzzle.lnd.websocketbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebsocketBridgeApplication {

	/**
	 * Run the Application with the following arguments
	 * --url=https://localhost?
	 * --topic=/topic/invoice
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(WebsocketBridgeApplication.class, args);
	}

}
