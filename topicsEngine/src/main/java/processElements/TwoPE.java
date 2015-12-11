package processElements;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.apache.s4.core.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoPE extends ProcessingElement {
	private static Logger logger = LoggerFactory.getLogger(TwoPE.class);

	private boolean showEvent = false;

	Stream<Event> downStream;

	public void setDownStream(Stream<Event> stream) {
		downStream = stream;
	}

	public void onEvent(Event event) {
		
		// Processing
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			logger.error(e.toString());
		}

		Event eventOutput = new Event();
		//System.out.println(event.get("text", String.class));
		
		eventOutput.put("levelThreeStream", Long.class, getEventCount()	% 50);
		
		try {
			String[] inputText = event.get("text", String.class).split(" ");
			for (int i = 0; i < inputText.length; i++) {
				eventOutput.put("word", String.class, inputText[i]);
			}
			
		} catch (Exception e) {
			System.out.println("Eror al parsear la frase: "+ event.get("text", String.class));
		}
		
		//eventOutput.put("time", Long.class, event.get("time", Long.class));

		if (showEvent) {
			logger.debug(eventOutput.getAttributesAsMap().toString());
		}

		downStream.put(eventOutput);
	}

	@Override
	protected void onCreate() {
		logger.info("Create Two PE");
	}

	@Override
	protected void onRemove() {
		logger.info("Remove Two PE");
	}

}
