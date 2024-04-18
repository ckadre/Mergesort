import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Doubly linked node implementation of IndexedUnsortedList.
 * An Iterator and ListIterator are implemented.
 * @author cameron kadre
 * @version Spring 2024
 */

public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T>
{
    private Node<T> head, tail;
    private int size;
    private int modCount;

    public IUDoubleLinkedList()
    {
        head = null;
        tail = null;
        size = 0;
        modCount = 0;
    }

    /**
     * Adds the specified element to the front of this list.
     *
     * @param element the element to be added to the front of this list
     */
    public void addToFront(T element)
    {
        Node<T> newNode = new Node<T>(element);
        if(size == 0)
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Adds the specified element to the rear of this list.
     *
     * @param element the element to be added to the rear of this list
     */
    public void addToRear(T element)
    {
        Node<T> newNode = new Node<T>(element);
        if (size == 0)
        {
            tail = newNode;
            head = newNode;
        }
        else
        {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
        modCount++;
    }

    /**
     * Adds the specified element to the rear of this list.
     *
     * @param element  the element to be added to the rear of the list
     */
    public void add(T element)
    {
        Node<T> newNode = new Node<T>(element);
        if (size == 0)
        {
            tail = newNode;
            head = newNode;
        } else
        {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
        modCount++;
    }

	/**
     * Adds the specified element after the first element of the list matching the specified target.
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element will be added after
     * @throws NoSuchElementException if target element is not in this list
     */
    public void addAfter(T element, T target)
    {
        Node<T> newNode = new Node<T>(element);
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty.");
        }
        Node<T> currNode = head;
        boolean foundTarget = false;
        while ((currNode != null) && (foundTarget == false))
        {
            if (currNode.getElement().equals(target))
            {
                newNode.setNext(currNode.getNext());
                newNode.setPrev(currNode);
                currNode.setNext(newNode);
                currNode.getNext().setPrev(newNode);
                foundTarget = true;
            }
            currNode = currNode.getNext();
        }
        if (foundTarget == false)
        {
            throw new NoSuchElementException("Target element is not in this list");
        }
        size++;
        modCount++;
    }

    /**
     * Inserts the specified element at the specified index.
     *
     * @param index   the index into the array to which the element is to be inserted.
     * @param element the element to be inserted into the array
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    public void add(int index, T element)
    {
        Node<T> newNode = new Node<T>(element);
        Node<T> currNode = head;
        if ((index < 0)  || (index > size))
        {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        else if ((index == 0) && (size == 0))
        {
            head = newNode;
            tail = newNode;
        }
        else if (index == 0)
        {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        else
        {
            for (int i = 0; i < index - 1; i++)
            {
                currNode = currNode.getNext();
            }
            if (currNode.equals(tail))
            {
                newNode.setPrev(tail);
                tail.setNext(newNode);
                tail = newNode;
            } else
            {
                newNode.setNext(currNode.getNext());
                newNode.getNext().setPrev(newNode);
                currNode.setNext(newNode);
                newNode.setPrev(currNode);
            }
            
        }
        size++;
        modCount++;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws NoSuchElementException if list contains no elements
     */
    public T removeFirst() {
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty");
        }
        Node<T> currNode = new Node<T>();
        currNode = head;
        T returnElement = head.getElement();
        if (size == 1)
        {
            head = null;
            tail = null;
        } else if (size == 2)
        {
            currNode = head.getNext();
            head = currNode;
            head.setNext(null);
            tail = currNode;
            tail.setPrev(null);
        }
        else 
        {
            currNode = head.getNext();
            currNode.setPrev(null);
            head = currNode;
        }
        size--;
        modCount++;
        return returnElement;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if list contains no elements
     */
    public T removeLast()
    {
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty");
        }
        T returnElement = tail.getElement();
        if (size == 1)
        {
            head = null;
            tail = null;
        }
        else if (size == 2)
        {
            head.setNext(null);
            tail.setPrev(null);
            tail = head;
        }
        else 
        {
            Node<T> tempNode = tail.getPrev();
            tempNode.setNext(null);
            tail = tempNode;
        }
        size--;
        modCount++;
        return returnElement;
    }

    /**
     * Removes and returns the first element from the list matching the specified element.
     *
     * @param element the element to be removed from the list
     * @return removed element
     * @throws NoSuchElementException if element is not in this list
     */
    public T remove(T element)
    {
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty");
        }
        T target = element;
        boolean found = false;
        Node<T> currNode = head;
        while ((currNode != null) && (found == false))
        {
            if(currNode.getElement().equals(target))
            {
                found = true;
            }
            else
            {
                currNode = currNode.getNext();
            }
        }
        if (!found)
        {
            throw new NoSuchElementException("Target element is not in the list");
        }
        if (size == 1)
        {
            head = null;
        }
        else if (currNode.equals(head))
        {
            head = head.getNext();
            head.setPrev(null);
        }
        else if (currNode.equals(tail))
        {
            tail = tail.getPrev();
            tail.setNext(null);
        }
        else
        {
            currNode.getPrev().setNext(currNode.getNext());
            currNode.getNext().setPrev(currNode.getPrev());
        }
        size--;
        modCount++;
        return currNode.getElement();
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the index of the element to be retrieved
     * @return the element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T remove(int index)
    {
        if (((size == 0) && (index != 0)) || (index < 0) || (index >= size))
        {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        Node<T> currNode = head;
        T returnElement;
        if (index == 0)
        {
            if (currNode.getNext() == null)
            {
                returnElement = currNode.getElement();
                currNode = null;
                head = null;
                tail = null;
                size--;
                modCount++;
                return returnElement;
            }
            else
            {
                returnElement = head.getElement();
                currNode = currNode.getNext();
                currNode.setPrev(null);
                head = currNode;
                size--;
                modCount++;
                return returnElement;
            }

        }
        else 
        {
            for (int i = 0; i < index; i++)
            {
                currNode = currNode.getNext();
            }
            if (currNode.equals(tail))
            {
                returnElement = currNode.getElement();
                tail = currNode.getPrev();
                tail.setNext(null);
            }
            else
            {
                returnElement = currNode.getElement();
                currNode.getPrev().setNext(currNode.getNext());
                currNode.getNext().setPrev(currNode.getPrev());
            }
        }
        size--;
        modCount++;
        return returnElement;
    }

    /**  
     * Replace the element at the specified index with the given element. 
     *
     * @param index   the index of the element to replace
     * @param element the replacement element to be set into the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public void set(int index, T element)
    {
        if ((index < 0) || (index >= size))
        {
            throw new IndexOutOfBoundsException("Index is out of range");
        }
        Node<T> newNode = new Node<T>(element);
        Node<T> currNode = head;
        if (index == 0)
        {
            newNode.setNext(head.getNext());
            head = newNode;
        }
        else 
        {
            for (int i = 0; i < index; i++)
            {
                currNode = currNode.getNext();
            }
            if (currNode.getNext() == null)
            {
                newNode.setNext(null);
                currNode.getPrev().setNext(newNode);
                newNode.setPrev(currNode.getPrev());
                tail = newNode;
            }
            newNode.setNext(currNode.getNext());
            currNode.getPrev().setNext(newNode);
            newNode.setPrev(currNode.getPrev());
        }
        modCount++;
    }

    /**  
     * Returns a reference to the element at the specified index. 
     *
     * @param index  the index to which the reference is to be retrieved from
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T get(int index)
    {
        if ((index < 0) || (index >= size))
        {
            throw new IndexOutOfBoundsException("Index is our of range");
        }
        if (size == 0)
        {
            throw new IndexOutOfBoundsException("List is empty");
        }
        Node<T> currNode = head;
        for (int i = 0; i < index; i++)
        {
            currNode = currNode.getNext();
        }
        return currNode.getElement();
    }

    /**  
     * Returns the index of the first element from the list matching the specified element.
     *
     * @param element  the element for the index is to be retrieved
     * @return the integer index for this element or -1 if element is not in the list
     */
    public int indexOf(T element)
    {
        Node<T> currNode = head;
        int index = -1;
        for (int i = 0; i < size; i++)
        {
            if (currNode.getElement().equals(element))
            {
                index = i;
            }
            currNode = currNode.getNext();
        }
        return index;
    }

    /**  
     * Returns a reference to the first element in this list. 
     *
     * @return a reference to the first element in this list
     * @throws NoSuchElementException if list contains no elements
     */
    public T first()
    {
        if (head == null)
        {
            throw new NoSuchElementException("List is empty");
        }
        return head.getElement();
    }

    /**  
     * Returns a reference to the last element in this list. 
     *
     * @return a reference to the last element in this list
     * @throws NoSuchElementException if list contains no elements
     */
    public T last()
    {
        if (size == 0)
        {
            throw new NoSuchElementException("List is empty");
        }
        return tail.getElement();
    }

    /**  
     * Returns true if this list contains the specified target element. 
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element, else false
     */
    public boolean contains(T target)
    {
        boolean contains = false;
        Node<T> currNode = head;
        for (int i = 0; i < size; i++)
        {
            if (currNode.getElement().equals(target))
            {
                contains = true;
            }
            currNode = currNode.getNext();
        }
        return contains;
    }

    /**  
     * Returns true if this list contains no elements. 
     *
     * @return true if this list contains no elements
     */
    public boolean isEmpty()
    {
        boolean isEmpty;
        if (size == 0)
        {
        isEmpty = true;
        } else
        {
        isEmpty = false;
        }
        return isEmpty;
    }

    /**  
     * Returns the number of elements in this list. 
     *
     * @return the integer representation of number of elements in this list
     */
    public int size()
    {
        int returnSize = size;
        return returnSize;
    }
    
    /**
     * Returns a String representation of the list in the format [ELEMENT1, ELEMENT2, ... ELEMENTN]
     * @return a String representation of the list
     */
    public String toString()
    {
        String returnString = "[";
        if (size == 0)
        {
            returnString += "]";
            return returnString;
        }
        Node<T> currNode = new Node<T>();
        currNode = head;
        if (size == 1)
        {
            returnString += currNode.getElement().toString() + "]";
        } else
        {
            returnString += currNode.getElement().toString() + ", ";
        }
        for (int i = 1; i < size; i++)
        {
            currNode = currNode.getNext();
            if (i == (size - 1))
            {
                returnString += currNode.getElement().toString() + "]";
            } else
            {
                returnString += currNode.getElement().toString() + ", ";
            }
        }
        return returnString;
    }

    /*
     * Creates a new Iterator for the list
     * @return a new Iterator
     */
    public Iterator<T> iterator()
    {
        return new SLLIterator();
    }

    /*
     * Creates a new ListIterator for the list, beginning at the start of the list
     * @return a new ListIterator
     */
    public ListIterator<T> listIterator() {
        return new SLLListIterator();
    }

    /*
     * Creates a new ListIterator for the list, beginning at the given starting index
     * @param integer to start the iterator at
     * @return a new ListIterator pointing at a given index
     */
    public ListIterator<T> listIterator(int startingIndex) {
        return new SLLListIterator(startingIndex);
    }

    // Iterator class for IUDoubleLinkedList
    private class SLLIterator implements Iterator<T>
    {
        private Node<T> nextNode;
        private int iterModCount;
        private boolean legalRemove;
        private int prevIndex;
        private Node<T> prevNode;

        //Constructor for a new Iterator
        public SLLIterator()
        {
            nextNode = head;
            iterModCount = modCount;
            legalRemove = false;
            prevIndex = -1;
            prevNode = head;
        }

        /*
         * Checks if the list has a next node
         * @return boolean true if the list has a next node, otherwise false
         */
        public boolean hasNext() {
            boolean hasNext = false;
            if (nextNode != null)
            {
                hasNext = true;
            }
            return hasNext;
        }
        
        /**
         * Returns the element of the next node and advances the iterator.
         * @return element of the next node in the list
         * @throws NoSuchElementException if there are no more entries in the list
         * @throws ConcurrentModificationException if the list has been modified outside of the iterator.
         */
        public T next() {
            T returnElement;
            if (iterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            else
            {
                returnElement = nextNode.getElement();
                prevNode = nextNode;
                nextNode = nextNode.getNext();
                legalRemove = true;
                prevIndex++;
            }
            iterModCount++;
            modCount++;
            return returnElement;
        }

        /**
         * Removes the node previously returned by next(). Can only be used once per call to next().
         * @throws IllegalStateException if next has not been called or remove was previously called.
         * @throws ConcurrentModificationException if the list has been altered outside of the iterator.
         */
        public void remove()
        {
            if (iterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!legalRemove)
            {
                throw new IllegalStateException();
            }
            else
            {
                Node<T> removeNode = prevNode;
                if ((removeNode.getNext() == null) && (removeNode.getPrev() == null))
                {
                    head = null;
                    tail = null;
                    legalRemove = false;
                }
                else if (removeNode.getNext() == null)
                {
                    prevNode.setNext(null);
                    tail = prevNode;
                    legalRemove = false;
                }
                else if (removeNode.getPrev() == null)
                {
                    head = removeNode.getNext();
                    removeNode.getNext().setPrev(null);
                    legalRemove = false;
                }
                else
                {
                    removeNode.getNext().setPrev(prevNode);
                    removeNode.getPrev().setNext(removeNode.getNext());
                    legalRemove = false;
                }
                size --;
                modCount++;
                iterModCount++;
            }
        }
    }

    // ListIterator for IUDoubleLinkedList
    private class SLLListIterator implements ListIterator<T>
    {
        private Node<T> nextNode;
        private int listIterModCount;
        private boolean legalRemove;
        private boolean legalSet;
        private int direction;
        private Node<T> returnedNode;
        private Node<T> prevNode;

        // Contructs a new ListIterator beginning at index 0
        public SLLListIterator()
        {
            nextNode = head;
            prevNode = head;
            returnedNode = null;
            listIterModCount = modCount;
            legalRemove = false;
            legalSet = false;
            direction = 1;
        }

        /**
         * Constructs a new ListIterator beginning at the given index, so that
         * a call to next() will return the entry of the given index.
         * @param startingIndex
         */
        public SLLListIterator(int startingIndex)
        {
            nextNode = head;
            prevNode = head;
            returnedNode = null;
            if ((startingIndex < 0) || (startingIndex > size))
            {
                throw new IndexOutOfBoundsException();
            }
            for (int i = 0; i < startingIndex; i++)
            {
                prevNode = nextNode;
                nextNode = nextNode.getNext();
            }
            listIterModCount = modCount;
            legalRemove = false;
            legalSet = false;
            direction = 1;
        }

        /**
         * True if the list has a next entry, otherwise false
         * @return boolean true if there is a next entry, otherwise false
         */
        public boolean hasNext()
        {
            boolean hasNext = false;
            if (nextNode != null)
            {
                hasNext = true;
            }
            return hasNext;
        }

        /**
         * Returns the element of the next node in the list and advances the ListIterator
         * @return the element of the next entry
         * @throws ConcurrentModificationException if the list has been modified outside of the ListIterator
         * @throws NoSuchElementException if there is no next entry
         */
        public T next() {
            T returnElement;
            if (listIterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            else 
            {
                returnedNode = nextNode;
                prevNode = nextNode;
                returnElement = nextNode.getElement();
                nextNode = nextNode.getNext();
                legalRemove = true;
                legalSet = true;
                direction = 1;
            }
            return returnElement;
        }

        /**
         * Returns true if there is a previous element, otherwise false.
         * @return boolean true if there is a previous element, otherwise false
         */
        public boolean hasPrevious() {
            boolean hasPrevious = false;
            if (size == 0)
            {
                hasPrevious = false;
            }
            else if (prevNode != null)
            {
                hasPrevious = true;
            }
            return hasPrevious;
        }

        /**
         * Returns the element of the previous node in the list and moves the ListIterator backwards.
         * @return the element of the previous node in the list
         * @throws ConcurrentModificationException if the list has been modified outside of the ListIterator
         * @throws NoSuchElementException if there is no previous node
         */
        public T previous() {
            T returnElement;
            if (listIterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!hasPrevious())
            {
                throw new NoSuchElementException();
            }
            else 
            {
                nextNode = prevNode;
                prevNode = prevNode.getPrev();
                returnedNode = nextNode;
                returnElement = nextNode.getElement();
                legalRemove = true;
                legalSet = true;
                direction = -1;
            }
            return returnElement;
        }

        /**
         * Gets the index of the next element of the list (the element that would be returned by a subsequent call to next).
         * @return the index of the next element, or the size of the list if there is no next element.
         */
        public int nextIndex()
        {
            int index = 0;
            Node<T> currNode = head;
            if (nextNode == null)
            {
                index = size;
                return index;
            }
            else 
            {
                while ((currNode != nextNode) && (currNode != null))
                {
                    currNode = currNode.getNext();
                    index ++;
                }
            }
            return index;
        }

        /**
         * Gets the index of the previous element in the list (the element that would be returned by a 
         * subsequent call to previous().)
         * @return integer the index of the previous element, or -1 if there is not previous element
         */
        public int previousIndex() {
            int index = 0;
            Node<T> currNode = nextNode;
            if (nextNode == null)
            {
                index = -1;
                return index;
            }
            else if ((prevNode == null) || (nextNode.getPrev() == null))
            {
                index = -1;
                return index;
            }
            else
            {
                while ((currNode != nextNode.getPrev()) && (currNode != null) && (currNode.getNext() != null))
                {
                    int i = 0;
                    for (i = 0; i < size; i++)
                    {
                        currNode = currNode.getNext();
                    }
                    index = i;
                }
            }
            return index;
        }

        /**
         * Removes from the list the node previously returned by a call to next() or previous(). 
         * Can only be called once after a call to next() or previous().
         * @throws ConcurrentModificationException if the list has been modified outside of the ListIterator.
         * @throws IllegalStateException if next or previous have not been called, remove has already been called,
         *  or set()/ add() have been called beforehand.
         */
        public void remove()
        {
            if (listIterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!legalRemove)
            {
                throw new IllegalStateException();
            }
            else
            {
                if (direction == 1)
                {
                    Node<T> removeNode = returnedNode;
                    if ((removeNode.getPrev() == null) && (removeNode.getNext() == null))
                    {
                        head = null;
                        tail = null;
                        legalRemove = false;
                    }
                    else if (removeNode.getPrev() == null)
                    {
                        removeNode.getNext().setPrev(null);
                        head = removeNode.getNext();
                        legalRemove = false;
                        
                    }
                    else if (removeNode.getNext() == null)
                    {
                        removeNode.getPrev().setNext(null);
                        tail = removeNode.getPrev();
                        legalRemove = false;
                    }
                    else 
                    {
                        removeNode.getPrev().setNext(nextNode);
                        nextNode.setPrev(removeNode.getPrev());
                        legalRemove = false;
                    }
                    
                }
                else if (direction == -1)
                {
                    Node<T> removeNode = returnedNode;
                    if ((removeNode.getNext() == null) && (removeNode.getPrev() == null))
                    {
                        head = null;
                        tail = null;
                        legalRemove = false;
                    }
                    else if (removeNode.getPrev() == null)
                    {
                        removeNode.getNext().setPrev(null);
                        head = removeNode.getNext();
                        legalRemove = false;
                    }
                    else if (removeNode.getNext() == null)
                    {
                        removeNode.getPrev().setNext(null);
                        tail = removeNode.getPrev();
                        legalRemove = false;
                    }
                    else 
                    {
                        removeNode.getNext().setPrev(removeNode.getPrev());
                        removeNode.getPrev().setNext(removeNode.getNext());
                        legalRemove = false;
                    }
                }
            }
            listIterModCount++;
            modCount++;
            size--;
        }


        /**
         * Sets the element previously returned by next() or previous() to an element given as a parameter. Can only be called once
         * after next() or previous().
         * @param element to replace the element in the list
         * @throws ConcurrentModificationException if the list has been modified outside of the ListIterator
         * @throws IllegalStateException if next or previous have not been called, or if set(), add(), or remove() have already been called.
         */
        public void set(T e) {
            if (listIterModCount != modCount)
            {
                throw new ConcurrentModificationException();
            }
            else if (!legalSet)
            {
                throw new IllegalStateException();
            }
            else
            {
                if (direction == 1)
                {
                    Node<T> newNode = new Node<T>(e);
                    Node<T> setNode = returnedNode;
                    if ((setNode.getNext() == null) && (setNode.getPrev() == null))
                    {
                        head = newNode;
                        tail = newNode;
                        legalSet = false;
                    }
                    else if (setNode.getNext() == null)
                    {
                        newNode.setPrev(setNode.getPrev());
                        setNode.getPrev().setNext(newNode);
                        tail = newNode;
                        legalSet = false;
                    }
                    else if (setNode.getPrev() == null)
                    {
                        newNode.setNext(setNode.getNext());
                        setNode.getNext().setPrev(newNode);
                        head = newNode;
                        legalSet = false;
                    }
                    else
                    {
                        newNode.setNext(setNode.getNext());
                        newNode.setPrev(setNode.getPrev());
                        setNode.getNext().setPrev(newNode);
                        setNode.getPrev().setNext(newNode);
                        legalSet = false;
                    }
                }
                else if (direction == -1)
                {
                    Node<T> newNode = new Node<T>(e);
                    Node<T> setNode = returnedNode;
                    if ((setNode.getNext() == null) && (setNode.getPrev() == null))
                    {
                        head = newNode;
                        tail = newNode;
                        legalSet = false;
                    }
                    else if (setNode.getNext() == null)
                    {
                        newNode.setPrev(setNode.getPrev());
                        setNode.getPrev().setNext(newNode);
                        tail = newNode;
                        legalSet = false;
                    }
                    else if (setNode.getPrev() == null)
                    {
                        newNode.setNext(setNode.getNext());
                        setNode.getNext().setPrev(newNode);
                        head = newNode;
                        legalSet = false;
                    }
                    else
                    {
                        newNode.setNext(setNode.getNext());
                        newNode.setPrev(setNode.getPrev());
                        setNode.getNext().setPrev(newNode);
                        setNode.getPrev().setNext(newNode);
                        legalSet = false;
                    }
                }
            }
            modCount++;
            listIterModCount++;
        }

        /**
         * Adds a new node with the element given as a parameter into the list. This new node is inserted before the element that would be
         * returned by next() and after the element that would be returned by previous(), so that a subsequent call to previous (but not next) 
         * would return the new element.
         * @param element to be added
         */
        public void add(T e) {
            Node<T> newNode = new Node<T>(e);
            if (nextNode == null)
            {
                head = newNode;
                tail = newNode;
            }
            else if ((nextNode.getPrev() == null) && (nextNode.getNext() == null))
            {
                nextNode.setPrev(newNode);
                newNode.setNext(nextNode);
                head = newNode;
            }
            else if (nextNode.getPrev() == null)
            {
                prevNode = newNode;
                head = newNode;
                newNode.setNext(nextNode);
                nextNode.setPrev(newNode);
            }
            else if (nextNode.getNext() == null)
            {
                newNode.setNext(nextNode);
                newNode.setPrev(prevNode);
                nextNode.setPrev(newNode);
                prevNode.setNext(newNode);
                prevNode = newNode;
                tail = nextNode;
            }
            else
            {
                newNode.setNext(nextNode);
                newNode.setPrev(prevNode);
                nextNode.setPrev(newNode);
                prevNode.setNext(newNode);
                prevNode = newNode;
            }
            size++;
            modCount++;
            listIterModCount++;
        }
        
    }
}