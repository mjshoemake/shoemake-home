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
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.ContentHandler;

/**
 * Class BaseLocalization.
 * 
 * @version $Revision$ $Date$
 */
public class BaseLocalization implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _locale
     */
    private java.lang.String _locale;

    /**
     * Field _messageText
     */
    private java.lang.String _messageText;

    /**
     * Field _component
     */
    private java.lang.String _component;

    /**
     * Field _subComponent
     */
    private java.lang.String _subComponent;

    /**
     * Field _category
     */
    private java.lang.String _category;


      //----------------/
     //- Constructors -/
    //----------------/

    public BaseLocalization() {
        super();
    } //-- BaseLocalization()


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'category'.
     * 
     * @return the value of field 'category'.
     */
    public java.lang.String getCategory()
    {
        return this._category;
    } //-- java.lang.String getCategory() 

    /**
     * Returns the value of field 'component'.
     * 
     * @return the value of field 'component'.
     */
    public java.lang.String getComponent()
    {
        return this._component;
    } //-- java.lang.String getComponent() 

    /**
     * Returns the value of field 'locale'.
     * 
     * @return the value of field 'locale'.
     */
    public java.lang.String getLocale()
    {
        return this._locale;
    } //-- java.lang.String getLocale() 

    /**
     * Returns the value of field 'messageText'.
     * 
     * @return the value of field 'messageText'.
     */
    public java.lang.String getMessageText()
    {
        return this._messageText;
    } //-- java.lang.String getMessageText() 

    /**
     * Returns the value of field 'subComponent'.
     * 
     * @return the value of field 'subComponent'.
     */
    public java.lang.String getSubComponent()
    {
        return this._subComponent;
    } //-- java.lang.String getSubComponent() 

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
     * Sets the value of field 'category'.
     * 
     * @param category the value of field 'category'.
     */
    public void setCategory(java.lang.String category)
    {
        this._category = category;
    } //-- void setCategory(java.lang.String) 

    /**
     * Sets the value of field 'component'.
     * 
     * @param component the value of field 'component'.
     */
    public void setComponent(java.lang.String component)
    {
        this._component = component;
    } //-- void setComponent(java.lang.String) 

    /**
     * Sets the value of field 'locale'.
     * 
     * @param locale the value of field 'locale'.
     */
    public void setLocale(java.lang.String locale)
    {
        this._locale = locale;
    } //-- void setLocale(java.lang.String) 

    /**
     * Sets the value of field 'messageText'.
     * 
     * @param messageText the value of field 'messageText'.
     */
    public void setMessageText(java.lang.String messageText)
    {
        this._messageText = messageText;
    } //-- void setMessageText(java.lang.String) 

    /**
     * Sets the value of field 'subComponent'.
     * 
     * @param subComponent the value of field 'subComponent'.
     */
    public void setSubComponent(java.lang.String subComponent)
    {
        this._subComponent = subComponent;
    } //-- void setSubComponent(java.lang.String) 

    /**
     * Method unmarshal
     * 
     * @param reader
     */
    public static java.lang.Object unmarshal(java.io.Reader reader)
        throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException
    {
        return (BaseLocalization) Unmarshaller.unmarshal(BaseLocalization.class, reader);
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
