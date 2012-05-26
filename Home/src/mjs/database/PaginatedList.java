package mjs.database;

import java.util.ArrayList;
import java.util.Collection;
import mjs.aggregation.AbstractArrayList;
import mjs.aggregation.OrderedMap;
import org.apache.log4j.Logger;


/**
 * The list of objects retrieved from the database. This list includes
 * pagination functionality so the list can be retrieved from the
 * database once and paginated from there.
 *
 * @author   mshoemake
 */
@SuppressWarnings("rawtypes")
public class PaginatedList extends AbstractArrayList
{
   /**
    * The log4j logger to use when writing log messages. This is
    * populated by extracting the logger using the Logger category.
    * The default Logger category is "Model".
    */
   protected Logger log = Logger.getLogger("Model");

   /**
    * The max number of records allowed in the list. Defaults to 1500.
    */
   private int maxRecords = 1500;

   /**
    * The number of records on a page. Defaults to 10.
    */
   private int pageLength = 10;

   /**
    * The first record of the current page.
    */
   private int startOfPage = 0;

   /**
    * The class type used to dynamically load beans into this list.
    */
   private Class dataType = null;

   /**
    * The global forward to use for to get back to the current JSP page.
    * This is used by the pagination actions.
    */
   private String globalForward = null;

   /**
    * Field definitions from mapping file.
    */
   private OrderedMap fieldDefs = null;
   
   /**
    * The action used to execute a filter when in detail mode.  
    */
   private String filterAction = null;
   
   /**
    * Constructor.
    *
    * @param type           Description of Parameter
    * @param newPageLength  Description of Parameter
    * @param newMaxRecords  Description of Parameter
    * @param globalForward  The global forward to use for to get back to the current JSP page.
    *                       This is used by the pagination actions.
    */
   public PaginatedList(Class type, int newPageLength, int newMaxRecords, String globalForward)
   {
      super();
      this.dataType = type;
      this.pageLength = newPageLength;
      this.maxRecords = newMaxRecords;
      this.globalForward = globalForward;
   }

   /**
    * Is the number of items in the list higher than maxRecords?
    *
    * @return   The value of the Overflowed property.
    */
   private boolean isOverflowed()
   {
      return size() >= maxRecords;
   }

   /**
    * Appends items in the specified list to this one.
    *
    * @param list
    * @throws DataLayerException
    */
   public void appendList(Collection list)
   {
      Object items[] = list.toArray();
      int i = 0;

      while ((i < items.length) && ! isOverflowed())
      {
         Object o = items[i];

         add(o);
         i++;
      }
   }

   /**
    * Returns an ArrayList of items on the current page.
    *
    * @return   ArrayList
    */
   @SuppressWarnings("unchecked")
   public ArrayList getItemsOnCurrentPage()
   {
      ArrayList pageList = new ArrayList();

      // Populate pageList with only the items on this page.
      for (int C = startOfPage; C <= getEndOfPage(); C++)
         pageList.add(this.get(C));
      return pageList;
   }

   /**
    * Returns an ArrayList of all items in this list.
    *
    * @return   ArrayList
    */
   public ArrayList getEntireList()
   {
      return cloneAsArrayList();
   }

   /**
    * The first record of the current page.
    *
    * @return   The value of the StartOfPage property.
    */
   public int getStartOfPage()
   {
      return startOfPage;
   }

   /**
    * The last record of the current page.
    *
    * @return   The value of the EndOfPage property.
    */
   public int getEndOfPage()
   {
      int endOfPage = startOfPage + pageLength - 1;

      if (endOfPage > this.size() - 1)
         endOfPage = this.size() - 1;
      return endOfPage;
   }

   /**
    * The class type used to dynamically load beans into this list.
    *
    * @return   The value of the DataType property.
    */
   public Class getDataType()
   {
      return dataType;
   }

   /**
    * The class type used to dynamically load beans into this list.
    *
    * @param newDataType  The new DataType value.
    */
   public void setDataType(Class newDataType)
   {
      dataType = newDataType;
   }

   /**
    * The max number of records allowed in the list. Defaults to 1500.
    *
    * @param newMaxRecords  The new MaxRecords value.
    */
   public void setMaxRecords(int newMaxRecords)
   {
      maxRecords = newMaxRecords;
   }

   /**
    * The max number of records allowed in the list. Defaults to 1500.
    *
    * @return   The value of the MaxRecords property.
    */
   public int getMaxRecords()
   {
      return maxRecords;
   }

