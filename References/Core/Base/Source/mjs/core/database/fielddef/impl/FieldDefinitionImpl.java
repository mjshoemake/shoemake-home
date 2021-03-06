//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.04.01 at 11:41:32 EST
//


package mjs.core.database.fielddef.impl;



public class FieldDefinitionImpl implements mjs.core.database.fielddef.FieldDefinition, com.sun.xml.bind.JAXBObject, mjs.core.database.fielddef.impl.runtime.UnmarshallableObject, mjs.core.database.fielddef.impl.runtime.ValidatableObject, mjs.core.database.fielddef.impl.runtime.XMLSerializable
{

   protected java.lang.String _Type;
   protected java.lang.String _Sequence;
   protected java.lang.String _Name;
   protected java.math.BigInteger _Maxlen;
   protected boolean has_Ispercent;
   protected boolean _Ispercent;
   protected java.lang.String _Format;
   public final static java.lang.Class version = (mjs.core.database.fielddef.impl.JAXBVersion.class);
   private static com.sun.msv.grammar.Grammar schemaFragment;

   private final static java.lang.Class PRIMARY_INTERFACE_CLASS()
   {
      return (mjs.core.database.fielddef.FieldDefinition.class);
   }

   public java.lang.String getType()
   {
      return _Type;
   }

   public void setType(java.lang.String value)
   {
      _Type = value;
   }

   public java.lang.String getSequence()
   {
      if (_Sequence == null)
      {
         return "";
      }
      else
      {
         return _Sequence;
      }
   }

   public void setSequence(java.lang.String value)
   {
      _Sequence = value;
   }

   public java.lang.String getName()
   {
      return _Name;
   }

   public void setName(java.lang.String value)
   {
      _Name = value;
   }

