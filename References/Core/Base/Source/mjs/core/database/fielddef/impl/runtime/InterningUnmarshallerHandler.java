//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2005.04.01 at 11:41:32 EST
//

package mjs.core.database.fielddef.impl.runtime;

import javax.xml.bind.JAXBException;
import javax.xml.bind.ValidationEvent;
import com.sun.xml.bind.unmarshaller.InterningXMLReader;
import org.xml.sax.SAXException;


/**
 * Filter {@link SAXUnmarshallerHandler} that interns all the Strings
 * in the SAX events.
 *
 * @author   Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
final class InterningUnmarshallerHandler extends InterningXMLReader implements SAXUnmarshallerHandler
{

   private final SAXUnmarshallerHandler core;

   InterningUnmarshallerHandler(SAXUnmarshallerHandler core)
   {
      super();
      setContentHandler(core);
      this.core = core;
   }

   public void handleEvent(ValidationEvent event, boolean canRecover) throws SAXException
   {
      core.handleEvent(event, canRecover);
   }

   public Object getResult() throws IllegalStateException, JAXBException
   {
      return core.getResult();
   }

}
