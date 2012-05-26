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
 * Class BaseResourceBundle.
 * 
 * @version $Revision$ $Date$
 */
public class BaseMessageResourceBundle implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _messageResourceList
     */
    private java.util.Hashtable messageTable;
    


      //----------------/
     //- Constructors -/
    //----------------/

    public BaseMessageResourceBundle() {
        super();
        messageTable = new Hashtable();
    } //-- BaseResourceBundle()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method addMessageResource
     * 
     * @param vMessageResource
     */
    public void addMessageResource(MessageResource vMessageResource)
        throws java.lang.IndexOutOfBoundsException
    {
         messageTable.put(vMessageResource.getMessageID(), vMessageResource);
    } //-- void addMessageResource(MessageResource) 

    /**
     * Method enumerateMessageResource
     */
    public java.util.Enumeration enumerateMessageResource()
    {
       return messageTable.elements();
    } //-- java.util.Enumeration enumerateMessageResource() 

    /**
     * Method getMessageResource
     * 
     * @param index
     */
    public MessageResource getMessageResource(String messageID)
        throws java.lang.IndexOutOfBoundsException
    {
       return (MessageResource)(messageTable.get(messageID));
    } //-- MessageResource getMessageResource(int) 

    /**
     * Method getMessageResource
     */
    public MessageResource[] getMessageResources()
    {
       int size = messageTable.size();
       java.util.Enumeration enum = messageTable.elements();
       MessageResource[] mArray = new MessageResource[size];
       int index=0;
       while (enum.hasMoreElements())
       {
          mArray[index++] = (MessageResource)(enum.nextElement());
       }
        return mArray;
    } //-- MessageResource[] getMessageResource() 

    /**
     * Method getMessageResourceCount
     */
    public int getMessageResourceCount()
    {
        return messageTable.size();
    } //-- int getMessageResourceCount() 

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
     * Method removeAllMessageResource
     */
    public void removeAllMessageResources()
    {
        messageTable.clear();
    } //-- void removeAllMessageResource() 

    /**
     * Method removeMessageResource
     * 
     * @param index
     */
    public MessageResource removeMessageResource(String messageID)
    {
       java.lang.Object obj = messageTable.remove(messageID);
        return (MessageResource) obj;
    } //-- MessageResource removeMessageResource(int) 

    /**
     * Method setMessageResource
     * 
     * @param messageResourceArray
     */
    public void setMessageResource(MessageResource[] messageResourceArray)
    {
        //-- copy array
        messageTable.clear();
        for (int i = 0; i < messageResourceArray.length; i++) {
            messageTable.put(messageResourceArray[i].getMessageID(), messageResourceArray[i]);
        }
    } //-- void setMessageResource(MessageResource) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (BaseMessageResourceBundle) Unmarshaller.unmarshal(BaseMessageResourceBundle.class, reader);
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
