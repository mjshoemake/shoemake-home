/* File name:     ParameterList.java
 * Package name:  mjs.core.model
 * Project name:  Core
 * Date Created:  Apr 21, 2004
 * Created by:    mshoemake
 * Year:          2004
 */
package mjs.core.model;

// MJS imports
import mjs.core.aggregation.AbstractVector;
import mjs.core.model.Parameter;

/**
 * A list of Parameters.
 * 
 * @author mshoemake
 */
public class ParameterList extends AbstractVector
{


   /**
    * Tests if the specified object is a component in this vector.
    *
    * @param   elem   an object.
    * @return  <code>true</code> if and only if the specified object
    * is the same as a component in this vector, as determined by the
    * <tt>equals</tt> method; <code>false</code> otherwise.
    */
   public boolean contains(Parameter elem)
   {
      return super.base_contains((Object)elem);
   }

   /**
    * Searches for the first occurence of the given argument, testing
    * for equality using the <code>equals</code> method.
    *
    * @param   elem   an object.
    * @return  the index of the first occurrence of the argument in this
    *          vector, that is, the smallest value <tt>k</tt> such that
    *          <tt>elem.equals(elementData[k])</tt> is <tt>true</tt>;
    *          returns <code>-1</code> if the object is not found.
    * @see     Object#equals(Object)
    */
   public int indexOf(Parameter elem)
   {
      return super.base_indexOf((Object)elem);
   }

   /**
    * Searches for the first occurence of the given argument, beginning
    * the search at <code>index</code>, and testing for equality using
    * the <code>equals</code> method.
    *
    * @param   elem    an object.
    * @param   index   the non-negative index to start searching from.
    * @return  the index of the first occurrence of the object argument in
    *          this vector at position <code>index</code> or later in the
    *          vector, that is, the smallest value <tt>k</tt> such that
    *          <tt>elem.equals(elementData[k]) && (k &gt;= index)</tt> is
    *          <tt>true</tt>; returns <code>-1</code> if the object is not
    *          found. (Returns <code>-1</code> if <tt>index</tt> &gt;= the
    *          current size of this <tt>Vector</tt>.)
    * @exception  IndexOutOfBoundsException  if <tt>index</tt> is negative.
    * @see     Object#equals(Object)
    */
   public synchronized int indexOf(Parameter elem, int index)
   {
      return super.base_indexOf((Object)elem, index);
   }

   /**
    * Returns the index of the last occurrence of the specified object in
    * this vector.
    *
    * @param   elem   the desired component.
    * @return  the index of the last occurrence of the specified object in
    *          this vector, that is, the largest value <tt>k</tt> such that
    *          <tt>elem.equals(elementData[k])</tt> is <tt>true</tt>;
    *          returns <code>-1</code> if the object is not found.
    */
   public int lastIndexOf(Parameter elem)
   {
      return super.base_lastIndexOf((Object)elem);
   }

   /**
    * Searches backwards for the specified object, starting from the
    * specified index, and returns an index to it.
    *
    * @param  elem    the desired component.
    * @param  index   the index to start searching from.
    * @return the index of the last occurrence of the specified object in this
    *          vector at position less than or equal to <code>index</code> in
    *          the vector, that is, the largest value <tt>k</tt> such that
    *          <tt>elem.equals(elementData[k]) && (k &lt;= index)</tt> is
    *          <tt>true</tt>; <code>-1</code> if the object is not found.
    *          (Returns <code>-1</code> if <tt>index</tt> is negative.)
    * @exception  IndexOutOfBoundsException  if <tt>index</tt> is greater
    *             than or equal to the current size of this vector.
    */
   public synchronized int lastIndexOf(Parameter elem, int index)
   {
     return  super.base_lastIndexOf((Object)elem, index);
   }

   /**
    * Returns the component at the specified index.<p>
    *
    * This method is identical in functionality to the get method
    * (which is part of the List interface).
    *
    * @param      index   an index into this vector.
    * @return     the component at the specified index.
    * @exception  ArrayIndexOutOfBoundsException  if the <tt>index</tt>
    *             is negative or not less than the current size of this
    *             <tt>Vector</tt> object.
    *             given.
    * @see     #get(int)
    * @see     List
    */
   public synchronized Parameter elementAt(int index)
   {
      return (Parameter)(super.base_elementAt(index));
   }

   /**
    * Returns the first component (the item at index <tt>0</tt>) of
    * this vector.
    *
    * @return     the first component of this vector.
    * @exception  NoSuchElementException  if this vector has no components.
    */
   public synchronized Parameter firstElement()
   {
      return (Parameter)(super.base_firstElement());
   }

   /**
    * Returns the last component of the vector.
    *
    * @return  the last component of the vector, i.e., the component at index
    *          <code>size()&nbsp;-&nbsp;1</code>.
    * @exception  NoSuchElementException  if this vector is empty.
    */
   public synchronized Parameter lastElement()
   {
      return (Parameter)(super.base_lastElement());
   }

