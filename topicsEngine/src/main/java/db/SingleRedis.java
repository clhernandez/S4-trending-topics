package db;

import redis.clients.jedis.Jedis;

public class SingleRedis {
	public static String HASHMAP = "words";
	private static Jedis JEDIS = new Jedis("localhost");
	
	static{
		JEDIS.hset("words", "init", "1");
	}
	
	public static Jedis getRedis(){
		
		return JEDIS;

	}

}
