package com.technipun.ds;

import java.util.ArrayList;
import java.util.Iterator;

import com.technipun.dc.algorithm.Message;

public class MessageQueue {

	private ArrayList<Message> messageQue;
	
	public  MessageQueue()
	{
		messageQue = new ArrayList<Message>();
	}

	public void clear() {
		messageQue.clear();
	}

	public boolean isEmpty() {
		return messageQue.isEmpty();
	}

	public void remove(Object o) {

		Iterator<Message> msgQueItr = messageQue.iterator();
		while (msgQueItr.hasNext()) {
			Message msg = msgQueItr.next();
			if (msg == o) {
				msgQueItr.remove();
			}
		}
	}

	public int size() {
		return messageQue.size();
	}

	public void add(Message e) {
		messageQue.add(e);
	}

	public Message poll() {
		if (!messageQue.isEmpty()) {
			Message msg = messageQue.get(0);
			messageQue.remove(0);
			return msg;
		} else {
			return null;
		}
	}

	public void remove() {
		if (!messageQue.isEmpty()) {
			messageQue.remove(0);
		}
	}
}
