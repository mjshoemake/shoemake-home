// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1, build R59)

package mjs.home.commands.users.client;

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

public class UpdateUser_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_int_1_QNAME = new QName("", "int_1");
    private static final QName ns2_int_TYPE_QNAME = SchemaConstants.QNAME_TYPE_INT;
    private CombinedSerializer ns2_myns2__int__int_Int_Serializer;
    private static final QName ns1_User_2_QNAME = new QName("", "User_2");
    private static final QName ns3_User_TYPE_QNAME = new QName("http://www.shoemake.com/types", "User");
    private CombinedSerializer ns3_myUser_LiteralSerializer;
    
    public UpdateUser_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public UpdateUser_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns2_myns2__int__int_Int_Serializer = (CombinedSerializer)registry.getSerializer("", int.class, ns2_int_TYPE_QNAME);
        ns3_myUser_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", mjs.home.commands.users.client.User.class, ns3_User_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        mjs.home.commands.users.client.UpdateUser instance = new mjs.home.commands.users.client.UpdateUser();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_int_1_QNAME)) {
                member = ns2_myns2__int__int_Int_Serializer.deserialize(ns1_int_1_QNAME, reader, context);
                if (member == null) {
                    throw new DeserializationException("literal.unexpectedNull");
                }
                instance.setInt_1(((Integer)member).intValue());
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_int_1_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        elementName = reader.getName();
        if (reader.getState() == XMLReader.START) {
            if (elementName.equals(ns1_User_2_QNAME)) {
                member = ns3_myUser_LiteralSerializer.deserialize(ns1_User_2_QNAME, reader, context);
                instance.setUser_2((mjs.home.commands.users.client.User)member);
                reader.nextElementContent();
            } else {
                throw new DeserializationException("literal.unexpectedElementName", new Object[] { ns1_User_2_QNAME, reader.getName() });
            }
        }
        else {
            throw new DeserializationException("literal.expectedElementName", reader.getName().toString());
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.client.UpdateUser instance = (mjs.home.commands.users.client.UpdateUser)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.client.UpdateUser instance = (mjs.home.commands.users.client.UpdateUser)obj;
        
        if (new Integer(instance.getInt_1()) == null) {
            throw new SerializationException("literal.unexpectedNull");
        }
        ns2_myns2__int__int_Int_Serializer.serialize(new Integer(instance.getInt_1()), ns1_int_1_QNAME, null, writer, context);
        ns3_myUser_LiteralSerializer.serialize(instance.getUser_2(), ns1_User_2_QNAME, null, writer, context);
    }
}
