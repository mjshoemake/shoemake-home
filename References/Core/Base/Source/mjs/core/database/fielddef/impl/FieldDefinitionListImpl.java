//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.04.01 at 11:41:32 EST
//


package mjs.core.database.fielddef.impl;



public class FieldDefinitionListImpl implements mjs.core.database.fielddef.FieldDefinitionList, com.sun.xml.bind.JAXBObject, mjs.core.database.fielddef.impl.runtime.UnmarshallableObject, mjs.core.database.fielddef.impl.runtime.ValidatableObject, mjs.core.database.fielddef.impl.runtime.XMLSerializable
{

   protected com.sun.xml.bind.util.ListImpl _Field;
   public final static java.lang.Class version = (mjs.core.database.fielddef.impl.JAXBVersion.class);
   private static com.sun.msv.grammar.Grammar schemaFragment;

   private final static java.lang.Class PRIMARY_INTERFACE_CLASS()
   {
      return (mjs.core.database.fielddef.FieldDefinitionList.class);
   }

   protected com.sun.xml.bind.util.ListImpl _getField()
   {
      if (_Field == null)
      {
         _Field = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
      }
      return _Field;
   }

   public java.util.List getField()
   {
      return _getField();
   }

   public mjs.core.database.fielddef.impl.runtime.UnmarshallingEventHandler createUnmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
   {
      return new mjs.core.database.fielddef.impl.FieldDefinitionListImpl.Unmarshaller(context);
   }

   public void serializeBody(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      int idx1 = 0;
      final int len1 = ((_Field == null) ? 0 : _Field.size());

      while (idx1 != len1)
      {
         context.startElement("", "field");

         int idx_0 = idx1;

         context.childAsURIs(((com.sun.xml.bind.JAXBObject)_Field.get(idx_0++)), "Field");
         context.endNamespaceDecls();

         int idx_1 = idx1;

         context.childAsAttributes(((com.sun.xml.bind.JAXBObject)_Field.get(idx_1++)), "Field");
         context.endAttributes();
         context.childAsBody(((com.sun.xml.bind.JAXBObject)_Field.get(idx1++)), "Field");
         context.endElement();
      }
   }

   public void serializeAttributes(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      int idx1 = 0;
      final int len1 = ((_Field == null) ? 0 : _Field.size());

      while (idx1 != len1)
      {
         idx1 += 1;
      }
   }

   public void serializeURIs(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      int idx1 = 0;
      final int len1 = ((_Field == null) ? 0 : _Field.size());

      while (idx1 != len1)
      {
         idx1 += 1;
      }
   }

   public java.lang.Class getPrimaryInterface()
   {
      return (mjs.core.database.fielddef.FieldDefinitionList.class);
   }

   public com.sun.msv.verifier.DocumentDeclaration createRawValidator()
   {
      if (schemaFragment == null)
      {
         schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
               "\u00ac\u00ed\u0000\u0005sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
               + "msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gramm"
               + "ar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expression"
               + "\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bex"
               + "pandedExpq\u0000~\u0000\u0002xpppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000"
               + "\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002x"
               + "q\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\'com.sun"
               + ".msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLc"
               + "om/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.grammar.Element"
               + "Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcontentModelq\u0000"
               + "~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\np\u0000sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
               + "\u0000\u0000xq\u0000~\u0000\u0001ppsq\u0000~\u0000\u000bpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0006q\u0000~\u0000\npsr\u0000 com.sun.msv.gramm"
               + "ar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\fxq\u0000~\u0000\u0003"
               + "q\u0000~\u0000\npsr\u00002com.sun.msv.grammar.Expression$AnyStringExpression"
               + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\t\u0001psr\u0000 com.sun.msv.grammar.AnyNameClas"
               + "s\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr"
               + "\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
               + "\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0018psr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
               + "\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001fxq\u0000~"
               + "\u0000\u001at\u00001mjs.core.database.fielddef.FieldDefinitiont\u0000+htt"
               + "p://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0014q\u0000~\u0000\np"
               + "sr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relax"
               + "ng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/ut"
               + "il/StringPair;xq\u0000~\u0000\u0003ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType"
               + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000"
               + "\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
               + "xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnam"
               + "espaceUriq\u0000~\u0000\u001fL\u0000\btypeNameq\u0000~\u0000\u001fL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/d"
               + "atatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/"
               + "XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProc"
               + "essor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteS"
               + "paceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression"
               + "$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.St"
               + "ringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001fL\u0000\fnamespaceURIq\u0000~\u0000\u001fxpq\u0000"
               + "~\u00000q\u0000~\u0000/sq\u0000~\u0000\u001et\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-ins"
               + "tanceq\u0000~\u0000\u001dsq\u0000~\u0000\u001et\u0000\u0005fieldt\u0000\u0000q\u0000~\u0000\u001dsr\u0000\"com.sun.msv.grammar.Expr"
               + "essionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expr"
               + "essionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPo"
               + "ol$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$"
               + "Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\u0006\u0001pq\u0000~\u0000\u0005q\u0000~\u0000\u0013q\u0000~\u0000\u0012q"
               + "\u0000~\u0000\bq\u0000~\u0000\u0010q\u0000~\u0000#x"));
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
         return mjs.core.database.fielddef.impl.FieldDefinitionListImpl.this;
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
                  if (("field" == ___local) && ("" == ___uri))
                  {
                     context.pushAttributes(__atts, false);
                     state = 1;
                     return;
                  }
                  revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                  return;
               case 0:
                  if (("field" == ___local) && ("" == ___uri))
                  {
                     context.pushAttributes(__atts, false);
                     state = 1;
                     return;
                  }
                  state = 3;
                  continue outer;
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
               case 2:
                  if (("field" == ___local) && ("" == ___uri))
                  {
                     context.popAttributes();
                     state = 3;
                     return;
                  }
                  break;
               case 3:
                  revertToParentFromLeaveElement(___uri, ___local, ___qname);
                  return;
               case 0:
                  state = 3;
                  continue outer;
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
               case 0:
                  state = 3;
                  continue outer;
               case 1:
                  if (("format" == ___local) && ("" == ___uri))
                  {
                     _getField().add(((mjs.core.database.fielddef.impl.FieldDefinitionImpl)spawnChildFromEnterAttribute((mjs.core.database.fielddef.impl.FieldDefinitionImpl.class), 2, ___uri, ___local, ___qname)));
                     return;
                  }
                  if (("ispercent" == ___local) && ("" == ___uri))
                  {
                     _getField().add(((mjs.core.database.fielddef.impl.FieldDefinitionImpl)spawnChildFromEnterAttribute((mjs.core.database.fielddef.impl.FieldDefinitionImpl.class), 2, ___uri, ___local, ___qname)));
                     return;
                  }
                  if (("maxlen" == ___local) && ("" == ___uri))
                  {
                     _getField().add(((mjs.core.database.fielddef.impl.FieldDefinitionImpl)spawnChildFromEnterAttribute((mjs.core.database.fielddef.impl.FieldDefinitionImpl.class), 2, ___uri, ___local, ___qname)));
                     return;
                  }
                  if (("name" == ___local) && ("" == ___uri))
                  {
                     _getField().add(((mjs.core.database.fielddef.impl.FieldDefinitionImpl)spawnChildFromEnterAttribute((mjs.core.database.fielddef.impl.FieldDefinitionImpl.class), 2, ___uri, ___local, ___qname)));
                     return;
                  }
                  break;
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
               case 0:
                  state = 3;
                  continue outer;
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
                  case 0:
                     state = 3;
                     continue outer;
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