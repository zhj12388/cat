package com.dianping.cat.demo;

import org.junit.Test;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;

public class TestSendMessage {

	@Test
	public void sendSendUrlErrorMessage() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("URL", "Test");

			t.addData("key and value");
			t.setStatus(new NullPointerException());
			t.complete();

		}
		Thread.sleep(1000);
	}

	@Test
	public void sendSendCallErrorMessage() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("Call", "Test");

			t.addData("key and value");
			t.setStatus(new NullPointerException());
			t.complete();

		}
		Thread.sleep(1000);
	}

	@Test
	public void sendSendSqlErrorMessage() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("SQL", "Test");

			t.addData("key and value");
			t.setStatus(new NullPointerException());
			t.complete();
		}
		Thread.sleep(1000);
	}

	@Test
	public void sendMessage() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("Test", "Test");

			t.addData("key and value");
			t.complete();

		}
		Thread.sleep(1000);
	}

	@Test
	public void sendError() throws Exception {
		for (int i = 0; i < 100; i++) {
			Cat.getProducer().logError(new NullPointerException());
			Cat.getProducer().logError(new OutOfMemoryError());
		}
		Thread.sleep(1000);
	}

	@Test
	public void sendEvent() throws Exception {
		for (int i = 0; i < 100; i++) {
			Event t = Cat.getProducer().newEvent("Test", "Test");

			t.addData("key and value");
			t.complete();
		}
		Thread.sleep(1000);
	}

	@Test
	public void sendPigeonClientTransaction() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonCall", "Method3");
			Cat.getProducer().newEvent("PigeonCall.server", "192.168.64.11:2280");
			t.addData("key and value");

			Thread.sleep(1);
			t.complete();
		}
		for (int i = 0; i < 200; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonCall", "Method3");
			Cat.getProducer().newEvent("PigeonCall.server", "192.168.7.24:8080");
			t.addData("key and value");

			Thread.sleep(1);
			t.complete();
		}

		for (int i = 0; i < 300; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonCall", "Method3");
			Cat.getProducer().newEvent("PigeonCall.server", "192.168.7.39:8080");
			t.addData("key and value");

			Thread.sleep(1);
			t.complete();
		}
		Thread.sleep(100);
	}

	@Test
	public void sendPigeonServerTransaction() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonService", "Method6");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.77");
			t.addData("key and value");

			Thread.sleep(51);
			t.complete();
		}
		for (int i = 0; i < 200; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonService", "Method8");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.20");
			t.addData("key and value");

			Thread.sleep(1);
			t.complete();
		}

		for (int i = 0; i < 300; i++) {
			Transaction t = Cat.getProducer().newTransaction("PigeonService", "Method5");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.231");
			t.addData("key and value");

			Thread.sleep(1);
			t.complete();
		}
		Thread.sleep(100);
	}

	@Test
	public void sendCacheTransaction() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("Cache.kvdb", "Method6");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.77");
			t.addData("key and value");

			Thread.sleep(11);
			Transaction t2 = Cat.getProducer().newTransaction("Cache.local", "Method");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.77");
			t2.addData("key and value");

			Thread.sleep(11);
			t2.complete();
			t.complete();
		}
	}

	@Test
	public void sendLongCacheTransaction() throws Exception {
		for (int i = 0; i < 100; i++) {
			Transaction t = Cat.getProducer().newTransaction("Cache.kvdb", "Method6");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.77");
			t.addData("key and value");

			Thread.sleep(11);
			Transaction t2 = Cat.getProducer().newTransaction("Cache.local", "Method");
			Cat.getProducer().newEvent("PigeonService.client", "192.168.7.77");
			t2.addData("key and value");

			Thread.sleep(11);
			t2.complete();
			t.complete();
		}
	}

	@Test
	public void sendLongURLTransaction() throws Exception {
		for (int i = 0; i < 10; i++) {
			Transaction t = Cat.getProducer().newTransaction("URL", "Method6");
			t.addData("key and value");
			Thread.sleep(60);
			t.complete();
		}
	}

	@Test
	public void sendLongSQLTransaction() throws Exception {
		for (int i = 0; i < 10; i++) {
			Transaction t = Cat.getProducer().newTransaction("SQL", "Method6");
			t.addData("key and value");
			Thread.sleep(102);
			t.complete();
		}
	}

	@Test
	public void sendCacheTransactionWithMissed() throws Exception {
		for (int i = 0; i < 130; i++) {
			Transaction t = Cat.getProducer().newTransaction("Cache.kvdb", "Method" + i % 10);
			Cat.getProducer().newEvent("Cache.kvdb", "Method" + i % 10 + ":missed");
			t.addData("key and value");

			Transaction t2 = Cat.getProducer().newTransaction("Cache.web", "Method" + i % 10);
			Cat.getProducer().newEvent("Cache.web", "Method" + i % 10 + ":missed");
			t2.addData("key and value");
			Thread.sleep(2);
			t2.complete();
			t.complete();

			Transaction t3 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t3.addData("key and value");
			Thread.sleep(3);
			t3.complete();
		}

		Transaction t2 = Cat.getProducer().newTransaction("Cache.web", "Method");
		t2.addData("key and value");
		Thread.sleep(2);
		t2.complete();
		Thread.sleep(1000);
	}

	@Test
	public void sendMaxMessage() throws Exception {
		long time = System.currentTimeMillis();
		int i = 10;
		while (i > 0) {
			i = 10 * 1000000 - (int) (System.currentTimeMillis() - time);

			Transaction t = Cat.getProducer().newTransaction("Cache.kvdb", "Method" + i % 10);
			t.setStatus(Message.SUCCESS);
			Cat.getProducer().newEvent("Cache.kvdb", "Method" + i % 10 + ":missed");
			t.addData("key and value");

			Transaction t2 = Cat.getProducer().newTransaction("Cache.web", "Method" + i % 10);
			Cat.getProducer().newEvent("Cache.web", "Method" + i % 10 + ":missed");
			t2.addData("key and value");
			t2.setStatus(Message.SUCCESS);
			t2.complete();

			Transaction t3 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t3.addData("key and value");
			t3.setStatus(Message.SUCCESS);
			t3.complete();
			
			Transaction t4 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t4.addData("key and value");
			t4.setStatus(Message.SUCCESS);
			t4.complete();
			
			Transaction t5 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t5.addData("key and value");
			t5.setStatus(Message.SUCCESS);
			t5.complete();
			
			Transaction t6 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t6.addData("key and value");
			t6.setStatus(Message.SUCCESS);
			t6.complete();
			
			Transaction t7 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t7.addData("key and value");
			t7.setStatus(Message.SUCCESS);
			t7.complete();
			
			Transaction t8 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t8.addData("key and value");
			t8.setStatus(Message.SUCCESS);
			t8.complete();
			
			Transaction t9 = Cat.getProducer().newTransaction("Cache.memcached", "Method" + i % 10);
			t9.addData("key and value");
			t9.setStatus(Message.SUCCESS);
			t9.complete();
			t.complete();
		}
		Thread.sleep(10 * 1000);
	}
}