
/**
 * Driver program for CS 0445 Assignment 1
 * 
 * @author Sherif Khattab (Adapted from Dr. John Ramirez's Spring 2017 CS 0445
 *         Assignment 1 code)
 *
 *         This program must work as is with your ArrayDS<T> class.
 *         Look carefully at all of the method calls so that
 *         you create your ArrayDS<T> methods correctly. For example,
 *         note the constructor calls and the toString() method call.
 *         The output should be identical to the sample output except for the
 *         predecessor method output.
 *         This can be verified by running the diff program (fc on Windows)
 *         between
 *         the provided A1Out.txt and the output of running this driver using
 *         your
 *         ArrayDS<T> implementation. The diff program shouldn't
 *         give any differences.
 */
public class Assig1Test {
	private static final int SIZE = 5;
	private static final int LARGE_SIZE = 1000000;

	public static void main(String[] args) {
		// Testing constructors and SequenceInterface<T> interface

		ArrayDS<Integer> s1 = new ArrayDS<>();

		// Testing append and toString()
		for (int i = 0; i < SIZE; i++) {
			s1.append(2 * i);
			System.out.println(2 * i + " appended to sequence");
		}

		System.out.println(s1);

		// Testing clear() and size()
		int sz = s1.size();
		System.out.println("Before clear(): " + sz + " item(s) in the sequence.");

		s1.clear();
		sz = s1.size();
		System.out.println("After clear(): " + sz + " item(s) in the sequence.");

		// Testing prefix and toString
		for (int i = 0; i < SIZE; i++) {
			s1.prefix(2 * i);
			System.out.println(2 * i + " prefixed to sequence");
		}
		System.out.println(s1);

		// Testing Copy Constructor
		ArrayDS<Integer> s2 = new ArrayDS<>(s1);
		System.out.println(s2);

		SequenceInterface<Integer> theS = new ArrayDS<>();

		for (int i = 0; i < SIZE; i++) {
			theS.append(2 * i);
			System.out.println(2 * i + " appended to sequence");
		}
		System.out.println(theS);

		// Testing deleteHead() and isEmpty()
		while (!theS.isEmpty()) {
			Integer oldItem = theS.deleteHead();
			System.out.println(oldItem + " retrieved from head of sequence");
		}
		try {
			Integer noItem = theS.deleteHead();
		} catch (EmptySequenceException e) {
			System.out.println("Nothing in the sequence");
		}

		// Testing deleteTail()
		while (!(s1.isEmpty())) {
			Integer oldItem = s1.deleteTail();
			System.out.println(oldItem + " retrieved from tail of sequence");
		}
		try {
			Integer noItem = s1.deleteTail();
		} catch (EmptySequenceException e) {
			System.out.println("Nothing in the sequence");
		}

		// Alternating prefix, deleteHead, and deleteTail
		SequenceInterface<String> theS2 = new ArrayDS<>();
		for (int i = 0; i < 9; i++) {
			String theItem = new String("Item " + i);
			System.out.println("Prefixing " + theItem);
			theS2.prefix(theItem);
			theItem = theS2.deleteHead();
			System.out.println(theItem + " removed from Sequence head");
			try {
				theS2.deleteTail();
			} catch (EmptySequenceException e) {
				System.out.println("Nothing in the sequence");
			}
		}
		sz = theS2.size();
		System.out.println(sz + " item(s) in the sequence.");

		// Testing first, last, itemAt, lastOccurrenceOf, getFrequencyOf, and
		// predecessor()
		SequenceInterface<Character> sentence = new ArrayDS<>();
		String s = "algorithms and data structures are fun";
		for (Character c : s.toCharArray()) {
			sentence.append(c);
		}
		System.out.println(sentence);
		System.out.println("The first letter is " + sentence.first());
		System.out.println("The last letter is " + sentence.last());

		for (int i = 0; i < sentence.size(); i++) {
			System.out.println("Character at index " + i + " is " + sentence.itemAt(i));
			System.out.println("The letter " + sentence.itemAt(i) + " appeared "
					+ sentence.getFrequencyOf(sentence.itemAt(i)) + " times");
			System.out.println("Last occurrence of " + sentence.itemAt(i) + " is at position "
					+ sentence.lastOccurrenceOf(sentence.itemAt(i)));
			System.out.println(
					"A letter preceding an " + sentence.itemAt(i) + " is " + sentence.predecessor(sentence.itemAt(i)));
		}
		try {
			System.out.println("Character at index " + sentence.size() + " is " + sentence.itemAt(sentence.size()));
		} catch (IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException");
		}
		System.out.println("The letter " + 'z' + " appeared " + sentence.getFrequencyOf('z') + " times");
		System.out.println("A letter preceding a " + 'p' + " is " + sentence.predecessor('p'));

		// Testing cut, trim, and insert
		sentence.trim(8);
		System.out.println(sentence);
		for (Character c : " one is fun".toCharArray()) {
			sentence.append(c);
		}
		System.out.println(sentence);
		int pos = sentence.lastOccurrenceOf('o');
		sentence.cut(pos, 4);
		System.out.println(sentence);
		int offset = 0;
		for (Character c : "two ".toCharArray()) {
			sentence.insert(c, pos + offset);
			offset++;
		}
		System.out.println(sentence);
		pos = sentence.lastOccurrenceOf('f');
		offset = 0;
		for (Character c : "also ".toCharArray()) {
			sentence.insert(c, pos + offset);
			offset++;
		}
		System.out.println(sentence);

		System.out.println("Trimming by 100 letters should return " + sentence.trim(100));
		System.out.println(sentence);

		// Testing compareTo
		ArrayDS<Character> seq1 = new ArrayDS<>();
		for (Character c : "algorithms and data structures one".toCharArray()) {
			seq1.append(c);
		}
		ArrayDS<Character> seq2 = new ArrayDS<>();
		for (Character c : "algorithms and data structures two".toCharArray()) {
			seq2.append(c);
		}
		ArrayDS<Character> seq3 = new ArrayDS<>();
		for (Character c : "algorithms and data structures".toCharArray()) {
			seq3.append(c);
		}

		compare(seq1, seq2);
		compare(seq1, seq1);
		compare(seq1, seq3);
		compare(seq3, seq2);


		// Testing Reorder interface methods
		ArrayDS<Integer> newDS = new ArrayDS<>();
		for (int i = 0; i < 8; i++) {
			System.out.println("Prefixing " + i);
			newDS.prefix(i);
		}
		System.out.println(newDS);
		System.out.println("Reversing");
		newDS.reverse();
		System.out.println(newDS);
		System.out.println("Removing 3 items from tail then appending 8");
		newDS.deleteTail();
		newDS.deleteTail();
		newDS.deleteTail();
		newDS.append(8);
		System.out.println(newDS);
		System.out.println("Reversing");
		newDS.reverse();
		System.out.println(newDS);
		System.out.println("Rotating right");
		newDS.rotateRight();
		System.out.println(newDS);
		System.out.println("Rotating left twice");
		newDS.rotateLeft();
		newDS.rotateLeft();
		System.out.println(newDS);

		// Testing with a large sequence
		SequenceInterface<Integer> largeNumber = new ArrayDS<>();
		for (int i = 0; i < LARGE_SIZE; i++) {
			largeNumber.append(9);
		}
		// System.out.println(largeNumber);
		System.out.println("The size of the sequence is " + largeNumber.size());
	}

	private static void compare(ArrayDS<Character> first, ArrayDS<Character> second){
		int result = first.compareTo(second);
		if(result == 0){
			System.out.println(first + " == " + second);
		} else if(result < 0){
			System.out.println(first + " < " + second);
		} else {
			System.out.println(first + " > " + second);
		}
	}
}
