//
// file: WSTextArea.java
// proj: eQuality 6.3 and up
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/TextArea.java-arc  $
// $Author:Helen Faynzilberg$
// $Revision:5$
//
// Copyright (c) 2001 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components;

// Java imports
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.event.*;
// Witness imports
import mjs.core.utils.StringUtils;
import mjs.core.administration.SystemDefaults;
import mjs.core.components.models.DefaultTextAreaDocument;


/**
 * WSTextArea:  This is the base class for Witness Systems memo-field
 * components.  It extends a WSScrollPane, so all scroll functionality is imbedded in the component. <p>
 * Several methods were added to the JComponent functionality (setLeft, setTop,
 * etc.) to make the interface more consistent (JComponent had a getWidth but no setWidth, etc.). <p>
 * Several empty event methods have been made public to allow the developer to
 * perform some action when the event occurs without having to add their own
 * listeners to the component.  To handle the events simply override the event
 * handler methods and implement the desired functionality.  Not all of the events
 * are triggered by listeners.  Some, like OnDelete, are called when the delete() method is called. <code>
 * <p>   public void event_OnDelete() <p>   public void event_OnSelectionChanged (ListSelectionEvent e)
 * <p>   public void event_OnMouseClicked (MouseEvent e) <p>   public void event_OnMousePressed (MouseEvent e)
 * <p>   public void event_OnMouseReleased (MouseEvent e) <p>   public void event_OnMouseEntered (MouseEvent e)
 * <p>   public void event_OnMouseExited (MouseEvent e)
 * <p>   public void event_BeforeShowPopupMenu (Vector vctMenuItemList) </code>
 */
public class TextArea extends ScrollPane implements Component
{
   /**
    * The actual JList component (this class wraps the JList rather than extending it so it can extend from the JScrollPane).
    */
   private JTextArea textbox = null;

   /**
    * A string representation of the class title.
    */
   private String sClassTitle = "TextArea";

   /**
    * The default or saved text.
    */
   private String sDefaultText = "";

   /**
    * The index of this component in the create order.
    */
   private int nCreateOrder = 0;

   /**
    * This is the background set using the setBackground() method.  If this is null then
    * the background will depend on whether it's read only or not.
    */
   private Color updatedBackground = null;

   /**
    * Allow the user to use the tab key inside the text area.  If tabs are
    * not allowed, the tab key will take the user to the next component.
    */
   private boolean bAllowTabs = true;

   /**
    * The maximum number of characters allowed.
    */
   private int nMaxChars = -1;


