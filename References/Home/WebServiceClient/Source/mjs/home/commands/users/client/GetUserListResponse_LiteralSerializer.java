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

public class GetUserListResponse_LiteralSerializer extends LiteralObjectSerializerBase implements Initializable  {
    private static final QName ns1_result_QNAME = new QName("", "result");
    private static final QName ns3_User_TYPE_QNAME = new QName("http://www.shoemake.com/types", "User");
    private CombinedSerializer ns3_myUser_LiteralSerializer;
    
    public GetUserListResponse_LiteralSerializer(QName type, String encodingStyle) {
        this(type, encodingStyle, false);
    }
    
    public GetUserListResponse_LiteralSerializer(QName type, String encodingStyle, boolean encodeType) {
        super(type, true, encodingStyle, encodeType);
    }
    
    public void initialize(InternalTypeMappingRegistry registry) throws Exception {
        ns3_myUser_LiteralSerializer = (CombinedSerializer)registry.getSerializer("", mjs.home.commands.users.client.User.class, ns3_User_TYPE_QNAME);
    }
    
    public Object doDeserialize(XMLReader reader,
        SOAPDeserializationContext context) throws Exception {
        mjs.home.commands.users.client.GetUserListResponse instance = new mjs.home.commands.users.client.GetUserListResponse();
        Object member=null;
        QName elementName;
        List values;
        Object value;
        
        reader.nextElementContent();
        elementName = reader.getName();
        if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_result_QNAME))) {
            values = new ArrayList();
            for(;;) {
                elementName = reader.getName();
                if ((reader.getState() == XMLReader.START) && (elementName.equals(ns1_result_QNAME))) {
                    value = ns3_myUser_LiteralSerializer.deserialize(ns1_result_QNAME, reader, context);
                    values.add(value);
                    reader.nextElementContent();
                } else {
                    break;
                }
            }
            member = new mjs.home.commands.users.client.User[values.size()];
            member = values.toArray((Object[]) member);
            instance.setResult((mjs.home.commands.users.client.User[])member);
        }
        else {
            instance.setResult(new mjs.home.commands.users.client.User[0]);
        }
        
        XMLReaderUtil.verifyReaderState(reader, XMLReader.END);
        return (Object)instance;
    }
    
    public void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.client.GetUserListResponse instance = (mjs.home.commands.users.client.GetUserListResponse)obj;
        
    }
    public void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
        mjs.home.commands.users.client.GetUserListResponse instance = (mjs.home.commands.users.client.GetUserListResponse)obj;
        
        if (instance.getResult() != null) {
            for (int i = 0; i < instance.getResult().length; ++i) {
                ns3_myUser_LiteralSerializer.serialize(instance.getResult()[i], ns1_result_QNAME, null, writer, context);
            }
        }
    }
}