   public java.math.BigInteger getMaxlen()
   {
      if (_Maxlen == null)
      {
         return javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.DatatypeConverterImpl.installHook("4000"));
      }
      else
      {
         return _Maxlen;
      }
   }

   public void setMaxlen(java.math.BigInteger value)
   {
      _Maxlen = value;
   }

   public boolean isIspercent()
   {
      if (! has_Ispercent)
      {
         return javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.DatatypeConverterImpl.installHook("false"));
      }
      else
      {
         return _Ispercent;
      }
   }

   public void setIspercent(boolean value)
   {
      _Ispercent = value;
      has_Ispercent = true;
   }

   public java.lang.String getFormat()
   {
      if (_Format == null)
      {
         return "";
      }
      else
      {
         return _Format;
      }
   }

   public void setFormat(java.lang.String value)
   {
      _Format = value;
   }

   public mjs.core.database.fielddef.impl.runtime.UnmarshallingEventHandler createUnmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
   {
      return new mjs.core.database.fielddef.impl.FieldDefinitionImpl.Unmarshaller(context);
   }

   public void serializeBody(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
   }

   public void serializeAttributes(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
      if (_Format != null)
      {
         context.startAttribute("", "format");
         try
         {
            context.text(((java.lang.String)_Format), "Format");
         }
         catch (java.lang.Exception e)
         {
            mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
         }
         context.endAttribute();
      }
      if (has_Ispercent)
      {
         context.startAttribute("", "ispercent");
         try
         {
            context.text(javax.xml.bind.DatatypeConverter.printBoolean(((boolean)_Ispercent)), "Ispercent");
         }
         catch (java.lang.Exception e)
         {
            mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
         }
         context.endAttribute();
      }
      if (_Maxlen != null)
      {
         context.startAttribute("", "maxlen");
         try
         {
            context.text(javax.xml.bind.DatatypeConverter.printInteger(((java.math.BigInteger)_Maxlen)), "Maxlen");
         }
         catch (java.lang.Exception e)
         {
            mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
         }
         context.endAttribute();
      }
      context.startAttribute("", "name");
      try
      {
         context.text(((java.lang.String)_Name), "Name");
      }
      catch (java.lang.Exception e)
      {
         mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
      }
      context.endAttribute();
      if (_Sequence != null)
      {
         context.startAttribute("", "sequence");
         try
         {
            context.text(((java.lang.String)_Sequence), "Sequence");
         }
         catch (java.lang.Exception e)
         {
            mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
         }
         context.endAttribute();
      }
      context.startAttribute("", "type");
      try
      {
         context.text(((java.lang.String)_Type), "Type");
      }
      catch (java.lang.Exception e)
      {
         mjs.core.database.fielddef.impl.runtime.Util.handlePrintConversionException(this, e, context);
      }
      context.endAttribute();
   }

   public void serializeURIs(mjs.core.database.fielddef.impl.runtime.XMLSerializer context)
          throws org.xml.sax.SAXException
   {
   }

   public java.lang.Class getPrimaryInterface()
   {
      return (mjs.core.database.fielddef.FieldDefinition.class);
   }

   public com.sun.msv.verifier.DocumentDeclaration createRawValidator()
   {
      if (schemaFragment == null)
      {
         schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
               "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
               + "n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
               + "mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
               + "on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
               + "expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom."
               + "sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv."
               + "grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLco"
               + "m/sun/msv/grammar/NameClass;xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5"
               + "\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003"
               + "L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004name"
               + "t\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003ppsr\u0000#com.sun.msv.data"
               + "type.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*com.sun.ms"
               + "v.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.d"
               + "atatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype."
               + "xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/St"
               + "ring;L\u0000\btypeNameq\u0000~\u0000\u0019L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/x"
               + "sd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchema"
               + "t\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Pr"
               + "eserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProc"
               + "essor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expression$NullSe"
               + "tExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003ppsr\u0000\u001bcom.sun.msv.util.StringPai"
               + "r\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0019L\u0000\fnamespaceURIq\u0000~\u0000\u0019xpq\u0000~\u0000\u001dq\u0000~\u0000"
               + "\u001csr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalN"
               + "ameq\u0000~\u0000\u0019L\u0000\fnamespaceURIq\u0000~\u0000\u0019xr\u0000\u001dcom.sun.msv.grammar.NameClas"
               + "s\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0006formatt\u0000\u0000sr\u00000com.sun.msv.grammar.Expression"
               + "$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u000f\u0001psq\u0000~\u0000\nppsq\u0000~\u0000\fq\u0000~"
               + "\u0000\u0010psq\u0000~\u0000\u0011ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
               + "\u0000\u0000xq\u0000~\u0000\u0016q\u0000~\u0000\u001ct\u0000\u0007booleansr\u00005com.sun.msv.datatype.xsd.WhiteSpa"
               + "ceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u001fq\u0000~\u0000\"sq\u0000~\u0000#q\u0000~\u00002q\u0000~\u0000\u001csq"
               + "\u0000~\u0000%t\u0000\tispercentq\u0000~\u0000)q\u0000~\u0000+sq\u0000~\u0000\nppsq\u0000~\u0000\fq\u0000~\u0000\u0010psq\u0000~\u0000\u0011ppsr\u0000$co"
               + "m.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000+com.sun.msv"
               + ".datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001L\u0000\nbaseFacetst\u0000)L"
               + "com/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~\u0000\u0016q\u0000~\u0000\u001ct\u0000\u0007intege"
               + "rq\u0000~\u00004sr\u0000,com.sun.msv.datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000"
               + "\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.sun.msv.datatype.xsd.DataTypeWithLexica"
               + "lConstraintFacetT\u0090\u001c>\u001azb\u00ea\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Data"
               + "TypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlag"
               + "L\u0000\bbaseTypeq\u0000~\u0000=L\u0000\fconcreteTypet\u0000\'Lcom/sun/msv/datatype/xsd/"
               + "ConcreteType;L\u0000\tfacetNameq\u0000~\u0000\u0019xq\u0000~\u0000\u0018ppq\u0000~\u00004\u0001\u0000sr\u0000#com.sun.msv"
               + ".datatype.xsd.NumberType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0016q\u0000~\u0000\u001ct\u0000\u0007decimalq\u0000~\u0000"
               + "4q\u0000~\u0000Ft\u0000\u000efractionDigits\u0000\u0000\u0000\u0000q\u0000~\u0000\"sq\u0000~\u0000#q\u0000~\u0000?q\u0000~\u0000\u001csq\u0000~\u0000%t\u0000\u0006max"
               + "lenq\u0000~\u0000)q\u0000~\u0000+sq\u0000~\u0000\fppq\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\u0004nameq\u0000~\u0000)sq\u0000~\u0000\nppsq\u0000~\u0000\fq\u0000"
               + "~\u0000\u0010pq\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\bsequenceq\u0000~\u0000)q\u0000~\u0000+sq\u0000~\u0000\fppq\u0000~\u0000\u0014sq\u0000~\u0000%t\u0000\u0004ty"
               + "peq\u0000~\u0000)sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\be"
               + "xpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xps"
               + "r\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I"
               + "\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Expr"
               + "essionPool;xp\u0000\u0000\u0000\t\u0001pq\u0000~\u0000\u0005q\u0000~\u0000\tq\u0000~\u0000\u000bq\u0000~\u0000\u0007q\u0000~\u0000\bq\u0000~\u0000\u0006q\u0000~\u00008q\u0000~\u0000-q"
               + "\u0000~\u0000Ox"));
      }
      return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
   }

   public class Unmarshaller
          extends mjs.core.database.fielddef.impl.runtime.AbstractUnmarshallingEventHandlerImpl
   {

      public Unmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context)
      {
         super(context, "-------------------");
      }

      protected Unmarshaller(mjs.core.database.fielddef.impl.runtime.UnmarshallingContext context, int startState)
      {
         this(context);
         state = startState;
      }

      public java.lang.Object owner()
      {
         return mjs.core.database.fielddef.impl.FieldDefinitionImpl.this;
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
               case 12:
                  attIdx = context.getAttribute("", "sequence");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText1(v);
                     state = 15;
                     continue outer;
                  }
                  state = 15;
                  continue outer;
               case 9:
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText2(v);
                     state = 12;
                     continue outer;
                  }
                  break;
               case 3:
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText3(v);
                     state = 6;
                     continue outer;
                  }
                  state = 6;
                  continue outer;
               case 0:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText4(v);
                     state = 3;
                     continue outer;
                  }
                  state = 3;
                  continue outer;
               case 6:
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText5(v);
                     state = 9;
                     continue outer;
                  }
                  state = 9;
                  continue outer;
               case 15:
                  attIdx = context.getAttribute("", "type");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText6(v);
                     state = 18;
                     continue outer;
                  }
                  break;
               case 18:
                  revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                  return;
            }
            super.enterElement(___uri, ___local, ___qname, __atts);
            break;
         }
      }

      private void eatText1(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Sequence = value;
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
         }
      }

      private void eatText2(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Name = value;
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
         }
      }

      private void eatText3(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Ispercent = javax.xml.bind.DatatypeConverter.parseBoolean(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            has_Ispercent = true;
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
         }
      }

      private void eatText4(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Format = value;
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
         }
      }

      private void eatText5(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Maxlen = javax.xml.bind.DatatypeConverter.parseInteger(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
         }
      }

      private void eatText6(final java.lang.String value)
             throws org.xml.sax.SAXException
      {
         try
         {
            _Type = value;
         }
         catch (java.lang.Exception e)
         {
            handleParseConversionException(e);
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
               case 12:
                  attIdx = context.getAttribute("", "sequence");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText1(v);
                     state = 15;
                     continue outer;
                  }
                  state = 15;
                  continue outer;
               case 9:
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText2(v);
                     state = 12;
                     continue outer;
                  }
                  break;
               case 3:
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText3(v);
                     state = 6;
                     continue outer;
                  }
                  state = 6;
                  continue outer;
               case 0:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText4(v);
                     state = 3;
                     continue outer;
                  }
                  state = 3;
                  continue outer;
               case 6:
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText5(v);
                     state = 9;
                     continue outer;
                  }
                  state = 9;
                  continue outer;
               case 15:
                  attIdx = context.getAttribute("", "type");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText6(v);
                     state = 18;
                     continue outer;
                  }
                  break;
               case 18:
                  revertToParentFromLeaveElement(___uri, ___local, ___qname);
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
               case 12:
                  if (("sequence" == ___local) && ("" == ___uri))
                  {
                     state = 13;
                     return;
                  }
                  state = 15;
                  continue outer;
               case 9:
                  if (("name" == ___local) && ("" == ___uri))
                  {
                     state = 10;
                     return;
                  }
                  break;
               case 3:
                  if (("ispercent" == ___local) && ("" == ___uri))
                  {
                     state = 4;
                     return;
                  }
                  state = 6;
                  continue outer;
               case 0:
                  if (("format" == ___local) && ("" == ___uri))
                  {
                     state = 1;
                     return;
                  }
                  state = 3;
                  continue outer;
               case 6:
                  if (("maxlen" == ___local) && ("" == ___uri))
                  {
                     state = 7;
                     return;
                  }
                  state = 9;
                  continue outer;
               case 15:
                  if (("type" == ___local) && ("" == ___uri))
                  {
                     state = 16;
                     return;
                  }
                  break;
               case 18:
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
               case 12:
                  attIdx = context.getAttribute("", "sequence");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText1(v);
                     state = 15;
                     continue outer;
                  }
                  state = 15;
                  continue outer;
               case 14:
                  if (("sequence" == ___local) && ("" == ___uri))
                  {
                     state = 15;
                     return;
                  }
                  break;
               case 9:
                  attIdx = context.getAttribute("", "name");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText2(v);
                     state = 12;
                     continue outer;
                  }
                  break;
               case 11:
                  if (("name" == ___local) && ("" == ___uri))
                  {
                     state = 12;
                     return;
                  }
                  break;
               case 3:
                  attIdx = context.getAttribute("", "ispercent");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText3(v);
                     state = 6;
                     continue outer;
                  }
                  state = 6;
                  continue outer;
               case 8:
                  if (("maxlen" == ___local) && ("" == ___uri))
                  {
                     state = 9;
                     return;
                  }
                  break;
               case 0:
                  attIdx = context.getAttribute("", "format");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText4(v);
                     state = 3;
                     continue outer;
                  }
                  state = 3;
                  continue outer;
               case 6:
                  attIdx = context.getAttribute("", "maxlen");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText5(v);
                     state = 9;
                     continue outer;
                  }
                  state = 9;
                  continue outer;
               case 15:
                  attIdx = context.getAttribute("", "type");
                  if (attIdx >= 0)
                  {
                     final java.lang.String v = context.eatAttribute(attIdx);

                     eatText6(v);
                     state = 18;
                     continue outer;
                  }
                  break;
               case 17:
                  if (("type" == ___local) && ("" == ___uri))
                  {
                     state = 18;
                     return;
                  }
                  break;
               case 2:
                  if (("format" == ___local) && ("" == ___uri))
                  {
                     state = 3;
                     return;
                  }
                  break;
               case 18:
                  revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                  return;
               case 5:
                  if (("ispercent" == ___local) && ("" == ___uri))
                  {
                     state = 6;
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
                  case 12:
                     attIdx = context.getAttribute("", "sequence");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText1(v);
                        state = 15;
                        continue outer;
                     }
                     state = 15;
                     continue outer;
                  case 4:
                     eatText3(value);
                     state = 5;
                     return;
                  case 9:
                     attIdx = context.getAttribute("", "name");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText2(v);
                        state = 12;
                        continue outer;
                     }
                     break;
                  case 1:
                     eatText4(value);
                     state = 2;
                     return;
                  case 3:
                     attIdx = context.getAttribute("", "ispercent");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText3(v);
                        state = 6;
                        continue outer;
                     }
                     state = 6;
                     continue outer;
                  case 0:
                     attIdx = context.getAttribute("", "format");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText4(v);
                        state = 3;
                        continue outer;
                     }
                     state = 3;
                     continue outer;
                  case 7:
                     eatText5(value);
                     state = 8;
                     return;
                  case 13:
                     eatText1(value);
                     state = 14;
                     return;
                  case 6:
                     attIdx = context.getAttribute("", "maxlen");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText5(v);
                        state = 9;
                        continue outer;
                     }
                     state = 9;
                     continue outer;
                  case 16:
                     eatText6(value);
                     state = 17;
                     return;
                  case 15:
                     attIdx = context.getAttribute("", "type");
                     if (attIdx >= 0)
                     {
                        final java.lang.String v = context.eatAttribute(attIdx);

                        eatText6(v);
                        state = 18;
                        continue outer;
                     }
                     break;
                  case 18:
                     revertToParentFromText(value);
                     return;
                  case 10:
                     eatText2(value);
                     state = 11;
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
