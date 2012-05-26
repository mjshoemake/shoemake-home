/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 0.9.5.3</a>, using an XML
 * Schema.
 * $Id$
 */
package mjs.core.xml.resourceBundle;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BaseMessageResource.
 * 
 * @version $Revision$ $Date$
 */
public class BaseMessageResource implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _messageID
     */
    private java.lang.String _messageID;

    /**
     * Field _localizationList
     */
    private Hashtable localizationTable;


      //----------------/
     //- Constructors -/
    //----------------/

    public BaseMessageResource() {
        super();
        localizationTable = new Hashtable();
        
    } //-- BaseMessageResource()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addLocalization
     * 
     * @param vLocalization
     */
    public void addLocalization(Localization vLocalization)
        throws java.lang.IndexOutOfBoundsException
    {
       localizationTable.put(vLocalization.getLocale(), vLocalization);
    } //-- void addLocalization(Localization) 

    /**
     * Method enumerateLocalization
     */
    public java.util.Enumeration enumerateLocalization()
    {
        return localizationTable.elements();
    } //-- java.util.Enumeration enumerateLocalization() 

    /**
     * Method getLocalization
     */
    public Localization[] getLocalization()
    {
       int size = localizationTable.size();
       java.util.Enumeration enum = localizationTable.elements();
       Localization[] mArray = new Localization[size];
       int index=0;
       while (enum.hasMoreElements())
       {
          mArray[index++] = (Localization)(enum.nextElement());
       }
       return mArray;
    } //-- Localization[] getLocalization() 

   /**
    * Method getLocalization
    */
   public Localization getLocalization(String locale)
   {
      Localization value = (Localization)(localizationTable.get(locale));
      if (null == null)
      {
         // Use the default locale (en_US).
         value = (Localization)(localizationTable.get("en_US"));
      }
      return value;
   } //-- Localization[] getLocalization() 

    /**
     * Method getLocalizationCount
     */
    public int getLocalizationCount()
    {
        return localizationTable.size();
    } //-- int getLocalizationCount() 

    /**
     * Returns the value of field 'messageID'.
     * 
     * @return the value of field 'messageID'.
     */
    public java.lang.String getMessageID()
    {
        return this._messageID;
    } //-- java.lang.String getMessageID() 

    /**
     * Method isValid
     */
    public boolean isValid()
    {
        try {
            validate();
        }
        catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    } //-- boolean isValid() 

    /**
     * Method marshal
     * 
     * @param out
     */
    public void marshal(java.io.Writer out)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, out);
    } //-- void marshal(java.io.Writer) 

    /**
     * Method marshal
     * 
     * @param handler
     */
    public void marshal(org.xml.sax.ContentHandler handler)
        throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        
        Marshaller.marshal(this, handler);
    } //-- void marshal(org.xml.sax.ContentHandler) 

    /**
     * Method removeAllLocalization
     */
    public void removeAllLocalization()
    {
        localizationTable.clear();
    } //-- void removeAllLocalization() 

    /**
     * Method removeLocalization
     * 
     * @param index
     */
    public Localization removeLocalization(String locale)
    {
       return (Localization)(localizationTable.remove(locale));
    } //-- Localization removeLocalization(int) 

    /**
     * Method setLocalization
     * 
     * @param localizationArray
     */
    public void setLocalization(Localization[] localizationArray)
    {
        //-- copy array
        localizationTable.clear();
        for (int i = 0; i < localizationArray.length; i++) 
        {
           localizationTable.put(localizationArray[i].getLocale(), localizationArray[i]);
        }
    } //-- void setLocalization(Localization) 

    /**
     * Sets the value of field 'messageID'.
     * 
     * @param messageID the value of field 'messageID'.
     */
    public void setMessageID(java.lang.String messageID)
    {
        this._messageID = messageID;
    } //-- void setMessageID(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (BaseMessageResource) Unmarshaller.unmarshal(BaseMessageResource.class, reader);
    } //-- java.lang.Object unmarshal(java.io.Reader) 

    /**
     * Method validate
     */
    public void validate()
        throws org.exolab.castor.xml.ValidationException
    {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    } //-- void validate() 

}
