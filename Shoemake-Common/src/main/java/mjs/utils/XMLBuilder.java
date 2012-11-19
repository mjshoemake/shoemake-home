/*******************************************************************************
 * $Id$
 *
 * CISCO CONFIDENTIAL
 * Copyright (c) 2009, 2011, Cisco Systems, Inc.
 *******************************************************************************/
package mjs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import mjs.exceptions.CoreException;
import mjs.exceptions.XmlParserException;
import mjs.utils.ConfigFileLoader;
import mjs.utils.ConfigFileLocation;

/**
 * @author MShoemake
 */
public class XMLBuilder {

    private static final Logger log = Logger.getLogger("Core");

    private static HashMap<String, XMLBuilder> builderMap = new HashMap<String, XMLBuilder>();
    private Validator _validator;

    /**
     * The ConfigFileType associated with the xsd file.
     */
    // private ConfigFileType xsdType = null;

    /**
     * Constructor.
     * 
     * @param xsdType ConfigFileType
     * @param xsdPath String
     */
    private XMLBuilder(ConfigFileLocation xsdType, String xsdPath) {

        try {
            // Create Document Validator
            SchemaFactory constraintFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            if (!ConfigFileLoader.doesFileExist(xsdType, xsdPath)) {
                throw new CoreException("Failed to load xsd " + xsdPath + ".  File not found.");
            }
            InputStream xsdIStream = ConfigFileLoader.getFileAsStream(xsdType, xsdPath);

            log.debug("Able to read XSD: " + (xsdIStream != null));

            Source constraints = new StreamSource(xsdIStream);
            Schema schema = constraintFactory.newSchema(constraints);
            _validator = schema.newValidator();
        } catch (SAXException se) {
            log.error("Unable to create a valid Schema.", se);
        } catch (Exception se) {
            log.error("Unable to create a valid Schema.", se);
        }
    }

    public static XMLBuilder getInstance(ConfigFileLocation xsdType,
                                         String xsdPath) {

        XMLBuilder builder = null;

        if (xsdType == null) {
            return null;
        }
        // Try to find an instance in the HashMap.
        builder = builderMap.get(xsdType.toString() + xsdPath);

        synchronized (XMLBuilder.class) {
            log.info("In XMLBuilder :XSD_RELATIVE_PATH");
            if (builder == null) {
                builder = new XMLBuilder(xsdType, xsdPath);
                builderMap.put(xsdType.toString() + xsdPath, builder);
            }
        }
        return builder;
    }

    /**
     * Parse the XML stream and generate a DOM document.
     * 
     * @param is InputStream
     * @return Document
     * @throws XmlParserException
     */
    public static Document parse(InputStream is) throws XmlParserException {

        Document retDoc = null;

        try {
            // Create Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();

            // Parse the input stream to a DOM tree
            retDoc = docBuilder.parse(is);
            //docBuilder.reset();
        } catch (ParserConfigurationException pce) {
            throw new XmlParserException(
                "The underlying parser does not support the requested features.", pce);
        } catch (SAXException se) {
            throw new XmlParserException(se.getMessage(), se);
        } catch (IOException ioe) {
            throw new XmlParserException(ioe.getMessage(), ioe);
        }

        return retDoc;
    }

    public synchronized void validate(Document doc) throws XmlParserException {

        try {
            // Validate the DOM tree
            // log.debug("Validating using SwitchControl XSD...");
            _validator.validate(new DOMSource(doc));
            _validator.reset();

            log.debug("Document validates fine.");
        } catch (SAXException se) {
            throw new XmlParserException(se.getMessage(), se);
        } catch (IOException ioe) {
            throw new XmlParserException(ioe.getMessage(), ioe);
        }
    }

    /**
     * Create a new XML document using the specified namespace and root element
     * name.
     * 
     * @param namespace String
     * @param rootElementName String
     * @return Document
     */
    public Document newDocument(String namespace,
                                String rootElementName) throws CoreException {
        try {
            Element rootEl = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document retDoc = docBuilder.newDocument();
            // Femto: ServerConstants.NS_FEMTO, ServerConstants.FEMTO
            // Snooper: ServerConstants.NS_SNOOP, ServerConstants.SNOOP_RESP
            rootEl = retDoc.createElementNS(namespace, rootElementName);
            rootEl.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", namespace);
            retDoc.appendChild(rootEl);

            return retDoc;
        } catch (Exception e) {
            throw new CoreException("Failed to create a new XML document. " + e.getMessage(), e);
        }
    }

}
