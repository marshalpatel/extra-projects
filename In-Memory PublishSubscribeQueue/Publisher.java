//package com.marshalp.pubsubmsgq;

/*
 * Publisher Interface
 */
public interface Publisher {

	public int addSubscriber(Subscriber subObj);
	public void removeSubscriber(Subscriber subObj);
	public void notifySubscribers(Object o);
}