   /**
    * Constructor.
    */
   public TextArea()
   {
      super();
      // Inialize component.
      taBaseInit();
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    */
   public TextArea(long pObjectId)
   {
      super(pObjectId);
      // Inialize component.
      taBaseInit();
   }

   /**
    * Constructor.
    * @param   maxLength   The maximum number of bytes for the text data.
    */
   public TextArea(int maxLength)
   {
      super();
      nMaxChars = maxLength;
      // Inialize component.
      taBaseInit();
   }

   /**
    * Constructor.
    * @param   pButtonId   The unique, numeric ID used to distinguish the component from other components.
    * @param   maxLength   The maximum number of bytes for the text data.
    */
   public TextArea(long pObjectId, int maxLength)
   {
      super(pObjectId);
      nMaxChars = maxLength;
      // Inialize component.
      taBaseInit();
   }


   /**
    * Initialize the component.
    */
   private void taBaseInit()
   {
      // Set the max text length.
      if (nMaxChars > -1)
      {
         // Create the document.
         DefaultTextAreaDocument document = new DefaultTextAreaDocument(nMaxChars);
         // Create the text area.
         textbox = new JTextArea(document);
      }
      else
      {
         // Create the text area.
         textbox = new JTextArea();
      }

      setSize(100, 100);
      setWordWrapEnabled(true);
      setTabsAllowed(true);
      setReadOnly(false);
      getViewport().add(textbox);
      //Add Foreground and Background
      setFont(SystemDefaults.getDefaultFont());
      // Background set in setReadOnly() method.
      setForeground(SystemDefaults.getDefaultForeground());
   }

   /**
    * Set whether or not this component is Read Only to the user.
    */
   public void setReadOnly(boolean value)
   {
      setEditable(! value);
   }

   /**
    * Is this component Read Only to the user?
    */
   public boolean isReadOnly()
   {
      return !textbox.isEditable();
   }

   /**
    * If editable then show edit background.  Otherwise show non-edit background.
    * If the background property has been explicitly set after the creation of
    * the component then that background will be used.  Otherwise is uses the
    * defaults specified in the SystemDefaults class.
    */
   public void setEditable(boolean b)
   {
      if (textbox != null)
      {
         // Update the edit value.
         textbox.setEditable(b);

         if (updatedBackground == null)
         {
            if (b)
            {
               // Editable.
               textbox.setBackground(SystemDefaults.getDefaultEditBackground());
            }
            else
            {
               // Not editable.
               textbox.setBackground(SystemDefaults.getDefaultNonEditBackground());
            }
         }
      }
   }


   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public int getCreateOrder()
   {
      return nCreateOrder;
   }

   /**
    * The index of this component in the create order.  The create order of
    * components determines the overlap hierarchy and the tab order.
    */
   public void setCreateOrder(int value)
   {
      nCreateOrder = value;
   }

   /**
    * The maximum number of characters allowed.
    */
   public int getMaxCharacters()
   {
      return nMaxChars;
   }

   /**
    * The maximum number of characters allowed.
    */
   public void setMaxCharacters(int value)
   {
      nMaxChars = value;
   }

   /**
    * The default or saved text.
    */
   public void setDefaultText(String text)
   {
      sDefaultText = text;
      setText(text);
   }

   /**
    * The default or saved text.
    */
   public String getDefaultText()
   {
      return sDefaultText;
   }

   /**
    * Reset to the default or saved text.
    */
   public void resetToDefaultText()
   {
      setText(sDefaultText);
   }

   /**
    * Allow the user to use the tab key inside the text area.  If tabs are
    * not allowed, the tab key will take the user to the next component.
    */
   public void setTabsAllowed(boolean allowTabs)
   {
      this.bAllowTabs = allowTabs;
      if (bAllowTabs)
      {
         textbox.setTabSize(4);
      }
      else
      {
         textbox.setTabSize(0);
      }
   }

   /**
    * Allow the user to use the tab key inside the text area.  If tabs are
    * not allowed, the tab key will take the user to the next component.
    */
   public boolean isTabsAllowed()
   {
      return bAllowTabs;
   }

   /**
    * The font of this component.
    */
   public void setFont(Font font)
   {
      super.setFont(font);
      if (textbox != null)
      { textbox.setFont(font); }
   }
   // ****** Event methods ******

   /* Event methods are triggered at various times and can be
      overridden by child class to perform actions before a process
      resumes (See description above).  */
   // NONE


   // ****** Functionality methods ******

   /**
    * Delete this component.
    */
   public void delete()
   {
      // WSScrollPane delete() method will call the OnDelete() event.
      super.delete();
   }


   // ****** Listener methods ******
   public void addFocusListener(FocusListener l)
   {
      super.addFocusListener(l);
      if (textbox != null)
      { textbox.addFocusListener(l); }
   }

   public void addInputMethodListener(InputMethodListener l)
   {
      super.addInputMethodListener(l);
      if (textbox != null)
      { textbox.addInputMethodListener(l); }
   }

   public void addKeyListener(KeyListener l)
   {
      super.addKeyListener(l);
      if (textbox != null)
      { textbox.addKeyListener(l); }
   }

   public void addMouseListener(MouseListener l)
   {
      super.addMouseListener(l);
      if (textbox != null)
      { textbox.addMouseListener(l); }
   }

   public void addMouseMotionListener(MouseMotionListener l)
   {
      super.addMouseMotionListener(l);
      if (textbox != null)
      { textbox.addMouseMotionListener(l); }
   }

   public void addPropertyChangeListener(PropertyChangeListener l)
   {
      super.addPropertyChangeListener(l);
      if (textbox != null)
      { textbox.addPropertyChangeListener(l); }
   }

   public void addVetoableChangeListener(VetoableChangeListener l)
   {
      super.addVetoableChangeListener(l);
      if (textbox != null)
      { textbox.addVetoableChangeListener(l); }
   }

   public void removeFocusListener(FocusListener l)
   {
      super.removeFocusListener(l);
      if (textbox != null)
      { textbox.removeFocusListener(l); }
   }

   public void removeInputMethodListener(InputMethodListener l)
   {
      super.removeInputMethodListener(l);
      if (textbox != null)
      { textbox.removeInputMethodListener(l); }
   }

   public void removeKeyListener(KeyListener l)
   {
      super.removeKeyListener(l);
      if (textbox != null)
      { textbox.removeKeyListener(l); }
   }

   public void removeMouseListener(MouseListener l)
   {
      super.removeMouseListener(l);
      if (textbox != null)
      { textbox.removeMouseListener(l); }
   }

   public void removeMouseMotionListener(MouseMotionListener l)
   {
      super.removeMouseMotionListener(l);
      if (textbox != null)
      { textbox.removeMouseMotionListener(l); }
   }

   public void removePropertyChangeListener(PropertyChangeListener l)
   {
      super.removePropertyChangeListener(l);
      if (textbox != null)
      { textbox.removePropertyChangeListener(l); }
   }

   public void removeVetoableChangeListener(VetoableChangeListener l)
   {
      super.removeVetoableChangeListener(l);
      if (textbox != null)
      { textbox.removeVetoableChangeListener(l); }
   }
   // ****** Font ******

   /**
    * Get the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public String getFontName()
   {
      return getFont().getFamily();
   }

   /**
    * Get the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontStyle()
   {
      return getFont().getStyle();
   }

   /**
    * Get the font size.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public int getFontSize()
   {
      return getFont().getSize();
   }

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontName(String sfontName)
   {
      setFont(new Font(sfontName, getFont().getStyle(), getFont().getSize()));
   }

   /**
    * Set the font style.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontStyle(int nfontStyle)
   {
      setFont(new Font(getFont().getFamily(), nfontStyle, getFont().getSize()));
   }

   /**
    * Set the font name.  Allows the developer to manage fonts without dealing with the font
    * object.  That work done behind the scenes.
    */
   public void setFontSize(int nfontSize)
   {
      setFont(new Font(getFont().getFamily(), getFont().getStyle(), nfontSize));
   }

   // ****** TextArea methods ******
   public void replaceRange(String str, int start, int end)
   {
      if (textbox != null)
      { textbox.replaceRange(str, start, end); }
   }

   public void setColumns(int value)
   {
      if (textbox != null)
      { textbox.setColumns(value); }
   }

   public void setLineWrap(boolean bWordWrap)
   {
      if (textbox != null)
      { textbox.setLineWrap(bWordWrap); }
   }

   public void setBackground(Color bg)
   {
      updatedBackground = bg;
      super.setBackground(bg);
      if (textbox != null)
      { textbox.setBackground(bg); }
   }

   public void setForeground(Color fg)
   {
      super.setForeground(fg);
      if (textbox != null)
      { textbox.setForeground(fg); }
   }

   public void setRows(int value)
   {
      if (textbox != null)
      { textbox.setRows(value); }
   }

   public void setTabSize(int size)
   {
      if (textbox != null)
      { textbox.setTabSize(size); }
   }

   public boolean isEditable()
   {
      if (textbox != null)
      { return textbox.isEditable(); }
      return false;
   }

   public void setText(String str)
   {
      if (textbox != null)
      { textbox.setText(str); }
   }

   public void setWrapStyleWord(boolean value)
   {
      if (textbox != null)
      { textbox.setWrapStyleWord(value); }
   }

   public void setHighlighter(Highlighter h)
   {
      if (textbox != null)
      { textbox.setHighlighter(h); }
   }

   public void setWordWrapEnabled(boolean value)
   {
      if (textbox != null)
      {
         textbox.setLineWrap(value);
         textbox.setWrapStyleWord(value);
      }
   }

   // SHOULD BE PHASED OUT AS SOON AS POSSIBLE.
   public JTextArea getInputElement()
   {
      return textbox;
   }

   public void setEnabled(boolean value)
   {
      if (textbox != null)
      { textbox.setEnabled(value); }
   }

   public boolean isEnabled()
   {
      if (textbox != null)
      { return textbox.isEnabled(); }
      else { return false; }
   }

   public int getColumns()
   {
      if (textbox != null)
      { return textbox.getColumns(); }
      else { return 1; }
   }

   public int getLineCount()
   {
      if (textbox != null)
      { return textbox.getLineCount(); }
      else { return 0; }
   }

   public boolean getLineWrap()
   {
      if (textbox != null)
      { return textbox.getLineWrap(); }
      else { return true; }
   }

   public int getRows()
   {
      if (textbox != null)
      { return textbox.getRows(); }
      else { return 0; }
   }

   public int getTabSize()
   {
      if (textbox != null)
      { return textbox.getTabSize(); }
      else { return 4; }
   }

   public String getText()
   {
      if (textbox != null)
      {
         // Replace carriage returns and line feeds with spaces (since not XML friendly)
         String sCorrectedText = textbox.getText();
         sCorrectedText = sCorrectedText.replace('\r', ' ');
         sCorrectedText = sCorrectedText.replace('\n', ' ');
         // Replace "fancy" apostrophe with plain one
         sCorrectedText = sCorrectedText.replace('\u2019', '\'');
         sCorrectedText = sCorrectedText.replace('\u2018', '\'');
         // Replace "fancy" double quote with plain single quote (not SQL friendly)
         sCorrectedText = sCorrectedText.replace('\u201D', '\"');
         sCorrectedText = sCorrectedText.replace('\u201C', '\"');
         // Replace fancy dash with plain one
         sCorrectedText = sCorrectedText.replace('\u2013', '-');

         return sCorrectedText;

      }
      else { return ""; }
   }

   public boolean getWrapStyleWord()
   {
      if (textbox != null)
      { return textbox.getWrapStyleWord(); }
      else { return true; }
   }

   public boolean isWordWrapEnabled()
   {
      if (textbox != null)
      { return textbox.getLineWrap() && textbox.getWrapStyleWord(); }
      else { return true; }
   }

   public void insert(String str, int pos)
   {
      if (textbox != null)
      { textbox.insert(str, pos); }
   }
}
// $Log:
//  5    Balance   1.4         5/6/2003 4:05:51 PM    Helen Faynzilberg Onyx #
//       16750 Added Invalid character validation.
//  4    Balance   1.3         12/20/2002 10:05:03 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         12/19/2002 2:58:28 PM  Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  2    Balance   1.1         12/18/2002 8:55:53 PM  Mike Shoemake   Onyx
//       #15897:  Supervisor workstation locking up when entering comments.
//  1    Balance   1.0         8/23/2002 2:44:54 PM   Mike Shoemake
// $
//
//     Rev 1.0   Aug 23 2002 14:44:54   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:18   mshoemake
//  Initial revision.
//
//     Rev 1.1   Jan 29 2002 16:30:32   sputtagunta
//  Fixed :11386 Background color not persisted properly for the Comment boxes.
//
//     Rev 1.0   Dec 17 2001 11:05:34   mshoemake
//  Initial revision.


