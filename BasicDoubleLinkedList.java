import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BasicDoubleLinkedList <T> implements Iterable<T>{
	protected Node firstNode, lastNode;
	protected int nodeCount;
	
	/**
	 * Default constructor with nothing initialized
	 */
	public BasicDoubleLinkedList() {
		firstNode = null;
		lastNode = null;
		nodeCount = 0;
	}
	
	/**
	 * Create a list with a single data point
	 * @param data the data to add to the list
	 */
	public BasicDoubleLinkedList(T data) {
		firstNode = new Node(data);
		lastNode = firstNode;
		nodeCount = 1;
	}
	
	/**
	 * Create a list and populate with objects of type T. The objects will be added to the list
	 * in the order in which the appear in the argument list
	 * @param data the first data point in the argument list
	 * @param additionalData the rest of the argument objects
	 */
	@SafeVarargs //Should be safe because all data will be of type T (TODO: I may be misunderstanding what heap pollution is, though)
	public BasicDoubleLinkedList(T data, T... additionalData) {
		firstNode = new Node(data);
		nodeCount = 1;
		
		for (T datum : additionalData) {
			this.addToEnd(datum);
		}
	}
	
	/**
	 * Add a Node to the front of the list
	 * @param data the data to be added to the list
	 * @return the existing BasicDoubleLinkedList with the new data at the front of the list
	 */
	public BasicDoubleLinkedList<T> addToFront(T data) {
		
		//Create a new Node containing the argument data
		//and set the new Node as the firstNode
		Node newNode = new Node (data);
		newNode.nextNode = firstNode;
		firstNode = newNode;
		
		//Return the list with the Node added
		return this;
	}
	
	/**
	 * Add a Node to the end of the list
	 * @param data the data to be added to the list
	 * @return the exist
	 */
	public BasicDoubleLinkedList<T> addToEnd(T data) {
		
		//Create a new Node containing the argument data
		//and set the new Node as the lastNode
		Node newNode = new Node (data);
		newNode.previousNode = lastNode;
		lastNode = newNode;
		
		//Return the list with the Node added
		return this;
	}
	
	/**
	 * Get the data contained in the front of the list (non-destructive)
	 * @return the data in the first Node of the list
	 */
	public T getFirst() {
		return firstNode.data;
	}
	
	/**
	 * Get the data at the end of the list (non-destructive)
	 * @return the data in the last Node of the list
	 */
	public T getLast() {
		return lastNode.data;
	}
	
	/**
	 * Get the number of Nodes in the list
	 * @return the size of the list
	 */
	public int getSize() {
		return nodeCount;
	}
	
	/**
	 * Create an iterator for traversing the list
	 * @return an Iterator object at list position 0
	 */
	public ListIterator<T> iterator() throws UnsupportedOperationException, NoSuchElementException{
		//Create a new Iterator object using default constructor
		return new Iterator();
	}
	
	/**
	 * Remove a target data point from the list
	 * @param targetData the data to be searched for
	 * @param comparator the comparator to determine the equality of the data elements
	 * @return the element if found, or null if not found
	 */
	public BasicDoubleLinkedList<T> remove(T targetData, java.util.Comparator<T> comparator) {
		
		//Create iterator
		ListIterator<T> iterator = iterator();
		
		//Iterate through the list to find the given data
		boolean found = false;
		while(found == false && iterator.hasNext()) {
			if (comparator.compare(targetData, iterator.next()) == 0) {
				found = true;
			}
		}
		
		//If the data is found, have the iterator remove it
		if(found == true) {
			iterator.remove();
		}
		
		//Return the list with the data entry removed
		return this;
	}
	
	/**
	 * Remove the first Node from the list and obtain its data
	 * @return the data that had been first in the list, or null if the list is empty
	 */
	public T retrieveFirstElement() {
		
		//If the list is empty, return null
		if (getSize() == 0) return null;
		
		//Retrieve the data from the first Node and set the Node to null
		T data = firstNode.data;
		firstNode.data = null;
		
		//Remove the first Node by promoting the next Node in line
		firstNode = firstNode.nextNode;
		firstNode.previousNode = null;
		
		//Return the retrieved data
		return data;
	}
	
	/**
	 * Remove the last Node from the list and obtain its data
	 * @return the data that had been last in the list, or null if the list is empty
	 */
	public T retrieveLastElement() {
		 
		//If the list is empty, return null
		if (getSize() == 0) return null;
		
		//Retrieve the data from the last Node and set the Node to null
		T data = lastNode.data;
		lastNode.data = null;
		
		//Remove the last Node and set the second-to-last node to last
		lastNode = lastNode.previousNode;
		lastNode.nextNode = null;
		
		return data;
	}
	
	/**
	 * Convert the list to an ArrayList of the same type
	 * @return an ArrayList of all elements in the BasicDoubleLinkedList
	 */
	public ArrayList<T> toArrayList() {
		ArrayList<T> array = new ArrayList<>();
		
		//If the list is empty, return null
		if(getSize() == 0) return null;
		
		else {
		
		//Create an iterator to traverse the list
		ListIterator<T> iterator = iterator();
		
		//If the list is empty, return a null ArrayList
		
		//Traverse the list and add each Node's data to the ArrayList
		while(iterator.hasNext()) {
			array.add(iterator.next());
		}
		
		//Return the populated ArrayList
		return array;
		}
	}
	
	/**
	 * A class for the nodes of the list. Contains methods for instantiating uninitialized Nodes and inserted Nodes.
	 * Use the methods of the main list class for creating and accessing Nodes
	 * @author Mike Meyers
	 * @version 1.0
	 *
	 */
	class Node {
		private Node previousNode, nextNode;
		private T data;
		
		/**
		 * Default constructor with nothing initialized
		 */
		public Node() {
			previousNode = null;
			nextNode = null;
			data = null;
		}
		
		/**
		 * Constructor with only data. For creating the first Node in a list, with no other
		 * Nodes to link to
		 * @param data the data to be wrapped in the Node
		 */
		public Node(T data) {
			previousNode = null;
			nextNode = null;
			this.data = data;
		}

		/**
		 * Constructor for inserting a Node into a list, with links to both previous and next nodes
		 * @param data the data to be wrapped in the Node
		 * @param prevNode the Node previous to this one in the list
		 * @param nextNode the Node following this one in the list
		 */
		public Node (T data, Node prevNode, Node nextNode) {
			this.previousNode = prevNode;
			this.nextNode = nextNode;
			this.data = data;
		}
		
	}
	
	class Iterator implements ListIterator<T> {
		private Node thisNode, previousNode, nextNode;
		private int cursor;
		
		/**
		 * Create a default Iterator at position 0 in the list
		 */
		public Iterator() {
			thisNode = null;
			previousNode = null;
			nextNode = firstNode;
			cursor = 0;
		}
		
		/**
		 * Determine if the Iterator is able to move to the next Node in the list
		 * @return true if the Iterator has a next Node, and false otherwise
		 */
		@Override
		public boolean hasNext() {
			if (nextNode != null) return true;
			else return false;
		}

		/**
		 * Advance the Iterator to the next Node in the list
		 * @return the data from the next Node in the list
		 * @throws NoSuchElementException if there is no next Node in the list
		 */
		@Override
		public T next() throws NoSuchElementException{
			
			//If there is no next Node to advance to, throw exception
			if(!hasNext()) throw new NoSuchElementException("There are no more Nodes in the list");
			
			else {
				//Step all references one Node further down the list and advance the cursor
				previousNode = thisNode;
				nextNode = this.nextNode.nextNode;
				thisNode = this.nextNode;
				cursor++;
				
				//Return the data from the new thisNode
				return thisNode.data;
			}
			
		}

		/**
		 * Determine if there is a Node previous to the current one
		 */
		@Override
		public boolean hasPrevious() {
			if(previousNode != null) return true;
			else return false;
		}

		/**
		 * Step back to the previous Node in the list
		 * @return T the data in the previous Node in the list
		 * @throws NoSuchElementException if there is no previous node to step back to
		 */
		@Override
		public T previous() throws NoSuchElementException{
			if(!hasPrevious()) throw new NoSuchElementException("There is no previous node in the list");
			else {
				nextNode = thisNode;
				thisNode = previousNode;
				previousNode = previousNode.previousNode;
				cursor--;
				
				//Return the data from the new thisNode
				return thisNode.data;
			}
		}

		/**
		 * Get the index of the next item in the list
		 * @return the one-based index of the next item in the list
		 */
		@Override
		public int nextIndex() {
			return cursor + 1;
		}

		/**
		 * Get the index of the previous item in the list
		 * @return the one-based index of the next item in the list
		 * @throws NoSuchElementException if the iterator is already at the beginning of the list
		 */
		@Override
		public int previousIndex() throws NoSuchElementException{
			if(cursor == 0) throw new NoSuchElementException("Already at the beginning of the list");
			else return cursor - 1;
		}

		/**
		 * Remove the current Node in the list
		 * @throws NoSuchElementException if the list is empty
		 */
		@Override
		public void remove() throws NoSuchElementException{
			
			if(getSize() == 0) throw new NoSuchElementException("Cannot remove a Node from an empty list");
			
			//Set the previous Node's next reference to the next Node, and vice versa
			this.previousNode.nextNode = this.nextNode;
			this.nextNode.previousNode = this.previousNode;
			
			//Remove the data from the removed Node
			thisNode.data = null;
			
			//Step the iterator back to the previous Node
			this.previousNode = previousNode.previousNode;
			thisNode = previousNode;
			cursor--;
			
			
		}

		/**
		 * Set function not supported for BasicDoubleLinkedList's Iterator
		 * @param e not used
		 * @throws UnsupportedOperationException since operation is not implemented
		 */
		@Override
		public void set(T e) throws UnsupportedOperationException{
			throw new UnsupportedOperationException("Operation not implemented");
			
		}

		/**
		 * Add function not supported for BasicDoubleLinkedList's Iterator
		 * @param e not used
		 * @throws UnsupportedOperationException since operation is not implemented
		 */
		@Override
		public void add(T e) throws UnsupportedOperationException{
			throw new UnsupportedOperationException("Operation not implemented");
			
		}
		
	}
}
