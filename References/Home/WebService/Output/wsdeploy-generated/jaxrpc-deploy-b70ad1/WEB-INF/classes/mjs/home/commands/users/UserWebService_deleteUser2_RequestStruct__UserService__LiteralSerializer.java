// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1, build R59)

package mjs.home.commands.users;

import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.xsd.XSDConstants;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.SOAPConstants;
import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
import com.sun.xml.rpc.streaming.*;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;

public class UserWebService_deleteUser2_RequestStruct__UserService__LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_String_1_QNAME = new QName("", "String_1");
    private static final QName ns2_string_TYPE_QNAME = SchemaConstants.QNAME_TYPE_STRING;
    private CombinedSerializer ns2_myns2_string__java_lang_String_String_Serializer;
    
    public UserWebService_deleteUser2_RequestStruct__UserService__LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public UserWebService_deleteUser2_RequestStruct__UserService__LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myns2_string__java_lang_String_String_Serializer = (CombinedSerializer)registry.getSerializer("", java.lang.String.class, ns2_string_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct instance = new mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_String_1_QNAME)) {
                member = ns2_myns2_string__java_lang_String_String_Serializer.deserialize(ns1_String_1_QNAME, reader, context);
                instance.setString_1((java.lang.String)member);
                reader.nextElementContent();
            }
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct instance = (mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct instance = (mjs.home.commands.users.UserWebService_deleteUser2_RequestStruct)obj;
        
        ns2_myns2_string__java_lang_String_String_Serializer.serialize(instance.getString_1(), ns1_String_1_QNAME, null, writer, context);
    }
}
