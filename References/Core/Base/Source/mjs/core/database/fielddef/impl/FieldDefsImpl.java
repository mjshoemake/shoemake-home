//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.04.01 at 11:41:32 EST
//


package mjs.core.database.fielddef.impl;



public class FieldDefsImpl
       extends mjs.core.database.fielddef.impl.FieldDefinitionListImpl
       implements mjs.core.database.fielddef.FieldDefs, com.sun.xml.bind.JAXBObject, com.sun.xml.bind.RIElement, mjs.core.database.fielddef.impl.runtime.UnmarshallableObject, mjs.core.database.fielddef.impl.runtime.ValidatableObject, mjs.core.database.fielddef.impl.runtime.XMLSerializable
{

   public final static java.lang.Class version = (mjs.core.database.fielddef.impl.JAXBVersion.class);
   private static com.sun.msv.grammar.Grammar schemaFragment;

   private final static java.lang.Class PRIMARY_INTERFACE_CLASS()
   {
      return (mjs.core.database.fielddef.FieldDefs.class);
   }

   public java.lang.String ____jaxb_ri____getNamespaceURI()
   {
      return "http://www.accenture.com/core/model/fielddef";
   }

   public java.lang.String ____jaxb_ri____getLocalName()
   {
      return "fieldDefs";
   }

   public mjs.core.database.fielddef.impl.runtime.UnmarshallingEventHandler createUnmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
   {
      return new mjs.core.database.fielddef.impl.FieldDefsImpl.Unmarshaller(context);
   }

   public void serializeBody(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      context.startElement("http://www.accenture.com/core/model/fielddef", "fieldDefs");
      super.serializeURIs(context);
      context.endNamespaceDecls();
      super.serializeAttributes(context);
      context.endAttributes();
      super.serializeBody(context);
      context.endElement();
   }

   public void serializeAttributes(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
   }

   public void serializeURIs(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
   }

   public java.lang.Class getPrimaryInterface()
   {
      return (mjs.core.database.fielddef.FieldDefs.class);
   }

   public com.sun.msv.verifier.DocumentDeclaration createRawValidator()
   {
      if (schemaFragment == null)
      {
         schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
               "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
               + "\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
               + "grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
               + "\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
               + "msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
               + "ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
               + "mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
               + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000\u001dcom.sun.msv.g"
               + "rammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.grammar.O"
               + "neOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000"
               + "\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005val"
               + "uexp\u0000psq\u0000~\u0000\u0000q\u0000~\u0000\u0010p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\nppsq\u0000~\u0000\fq\u0000~\u0000\u0010psr\u0000 "
               + "com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnam"
               + "eClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0010psr\u00002com.sun.msv.grammar.Expression$An"
               + "yStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u000f\u0001psr\u0000 com.sun.msv.gr"
               + "ammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameCla"
               + "ss\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExp"
               + "ression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001apsr\u0000#com.sun.msv.grammar.Simple"
               + "NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fname"
               + "spaceURIq\u0000~\u0000!xq\u0000~\u0000\u001ct\u00001mjs.core.database.fielddef.Fiel"
               + "dDefinitiont\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000"
               + "~\u0000\nppsq\u0000~\u0000\u0016q\u0000~\u0000\u0010psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L"
               + "\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet"
               + "\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000\"com.sun.msv.datat"
               + "ype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Bui"
               + "ltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concre"
               + "teType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl"
               + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000!L\u0000\btypeNameq\u0000~\u0000!L\u0000\nwhiteSpace"
               + "t\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http:/"
               + "/www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.x"
               + "sd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.da"
               + "tatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.g"
               + "rammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bco"
               + "m.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000!L\u0000\fname"
               + "spaceURIq\u0000~\u0000!xpq\u0000~\u00002q\u0000~\u00001sq\u0000~\u0000 t\u0000\u0004typet\u0000)http://www.w3.org/2"
               + "001/XMLSchema-instanceq\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\u0005fieldt\u0000\u0000q\u0000~\u0000\u001fsq\u0000~\u0000\nppsq\u0000"
               + "~\u0000\u0016q\u0000~\u0000\u0010pq\u0000~\u0000*q\u0000~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000\tfieldDefst\u0000,http://www.acce"
               + "nture.com/core/model/fielddefsr\u0000\"com.sun.msv.grammar.Express"
               + "ionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Express"
               + "ionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$"
               + "ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lco"
               + "m/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\b\u0001pq\u0000~\u0000\u000bq\u0000~\u0000\u0015q\u0000~\u0000\u0014q\u0000~\u0000"
               + "\tq\u0000~\u0000\u000eq\u0000~\u0000\u0012q\u0000~\u0000%q\u0000~\u0000@x"));
      }
      return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
   }

   public class Unmarshaller
          extends mjs.core.database.fielddef.impl.runtime.AbstractUnmarshallingEventHandlerImpl
   {

      public Unmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
      {
         super(context, "----");
      }

      protected Unmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context, int startState)
      {
         this(context);
         state = startState;
      }

      public java.lang.Object owner()
      {
         return mjs.core.database.fielddef.impl.FieldDefsImpl.this;
      }

      public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
             throws org.xml.sax.SAXException
      {
         int attIdx;

         outer:
         while (true)
         {
            switch (state)
            {
               case 3:
                  revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                  return;
               case 0:
                  if (("fieldDefs" == ___local) && ("http://www.accenture.com/core/model/fielddef" == ___uri))
                  {
                     context.pushAttributes(__atts, false);
                     state = 1;
                     return;
                  }
                  break;
               case 1:
                  if (("field" == ___local) && ("" == ___uri))
                  {
                     spawnHandlerFromEnterElement((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                     return;
                  }
                  spawnHandlerFromEnterElement((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                  return;
            }
            super.enterElement(___uri, ___local, ___qname, __atts);
            break;
         }
      }

      public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
             throws org.xml.sax.SAXException
      {
         int attIdx;

         outer:
         while (true)
         {
            switch (state)
            {
               case 3:
                  revertToParentFromLeaveElement(___uri, ___local, ___qname);
                  return;
               case 2:
                  if (("fieldDefs" == ___local) && ("http://www.accenture.com/core/model/fielddef" == ___uri))
                  {
                     context.popAttributes();
                     state = 3;
                     return;
                  }
                  break;
               case 1:
                  spawnHandlerFromLeaveElement((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                  return;
            }
            super.leaveElement(___uri, ___local, ___qname);
            break;
         }
      }

      public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
             throws org.xml.sax.SAXException
      {
         int attIdx;

         outer:
         while (true)
         {
            switch (state)
            {
               case 3:
                  revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                  return;
               case 1:
                  spawnHandlerFromEnterAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                  return;
            }
            super.enterAttribute(___uri, ___local, ___qname);
            break;
         }
      }

      public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
             throws org.xml.sax.SAXException
      {
         int attIdx;

         outer:
         while (true)
         {
            switch (state)
            {
               case 3:
                  revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                  return;
               case 1:
                  spawnHandlerFromLeaveAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                  return;
            }
            super.leaveAttribute(___uri, ___local, ___qname);
            break;
         }
      }

      public void handleText(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         int attIdx;

         outer:
         while (true)
         {
            try
            {
               switch (state)
               {
                  case 3:
                     revertToParentFromText(value);
                     return;
                  case 1:
                     spawnHandlerFromText((((mjs.core.database.fielddef.impl.FieldDefinitionListImpl)mjs.core.database.fielddef.impl.FieldDefsImpl.this).new Unmarshaller(context)), 2, value);
                     return;
               }
            }
            catch (java.lang.RuntimeException e)
            {
               handleUnexpectedTextException(value, e);
            }
            break;
         }
      }

   }

}
