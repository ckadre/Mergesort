/**
 * Node represents a node in a linked list.
 *
 * @author Java Foundations, mvail, cameron kadre
 * 
 * @version 4.0
 */

public class Node<E> {
	private Node<E> next;
	private Node<E> previous;
	private E element;

	/**
  	 * Creates an empty node.
  	 */
	public Node() {
		previous = null;
		next = null;
		element = null;
	}

	/**
  	 * Creates a node storing the specified element.
 	 *
  	 * @param elem the element to be stored within the new node
  	 */
	public Node(E elem) {
		next = null;
		previous = null;
		element = elem;
	}

	/**
 	 * Returns the node that follows this one.
  	 *
  	 * @return the node that follows the current one
  	 */
	public Node<E> getNext() {
		return next;
	}

	/**
	 * Returns the node that precedes this one. 
	 * 
	 * @return the node that precedes the current one
	 */
	public Node<E> getPrev()
	{
		return previous;
	}

	/**
 	 * Sets the node that follows this one.
 	 *
 	 * @param node
 	 *            the node to be set to follow the current one
 	 */
	public void setNext(Node<E> node) {
		next = node;
	}

	/**
	 * Sets the node that precedes this one.
	 * 
	 * @param node the node to be set to precede the current one
	 */
	public void setPrev(Node<E> node)
	{
		previous = node;
	}

	/**
 	 * Returns the element stored in this node.
 	 *
 	 * @return the element stored in this node
 	 */
	public E getElement() {
		return element;
	}

	/**
 	 * Sets the element stored in this node.
  	 *
  	 * @param elem
  	 *            the element to be stored in this node
  	 */
	public void setElement(E elem) {
		element = elem;
	}

	/**
	 * Returns a String representation of the node
	 * @return a String representation of the node
	 */
	public String toString() {
		return "Element: " + element.toString() + " Has next: " + (next != null);
	}
}

