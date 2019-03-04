import java.util.Comparator;

public class SortedDoubleLinkedList<T extends Comparable<T>> extends BasicDoubleLinkedList<T> {
	
	Comparator<T> comparator;
	
	/**
	 * Create an empty SortedDoubleLinkedList
	 * @param comparator the Comparator that will determine list sorting
	 */
	public SortedDoubleLinkedList(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}
	
	/**
	 * Create a SortedDoubleLinkedList with a single data point
	 * @param data the object to be added
	 * @param comparator the Comparator that will determine list sorting
	 */
	public SortedDoubleLinkedList(T data, Comparator<T> comparator) {
		super(data);
		this.comparator = comparator;
	}
	
	/**
	 * Create a list and populate with objects of type T. The objects will be added to the list
	 * in the order in which the appear in the argument list
	 * @param comparator the Comparator that will determine list sorting
	 * @param data the first data point in the argument list
	 * @param additionalData the rest of the argument objects
	 */
	@SafeVarargs //Should be safe because all data will be of type T (TODO: I may be misunderstanding what heap pollution is, though)
	public SortedDoubleLinkedList(Comparator<T> comparator, T data, T... additionalData) {
		firstNode = new Node(data);
		nodeCount = 1;
		this.comparator = comparator;
		
		for (T datum : additionalData) {
			this.add(datum);
		}
	}
	
	/**
	 * Add an object to the list
	 * @param data the object to be added
	 * @return a SortedDoubleLinkedList object with the added data
	 */
	public SortedDoubleLinkedList<T> add(T data) {
		Iterator listIterator = iterator();
		
		//If the list is empty, skip right to adding the data
		if(nodeCount == 0) {
			super.addToFront(data);
			return this;
		}
		
		else {
		//If the list has data, search through the data and find where to insert the data
		while(listIterator.hasNext()) {
			if (comparator.compare(data, listIterator.getNext()) < 0) break;
			listIterator.next();
		}
		
		Node newNode = new Node(data);
		
		listIterator.insert(newNode);
		
		return this;
		}
	}
	
	/**
	 * Unsupported method. The add function handles insertion of data where it is needed
	 * @param data the object to be added
	 * @throws UnsupportedOperationException because this method is not supported
	 */
	@Override
	public SortedDoubleLinkedList<T> addToEnd(T data) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Operation not supported");
	}
	
	/**
	 * Unsupported method. The add function handles insertion of data where it is needed
	 * @param data the object to be added
	 * @throws UnsupportedOperationException because this method is not supported
	 */
	@Override
	public SortedDoubleLinkedList<T> addToFront(T data) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Operation not supported");
	}
	
	/**
	 * Create a new iterator for traversing the array
	 * @return an iterator object that can traverse the array
	 */
	@Override
	public Iterator iterator() {
		return new Iterator();
	}
	
	/**
	 * Remove a specific object from the list
	 * @param data the object to be removed
	 * @param comparator the Comparator matching the argument data to the list data
	 * @return the sorted list minus the removed object (will return an empty list if the list is reduced to zero objects)
	 */
	public SortedDoubleLinkedList<T> remove (T data, Comparator<T> comparator) {
		return (SortedDoubleLinkedList<T>) super.remove(data, comparator);
	}
	
	/**
	 * An internal Iterator class for traversing the Sorted List
	 * @author Mike Meyers
	 * @version 1.0
	 *
	 */
	class Iterator extends BasicDoubleLinkedList<T>.Iterator {
		
		/**
		 * Reset the iterator to start at the beginning of the list
		 */
		public void startFromTop() {
			this.thisNode = firstNode;
			this.nextNode = thisNode.nextNode;
			this.previousNode = null;
			this.cursor = 0;
		}
		
		/**
		 * Insert a Node into the list at the iterator's current location
		 * @param nodeToAdd the Node to be entered into the list
		 */
		public void insert(Node nodeToAdd) {
			
			//If the iterator is at the start of the list already, just insert the data at the front of the list
			if(cursor == 0) {
				SortedDoubleLinkedList.super.addToFront(nodeToAdd.data);
				return;
			}
			
			//If the iterator is at the end of the list, add the data to the end of the list
			else if(cursor == nodeCount) {
				SortedDoubleLinkedList.super.addToEnd(nodeToAdd.data);
				return;
			}
			
			//Otherwise, we are inserting between two Nodes, and need to update references accordingly
			else {
			nodeToAdd.previousNode = thisNode;
			thisNode.nextNode = nodeToAdd;
			nodeToAdd.nextNode = nextNode;
			nextNode.previousNode = nodeToAdd; //TODO: did this fix it?
			nodeCount++; //Increment size counter. The two super-method calls above contain their own nodeCount++
			}
			
			//Lastly - if inserting at the second-to-last position, lastNode's previousNode reference will need updated
			if(cursor == nodeCount - 2) {
				lastNode.previousNode = nodeToAdd;
			}
		}
		
		/**
		 * Check the next Node's data without advancing the cursor
		 * @return the next Node's data
		 */
		public T getNext() {
			return nextNode.data;
		}
		
	}
	
}
