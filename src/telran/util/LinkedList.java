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
		// complexity O[1]
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
		// Complexity O[N]
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
		// O[N]
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
//O[1]
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
		// O[n]
		T elementToDelete = null;
		if (!isValidIndex(index)) {
			return elementToDelete;
		}

		if (index == 0) {
			elementToDelete = removeHead();
			;

		} else if (index == size - 1) {
			elementToDelete = removeTail();

		} else {
			elementToDelete = removeMeadle(index);
		}
		size--;
		return elementToDelete;
	}

	private T removeMeadle(int index) {
		Node<T> nodeToDelete = getNode(index);
		nodeToDelete.next.prev = nodeToDelete.prev;
		nodeToDelete.prev.next = nodeToDelete.next;
		return nodeToDelete.obj;
	}

	private T removeTail() {
		T elementForDel = tail.obj;
		tail = tail.prev;

		if (tail != null) {
			tail.next = null;
		}
		return elementForDel;
	}

	private T removeHead() {
		T elementForDel = head.obj;
		head = head.next;
		if (head != null) {
			head.prev = null;
		}
		return elementForDel;
	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		// O[N]
		int res = -1;
		Node<T> current = head;
		for (int i = 0; i < size; i++) {
			if (predicate.test(current.obj)) {
				res = i;
				break;
			} else {
				current = current.next;
			}

		}
		return res;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		// O[N]
		int res = -1;
		Node<T> current = tail;
		for (int i = size - 1; i >= 0; i--) {
			if (predicate.test(current.obj)) {
				res = i;
				break;
			} else {
				current = current.prev;
			}

		}

		return res;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int sizeBeforeRemove = size;
		Node<T> current = tail;
		for (int i = sizeBeforeRemove - 1; i >= 0; i--) {
			if (predicate.test(current.obj)) {
				remove(i);
			}
			current = current.prev;

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

		T[] array = (T[]) new Object[size];
		Node<T> current = head;
		for (int i = 0; i < array.length; i++) {
			array[i] = current.obj;
			current = current.next;
		}
		return array;
	}

	private void fillListFromArray(T[] array) {

		Node<T> current = head;
		for (int i = 0; i < array.length; i++) {
			current.obj = array[i];
			current = current.next;
		}
	}

	@Override
	public int sortedSearch(T pattern, Comparator<T> comp) {
		// TODO DONE
		Node<T> currentNode = head;
		int resIndex = 0;
		if (comp.compare(pattern, tail.obj) > 0) {
			return -(size + 1);
		}
		for (int i = 0; i < size; i++) {
			int resComp = comp.compare(pattern, currentNode.obj);
			if (resComp == 0) {
				return  resIndex;
			}
			if (resComp > 0) {
				resIndex++;
				currentNode = currentNode.next;
			} else {
				return -(resIndex + 1);
			}
		}
		return resIndex;
	}

	@Override
	public void clear() {
		// DONE
		head = tail = null;
		size = 0;
	}

}
