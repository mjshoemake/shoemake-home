//
// file: AbstractEvent.java
// proj: ER 6.4 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/events/ChildButtonMouseListenerList.java-arc  $
// $Author:Mike Shoemake$
// $Revision:2$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.events;

// Witness imports
import mjs.core.aggregation.AbstractList;

public class ChildButtonMouseListenerList extends AbstractList
{
   /**
    * A child button received a MouseClicked event.
    * This method is called by the list owner to notify all listeners in the list
    * that a button click has taken place.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseClicked(ChildButtonMouseEvent e)
   {
      for (int C=0; C <= size()-1; C++)
      {
         // Notify all listeners.
         ChildButtonMouseListener listener = get(C);
         listener.buttonMouseClicked(e);
      }
   }

   /**
    * A child button owned by the listener received a MouseEntered event.
    * This method is called by the list owner to notify all listeners in the list
    * that a button click has taken place.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseEntered(ChildButtonMouseEvent e)
   {
      for (int C=0; C <= size()-1; C++)
      {
         // Notify all listeners.
         ChildButtonMouseListener listener = get(C);
         listener.buttonMouseEntered(e);
      }
   }

   /**
    * A child button owned by the listener received a MouseExited event.
    * This method is called by the list owner to notify all listeners in the list
    * that a button click has taken place.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseExited(ChildButtonMouseEvent e)
   {
      for (int C=0; C <= size()-1; C++)
      {
         // Notify all listeners.
         ChildButtonMouseListener listener = get(C);
         listener.buttonMouseExited(e);
      }
   }

   /**
    * A child button received a MouseReleased event.
    * This method is called by the list owner to notify all listeners in the list
    * that a button click has taken place.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMousePressed(ChildButtonMouseEvent e)
   {
      for (int C=0; C <= size()-1; C++)
      {
         // Notify all listeners.
         ChildButtonMouseListener listener = get(C);
         listener.buttonMousePressed(e);
      }
   }

   /**
    * A child button owned by the listener received a MouseReleased event.
    * This method is called by the list owner to notify all listeners in the list
    * that a button click has taken place.
    *
    * @param e  The event object that describes the action that occured.
    */
   public void buttonMouseReleased(ChildButtonMouseEvent e)
   {
      for (int C=0; C <= size()-1; C++)
      {
         // Notify all listeners.
         ChildButtonMouseListener listener = get(C);
         listener.buttonMouseReleased(e);
      }
   }

   /**
    * Tests if the specified object is a component in this vector.
    *
    * @param   elem   an object.
    * @return  <code>true</code> if and only if the specified object
    * is the same as a component in this vector, as determined by the
    * <tt>equals</tt> method; <code>false</code> otherwise.
    */
   public boolean contains(ChildButtonMouseListener elem)
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
   public int indexOf(ChildButtonMouseListener elem)
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
   public synchronized int indexOf(ChildButtonMouseListener elem, int index)
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
   public int lastIndexOf(ChildButtonMouseListener elem)
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
   public synchronized int lastIndexOf(ChildButtonMouseListener elem, int index)
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
    * @see	   #get(int)
    * @see	   List
    */
   public synchronized ChildButtonMouseListener elementAt(int index)
   {
      return (ChildButtonMouseListener)(super.base_elementAt(index));
   }

   /**
    * Returns the first component (the item at index <tt>0</tt>) of
    * this vector.
    *
    * @return     the first component of this vector.
    * @exception  NoSuchElementException  if this vector has no components.
    */
   public synchronized ChildButtonMouseListener firstElement()
   {
      return (ChildButtonMouseListener)(super.base_firstElement());
   }

   /**
    * Returns the last component of the vector.
    *
    * @return  the last component of the vector, i.e., the component at index
    *          <code>size()&nbsp;-&nbsp;1</code>.
    * @exception  NoSuchElementException  if this vector is empty.
    */
   public synchronized ChildButtonMouseListener lastElement()
   {
      return (ChildButtonMouseListener)(super.base_lastElement());
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
    * @see	   #set(int, java.lang.Object)
    */
   public synchronized void setElementAt(ChildButtonMouseListener obj, int index)
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
    * @see	   #add(int, Object)
    * @see	   List
    */
   public synchronized void insertElementAt(ChildButtonMouseListener obj, int index)
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
    * @see	   #add(Object)
    * @see	   List
    */
   public synchronized void addElement(ChildButtonMouseListener obj)
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
    * @see	List#remove(Object)
    * @see	List
    */
   public synchronized boolean removeElement(ChildButtonMouseListener obj)
   {
      return super.base_removeElement((Object)obj);
   }

   /**
    * Returns the element at the specified position in this Vector.
    *
    * @param index index of element to return.
    * @exception ArrayIndexOutOfBoundsException index is out of range (index
    * 		  &lt; 0 || index &gt;= size()).
    * @since 1.2
    */
   public synchronized ChildButtonMouseListener get(int index)
   {
      return (ChildButtonMouseListener)(super.base_get(index));
   }

   /**
    * Replaces the element at the specified position in this Vector with the
    * specified element.
    *
    * @param index index of element to replace.
    * @param element element to be stored at the specified position.
    * @return the element previously at the specified position.
    * @exception ArrayIndexOutOfBoundsException index out of range
    *		  (index &lt; 0 || index &gt;= size()).
    * @exception IllegalArgumentException fromIndex &gt; toIndex.
    * @since 1.2
    */
   public synchronized ChildButtonMouseListener set(int index, ChildButtonMouseListener element)
   {
      return (ChildButtonMouseListener)(super.base_set(index, (Object)element));
   }

   /**
    * Appends the specified element to the end of this Vector.
    *
    * @param o element to be appended to this Vector.
    * @return true (as per the general contract of Collection.add).
    * @since 1.2
    */
   public synchronized boolean add(ChildButtonMouseListener o)
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
   public boolean remove(ChildButtonMouseListener o)
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
    *		  (index &lt; 0 || index &gt; size()).
    * @since 1.2
    */
   public void add(int index, ChildButtonMouseListener element)
   {
      super.base_add(index, (Object)element);
   }

   /**
    * Removes the element at the specified position in this Vector.
    * shifts any subsequent elements to the left (subtracts one from their
    * indices).  Returns the element that was removed from the Vector.
    *
    * @exception ArrayIndexOutOfBoundsException index out of range (index
    * 		  &lt; 0 || index &gt;= size()).
    * @param index the index of the element to removed.
    * @since 1.2
    */
   public synchronized ChildButtonMouseListener remove(int index)
   {
      return (ChildButtonMouseListener)(super.base_remove(index));
   }

   /**
    * @link dependency
    */
   /*# ChildButtonMouseListener lnkChildButtonMouseListener; */
}

// $Log:
//  2    Balance   1.1         3/28/2003 9:01:21 AM   Mike Shoemake   Added
//       Togethersoft diagram dependencies.
//  1    Balance   1.0         11/25/2002 11:30:40 AM Mike Shoemake
// $
//
//     Rev 1.0   Nov 25 2002 11:30:40   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 25 2002 10:02:04   mshoemake
//  Initial revision.
//
//     Rev 1.0   Nov 22 2002 08:56:26   mshoemake
//  Creating initial revision of class.
//
