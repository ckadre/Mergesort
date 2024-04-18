import java.util.Comparator;
import java.util.ListIterator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Mergesort algorithm.
 *
 * @author CS221, cameron kadre
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new WrappedDLL<T>(); //TODO: replace with your IUDoubleLinkedList for extra-credit
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		mergesort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		mergesort(list, c);
	}
	
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list)
	{
		// TODO: Implement recursive mergesort algorithm 
		int size = list.size();
		ListIterator<T> listIter = list.listIterator();
		if (list.size() <= 1)
		{
			return;
		}
		else 
		{
			IndexedUnsortedList<T> leftList = newList();
			IndexedUnsortedList<T> rightList = newList();
			if (size % 2 == 0)
			{
				for (int i = 0; i < (size / 2); i++)
				{
					leftList.add(listIter.next());
					listIter.remove();
				}
				for (int j = (size / 2); j < size; j++)
				{
					rightList.add(listIter.next());
					listIter.remove();
				}
			}
			else if (size % 2 == 1)
			{
				for (int i = 0; i <= (size / 2); i++)
				{
					leftList.add(listIter.next());
					listIter.remove();
				}
				for (int j = (size / 2) + 1; j < size; j++)
				{
					rightList.add(listIter.next());
					listIter.remove();
				}
			}
			mergesort(leftList);
			merge(list, leftList, rightList);
			mergesort(rightList);
			merge(list, leftList, rightList);
		}
	}

	private static <T extends Comparable<T>> void merge(IndexedUnsortedList<T> list, IndexedUnsortedList<T> leftList, IndexedUnsortedList<T> rightList)
	{
		int i = 0;
		int j = 0;
		while ((i < leftList.size()) && (j < rightList.size()))
		{
			if (leftList.first().compareTo(rightList.first()) < 0)
			{
				list.add(leftList.first());
				leftList.removeFirst();
				i++;
			}
			else if (leftList.first().compareTo(rightList.first()) == 0)
			{
				list.add(leftList.first());
				list.add(rightList.first());
				leftList.removeFirst();
				rightList.removeFirst();
				i++;
				j++;
			}
			else
			{
				list.add(rightList.first());
				rightList.removeFirst();
				j++;
			}
		}
		while (i < leftList.size())
		{
			if (leftList.size() > 1)
			{
				T tempElement = leftList.first();
				leftList.removeFirst();
				if (tempElement.compareTo(leftList.first()) < 0)
				{
					list.add(tempElement);
				}
				else if (tempElement.compareTo(leftList.first()) == 0)
				{
					list.add(tempElement);
					list.add(leftList.first());
					leftList.removeFirst();
				}
				else
				{
					list.add(leftList.first());
					leftList.removeFirst();
					leftList.addToFront(tempElement);
				}
			}
			else
			{
				list.add(leftList.first());
				leftList.removeFirst();
			}
			i++;
		}
		while (j < rightList.size())
		{
			if (rightList.size() > 1)
			{
				T tempElement = rightList.first();
				rightList.removeFirst();
				if (tempElement.compareTo(rightList.first()) < 0)
				{
					list.add(tempElement);
				}
				else if (tempElement.compareTo(rightList.first()) == 0)
				{
					list.add(tempElement);
					list.add(rightList.first());
					list.removeFirst();
				}
				else
				{
					list.add(rightList.first());
					rightList.removeFirst();
					rightList.addToFront(tempElement);
				}
			}
			else
			{
				list.add(rightList.first());
				rightList.removeFirst();
			}
			j++;
		}

	}

		
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
 	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		// TODO: Implement recursive mergesort algorithm using Comparator
		int size = list.size();
		ListIterator<T> listIter = list.listIterator();
		if (list.size() <= 1)
		{
			return;
		}
		else 
		{
			IndexedUnsortedList<T> leftList = newList();
			IndexedUnsortedList<T> rightList = newList();
			if (size % 2 == 0)
			{
				for (int i = 0; i < (size / 2); i++)
				{
					leftList.add(listIter.next());
					listIter.remove();
				}
				for (int j = (size / 2); j < size; j++)
				{
					rightList.add(listIter.next());
					listIter.remove();
				}
			}
			else if (size % 2 == 1)
			{
				for (int i = 0; i <= (size / 2); i++)
				{
					leftList.add(listIter.next());
					listIter.remove();
				}
				for (int j = (size / 2) + 1; j < size; j++)
				{
					rightList.add(listIter.next());
					listIter.remove();
				}
			}
			mergesort(leftList, c);
			comparatorMerge(list, leftList, rightList, c);
			mergesort(rightList, c);
			comparatorMerge(list, leftList, rightList, c);
		}
	}
	private static <T> void comparatorMerge (IndexedUnsortedList<T> list, IndexedUnsortedList<T> leftList, IndexedUnsortedList<T> rightList, Comparator<T> c)
	{
		int i = 0;
		int j = 0;
		while ((i < leftList.size()) && (j < rightList.size()))
		{
			if ((c.compare(leftList.first(), rightList.first())) < 0)
			{
				list.add(leftList.first());
				leftList.removeFirst();
				i++;
			}
			else if ((c.compare(leftList.first(), rightList.first())) == 0)
			{
				list.add(leftList.first());
				list.add(rightList.first());
				leftList.removeFirst();
				rightList.removeFirst();
				i++;
				j++;
			}
			else
			{
				list.add(rightList.first());
				rightList.removeFirst();
				j++;
			}
		}
		while (i < leftList.size())
		{
			list.add(leftList.first());
			leftList.removeFirst();
			i++;
		}
		while (j < rightList.size())
		{
			list.add(rightList.first());
			rightList.removeFirst();
			j++;
		}
	}
}
