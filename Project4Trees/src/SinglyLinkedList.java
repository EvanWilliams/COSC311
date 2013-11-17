

import java.io.IOException;
import java.io.RandomAccessFile;

public class SinglyLinkedList<E> {

	/**
	 * @param args
	 */

	public int size = 0;
	
	public Node<E> head = null;
	private Node<E> prev = null;
	public Node<E> iterator = null;

	private void addAfter(Node<E> node, E item) {
		node.next = new Node<E>(item, node.next);
		size++;

	}

	/**
	 * add decides if the index is at the head of the list and adds first. Other wise
	 * it adds the node at the end of the node pointer.
	 * @param index
	 * @param item
	 */
	private void add(int index, E item) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		if (index == 0) {
			addFirst(item);
		} else {
			Node<E> node = getNode(index - 1);
			addAfter(node, item);
		}
	}

	
	public boolean add(E item) {
		add(size, item);
		return true;
	}

	/**
	 * adds at the head of the list
	 * @param item
	 */
	private void addFirst(E item) {
		setHead(new Node<E>(item, getHead()));
		size++;

	}

	/**
	 * removes after the node that is at the pointer
	 * @param node
	 * @return
	 */
	private E removeAfter(Node<E> node) {
		Node<E> temp = node.next;
		if (temp != null) {
			node.next = temp.next;
			size--;

			return temp.data;
		} else {
			return null;
		}
	}

	/**
	 * removes the head of the linked list
	 * @return
	 */
	private E removeFirst() {
		Node<E> temp = getHead();
		if (getHead() != null) {
			setHead(getHead().next);
		}
		if (temp != null) {
			size--;
			return temp.data;
		} else {
			return null;
		}

	}

	/**
	 * @param index
	 * @param newValue
	 * @return
	 */
	public E set(int index, E newValue) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = getNode(index);
		E result = node.data;
		node.data = newValue;
		return result;

	}

	/**
	 * @param index
	 * @return
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		Node<E> node = getNode(index);
		return node.data;
	}

	/**
	 * @param index
	 * @return
	 */
	public Node<E> getNode(int index) {
		Node<E> node = getHead();
		for (int i = 0; i < index && node != null; i++) {
			node = node.next;
		}
		return node;
	}
	
	/**
	 * @param e
	 * @return
	 */
	public Node<E> getNext(Node<E> e){
		return e.next;
		
	}
	/**
	 * @param e
	 */
	public void setPrev(Node<E> e){
		prev = e;
	}
 
	/**
	 * @return
	 */
	public Node<E> next(){
		if(iterator == null){
			return null;
		}
		setPrev(iterator);
		return iterator = iterator.next;
		
	}
	
	public String toString() {
		Node<String> nodeRef = (Node<String>) getHead();
		StringBuilder result = new StringBuilder();
		while (nodeRef != null) {
			result.append(nodeRef.data);
			if (nodeRef.next != null) {
				result.append((" ==> "));
			}
			nodeRef = nodeRef.next;
		}
		return result.toString();
	}

	public class Node<E> {

		public E getData() {
			return data;
		}

		private E data;
		private Node<E> next;

		private Node(E dataItem) {
			data = dataItem;
			next = null;
		}

		private Node(E dataItem, Node<E> nodeRef) {
			data = dataItem;
			next = nodeRef;
		}
		public void setNext(Node<E> e){
			this.next = e;
			
		}

	}


	public Node<E> getIterator() {
		return iterator;
	}

	public void setIterator(Node<E> iterator) {
		this.iterator = iterator;
		this.prev = null;
	}

	public Node<E> getHead() {
		return head;
	}

	public void setHead(Node<E> head) {
		this.head = head;
	}

	public Node<E> getPrev() {
		return prev;
	}
}
