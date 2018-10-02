package ch.puzzle.lnd.websocketbridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootApplication
public class WebsocketBridgeApplication implements ApplicationRunner{
	
	private static String DEFAULT_URL = "wss://ln-self-order-pos-dev.ose3.puzzle.ch/websocket/invoice?access_token=";
	private static String DEFAULT_TOPIC = "/topic/invoice";
	
	private static String OPTION_ARG_URL = "url";
	private static String OPTION_ARG_TOPIC = "topic";
	private static String OPTION_ARG_COMMAND = "./dummy_command.sh";

	private static final Logger logger = LoggerFactory.getLogger(WebsocketBridgeApplication.class);
	
	/**
	 * Run the Application with the following arguments
	 * --url=https://localhost?
	 * --topic=/topic/invoice
	 * @param args
	 */
	public static void main(String[] args) {	
		SpringApplication.run(WebsocketBridgeApplication.class, args);
	}
	
	@Override
    public void run(ApplicationArguments args) throws Exception {
		logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());
        
        logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());
        
        String url = DEFAULT_URL;
        String topic = DEFAULT_TOPIC;
        String command = OPTION_ARG_COMMAND;
        if(args.getOptionValues(OPTION_ARG_URL) != null && !args.getOptionValues(OPTION_ARG_URL).equals("")) {
        	url = args.getOptionValues(OPTION_ARG_URL).get(0);
        }
        
        if(args.getOptionValues(OPTION_ARG_TOPIC) != null && !args.getOptionValues(OPTION_ARG_TOPIC).equals("")) {
        	topic = args.getOptionValues(OPTION_ARG_TOPIC).get(0);
        }
        
        if(args.getOptionValues(OPTION_ARG_COMMAND) != null && !args.getOptionValues(OPTION_ARG_COMMAND).equals("")) {
        	command = args.getOptionValues(OPTION_ARG_COMMAND).get(0);
        }
		
        List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport( new StandardWebSocketClient()) );
		WebSocketClient transport = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSessionHandler sessionHandler = new MyStompSessionHandler(topic, command);
        stompClient.connect(url, sessionHandler);
        // keep running
        new Scanner(System.in).nextLine();
	}
}
