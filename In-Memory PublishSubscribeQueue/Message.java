//package com.marshalp.pubsubmsgq;

import java.util.Date;

public class Message implements Comparable<Message> {

	private String msg;
	private static int id;
	 private int msgId;
	 private long timeStamp;
	 
	 public long getTimeStamp(){
		 return this.timeStamp;
	 }
	 
	 public int getMsgId(){
		 return this.msgId;
	 }
	 
	 public String getMessage(){
		 return this.msg;
	 }

	public Message(String msg) {
		this.msg = msg;
		this.msgId = ++id;
		this.timeStamp = new Date().getTime();
	}

	/*
	 * Implemented compareTo method in order to sort the message objects by timestamps
	 */
	@Override
	public int compareTo(Message msgObj) {

		if (this.timeStamp > msgObj.timeStamp)
			return 1;
		else if (this.timeStamp < msgObj.timeStamp)
			return -1;
		else
			return 0;
	}

}
