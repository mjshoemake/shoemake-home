//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.04.01 at 11:41:32 EST
//


package mjs.core.database.fielddef.impl;



public class FieldImpl
       extends mjs.core.database.fielddef.impl.FieldDefinitionImpl
       implements mjs.core.database.fielddef.Field, com.sun.xml.bind.JAXBObject, com.sun.xml.bind.RIElement, mjs.core.database.fielddef.impl.runtime.UnmarshallableObject, mjs.core.database.fielddef.impl.runtime.ValidatableObject, mjs.core.database.fielddef.impl.runtime.XMLSerializable
{

   public final static java.lang.Class version = (mjs.core.database.fielddef.impl.JAXBVersion.class);
   private static com.sun.msv.grammar.Grammar schemaFragment;

   private final static java.lang.Class PRIMARY_INTERFACE_CLASS()
   {
      return (mjs.core.database.fielddef.Field.class);
   }

   public java.lang.String ____jaxb_ri____getNamespaceURI()
   {
      return "http://www.accenture.com/core/model/fielddef";
   }

   public java.lang.String ____jaxb_ri____getLocalName()
   {
      return "field";
   }

   public mjs.core.database.fielddef.impl.runtime.UnmarshallingEventHandler createUnmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
   {
      return new mjs.core.database.fielddef.impl.FieldImpl.Unmarshaller(context);
   }

   public void serializeBody(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      context.startElement("http://www.accenture.com/core/model/fielddef", "field");
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
      return (mjs.core.database.fielddef.Field.class);
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
               + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
               + "q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000"
               + "\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
               + "\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r"
               + "\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
               + "\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004na"
               + "met\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000#com.sun.msv.da"
               + "tatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun."
               + "msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv"
               + ".datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatyp"
               + "e.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/"
               + "String;L\u0000\btypeNameq\u0000~\u0000\u001dL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype"
               + "/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSche"
               + "mat\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$"
               + "Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpacePr"
               + "ocessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$Null"
               + "SetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.StringP"
               + "air\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001dL\u0000\fnamespaceURIq\u0000~\u0000\u001dxpq\u0000~\u0000!q\u0000"
               + "~\u0000 sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tloca"
               + "lNameq\u0000~\u0000\u001dL\u0000\fnamespaceURIq\u0000~\u0000\u001dxr\u0000\u001dcom.sun.msv.grammar.NameCl"
               + "ass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0006formatt\u0000\u0000sr\u00000com.sun.msv.grammar.Expressi"
               + "on$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0013\u0001psq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011q"
               + "\u0000~\u0000\u0014psq\u0000~\u0000\u0015ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
               + "\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000 t\u0000\u0007booleansr\u00005com.sun.msv.datatype.xsd.WhiteS"
               + "paceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000#q\u0000~\u0000&sq\u0000~\u0000\'q\u0000~\u00006q\u0000~\u0000 "
               + "sq\u0000~\u0000)t\u0000\tispercentq\u0000~\u0000-q\u0000~\u0000/sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011q\u0000~\u0000\u0014psq\u0000~\u0000\u0015ppsr\u0000$"
               + "com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.m"
               + "sv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000"
               + ")Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~\u0000\u001aq\u0000~\u0000 t\u0000\u0007inte"
               + "gerq\u0000~\u00008sr\u0000,com.sun.msv.datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000"
               + "\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTypeWithLexi"
               + "calConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Da"
               + "taTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFl"
               + "agL\u0000\bbaseTypeq\u0000~\u0000AL\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xs"
               + "d/ConcreteType;L\u0000\tfacetNameq\u0000~\u0000\u001dxq\u0000~\u0000\u001cppq\u0000~\u00008\u0001\u0000sr\u0000#com.sun.m"
               + "sv.datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000 t\u0000\u0007decimalq\u0000"
               + "~\u00008q\u0000~\u0000Jt\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000&sq\u0000~\u0000\'q\u0000~\u0000Cq\u0000~\u0000 sq\u0000~\u0000)t\u0000\u0006m"
               + "axlenq\u0000~\u0000-q\u0000~\u0000/sq\u0000~\u0000\u0011ppq\u0000~\u0000\u0018sq\u0000~\u0000)t\u0000\u0004nameq\u0000~\u0000-sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011"
               + "q\u0000~\u0000\u0014pq\u0000~\u0000\u0018sq\u0000~\u0000)t\u0000\bsequenceq\u0000~\u0000-q\u0000~\u0000/sq\u0000~\u0000\u0011ppq\u0000~\u0000\u0018sq\u0000~\u0000)t\u0000\u0004"
               + "typeq\u0000~\u0000-sq\u0000~\u0000\u000fppsq\u0000~\u0000\u0011q\u0000~\u0000\u0014psq\u0000~\u0000\u0015ppsr\u0000\"com.sun.msv.datatyp"
               + "e.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001aq\u0000~\u0000 t\u0000\u0005QNameq\u0000~\u00008q\u0000~\u0000&sq\u0000~\u0000"
               + "\'q\u0000~\u0000_q\u0000~\u0000 sq\u0000~\u0000)t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-"
               + "instanceq\u0000~\u0000/sq\u0000~\u0000)t\u0000\u0005fieldt\u0000,http://www.accenture.com/core/"
               + "model/fielddefsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
               + "\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedH"
               + "ash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef"
               + "\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gramm"
               + "ar/ExpressionPool;xp\u0000\u0000\u0000\u000b\u0001pq\u0000~\u0000\nq\u0000~\u0000\u000eq\u0000~\u0000\u0010q\u0000~\u0000\tq\u0000~\u0000\fq\u0000~\u0000\rq\u0000~\u0000"
               + "\u000bq\u0000~\u0000<q\u0000~\u0000Zq\u0000~\u00001q\u0000~\u0000Sx"));
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
         return mjs.core.database.fielddef.impl.FieldImpl.this;
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
               case 1:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                     return;
                  }
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                     return;
                  }
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                     return;
                  }
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                     return;
                  }
                  break;
               case 3:
                  revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                  return;
               case 0:
                  if (("field" == ___local) && ("http://www.accenture.com/core/model/fielddef" == ___uri))
                  {
                     context.pushAttributes(__atts, false);
                     state = 1;
                     return;
                  }
                  break;
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
               case 1:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                     return;
                  }
                  break;
               case 3:
                  revertToParentFromLeaveElement(___uri, ___local, ___qname);
                  return;
               case 2:
                  if (("field" == ___local) && ("http://www.accenture.com/core/model/fielddef" == ___uri))
                  {
                     context.popAttributes();
                     state = 3;
                     return;
                  }
                  break;
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
               case 1:
                  if (("format" == ___local) && ("" == ___uri))
                  {
                     spawnHandlerFromEnterAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionImpl)mjs.core.database.fielddef.impl.FieldImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                     return;
                  }
                  if (("ispercent" == ___local) && ("" == ___uri))
                  {
                     spawnHandlerFromEnterAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionImpl)mjs.core.database.fielddef.impl.FieldImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                     return;
                  }
                  if (("maxlen" == ___local) && ("" == ___uri))
                  {
                     spawnHandlerFromEnterAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionImpl)mjs.core.database.fielddef.impl.FieldImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                     return;
                  }
                  if (("name" == ___local) && ("" == ___uri))
                  {
                     spawnHandlerFromEnterAttribute((((mjs.core.database.fielddef.impl.FieldDefinitionImpl)mjs.core.database.fielddef.impl.FieldImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                     return;
                  }
                  break;
               case 3:
                  revertToParentFromEnterAttribute(___uri, ___local, ___qname);
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
               case 1:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                     return;
                  }
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     context.consumeAttribute(attIdx);
                     context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                     return;
                  }
                  break;
               case 3:
                  revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
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
                  case 1:
                     attIdx = context.getAttribute("", "format");
                     if (attIdx >= 0)
                     {
                        context.consumeAttribute(attIdx);
                        context.getCurrentHandler().text(value);
                        return;
                     }
                     attIdx = context.getAttribute("", "ispercent");
                     if (attIdx >= 0)
                     {
                        context.consumeAttribute(attIdx);
                        context.getCurrentHandler().text(value);
                        return;
                     }
                     attIdx = context.getAttribute("", "maxlen");
                     if (attIdx >= 0)
                     {
                        context.consumeAttribute(attIdx);
                        context.getCurrentHandler().text(value);
                        return;
                     }
                     attIdx = context.getAttribute("", "name");
                     if (attIdx >= 0)
                     {
                        context.consumeAttribute(attIdx);
                        context.getCurrentHandler().text(value);
                        return;
                     }
                     break;
                  case 3:
                     revertToParentFromText(value);
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
