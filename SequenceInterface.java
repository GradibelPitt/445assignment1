/**  SequenceInterface<T> interface for CS 0445 Assignment 1
 * @author Sherif Khattab
 * @param <T> The data type of the items stored in the sequence.
 *
 * Carefully read the specifications for each of the operations and
 * Please make sure to implement them correctly in your ArrayDS class.
 *
 * A Sequence stores an unlimited number of items in a certain logical order. The head of the Sequence is 
 * its first item and the Sequence's tail is its last item. Items can only be added 
 * to the beginning (using the prefix operation) or to the end (using the tail operation) of 
 * the Sequence. The items in a Sequence are not necessarily unique. Don't worry as much about efficiency here as you do
 * about correctness. Items in the Sequence are comparable to each other.
 */

public interface SequenceInterface<T extends Comparable<? super T>> {
	/** Add a new item to the tail (logical end) of the SequenceInterface<T>
	 * @param item the item to be added.
	 */
	public void append(T item);

	/** Add a new item to the head (logical beginning) of the SequenceInterface<T>
	 * @param item the item to be added.
	 */
	public void prefix(T item);

	/** Add a new item at a given position in the SequenceInterface<T>. Inserting at position size() is 
	 * the same as appending. All existing items in the SequenceInterface<T> should maintain their relative order.
	 * @param item the T item to be added.
	 * @param position the int position of the added item
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()
	 */
	public void insert(T item, int position);

	/** Return the item at a given logical position in the SequenceInterface<T>
	 * @param position the int logical position
	 * @return the T item at position
	 * @throws IndexOutOfBoundsException if position < 0
	                                     or position > size()-1
	 */
	public T itemAt(int position);

	/**
	 * @return true if the SequenceInterface<T> is empty, and false otherwise
	 */
	public boolean isEmpty();

	/**
	 * @return the number of items currently in the SequenceInterface<T>
	 */
	public int size();

	/**
	 * @return the logical first item in the SequenceInterface<T> or null if the SequenceInterface<T> is empty
	 */
	public T first();

	/**
	 * @return the logical last item in the SequenceInterface<T> or null if the SequenceInterface<T> is empty,
	 */
	public T last();

	/** Find any predecessor (i.e., item right before) of a given item in the SequenceInterface<T>
	 * @param item the T item
	 * @return a T item that is right before item in the SequenceInterface<T>. If item occurs multiple times in the 
	 *         SequenceInterface<T>, any non-null predecessor is returned. If item doesn't exist or occurs once at the head
	 *         of the SequenceInterface<T>, null is returned.
	 */
	public T predecessor(T item);

	/** Return the number of occurrences in the SequenceInterface<T> of a given item
	 * @param item the T item
	 * @return the number of occurrences in the SequenceInterface<T> of item
	 */
	public int getFrequencyOf(T item);

	/** Reset the SequenceInterface<T> to an empty Sequence.
	 */
	public void clear();

	/** Return the logical position in the SequenceInterface<T> of the last occurrence of a given item
	 * @param item an item
	 * @return the int logical position in the SequenceInterface<T> of the last occurrence of item
	 *         or -1 if item doesn't exist in the SequenceInterface<T>
	 */
	public int lastOccurrenceOf(T item);


	/** Delete the first item of the SequenceInterface<T>
	* @return the deleted item
	* @throws EmptySequenceException if the SequenceInterface<T> is empty
	*/
	public T deleteHead();

	/** Delete the last item of the SequenceInterface<T>
	 * @return the deleted item
	 * @throws EmptySequenceException if the Sequence is empty
	 */
	public T deleteTail();

	/** Delete a given number of items from the end of the SequenceInterface<T> 
	 * @param numItems the int number of items to delete
	 * @return true if the operation is successful and false if the SequenceInterface<T> has less than
	 *         numItems. If the SequenceInterface<T> is not long enough, no change will happen.
	 */
	public boolean trim(int numItems);

	/** Delete a given number items from a given position in the SequenceInterface<T>
	 * @param start the int starting position of the cut
	 * @param numItems the int number of items to cut
	 * @return true if the operation is successful and false if start+numItems > the size of the SequenceInterface<T>.
	 *  	   If the SequenceInterface<T> is not long enough, no change will happen.
	 */
	public boolean cut(int start, int numItems);

	/** (EXTRA CREDIT) Return a slice of the SequenceInterface<T>, containing all items that are less than or equal a given item
	 * 
	 * @param item the T item
	 * @return a SequenceInterface<T> containing all items that are less than or equal to item, maintaining their relative order. 
	 *         No change should happen to the SequenceInterface<T> on which the method is called.
	 */
	public SequenceInterface<T> slice(T item);

	/** (EXTRA CREDIT) Return a slice of the SequenceInterface<T>, containing a given number of items starting from a given position in the SequenceInterface<T>
	 * @param start the int start position
	 * @param numItems the int number of items
	 * @return a SequenceInterface<T> containing numItems items of the SequenceInterface<T>, starting from start and with their relative order. If the SequenceInterface<T>
	 *         is not long enough, no change should happen and the method should return null.
	 */
	public SequenceInterface<T> slice(int start, int numItems);

}