   /**
    * Sets the component at the specified <code>index</code> of this
    * vector to be the specified object. The previous component at that
    * position is discarded.<p>
    *
    * The index must be a value greater than or equal to <code>0</code>
    * and less than the current size of the vector. <p>
    *
    * This method is identical in functionality to the set method
    * (which is part of the List interface). Note that the set method reverses
    * the order of the parameters, to more closely match array usage.  Note
    * also that the set method returns the old value that was stored at the
    * specified position.
    *
    * @param      obj     what the component is to be set to.
    * @param      index   the specified index.
    * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
    * @see        #size()
    * @see        List
    * @see     #set(int, java.lang.Object)
    */
   public synchronized void setElementAt(Parameter obj, int index)
   {
      super.base_setElementAt((Object)obj, index);
   }

   /**
    * Inserts the specified object as a component in this vector at the
    * specified <code>index</code>. Each component in this vector with
    * an index greater or equal to the specified <code>index</code> is
    * shifted upward to have an index one greater than the value it had
    * previously. <p>
    *
    * The index must be a value greater than or equal to <code>0</code>
    * and less than or equal to the current size of the vector. (If the
    * index is equal to the current size of the vector, the new element
    * is appended to the Vector.)<p>
    *
    * This method is identical in functionality to the add(Object, int) method
    * (which is part of the List interface). Note that the add method reverses
    * the order of the parameters, to more closely match array usage.
    *
    * @param      obj     the component to insert.
    * @param      index   where to insert the new component.
    * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
    * @see        #size()
    * @see     #add(int, Object)
    * @see     List
    */
   public synchronized void insertElementAt(Parameter obj, int index)
   {
      super.base_insertElementAt((Object)obj, index);
   }

   /**
    * Adds the specified component to the end of this vector,
    * increasing its size by one. The capacity of this vector is
    * increased if its size becomes greater than its capacity. <p>
    *
    * This method is identical in functionality to the add(Object) method
    * (which is part of the List interface).
    *
    * @param   obj   the component to be added.
    * @see     #add(Object)
    * @see     List
    */
   public synchronized void addElement(Parameter obj)
   {
      super.base_addElement((Object)obj);
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
    * @param   obj   the component to be removed.
    * @return  <code>true</code> if the argument was a component of this
    *          vector; <code>false</code> otherwise.
    * @see  List#remove(Object)
    * @see  List
    */
   public synchronized boolean removeElement(Parameter obj)
   {
      return super.base_removeElement((Object)obj);
   }

   /**
    * Returns the element at the specified position in this Vector.
    *
    * @param index index of element to return.
    * @exception ArrayIndexOutOfBoundsException index is out of range (index
    *         &lt; 0 || index &gt;= size()).
    * @since 1.2
    */
   public synchronized Parameter get(int index)
   {
      return (Parameter)(super.base_get(index));
   }

   /**
    * Replaces the element at the specified position in this Vector with the
    * specified element.
    *
    * @param index index of element to replace.
    * @param element element to be stored at the specified position.
    * @return the element previously at the specified position.
    * @exception ArrayIndexOutOfBoundsException index out of range
    *      (index &lt; 0 || index &gt;= size()).
    * @exception IllegalArgumentException fromIndex &gt; toIndex.
    * @since 1.2
    */
   public synchronized Parameter set(int index, Parameter element)
   {
      return (Parameter)(super.base_set(index, (Object)element));
   }

   /**
    * Appends the specified element to the end of this Vector.
    *
    * @param o element to be appended to this Vector.
    * @return true (as per the general contract of Collection.add).
    * @since 1.2
    */
   public synchronized boolean add(Parameter o)
   {
      return super.base_add((Object)o);
   }

   /**
    * Removes the first occurrence of the specified element in this Vector
    * If the Vector does not contain the element, it is unchanged.  More
    * formally, removes the element with the lowest index i such that
    * <code>(o==null ? get(i)==null : o.equals(get(i)))</code> (if such
    * an element exists).
    *
    * @param o element to be removed from this Vector, if present.
    * @return true if the Vector contained the specified element.
    * @since 1.2
    */
   public boolean remove(Parameter o)
   {
      return super.base_remove((Object)o);
   }

   /**
    * Inserts the specified element at the specified position in this Vector.
    * Shifts the element currently at that position (if any) and any
    * subsequent elements to the right (adds one to their indices).
    *
    * @param index index at which the specified element is to be inserted.
    * @param element element to be inserted.
    * @exception ArrayIndexOutOfBoundsException index is out of range
    *      (index &lt; 0 || index &gt; size()).
    * @since 1.2
    */
   public void add(int index, Parameter element)
   {
      super.base_add(index, (Object)element);
   }

   /**
    * Removes the element at the specified position in this Vector.
    * shifts any subsequent elements to the left (subtracts one from their
    * indices).  Returns the element that was removed from the Vector.
    *
    * @exception ArrayIndexOutOfBoundsException index out of range (index
    *         &lt; 0 || index &gt;= size()).
    * @param index the index of the element to removed.
    * @since 1.2
    */
   public synchronized Parameter remove(int index)
   {
      return (Parameter)(super.base_remove(index));
   }

   /**
    * Utility method.  Convert this list into an array of Parameters.
    */
   public Parameter[] toParameterArray()
   {
      Parameter[] params = new Parameter[this.size()];
      for (int C = 0; C <= this.size()-1; C++)
      {
         params[C] = this.get(C); 
      }
      return params;
   }

}