   /**
    * The number of records on a page. Defaults to 10.
    *
    * @return   The value of the PageLength property.
    */
   public int getPageLength()
   {
      return pageLength;
   }

   /**
    * The number of records on a page. Defaults to 10.
    *
    * @param newPageLength  The new PageLength value.
    */
   public void setPageLength(int newPageLength)
   {
      pageLength = newPageLength;
   }

   /**
    * Is this record number on this page?
    *
    * @param recordNum    Description of Parameter
    * @param startOfPage
    * @return             The value of the RecordOnThisPage property.
    */
   private boolean isRecordOnThisPage(int recordNum, int startOfPage)
   {
      return recordNum >= startOfPage && recordNum <= (startOfPage + pageLength - 1);
   }

   /**
    * Go to the specified record number and update the pagination
    * accordingly.
    *
    * @param newRecord
    */
   public void setCurrentRecord(int newRecord)
   {
      if (newRecord >= 0 && newRecord < size())
      {
         if (startOfPage <= newRecord && (startOfPage + pageLength - 1) >= newRecord)
         {
            // Already on the right page.  No change
            return;
         }
         else if (newRecord <= size() - 1 && newRecord >= size() - pageLength)
         {
            // Go to last page.
            int remainder = size() % pageLength;

            if (remainder == 0)
               remainder = 10;
            setStartOfPage(size() - remainder);
         }
         else if (newRecord <= pageLength - 1)
         {
            // Go to first page.
            setStartOfPage(0);
         }
         else if (newRecord >= startOfPage + pageLength)
         {
            // Keep going to next page until you find the right
            // one.
            while (! isRecordOnThisPage(newRecord, startOfPage))
            {
               startOfPage = startOfPage + pageLength;
            }
         }
         else if (newRecord < startOfPage)
         {
            // Keep going to the previous page until you find the
            // right one.
            while (! isRecordOnThisPage(newRecord, startOfPage))
            {
               startOfPage = startOfPage - pageLength;
            }
         }
      }
   }

   /**
    * The first record of the current page.
    *
    * @param newStartOfPage  The new StartOfPage value.
    */
   public void setStartOfPage(int newStartOfPage)
   {
      startOfPage = newStartOfPage;
   }

   /**
    * Moves to the first page.
    *
    * @return                        Description of Return Value
    * @exception DataLayerException
    */
   public boolean firstPage() throws DataLayerException
   {
      if (startOfPage == 0)
      {
         // Already on the first page.
         return false;
      }
      else
      {
         // Go to the first record in the list.
         setCurrentRecord(0);
         return true;
      }
   }

   /**
    * Moves to the previous page.
    *
    * @return                        Description of Return Value
    * @exception DataLayerException
    */
   public boolean lastPage() throws DataLayerException
   {
      if (startOfPage + pageLength >= size())
      {
         // Already on the last page.
         return false;
      }
      else
      {
         // Go to the last record in the list.
         if (pageLength >= size())
         {
            // Last page is the first page.
            setCurrentRecord(0);
         }
         else
         {
            // Go to the first record of the last page.
            int remainder = size() % pageLength;

            if (remainder == 0)
               remainder = 10;
            setCurrentRecord(size() - remainder);
         }

         return true;
      }
   }

   /**
    * Moves the current record pointer to the next record.
    *
    * @return                        Description of Return Value
    */
   public boolean nextPage()
   {
      if (startOfPage + pageLength >= size())
      {
         // Already on the last page.
         return false;
      }
      else
      {
         setStartOfPage(startOfPage + pageLength);
         setCurrentRecord(startOfPage);
         return true;
      }
   }

   /**
    * Moves the current record pointer to the previous record.
    *
    * @return                        Description of Return Value
    */
   public boolean previousPage()
   {
      if (startOfPage == 0)
      {
         // Already on the first page.
         return false;
      }
      else
      {
         setStartOfPage(startOfPage - pageLength);
         setCurrentRecord(startOfPage);
         return true;
      }
   }

   /**
    * Moves the current record pointer to the specified index.
    *
    * @param index
    * @return                        Description of Return Value
    * @exception DataLayerException
    */
   public boolean jump(int index) throws DataLayerException
   {
      // Is this a valid index?
      if (index - 1 >= 0)
      {
         if (index <= size())
         {
            setCurrentRecord(index - 1);
         }
         return true;
      }
      return false;
   }

   /**
    * The current page in the list.
    *
    * @return   int
    */
   public int getCurrentPage()
   {
      int pageNum = (getEndOfPage() + 1) / pageLength;
      int remainder = (getEndOfPage() + 1) % pageLength;

      if (remainder > 0)
         pageNum++;
      return pageNum;
   }

