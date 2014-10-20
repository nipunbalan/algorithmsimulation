package com.technipun.ds;

/**
 * @desc This class defines a Message Queue Structure
 * @author Nipun Balan Thekkummal
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Iterator;

import com.technipun.dc.algorithm.Message;

/**
 * The Class MessageQueue.
 */
public class MessageQueue {

	/** The message queue array */
	private ArrayList<Message> messageQue;
	
	/**
	 * Instantiates a new message queue.
	 */
	public  MessageQueue()
	{
		messageQue = new ArrayList<Message>();
	}

	/**
	 * Clear the message Queue
	 */
	public void clear() {
		messageQue.clear();
	}

	/**
	 * Checks if message que is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return messageQue.isEmpty();
	}

	/**
	 * Removes the message for the que.
	 *
	 * @param o the message object to be removed
	 */
	public void remove(Object o) {

		Iterator<Message> msgQueItr = messageQue.iterator();
		while (msgQueItr.hasNext()) {
			Message msg = msgQueItr.next();
			if (msg == o) {
				msgQueItr.remove();
			}
		}
	}

	/**
	 * Size of the message queue
	 *
	 * @return the int
	 */
	public int size() {
		return messageQue.size();
	}

	/**
	 * Adds the message to the end of the queue
	 *
	 * @param message the Message
	 */
	public void add(Message message) {
		messageQue.add(message);
	}

	/**
	 * Removes and returns message from front of the queue (FIFO)
	 *
	 * @return the message 
	 */
	public Message poll() {
		if (!messageQue.isEmpty()) {
			Message msg = messageQue.get(0);
			messageQue.remove(0);
			return msg;
		} else {
			return null;
		}
	}

	/**
	 * Removes the message from front of the queue
	 */
	public void remove() {
		if (!messageQue.isEmpty()) {
			messageQue.remove(0);
		}
	}
	
	
}
