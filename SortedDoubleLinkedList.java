import java.util.Comparator;

//TODO: Javadocs!
public class SortedDoubleLinkedList<T extends Comparable<T>> extends BasicDoubleLinkedList<T> {
	Comparator<T> comparator;
	
	//Default constructor initializing the comparator
	public SortedDoubleLinkedList(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}
	
	//Constructor for creating a SortedDoubleLinkedList with a single data point
	//No sorting is needed since there is only one data point
	public SortedDoubleLinkedList(T data, Comparator<T> comparator) {
		super(data);
		this.comparator = comparator;
	}
	
	
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
	
	@Override
	public SortedDoubleLinkedList<T> addToEnd(T data) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Operation not supported");
	}
	
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
	
	public SortedDoubleLinkedList<T> remove (T data, Comparator<T> comparator) {
		return (SortedDoubleLinkedList<T>) super.remove(data, comparator);
	}
	
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
			nodeCount++; //Increment size counter. The two super-method calls above contain their own nodeCount++
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
