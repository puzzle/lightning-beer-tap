package ch.puzzle.lnd.websocketbridge;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import ch.puzzle.lnd.websocketbridge.dto.InvoiceDTO;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);
	private String topic;
	private String command;
	private String memoPrefix;

	public MyStompSessionHandler(String topic, String command, String memoPrefix) {
		this.topic = topic;
		this.command = command;
		this.memoPrefix = memoPrefix;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		logger.info("New session established : " + session.getSessionId());
		session.subscribe(topic, this);
		logger.info("Subscribed to " + topic);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		logger.error("An error occured", exception);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return InvoiceDTO.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		logger.info("Received : " + payload.toString());
		final InvoiceDTO invoice = (InvoiceDTO) payload;
		new Thread(() -> {
			try {
				executeCommand(invoice);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void executeCommand(InvoiceDTO invoice) throws IOException, InterruptedException {
		String productsArg = "--products=" + invoice.getOrderedProducts();
		
		if(!invoice.getMemo().startsWith(memoPrefix)) {
			logger.info("Not a beerTap invoice");
			return;
		}
		
		logger.info("Command: " + command + ", Args: " + productsArg);

		ProcessBuilder pb = new ProcessBuilder(command, productsArg);
		Map<String, String> env = pb.environment();
		pb.directory(new File("./"));
		Process p = pb.start();
		p.waitFor();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		String line = "";
		while ((line = reader.readLine()) != null) {
			logger.info(line);
		}
	}
}
