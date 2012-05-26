
package mjs.core.aggregation;

import java.lang.Object;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;


/**
 * This abstract class wraps a Vector object, exposing the
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
public abstract class AbstractVector
{

   private Vector vector;


   /**
    * Constructs an empty vector with the specified initial capacity
    * and capacity increment.
    *
    * @param initialCapacity    the initial capacity of the vector.
    * @param capacityIncrement  the amount by which the capacity is
    * increased when the vector overflows.
    */
   public AbstractVector(int initialCapacity, int capacityIncrement)
   {
      vector = new Vector(initialCapacity, capacityIncrement);
   }


   /**
    * Constructs an empty vector with the specified initial capacity
    * and with its capacity increment equal to zero.
    *
    * @param initialCapacity  the initial capacity of the vector.
    */
   public AbstractVector(int initialCapacity)
   {
      vector = new Vector(initialCapacity);
   }


   /**
    * Constructs an empty vector so that its internal data array has
    * size <tt>10 </tt> and its standard capacity increment is zero.
    */
   public AbstractVector()
   {
      vector = new Vector();
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
   public AbstractVector(Collection c)
   {
      vector = new Vector(c);
   }


   /**
    * Tests if this vector has no components.
    *
    * @return   <code>true</code> if and only if this vector has no
    * components, that is, its size is zero; <code>false</code>
    * otherwise.
    */
   public boolean isEmpty()
   {
      return vector.isEmpty();
   }


   /**
    * Sets the size of this vector. If the new size is greater than
    * the current size, new <code>null</code> items are added to the
    * end of the vector. If the new size is less than the current
    * size, all components at index <code>newSize</code> and greater
    * are discarded.
    *
    * @param newSize                          the new size of this
    * vector.
    * @throws ArrayIndexOutOfBoundsException  if new size is negative.
    */
   public synchronized void setSize(int newSize)
   {
      vector.setSize(newSize);
   }


   /**
    * Make a clone of this list as a Vector object.
    *
    * @return   The clone of this list.
    */
   public Vector cloneAsVector()
   {
      return (Vector)(vector.clone());
   }


   /**
    * Copies the components of this vector into the specified array.
    * The item at index <tt>k</tt> in this vector is copied into
    * component <tt>k</tt> of <tt>anArray</tt> . The array must be big
    * enough to hold all the objects in this vector, else an <tt>
    * IndexOutOfBoundsException</tt> is thrown.
    *
    * @param anArray  the array into which the components get copied.
    */
   public synchronized void copyInto(Object[] anArray)
   {
      vector.copyInto(anArray);
   }


   /**
    * Trims the capacity of this vector to be the vector's current
    * size. If the capacity of this cector is larger than its current
    * size, then the capacity is changed to equal the size by
    * replacing its internal data array, kept in the field <tt>
    * elementData</tt> , with a smaller one. An application can use
    * this operation to minimize the storage of a vector.
    */
   public synchronized void trimToSize()
   {
      vector.trimToSize();
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
   public synchronized void ensureCapacity(int minCapacity)
   {
      vector.ensureCapacity(minCapacity);
   }


   /**
    * Returns the current capacity of this vector.
    *
    * @return   the current capacity (the length of its internal data
    * arary, kept in the field <tt>elementData</tt> of this vector.
    */
   public int capacity()
   {
      return vector.capacity();
   }


   /**
    * Returns the number of components in this vector.
    *
    * @return   the number of components in this vector.
    */
   public int size()
   {
      return vector.size();
   }


   /**
    * Returns an enumeration of the components of this vector. The
    * returned <tt> Enumeration</tt> object will generate all items in
    * this vector. The first item generated is the item at index <tt>0
    * </tt> , then the item at index <tt>1</tt> , and so on.
    *
    * @return   an enumeration of the components of this vector.
    * @see      Enumeration
    * @see      Iterator
    */
   public Enumeration elements()
   {
      return vector.elements();
   }


   /**
    * Deletes the component at the specified index. Each component in
    * this vector with an index greater or equal to the specified
    * <code>index</code> is shifted downward to have an index one
    * smaller than the value it had previously. The size of this
    * vector is decreased by <tt>1</tt> .<p>
    *
    * The index must be a value greater than or equal to <code>0</code>
    * and less than the current size of the vector. <p>
    *
    * This method is identical in functionality to the remove method
    * (which is part of the List interface). Note that the remove
    * method returns the old value that was stored at the specified
    * position.
    *
    * @param index  the index of the object to remove.
    * @see          #size()
    * @see          #remove(int)
    * @see          List
    */
   public synchronized void removeElementAt(int index)
   {
      vector.removeElementAt(index);
   }


   /**
    * Removes all components from this vector and sets its size to
    * zero.<p>
    *
    * This method is identical in functionality to the clear method
    * (which is part of the List interface).
    *
    * @see   #clear
    * @see   List
    */
   public synchronized void removeAllElements()
   {
      vector.removeAllElements();
   }


   /**
    * Returns an array containing all of the elements in this Vector
    * in the correct order.
    *
    * @return   Description of the Return Value
    * @since    1.2
    */
   public synchronized Object[] toArray()
   {
      return vector.toArray();
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
   public synchronized Object[] toArray(Object[] a)
   {
      return vector.toArray(a);
   }


   /**
    * Removes all of the elements from this Vector. The Vector will be
    * empty after this call returns (unless it throws an exception).
    *
    * @since   1.2
    */
   public void clear()
   {
      vector.clear();
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
   public synchronized boolean containsAll(Collection c)
   {
      return vector.containsAll(c);
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
   public synchronized boolean addAll(Collection c)
   {
      return vector.addAll(c);
   }


   /**
    * Removes from this Vector all of its elements that are contained
    * in the specified Collection.
    *
    * @param c  Description of the Parameter
    * @return   true if this Vector changed as a result of the call.
    * @since    1.2
    */
   public synchronized boolean removeAll(Collection c)
   {
      return vector.removeAll(c);
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
   public synchronized boolean retainAll(Collection c)
   {
      return vector.retainAll(c);
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
   public synchronized boolean addAll(int index, Collection c)
   {
      return vector.addAll(index, c);
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
      return vector.subList(fromIndex, toIndex);
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
      return vector.contains(elem);
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
      return vector.indexOf(elem);
   }


   /**
    * Searches for the first occurence of the given argument,
    * beginning the search at <code>index</code>, and testing for
    * equality using the <code>equals</code> method.
    *
    * @param elem   an object.
    * @param index  the non-negative index to start searching from.
    * @return       the index of the first occurrence of the object
    * argument in this vector at position <code>index</code> or later
    * in the vector, that is, the smallest value <tt>k</tt> such that
    * <tt>elem.equals(elementData[k]) && (k &gt;= index)</tt> is <tt>
    * true </tt> ; returns <code>-1</code> if the object is not found.
    * (Returns <code>-1</code> if <tt>index</tt> &gt;= the current
    * size of this <tt> Vector</tt> .)
    * @see          Object#equals(Object)
    */
   protected synchronized int base_indexOf(Object elem, int index)
   {
      return vector.indexOf(elem, index);
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
      return vector.lastIndexOf(elem);
   }


   /**
    * Searches backwards for the specified object, starting from the
    * specified index, and returns an index to it.
    *
    * @param elem   the desired component.
    * @param index  the index to start searching from.
    * @return       the index of the last occurrence of the specified
    * object in this vector at position less than or equal to <code>index</code>
    * in the vector, that is, the largest value <tt>k </tt> such that
    * <tt>elem.equals(elementData[k]) && (k &lt;= index) </tt> is <tt>
    * true</tt> ; <code>-1</code> if the object is not found. (Returns
    * <code>-1</code> if <tt>index</tt> is negative.)
    */
   protected synchronized int base_lastIndexOf(Object elem, int index)
   {
      return vector.lastIndexOf(elem, index);
   }


   /**
    * Returns the component at the specified index.<p>
    *
    * This method is identical in functionality to the get method
    * (which is part of the List interface).
    *
    * @param index  an index into this vector.
    * @return       the component at the specified index.
    * @see          #get(int)
    * @see          List
    */
   protected synchronized Object base_elementAt(int index)
   {
      return vector.elementAt(index);
   }


   /**
    * Returns the first component (the item at index <tt>0</tt> ) of
    * this vector.
    *
    * @return   the first component of this vector.
    */
   protected synchronized Object base_firstElement()
   {
      return vector.firstElement();
   }


   /**
    * Returns the last component of the vector.
    *
    * @return   the last component of the vector, i.e., the component
    * at index <code>size()&nbsp;-&nbsp;1</code>.
    */
   protected synchronized Object base_lastElement()
   {
      return vector.lastElement();
   }


   /**
    * Sets the component at the specified <code>index</code> of this
    * vector to be the specified object. The previous component at
    * that position is discarded.<p>
    *
    * The index must be a value greater than or equal to <code>0</code>
    * and less than the current size of the vector. <p>
    *
    * This method is identical in functionality to the set method
    * (which is part of the List interface). Note that the set method
    * reverses the order of the parameters, to more closely match
    * array usage. Note also that the set method returns the old value
    * that was stored at the specified position.
    *
    * @param obj    what the component is to be set to.
    * @param index  the specified index.
    * @see          #size()
    * @see          List
    * @see          #set(int, java.lang.Object)
    */
   protected synchronized void base_setElementAt(Object obj, int index)
   {
      vector.setElementAt(obj, index);
   }


   /**
    * Inserts the specified object as a component in this vector at
    * the specified <code>index</code>. Each component in this vector
    * with an index greater or equal to the specified <code>index</code>
    * is shifted upward to have an index one greater than the value it
    * had previously. <p>
    *
    * The index must be a value greater than or equal to <code>0</code>
    * and less than or equal to the current size of the vector. (If
    * the index is equal to the current size of the vector, the new
    * element is appended to the Vector.)<p>
    *
    * This method is identical in functionality to the add(Object,
    * int) method (which is part of the List interface). Note that the
    * add method reverses the order of the parameters, to more closely
    * match array usage.
    *
    * @param obj    the component to insert.
    * @param index  where to insert the new component.
    * @see          #size()
    * @see          #add(int, Object)
    * @see          List
    */
   protected synchronized void base_insertElementAt(Object obj, int index)
   {
      vector.insertElementAt(obj, index);
   }


   /**
    * Adds the specified component to the end of this vector,
    * increasing its size by one. The capacity of this vector is
    * increased if its size becomes greater than its capacity. <p>
    *
    * This method is identical in functionality to the add(Object)
    * method (which is part of the List interface).
    *
    * @param obj  the component to be added.
    * @see        #add(Object)
    * @see        List
    */
   protected synchronized void base_addElement(Object obj)
   {
      vector.addElement(obj);
   }


   /**
    * Removes the first (lowest-indexed) occurrence of the argument
    * from this vector. If the object is found in this vector, each
    * component in the vector with an index greater or equal to the
    * object's index is shifted downward to have an index one smaller
    * than the value it had previously.<p>
    *
    * This method is identical in functionality to the remove(Object)
    * method (which is part of the List interface).
    *
    * @param obj  the component to be removed.
    * @return     <code>true</code> if the argument was a component of
    * this vector; <code>false</code> otherwise.
    * @see        List#remove(Object)
    * @see        List
    */
   protected synchronized boolean base_removeElement(Object obj)
   {
      return vector.removeElement(obj);
   }

   // Positional Access Operations

   /**
    * Returns the element at the specified position in this Vector.
    *
    * @param index  index of element to return.
    * @return       Description of the Return Value
    * @since        1.2
    */
   protected synchronized Object base_get(int index)
   {
      return vector.get(index);
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
   protected synchronized Object base_set(int index, Object element)
   {
      return vector.set(index, element);
   }


   /**
    * Appends the specified element to the end of this Vector.
    *
    * @param o  element to be appended to this Vector.
    * @return   true (as per the general contract of Collection.add).
    * @since    1.2
    */
   protected synchronized boolean base_add(Object o)
   {
      return vector.add(o);
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
      return vector.remove(o);
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
   protected void base_add(int index, Object element)
   {
      vector.add(index, element);
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
   protected synchronized Object base_remove(int index)
   {
      return vector.remove(index);
   }

}

