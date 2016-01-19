//package com.marshalp.pubsubmsgq;

import java.util.*;

public class InMemSubscriber implements Subscriber {

	private List<Message> msgQueue = new ArrayList<Message>();
	private String subName;

	public InMemSubscriber(String name) {
		this.subName = name;

	}

	/*
	 * Method to update the subscriber with published message.
	 */
	@Override
	public void updateSubscriber(Message msg) {

		this.msgQueue.add(msg);
		System.out.println("Sub-Name: " + this.subName + ", MessageId: "
				+ msg.getMsgId() + ", Message: " + msg.getMessage()
				+ ", Timestamp: " + msg.getTimeStamp());
	}

	/*
	 * print the last message for this subscriber
	 */
	public void printLastMsg() {

		Message msg;
		if (this.msgQueue.size() > 0) {
			msg = this.msgQueue.get(this.msgQueue.size() - 1);
			System.out.println("MessageId: " + msg.getMsgId() + " Message: "
					+ msg.getMessage() + " Timestamp: " + msg.getTimeStamp());
		} else
			System.out.println("No messages in the queue.");
	}

	/*
	 * print all the messages of this subscriber
	 */
	public void printAllMessages() {

		System.out.println("Printing all msgs for subscriber: " + this.subName);
		for (Message m : this.msgQueue) {
			System.out.println("MessageId: " + m.getMsgId() + " Message: "
					+ m.getMessage() + " Timestamp: " + m.getTimeStamp());
		}
	}

	/*
	 * Overriding equals and hashCode method so that subscriber objects when treated as keys in hashmap 
	 * will be equal based on the subscriber name.
	 */
	@Override
	public boolean equals(Object o) {

		InMemSubscriber obj = (InMemSubscriber) o;

		if (obj != null && obj instanceof InMemSubscriber
				&& this.subName.equals(obj.subName))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {

		int hash = 31;
		hash = hash * 7 + this.subName.hashCode();
		return hash;
	}

}
