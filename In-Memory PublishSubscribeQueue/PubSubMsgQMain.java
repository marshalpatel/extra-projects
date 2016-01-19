//package com.marshalp.pubsubmsgq;


public class PubSubMsgQMain {

	public static void main(String[] args) {

		// Create subscribers
		InMemSubscriber sub1 = new InMemSubscriber("sub1");
		InMemSubscriber sub2 = new InMemSubscriber("sub2");
		InMemSubscriber sub3 = new InMemSubscriber("sub3");

		//Create Publisher
		InMemPublisher pub1 = new InMemPublisher("pub1");
		
		pub1.publishMessage("Publishing message for pub1");

		//add subscribers to generate unique ids.
		int id1 = pub1.addSubscriber(sub1);
		int id2 = pub1.addSubscriber(sub2);
		int id3 = pub1.addSubscriber(sub3);

		//re-subscribe adds the subscribers with the given id to the actual subscriber set.
		
		
		pub1.reSubscribe(id1);
		pub1.reSubscribe(id3);

		System.out.println();
		pub1.publishMessage("Publishing message for pub1");
		pub1.publishMessage("Publishing message for pub1");

		System.out.println();
		pub1.reSubscribe(id2);

		System.out.println();
		pub1.publishMessage("Publishing message for pub1");

		pub1.removeSubscriber(sub2);
		
		System.out.println();
		pub1.publishMessage("Publishing message for pub1");

		id2 = pub1.addSubscriber(sub2);

		System.out.println();
		pub1.publishMessage("Publishing message for pub1");

		System.out.println();
		pub1.reSubscribe(id2);
		
		InMemPublisher pub = new InMemPublisher("pub");
		
		int id1_2 = pub.addSubscriber(sub1);
		int id2_2 = pub.addSubscriber(sub2);
		int id2_3 = pub.addSubscriber(sub3);
		
		System.out.println();
		pub.publishMessage("Message from topic 2");
		
		pub.reSubscribe(id2_3);
		pub.removeSubscriber(sub2);
		pub.reSubscribe(id1_2);
		
		pub.reSubscribe(id2_2);
		

	}

}
