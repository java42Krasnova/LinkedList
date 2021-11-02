package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {
	private int size;

	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> head; // reference to the first element
	private Node<T> tail; // reference to the last element

	@Override
	public void add(T element) {
		Node<T> newNode = new Node<>(element);
		if (head == null) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;

			tail = newNode;
		}
		size++;

	}

	private Node<T> getNode(int index) {
		Node<T> res = null;
		if (isValidIndex(index)) {
			res = index <= size / 2 ? getNodefromLeft(index) : getNodeFromRight(index);
		}
		return res;
	}

	private Node<T> getNodeFromRight(int index) {
		Node<T> current = tail;
		int ind = size - 1;
		while (ind != index) {
			ind--;
			current = current.prev;
		}
		return current;
	}

	private Node<T> getNodefromLeft(int index) {
		Node<T> current = head;
		int ind = 0;
		while (ind != index) {
			ind++;
			current = current.next;
		}
		return current;
	}

	private boolean isValidIndex(int index) {

		return index >= 0 && index < size;
	}

	@Override
	public boolean add(int index, T element) {
		boolean res = false;
		if (index == size) {
			add(element);
			res = true;
		} else if (isValidIndex(index)) {
			res = true;
			Node<T> newNode = new Node<>(element);
			if (index == 0) {
				addHead(newNode);
			} else {
				addMiddle(newNode, index);
			}
			size++;
		}
		return res;
	}

	private void addMiddle(Node<T> newNode, int index) {
		Node<T> nodeAfter = getNode(index);
		newNode.next = nodeAfter;
		newNode.prev = nodeAfter.prev;
		// nodeAfter.prev => reference to the old previous element
		nodeAfter.prev.next = newNode;
		nodeAfter.prev = newNode;

	}

	private void addHead(Node<T> newNode) {
		newNode.next = head;
		head.prev = newNode;
		head = newNode;

	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public T get(int index) {
		T res = null;
		Node<T> resNode = getNode(index);
		if (resNode != null) {
			res = resNode.obj;
		}
		return res;
	}

	@Override
	public T remove(int index) {
		//TODO Done
		if (!isValidIndex(index)) {
			return null;
		}
		//[YG] - getting reference to T object causes additional passing over the list
		T elementToDelete = get(index);
		if (index == 0) {
			removeHead();
		} else if (index == size - 1) {
			removeTail();
		} else {
			//[YG] the previous comment should direct you for updating removeMeadle call
			removeMeadle(index);
		}
		return elementToDelete;
	}

	private void removeMeadle(int index) {
		Node<T> nodeToDelete = getNode(index);
		nodeToDelete.next.prev = nodeToDelete.prev;
		nodeToDelete.prev.next = nodeToDelete.next;
		size--;

	}

	private void removeTail() {
		tail = tail.prev;

		if (tail != null) {
			tail.next = null;
		}
		size--;

	}

	private void removeHead() {
		head = head.next;
		if (head != null) {
			head.prev = null;
		}
		size--;

	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		//TODO Done
		int res = -1;
		for (int i = 0; i < size; i++) {
			//[YG] very ineffective implementation as each get(i) requires addittional passing over list
			if (predicate.test(get(i))) {
				res = i;
				break;
			}
		}
		return res;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		//TODO done
		int res = -1;
		for (int i = size - 1; i >= 0; i--) {
			//[YG] very ineffective implementation as each get(i) requires addittional passing over list
			if (predicate.test(get(i))) {
				res = i;
				break;
			}
		}
		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		//TODO done
		int sizeBeforeRemove = size;
		for (int i = sizeBeforeRemove - 1; i >= 0; i--) {
			//[YG] very ineffective implementation as each get(i) requires addittional passing over list
			if (predicate.test(get(i))) {
				remove(i);
			}
		}

		return sizeBeforeRemove > size;
	}

	@Override
	public void sort(Comparator<T> comp) {
		T[] array = listToArray();
		Arrays.sort(array, comp);
		fillListFromArray(array);

	}

	private T[] listToArray() {
		// TODO
		//Done
		T[] array = (T[]) new Object[size];
		for (int i = 0; i < array.length; i++) {
			//[YG] very ineffective implementation as each get(i) requires addittional passing over list
			array[i] = get(i);
		}
		return array;
	}

	private void fillListFromArray(T[] array) {
		// TODO done
		// passes over whole list and fills elements from index=0 to index=size - 1
		//[YG] you don't need to create new List, but only fills already existing nodes
		clearList();
		for (int i = 0; i < array.length; i++) {
			
			add(array[i]);
		}
	}
	
	private void clearList() {
		head = null;
	    tail = null;
		size = 0;
	}

}
