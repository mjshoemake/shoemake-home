// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation (1.1, build R59)

package mjs.home.commands.users.client;

import com.sun.xml.rpc.client.BasicService;
import com.sun.xml.rpc.encoding.*;
import com.sun.xml.rpc.encoding.simpletype.*;
import com.sun.xml.rpc.encoding.soap.*;
import com.sun.xml.rpc.encoding.literal.*;
import com.sun.xml.rpc.soap.SOAPVersion;
import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.*;
import javax.xml.namespace.QName;

public class UserService_SerializerRegistry implements SerializerConstants {
    public UserService_SerializerRegistry() {
    }
    
    public TypeMappingRegistry getRegistry() {
        
        TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
        TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);
        TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);
        TypeMapping mapping2 = registry.getTypeMapping("");
        {
            QName type = new QName("http://www.shoemake.com/types", "getUserResponse");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUserResponse_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUserResponse.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "User");
            CombinedSerializer serializer = new mjs.home.commands.users.client.User_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.User.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "updateUser2");
            CombinedSerializer serializer = new mjs.home.commands.users.client.UpdateUser2_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.UpdateUser2.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "getUserList");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUserList_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUserList.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "addUser");
            CombinedSerializer serializer = new mjs.home.commands.users.client.AddUser_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.AddUser.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "getUser2");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUser2_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUser2.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "updateUser");
            CombinedSerializer serializer = new mjs.home.commands.users.client.UpdateUser_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.UpdateUser.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "deleteUser");
            CombinedSerializer serializer = new mjs.home.commands.users.client.DeleteUser_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.DeleteUser.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "getUser");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUser_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUser.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "getUser2Response");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUser2Response_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUser2Response.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "getUserListResponse");
            CombinedSerializer serializer = new mjs.home.commands.users.client.GetUserListResponse_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.GetUserListResponse.class, type, serializer);
        }
        {
            QName type = new QName("http://www.shoemake.com/types", "deleteUser2");
            CombinedSerializer serializer = new mjs.home.commands.users.client.DeleteUser2_LiteralSerializer(type, "", DONT_ENCODE_TYPE);
            registerSerializer(mapping2,mjs.home.commands.users.client.DeleteUser2.class, type, serializer);
        }
        return registry;
    }
    
    private static void registerSerializer(TypeMapping mapping, Class javaType, QName xmlType,
        Serializer ser) {
        mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),
            new SingletonDeserializerFactory((Deserializer)ser));
    }
    
}