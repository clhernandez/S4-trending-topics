package processElements;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.SingleRedis;

public class ThreePE extends ProcessingElement {
	private static Logger logger = LoggerFactory.getLogger(ThreePE.class);
	private boolean showEvent = false;

	public void onEvent(Event event) {

		// Processing
		try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			logger.error(e.toString());
		}

		if (showEvent) {
			logger.debug(event.getAttributesAsMap().toString());
		}

		//long timeInit = event.get("time", Long.class);
		long timeFinal = System.currentTimeMillis();
		
		String word = event.get("word", String.class);
		if(SingleRedis.getRedis().hexists(SingleRedis.HASHMAP, word)){
			System.out.println("EXISTE:"+word);
			SingleRedis.getRedis().hincrBy(SingleRedis.HASHMAP, word, 1);
		}else{
			System.out.println("NO EXISTE:"+word);
			SingleRedis.getRedis().hset(SingleRedis.HASHMAP, word, "1");
		}
		
	}

	@Override
	protected void onCreate() {		
		logger.info("Create Three PE");
	}

	@Override
	protected void onRemove() {
		logger.info("Remove Three PE");
	}

}
