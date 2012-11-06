
package mjs.aggregation;

import java.lang.Object;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This abstract class wraps an ArrayList object, exposing the
 * non-Object-related methods as public and Object-related methods as
 * protected. This allows the child class to inherently publish Vector
 * methods such as size() and clear() while hiding methods that deal
 * with the specific objects in the list such as add(), get(),
 * contains(), etc. <p>
 *
 * The intent is to allow the creation of a Vector that is tailored to
 * a specific class or interface (ComponentList, ListenerList, etc.).
 *
 * @author   mshoemake
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractArrayList
{
   /**
    * The wrapped ArrayList object.
    */
   private ArrayList list = null;


   /**
    * Constructs an empty ArrayList with the specified initial
    * capacity and with its capacity increment equal to zero.
    *
    * @param initialCapacity  the initial capacity of the list.
    */
   public AbstractArrayList(int initialCapacity)
   {
      list = new ArrayList(initialCapacity);
   }


   /**
    * Constructs an empty ArrayList so that its internal data array
    * has size <tt>10</tt> and its standard capacity increment is
    * zero.
    */
   public AbstractArrayList()
   {
      list = new ArrayList();
   }


   /**
    * Constructs a vector containing the elements of the specified
    * collection, in the order they are returned by the collection's
    * iterator.
    *
    * @param c  the collection whose elements are to be placed into
    * this vector.
    * @since    1.2
    */
   @SuppressWarnings("unchecked")
   public AbstractArrayList(Collection c)
   {
      list = new ArrayList(c);
   }


   /**
    * Tests if this list has no components.
    *
    * @return   <code>true</code> if and only if this vector has no
    * components, that is, its size is zero; <code>false</code>
    * otherwise.
    */
   public boolean isEmpty()
   {
      return list.isEmpty();
   }

   /**
    * Make a clone of this list as an ArrayList object.
    *
    * @return   The clone of this list.
    */
   public ArrayList cloneAsArrayList()
   {
      return (ArrayList)(list.clone());
   }

   /**
    * Trims the capacity of this vector to be the vector's current
    * size. If the capacity of this cector is larger than its current
    * size, then the capacity is changed to equal the size by
    * replacing its internal data array, kept in the field <tt>
    * elementData</tt> , with a smaller one. An application can use
    * this operation to minimize the storage of a vector.
    */
   public void trimToSize()
   {
      list.trimToSize();
   }


   /**
    * Increases the capacity of this vector, if necessary, to ensure
    * that it can hold at least the number of components specified by
    * the minimum capacity argument. <p>
    *
    * If the current capacity of this vector is less than <tt>
    * minCapacity</tt> , then its capacity is increased by replacing
    * its internal data array, kept in the field <tt>elementData</tt>
    * , with a larger one. The size of the new data array will be the
    * old size plus <tt>capacityIncrement</tt> , unless the value of
    * <tt>capacityIncrement</tt> is less than or equal to zero, in
    * which case the new capacity will be twice the old capacity; but
    * if this new size is still smaller than <tt>minCapacity</tt> ,
    * then the new capacity will be <tt>minCapacity</tt> .
    *
    * @param minCapacity  the desired minimum capacity.
    */
   public void ensureCapacity(int minCapacity)
   {
      list.ensureCapacity(minCapacity);
   }

   /**
    * Returns the number of components in this vector.
    *
    * @return   the number of components in this vector.
    */
   public int size()
   {
      return list.size();
   }

   /**
    * Returns an array containing all of the elements in this Vector
    * in the correct order.
    *
    * @return   Description of the Return Value
    * @since    1.2
    */
   public Object[] toArray()
   {
      return list.toArray();
   }

   /**
    * Returns an array containing all of the elements in this Vector
    * in the correct order. The runtime type of the returned array is
    * that of the specified array. If the Vector fits in the specified
    * array, it is returned therein. Otherwise, a new array is
    * allocated with the runtime type of the specified array and the
    * size of this Vector.<p>
    *
    * If the Vector fits in the specified array with room to spare
    * (i.e., the array has more elements than the Vector), the element
    * in the array immediately following the end of the Vector is set
    * to null. This is useful in determining the length of the Vector
    * <em>only</em> if the caller knows that the Vector does not
    * contain any null elements.
    *
    * @param a  the array into which the elements of the Vector are to
    * be stored, if it is big enough; otherwise, a new array of the
    * same runtime type is allocated for this purpose.
    * @return   an array containing the elements of the Vector.
    */
   @SuppressWarnings("unchecked")
   public Object[] toArray(Object[] a)
   {
      return list.toArray(a);
   }


   /**
    * Removes all of the elements from this ArrayList. The list will
    * be empty after this call returns (unless it throws an
    * exception).
    *
    * @since   1.2
    */
   public void clear()
   {
      list.clear();
   }

   // Bulk Operations

   /**
    * Returns true if this Vector contains all of the elements in the
    * specified Collection.
    *
    * @param c  Description of the Parameter
    * @return   true if this Vector contains all of the elements in
    * the specified collection.
    */
   @SuppressWarnings("unchecked")
   public boolean containsAll(Collection c)
   {
      return list.containsAll(c);
   }

   /**
    * Appends all of the elements in the specified Collection to the
    * end of this Vector, in the order that they are returned by the
    * specified Collection's Iterator. The behavior of this operation
    * is undefined if the specified Collection is modified while the
    * operation is in progress. (This implies that the behavior of
    * this call is undefined if the specified Collection is this
    * Vector, and this Vector is nonempty.)
    *
    * @param c  elements to be inserted into this Vector.
    * @return   Description of the Return Value
    * @since    1.2
    */
   @SuppressWarnings("unchecked")
   public boolean addAll(Collection c)
   {
      return list.addAll(c);
   }

   /**
    * Removes from this Vector all of its elements that are contained
    * in the specified Collection.
    *
    * @param c  Description of the Parameter
    * @return   true if this Vector changed as a result of the call.
    * @since    1.2
    */
   @SuppressWarnings("unchecked")
   public boolean removeAll(Collection c)
   {
      return list.removeAll(c);
   }

   /**
    * Retains only the elements in this Vector that are contained in
    * the specified Collection. In other words, removes from this
    * Vector all of its elements that are not contained in the
    * specified Collection.
    *
    * @param c  Description of the Parameter
    * @return   true if this Vector changed as a result of the call.
    * @since    1.2
    */
   @SuppressWarnings("unchecked")
   public boolean retainAll(Collection c)
   {
      return list.retainAll(c);
   }

   /**
    * Inserts all of the elements in in the specified Collection into
    * this Vector at the specified position. Shifts the element
    * currently at that position (if any) and any subsequent elements
    * to the right (increases their indices). The new elements will
    * appear in the Vector in the order that they are returned by the
    * specified Collection's iterator.
    *
    * @param index  index at which to insert first element from the
    * specified collection.
    * @param c      elements to be inserted into this Vector.
    * @return       Description of the Return Value
    * @since        1.2
    */
   @SuppressWarnings("unchecked")
   public boolean addAll(int index, Collection c)
   {
      return list.addAll(index, c);
   }


   /**
    * Returns a view of the portion of this List between fromIndex,
    * inclusive, and toIndex, exclusive. (If fromIndex and ToIndex are
    * equal, the returned List is empty.) The returned List is backed
    * by this List, so changes in the returned List are reflected in
    * this List, and vice-versa. The returned List supports all of the
    * optional List operations supported by this List. <p>
    *
    * This method eliminates the need for explicit range operations
    * (of the sort that commonly exist for arrays). Any operation that
    * expects a List can be used as a range operation by operating on
    * a subList view instead of a whole List. For example, the
    * following idiom removes a range of elements from a List: <pre>
    *	    list.subList(from, to).clear();
    * </pre> Similar idioms may be constructed for indexOf and
    * lastIndexOf, and all of the algorithms in the Collections class
    * can be applied to a subList.<p>
    *
    * The semantics of the List returned by this method become
    * undefined if the backing list (i.e., this List) is <i>
    * structurally modified</i> in any way other than via the returned
    * List. (Structural modifications are those that change the size
    * of the List, or otherwise perturb it in such a fashion that
    * iterations in progress may yield incorrect results.)
    *
    * @param fromIndex                   low endpoint (inclusive) of
    * the subList.
    * @param toIndex                     high endpoint (exclusive) of
    * the subList.
    * @return                            a view of the specified range
    * within this List.
    * @throws IndexOutOfBoundsException  endpoint index value out of
    * range <code>(fromIndex &lt; 0 || toIndex &gt; size)</code>
    * @throws IllegalArgumentException   endpoint indices out of order
    * <code>(fromIndex &gt; toIndex)</code>
    */
   public List subList(int fromIndex, int toIndex)
   {
      return list.subList(fromIndex, toIndex);
   }


   /**
    * Tests if the specified object is a component in this vector.
    *
    * @param elem  an object.
    * @return      <code>true</code> if and only if the specified
    * object is the same as a component in this vector, as determined
    * by the <tt>equals </tt> method; <code>false</code> otherwise.
    */
   protected boolean base_contains(Object elem)
   {
      return list.contains(elem);
   }


   /**
    * Searches for the first occurence of the given argument, testing
    * for equality using the <code>equals</code> method.
    *
    * @param elem  an object.
    * @return      the index of the first occurrence of the argument
    * in this vector, that is, the smallest value <tt>k</tt> such that
    * <tt> elem.equals(elementData[k])</tt> is <tt>true</tt> ; returns
    * <code>-1</code> if the object is not found.
    * @see         Object#equals(Object)
    */
   protected int base_indexOf(Object elem)
   {
      return list.indexOf(elem);
   }

   /**
    * Returns the index of the last occurrence of the specified object
    * in this vector.
    *
    * @param elem  the desired component.
    * @return      the index of the last occurrence of the specified
    * object in this vector, that is, the largest value <tt>k</tt>
    * such that <tt> elem.equals(elementData[k])</tt> is <tt>true</tt>
    * ; returns <code>-1</code> if the object is not found.
    */
   protected int base_lastIndexOf(Object elem)
   {
      return list.lastIndexOf(elem);
   }

   // Positional Access Operations

   /**
    * Returns the element at the specified position in this Vector.
    *
    * @param index  index of element to return.
    * @return       Description of the Return Value
    * @since        1.2
    */
   protected Object base_get(int index)
   {
      return list.get(index);
   }


   /**
    * Replaces the element at the specified position in this Vector
    * with the specified element.
    *
    * @param index    index of element to replace.
    * @param element  element to be stored at the specified position.
    * @return         the element previously at the specified
    * position.
    * @since          1.2
    */
   @SuppressWarnings("unchecked")
   protected Object base_set(int index, Object element)
   {
      return list.set(index, element);
   }


   /**
    * Appends the specified element to the end of this Vector.
    *
    * @param o  element to be appended to this Vector.
    * @return   true (as per the general contract of Collection.add).
    * @since    1.2
    */
   @SuppressWarnings("unchecked")
   protected boolean base_add(Object o)
   {
      return list.add(o);
   }


   /**
    * Removes the first occurrence of the specified element in this
    * Vector If the Vector does not contain the element, it is
    * unchanged. More formally, removes the element with the lowest
    * index i such that <code>(o==null ? get(i)==null : o.equals(get(i)))</code>
    * (if such an element exists).
    *
    * @param o  element to be removed from this Vector, if present.
    * @return   true if the Vector contained the specified element.
    * @since    1.2
    */
   protected boolean base_remove(Object o)
   {
      return list.remove(o);
   }


   /**
    * Inserts the specified element at the specified position in this
    * Vector. Shifts the element currently at that position (if any)
    * and any subsequent elements to the right (adds one to their
    * indices).
    *
    * @param index    index at which the specified element is to be
    * inserted.
    * @param element  element to be inserted.
    * @since          1.2
    */
   @SuppressWarnings("unchecked")
   protected void base_add(int index, Object element)
   {
      list.add(index, element);
   }


   /**
    * Removes the element at the specified position in this Vector.
    * shifts any subsequent elements to the left (subtracts one from
    * their indices). Returns the element that was removed from the
    * Vector.
    *
    * @param index  the index of the element to removed.
    * @return       Description of the Return Value
    * @since        1.2
    */
   protected Object base_remove(int index)
   {
      return list.remove(index);
   }

}