   /**
    * The last page in the list.
    *
    * @return   int
    */
   public int getPageCount()
   {
      int pageNum = size() / pageLength;
      int remainder = size() % pageLength;

      if (remainder > 0)
         pageNum++;
      return pageNum;
   }

   /**
    * Tests if the specified object is a component in this vector.
    *
    * @param elem  an object.
    * @return      <code>true</code> if and only if the specified
    * object is the same as a component in this vector, as determined
    * by the <tt>equals</tt> method; <code>false</code> otherwise.
    */
   public boolean contains(Object elem)
   {
      return super.base_contains((Object)elem);
   }

   /**
    * Searches for the first occurence of the given argument, testing
    * for equality using the <code>equals</code> method.
    *
    * @param elem  an object.
    * @return      the index of the first occurrence of the argument
    * in this vector, that is, the smallest value <tt>k</tt> such that
    * <tt>elem.equals(elementData[k])</tt> is <tt>true</tt> ; returns
    * <code>-1</code> if the object is not found.
    * @see         Object#equals(Object)
    */
   public int indexOf(Object elem)
   {
      return super.base_indexOf((Object)elem);
   }

   /**
    * Returns the index of the last occurrence of the specified object
    * in this vector.
    *
    * @param elem  the desired component.
    * @return      the index of the last occurrence of the specified
    * object in this vector, that is, the largest value <tt>k</tt>
    * such that <tt>elem.equals(elementData[k])</tt> is <tt>true</tt>
    * ; returns <code>-1</code> if the object is not found.
    */
   public int lastIndexOf(Object elem)
   {
      return super.base_lastIndexOf((Object)elem);
   }

   /**
    * Returns the element at the specified position in this Vector.
    *
    * @param index                               index of element to
    * return.
    * @return                                    Description of Return
    * Value
    * @since                                     1.2
    */
   public synchronized Object get(int index)
   {
      return (Object)(super.base_get(index));
   }

   /**
    * Replaces the element at the specified position in this Vector
    * with the specified element.
    *
    * @param index                               index of element to
    * replace.
    * @param element                             element to be stored
    * at the specified position.
    * @return                                    the element
    * previously at the specified position.
    * @since                                     1.2
    */
   public synchronized Object set(int index, Object element)
   {
      return (Object)(super.base_set(index, (Object)element));
   }

   /**
    * Appends the specified element to the end of this Vector.
    *
    * @param o  element to be appended to this Vector.
    * @return   true (as per the general contract of Collection.add).
    * @since    1.2
    */
   public synchronized boolean add(Object o)
   {
      return super.base_add((Object)o);
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
   public boolean remove(Object o)
   {
      return super.base_remove((Object)o);
   }

   /**
    * Inserts the specified element at the specified position in this
    * Vector. Shifts the element currently at that position (if any)
    * and any subsequent elements to the right (adds one to their
    * indices).
    *
    * @param index                               index at which the
    * specified element is to be inserted.
    * @param element                             element to be
    * inserted.
    * @since                                     1.2
    */
   public void add(int index, Object element)
   {
      super.base_add(index, (Object)element);
   }

   /**
    * Removes the element at the specified position in this Vector.
    * shifts any subsequent elements to the left (subtracts one from
    * their indices). Returns the element that was removed from the
    * Vector.
    *
    * @param index                               the index of the
    * element to removed.
    * @return                                    Description of Return
    * Value
    * @since                                     1.2
    */
   public Object remove(int index)
   {
      return super.base_remove(index);
   }

   /**
    * The global forward to use for to get back to the current JSP page.
    * This is used by the pagination actions.
    */
   public String getGlobalForward() {
      return globalForward;
   }
   
   /**
    * The global forward to use for to get back to the current JSP page.
    * This is used by the pagination actions.
    */
   public void setGlobalForward(String value) {
      globalForward = value;      
   }
   
   /**
    * Field definitions from mapping file.
    */
   public OrderedMap getFieldDefs() {
      return fieldDefs;
   }
   
   /**
    * Field definitions from mapping file.
    */
   public void setFieldDefs(OrderedMap value) {
      fieldDefs = value;
   }
   
   /**
    * The action used to execute a filter when in detail mode.  
    */
   public String getFilterAction() {
      return filterAction;
   }

   /**
    * The action used to execute a filter when in detail mode.  
    */
   public void setFilterAction(String value) {
      filterAction = value;
   }
   
}
