/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package processElements;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.apache.s4.core.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import db.SingleRedis;
import eda.Tweet;
import redis.clients.jedis.Jedis;
import java.util.ArrayList; 

public class OnePE extends ProcessingElement {
	private static Logger logger = LoggerFactory.getLogger(OnePE.class);
	private boolean showEvent = false;
	int cont = 0;
	String word;
	String toreplace;
	
	Stream<Event> downStream;

	public void setDownStream(Stream<Event> stream) {
		downStream = stream;
	}

	public void onEvent(Event event) {
		// Processing
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			logger.error(e.toString());
		}
		
		Event eventOutput = new Event();
		
		Tweet tweet = event.get("tweet",Tweet.class);

		toreplace = tweet.getText();
		Jedis jedis = new Jedis("localhost");
		
		while(cont != jedis.llen("deletew")){	
			word = "" + jedis.lrange("deletew",cont, cont);
			word = word.replace("[", "");
			word = word.replace("]", "");
			toreplace = toreplace.replace(word, " ");
			cont++;
		}
		cont=0;

		//System.out.println(tweet.getText());
		//System.out.println(toreplace);
		
		eventOutput.put("levelTwoStream", Long.class, getEventCount() % 100);
		eventOutput.put("text", String.class, toreplace);
		
		if (showEvent) {
			logger.debug(eventOutput.getAttributesAsMap().toString());
		}

		downStream.put(eventOutput);

	}

	@Override
	protected void onCreate() {
		logger.info("Create One PE");
	}

	@Override
	protected void onRemove() {
		logger.info("Remove One PE");
	}

}
