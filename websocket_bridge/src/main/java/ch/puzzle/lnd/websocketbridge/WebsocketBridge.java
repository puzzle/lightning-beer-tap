package ch.puzzle.lnd.websocketbridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Component
public class WebsocketBridge implements Runnable, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketBridgeApplication.class);

	private static String DEFAULT_URL = "ws://localhost/websocket/invoice?access_token=";
	private static String DEFAULT_TOPIC = "/topic/invoice";
	private static String DEFAULT_COMMAND = "./dummy_command.sh";

	private static String OPTION_ARG_URL = "url";
	private static String OPTION_ARG_TOPIC = "topic";
	private static String OPTION_ARG_COMMAND = "command";

	private String url = DEFAULT_URL;
	private String topic = DEFAULT_TOPIC;
	private String command = DEFAULT_COMMAND;

	private Thread thread;

	private boolean keepRunning = true;

	@Autowired
	public WebsocketBridge(ApplicationArguments args) {

		initArguments(args);

		this.thread = new Thread(this);
		this.thread.start();
	}

	private void initArguments(ApplicationArguments args) {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
		logger.info("OptionNames: {}", args.getOptionNames());

		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
		logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
		logger.info("OptionNames: {}", args.getOptionNames());

		if (args.getOptionValues(OPTION_ARG_URL) != null && !args.getOptionValues(OPTION_ARG_URL).equals("")) {
			url = args.getOptionValues(OPTION_ARG_URL).get(0);
		}

		if (args.getOptionValues(OPTION_ARG_TOPIC) != null && !args.getOptionValues(OPTION_ARG_TOPIC).equals("")) {
			topic = args.getOptionValues(OPTION_ARG_TOPIC).get(0);
		}

		if (args.getOptionValues(OPTION_ARG_COMMAND) != null && !args.getOptionValues(OPTION_ARG_COMMAND).equals("")) {
			command = args.getOptionValues(OPTION_ARG_COMMAND).get(0);
		}
	}
	
	private ListenableFuture<StompSession> connect() throws InterruptedException{
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient transport = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		StompSessionHandler sessionHandler = new MyStompSessionHandler(topic, command);
		
		ListenableFuture<StompSession> session = null;
		
		boolean connected = false;
		
		while(!connected) {
			try {
				Thread.sleep(5000);
				session = stompClient.connect(url, sessionHandler);
				session.get().isConnected();
				connected = true;
			}catch(HttpServerErrorException | ExecutionException e) {
				logger.error("Connection Error", e);
				// do try again
			}
		}
		
		
		return session;
	}
	
	

	@Override
	public void run() {
		ListenableFuture<StompSession> session = null;

		while (keepRunning) {
			try {
				Thread.sleep(5000);
				if(session == null || !session.get().isConnected()) {
					logger.info("is not connected! Try reconnect");
					session = connect();
				}
			} catch (InterruptedException | ExecutionException e) {
				keepRunning = false;
			}
		}
	}

	@Override
	public void destroy() throws Exception {
		logger.info("Do stuff on destroy here");
	}
}
