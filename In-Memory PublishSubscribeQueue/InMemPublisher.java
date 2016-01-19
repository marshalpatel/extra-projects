//package com.marshalp.pubsubmsgq;

import java.util.*;

public class InMemPublisher implements Publisher {

	private Map<Subscriber, Long> subscriberTSMap;
	private Map<Integer, Subscriber> subscriberIDMap;
	private Set<Subscriber> subcriberSet;
	private List<Message> msgQueue;
	private boolean changed;
	private String pubName;

	private static int subId;
	private int id;

	public InMemPublisher(String name) {
		this.subscriberTSMap = new HashMap<Subscriber, Long>();
		this.subscriberIDMap = new HashMap<Integer, Subscriber>();
		this.subcriberSet = new HashSet<Subscriber>();
		this.msgQueue = new ArrayList<Message>();
		this.pubName = name;
	}

	/*
	 * Method to add the subscriber to the hashset and update the subscriber
	 * with the messages since last subscription.
	 */

	public synchronized void reSubscribe(int id) {

		Collections.sort(this.msgQueue);
		Subscriber subObj = this.subscriberIDMap.get(id);

		if (this.subscriberTSMap.containsKey(subObj)) {

			long ts = this.subscriberTSMap.get(subObj);
			int index = -1;

			// perform binary search on time stamps, find index of msg
			// with smallest timestamp greater than equal to ts

			index = binarySearch(this.msgQueue, 0, this.msgQueue.size() - 1, ts);

			if (index > -1) {

				for (int i = index; i < msgQueue.size(); i++) {
					subObj.updateSubscriber(this.msgQueue.get(i));
				}
			}

			this.subscriberIDMap.remove(id);
			this.subscriberTSMap.remove(subObj);

			this.subcriberSet.add(subObj);

			if (this.subscriberIDMap.size() == 0
					&& this.subscriberTSMap.size() == 0)
				this.msgQueue.clear();
		}
		
		else{
			System.out.println("No Subscriber found for publisher: "+this.pubName + " with sub-id: "+ id);
		}

	}

	/*
	 * Method to generate identifier. Makes an entry to the subscriberIDMap <ID,
	 * Subscriber object> Makes an entry to the subscriberTSMap <Subscriber
	 * object, timestamp> Returns the id of the subscriber.
	 */
	@Override
	public synchronized int addSubscriber(Subscriber subObj) {

		if (subObj == null)
			throw new NullPointerException();

		id = ++subId;
		this.subscriberIDMap.put(id, subObj);
		this.subscriberTSMap.put(subObj, new Date().getTime());

		return id;

	}

	/*
	 * Method to remove subscribers
	 */
	@Override
	public synchronized void removeSubscriber(Subscriber subObj) {

		// Subscriber subObj = this.subscriberIDMap.get(id);
		this.subscriberTSMap.remove(subObj);
		this.subcriberSet.remove(subObj);

	}

	/*
	 * Method to update subscribers with the messages.
	 */
	@Override
	public void notifySubscribers(Object obj) {

		HashSet<Subscriber> tempSet;

		synchronized (this) {

			if (!this.changed)
				return;
			tempSet = new HashSet<Subscriber>(this.subcriberSet);
			this.changed = false;

		}

		Iterator<Subscriber> itr = tempSet.iterator();
		while (itr.hasNext()) {

			Subscriber sub = itr.next();

			sub.updateSubscriber((Message) obj);

		}

	}

	/*
	 * Method to publish message to subscribers. New message object is added to
	 * the Message Queue. 'changed' flag is updated every time a new message is
	 * published.
	 */
	public void publishMessage(String msg) {

		Message msgObj = new Message(msg);
		this.msgQueue.add(msgObj);
		this.changed = true;
		notifySubscribers(msgObj);

	}

	/*
	 * Method to perform binary seach. The method searches for the message with
	 * the smallest time stamp greater than the subscribed time stamp.
	 */
	private synchronized int binarySearch(List<Message> msgList, int s, int e,
			long k) {

		if (s > e)
			return -1;

		if (s == e) {
			if (s > -1 && msgList.get(s).getTimeStamp() >= k)
				return s;

			else
				return -1;
		}

		int mid = (s + e) / 2;

		if (s == mid) {

			if (msgList.get(s).getTimeStamp() > k)
				return s;

			else if (msgList.get(e).getTimeStamp() > k)
				return e;

			else
				return -1;
		}

		if (msgList.get(mid).getTimeStamp() == k)
			return mid + 1;

		if (msgList.get(mid - 1).getTimeStamp() < k
				&& msgList.get(mid).getTimeStamp() > k)
			return mid;

		if (msgList.get(mid).getTimeStamp() < k
				&& msgList.get(mid + 1).getTimeStamp() > k)
			return mid + 1;

		if (msgList.get(mid).getTimeStamp() > k)
			return binarySearch(msgList, s, mid - 1, k);

		else
			return binarySearch(msgList, mid + 1, e, k);

	}

}
