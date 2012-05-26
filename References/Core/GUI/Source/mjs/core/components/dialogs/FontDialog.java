//
// file: WSFontDialog.java
// desc:
// proj: ER 6.3
//
// $Archive:   N:/REPOS/Archives/eQuality/web/java/com/witsys/common/core/components/dialogs/FontDialog.java-arc  $
// $Author:Mike Shoemake$
// $Revision:8$
//
// Copyright (c) 2000 by Witness Systems, Inc.
// All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package mjs.core.components.dialogs;

// Java imports
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.event.*;
// Witness imports
import mjs.core.administration.SystemDefaults;
import mjs.core.components.lists.ListBox;
import mjs.core.components.ScrollPane;
import mjs.core.components.Panel;
import mjs.core.components.TextField;
import mjs.core.components.ButtonPanel;
import mjs.core.components.Label;
import mjs.core.components.lists.StringListItem;
import mjs.core.components.lists.ListItem;
import mjs.core.events.ButtonEvent;
import mjs.core.factories.StandardLabelFactory;
import mjs.core.messages.Message;
import mjs.core.messages.MessageDialogHandler;
import mjs.core.messages.MessageFactory;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.utils.ComponentUtils;
import mjs.core.strings.SystemStrings;
import mjs.core.strings.InternationalizationStrings;
import mjs.core.types.HorizontalLocationType;
import mjs.core.utils.ColorFactory;


/**
 * This dialog allows the user to select a Font name,Font Style and Font Size for
 * a component.The Selected font is set for the component when the Ok button is pressed.
 */
public class FontDialog extends BaseWindowDialog
{
   /**
    * Font Name related component ID.  This is used for lists, text fields, etc.
    */
   public static final int FONT_NAME = 0;

   /**
    * Font Style related component ID.  This is used for lists, text fields, etc.
    */
   public static final int FONT_STYLE = 1;

   /**
    * Font Size related component ID.  This is used for lists, text fields, etc.
    */
   public static final int FONT_SIZE = 2;

   /**
    * The list of available font names.
    */
   private Vector vctFontNames = createFontNamesList();

   /**
    * The list of available font sizes.
    */
   private Vector vctFontSizes = createFontSizesList();

   /**
    * The list of available font styles.
    */
   private Vector vctFontStyles = createFontStylesList();

   /**
    * The list of components for which to set the font.
    */
   private Vector vctComponents = null;

   /**
    * The sample text for the selected font.
    */
   private final String sSampleDisplayText = "AaBbCcDd";

   /**
    * The label factory.
    */
   private StandardLabelFactory wsLabelFactory = new StandardLabelFactory();

   /**
    * The font name label.
    */
   private Label lblFontName = wsLabelFactory.createLabel(FONT_NAME);

   /**
    * The font style label.
    */
   private Label lblFontStyle = wsLabelFactory.createLabel(FONT_STYLE);

   /**
    * The font size label.
    */
   private Label lblFontSize = wsLabelFactory.createLabel(FONT_SIZE);

   /**
    * The font name lookup edit box.
    */
   private TextField edtFontName = new TextField(FONT_NAME);

   /**
    * The font size lookup edit box.
    */
   private TextField edtFontSize = new TextField(FONT_SIZE);

   /**
    * The font style lookup edit box.
    */
   private TextField edtFontStyle = new TextField(FONT_STYLE);

   /**
    * The sample font display text box.
    */
   private TextField edtSampleView = new TextField();

   /**
    * The font name picklist.
    */
   private ListBox lstFontName = new ListBox(FONT_NAME);

   /**
    * The font style picklist.
    */
   private ListBox lstFontStyle = new ListBox(FONT_STYLE);

   /**
    * The font size picklist.
    */
   private ListBox lstFontSize = new ListBox(FONT_SIZE);

   /**
    * The main panel for this dialog.
    */
   private Panel pnlMain = null;

   /**
    * The panel used to display the sample font.
    */
   private Panel pnlSampleView = new Panel();

   /**
    * The key listener for the textfield components.
    */
   private KeyListener lKeyTextField = new java.awt.event.KeyAdapter()
   {
      public void keyReleased(KeyEvent e)
      {
         textField_KeyReleased(e);
      }
   };

   private KeyListener lKeyListBox = new java.awt.event.KeyAdapter()
   {
      public void keyPressed(KeyEvent e)
      {
         listbox_keyPressed(e);
      }
   };

   private ListSelectionListener lSelectionListBox = new javax.swing.event.ListSelectionListener()
   {
      public void valueChanged(ListSelectionEvent e)
      {
         listbox_valueChanged(e);
      }
   };

   /**
    * Constructor.
    * @param   frame   The frame to use when displaying the dialog.
    * @param   title   The title for the frame.
    * @param   modal   Is this dialog a modal dialog?
    */
   public FontDialog(Frame frame, String title, boolean modal)
   {
      super(frame, title, modal, false, 352, 245, false);
      try
      {
         fdBaseInit();
         pack();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Displays dialog
    */
   public void showDialog(Vector components, boolean bReadOnly)
   {
      vctComponents = components;
      setFont(getComponentFont(vctComponents));
      setReadOnly(bReadOnly);
      super.showDialog();
   }

   /**
    * The currently displayed font for this dialog.
    */
   public void setFont(Font f)
   {
      displayFont(f);
   }

   /**
    * This method takes a Font object as a parameter and makes the appropriate FontAttribute settings in the dialog box.
    */
   private void displayFont(Font f)
   {
      Integer iFontSize = new Integer(f.getSize());
      // Update the font preview.
      // Update stored font information
      String sFontName = f.getFamily();
      String sFontStyle = getFontStyleAsString(f.getStyle());
      String sFontSize = iFontSize.toString();
      if (sFontName.equals("dialog"))
      { sFontName = "Dialog"; }
      // set the value chosen for Name, Style, Size
      lstFontName.setSelectedText(sFontName);
      lstFontStyle.setSelectedText(sFontStyle);
      lstFontSize.setSelectedText(sFontSize);
      // set the text label to chosen value for Name, Style, Size
      edtFontName.setText(sFontName);
      edtFontStyle.setText(sFontStyle);
      edtFontSize.setText(sFontSize);
      // If not found, it's probably missing the dialog font.
      boolean bFound = false;
      bFound = lstFontName.getSelectedItem().toString().equals(edtFontName.getText());
      if ((!bFound) && (sFontName.equals(SystemStrings.szDialog)))
      { lstFontName.setSelectedText(SystemStrings.szDialog); }
   }

   /**
    * Create and return a list item object for the specified text.
    */
   private static ListItem createListItem(String text)
   {
      return (ListItem)(new StringListItem(text, text, null));
   }

   /**
    * This sets the components in the dialog to be readonly or not readonly.
    */
   public void setReadOnly(boolean readonly)
   {
      getButtonPanel().getButtonByText(InternationalizationStrings.szOK).setEnabled(!readonly);
      // Edit boxes
      edtFontName.setEditable(!readonly);
      edtFontSize.setEditable(!readonly);
      edtFontStyle.setEditable(!readonly);
      // Lists
      lstFontName.setReadOnly(readonly);
      lstFontSize.setReadOnly(readonly);
      lstFontStyle.setReadOnly(readonly);
   }

   /**
    * This sets the components in the dialog to be readonly or not readonly.
    */
   public boolean isReadOnly()
   {
      return !getButtonPanel().getButtonByText(InternationalizationStrings.szOK).isEnabled();
   }

   /**
    * Create the available font sizes list.
    */
   private static Vector createFontSizesList()
   {
      Vector vctFontSizes = new Vector();
      vctFontSizes.add(createListItem("10"));
      vctFontSizes.add(createListItem("12"));
      vctFontSizes.add(createListItem("14"));
      vctFontSizes.add(createListItem("16"));
      vctFontSizes.add(createListItem("18"));
      vctFontSizes.add(createListItem("20"));
      vctFontSizes.add(createListItem("22"));
      vctFontSizes.add(createListItem("24"));
      vctFontSizes.add(createListItem("26"));
      vctFontSizes.add(createListItem("28"));
      vctFontSizes.add(createListItem("36"));
      vctFontSizes.add(createListItem("48"));
      vctFontSizes.add(createListItem("72"));
      return vctFontSizes;
   }

   /**
    * Create the list of available font styles.
    */
   private static Vector createFontStylesList()
   {
      Vector vctFontStyles = new Vector();
      vctFontStyles.add(createListItem(InternationalizationStrings.szRegular));
      vctFontStyles.add(createListItem(InternationalizationStrings.szItalic));
      vctFontStyles.add(createListItem(InternationalizationStrings.szBold));
      vctFontStyles.add(createListItem(InternationalizationStrings.szBoldItalic));
      return vctFontStyles;
   }

   /**
    * This method is used for obtaining all the Font families available.
    */
   private static Vector createFontNamesList()
   {
      String[] sfontFamilies;
      Vector vctFonts = new Vector();
      sfontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
      for (int i = 0; i < sfontFamilies.length; i++)
      {
         if (!((sfontFamilies[i].equals("Map Symbols")) || (sfontFamilies[i].equals("Marlett")) ||
         (sfontFamilies[i].equals("Monotype Sorts")) || (sfontFamilies[i].equals("MS Outlook")) ||
         (sfontFamilies[i].equals("MT Extra")) || (sfontFamilies[i].equals("Symbol")) ||
         (sfontFamilies[i].equals("Wingdings")) || (sfontFamilies[i].equals("Webdings")) ||
         (sfontFamilies[i].equals("Wingdings 2")) || (sfontFamilies[i].equals("Bookshelf Symbol 1")) ||
         (sfontFamilies[i].equals("Bookshelf Symbol 2")) || (sfontFamilies[i].equals("Bookshelf Symbol 3")) ||
         (sfontFamilies[i].equals("SPW")) || (sfontFamilies[i].equals("Wingdings 3"))))
         {
            // Add this font name to the list.
            vctFonts.addElement(createListItem(sfontFamilies[i]));
         }
      }
      return vctFonts;
   }

   /**
    * Initializes buttons, labels and text fields.
    */
   private void fdBaseInit() throws Exception
   {
      pnlMain = getWindowPanel();
      Border bdrBevel = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
      lstFontName.setBorder(bdrBevel);
      lstFontStyle.setBorder(bdrBevel);
      lstFontSize.setBorder(bdrBevel);
      // Populate the lists.
      lstFontName.setItemList(vctFontNames);
      lstFontStyle.setItemList(vctFontStyles);
      lstFontSize.setItemList(vctFontSizes);
      ButtonPanel pnlButtons = getButtonPanel();
      // Add the buttons.
      pnlButtons.addButton(InternationalizationStrings.szCancel, HorizontalLocationType.CENTER);
      pnlButtons.addButton(InternationalizationStrings.szOK, HorizontalLocationType.CENTER);
      // Set the dialog and panel properties.
      //      getContentPane().setSize (300,300);
      pnlMain.setLayout(null);
      // Sample font panel
      TitledBorder bdrTitle = new TitledBorder(InternationalizationStrings.szSample);
      bdrTitle.setTitleFont(SystemDefaults.getDefaultFont());
      bdrTitle.setTitleColor(ColorFactory.black);
      pnlSampleView.setBorder(bdrTitle);
      pnlSampleView.setBounds(new Rectangle(2, 147, 349, 93));
      pnlSampleView.setLayout(null);
      // Caption Text
      lblFontName.setText(InternationalizationStrings.szFontColon);
      lblFontStyle.setText(InternationalizationStrings.szFontStyleColon);
      lblFontSize.setText(InternationalizationStrings.szSizeColon);
      edtSampleView.setText(sSampleDisplayText);
      // Bounds
      lblFontName.setBounds(new Rectangle(3, 1, 66, 17));
      edtFontName.setBounds(new Rectangle(2, 17, 167, 21));
      lstFontName.setBounds(new Rectangle(2, 40, 166, 98));
      lblFontStyle.setBounds(new Rectangle(177, 1, 66, 17));
      edtFontStyle.setBounds(new Rectangle(176, 17, 111, 21));
      lstFontStyle.setBounds(new Rectangle(176, 40, 110, 98));
      lblFontSize.setBounds(new Rectangle(295, 1, 66, 17));
      edtFontSize.setBounds(new Rectangle(294, 17, 55, 21));
      lstFontSize.setBounds(new Rectangle(294, 40, 54, 98));
      edtSampleView.setBounds(new Rectangle(13, 20, 322, 60));
      // Font
      lblFontName.setFont(SystemDefaults.getDefaultFont());
      edtFontName.setFont(SystemDefaults.getDefaultFont());
      lstFontName.setFont(SystemDefaults.getDefaultFont());
      lblFontStyle.setFont(SystemDefaults.getDefaultFont());
      edtFontStyle.setFont(SystemDefaults.getDefaultFont());
      lstFontStyle.setFont(SystemDefaults.getDefaultFont());
      lblFontSize.setFont(SystemDefaults.getDefaultFont());
      edtFontSize.setFont(SystemDefaults.getDefaultFont());
      lstFontSize.setFont(SystemDefaults.getDefaultFont());
      // Tab Order
      edtFontName.setNextFocusableComponent(edtFontStyle);
      lstFontName.setNextFocusableComponent(edtFontStyle);
      edtFontStyle.setNextFocusableComponent(edtFontSize);
      lstFontStyle.setNextFocusableComponent(edtFontSize);
      // Scroll Bar Policy
      lstFontName.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      lstFontStyle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      lstFontSize.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      // Sample View TextField
      edtSampleView.setBackground(pnlMain.getBackground());
      edtSampleView.setEditable(false);
      edtSampleView.setHorizontalAlignment(JTextField.CENTER);
      edtSampleView.setSelectedTextColor(ColorFactory.black);
      edtSampleView.setSelectionColor(SystemColor.control);
      // Listeners
      edtFontName.addKeyListener(lKeyTextField);
      edtFontStyle.addKeyListener(lKeyTextField);
      edtFontSize.addKeyListener(lKeyTextField);
      lstFontName.addKeyListener(lKeyListBox);
      lstFontStyle.addKeyListener(lKeyListBox);
      lstFontSize.addKeyListener(lKeyListBox);
      lstFontName.addListSelectionListener(lSelectionListBox);
      lstFontStyle.addListSelectionListener(lSelectionListBox);
      lstFontSize.addListSelectionListener(lSelectionListBox);
      // Add components
      getContentPane().add(pnlMain);
      pnlMain.add((Component)lblFontName, null);
      pnlMain.add((Component)lblFontStyle, null);
      pnlMain.add((Component)lblFontSize, null);
      pnlMain.add(edtFontName, null);
      pnlMain.add(edtFontStyle, null);
      pnlMain.add(edtFontSize, null);
      pnlMain.add(lstFontName, null);
      pnlMain.add(lstFontStyle, null);
      pnlMain.add(lstFontSize, null);
      pnlMain.add(pnlSampleView, null);
      pnlSampleView.add(edtSampleView, null);
   }

   /**
    * Return string representation of the int font style value.
    */
   private String getFontStyleAsString(int nfontStyle)
   {
      switch (nfontStyle)
      {
         case Font.PLAIN:
            return InternationalizationStrings.szRegular;
         case Font.BOLD:
            return InternationalizationStrings.szBold;
         case Font.ITALIC:
            return InternationalizationStrings.szItalic;
         case(Font.BOLD | Font.ITALIC):
            return InternationalizationStrings.szBoldItalic;
         default:
            return "";
      }
   }

   /**
    * Assign the new font to all selected components.
    */
   public void setComponentFont(Font f)
   {
      Enumeration enum = vctComponents.elements();
      while (enum.hasMoreElements())
      {
         JComponent jComp = (JComponent)(enum.nextElement());
         jComp.setFont(f);
      }
   }

   /**
    * Assign the new font to all selected components.
    */
   private Font getComponentFont(Vector vcomponents)
   {
      if (vcomponents.size() > 0)
      {
         JComponent jComp = (JComponent)(vcomponents.get(0));
         return jComp.getFont();
      }
      else
      {
         // Default font.
         return SystemDefaults.getDefaultFont();
      }
   }

   /**
    * Returns the integer value of the specified String font style representation.
    */
   private int getFontStyleAsInt(String sfontStyle)
   {
      int index = 0;
      for (int C = 0; C <= vctFontStyles.size() - 1; C++)
      {
         if (sfontStyle.equals(vctFontStyles.get(C).toString()))
         {
            index = C;
            break;
         }
      }
      switch (index)
      {
         case 0:
            return Font.PLAIN;
         case 1:
            return Font.ITALIC;
         case 2:
            return Font.BOLD;
         case 3:
            return (Font.BOLD | Font.ITALIC);
         default:
            return -1;
      }
   }

   /**
    * Get the related list box for this WSTextField component.
    */
   private ListBox getRelatedListBox(TextField textfield)
   {
      if (textfield.getObjectID() == FONT_NAME)
      { return lstFontName; }
      else if (textfield.getObjectID() == FONT_SIZE)
      { return lstFontSize; }
      else if (textfield.getObjectID() == FONT_STYLE)
      { return lstFontStyle; }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10416,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSFontDialog" );
         return lstFontName;
      }
   }

   /**
    * Get the related list box for this WSTextField component.
    */
   private TextField getRelatedTextField(ListBox listbox)
   {
      if (listbox.getObjectID() == FONT_NAME)
      { return edtFontName; }
      else if (listbox.getObjectID() == FONT_SIZE)
      { return edtFontSize; }
      else if (listbox.getObjectID() == FONT_STYLE)
      { return edtFontStyle; }
      else
      {
         // Create message object
         Message message = MessageFactory.createMessage( 10416,
                                                        "Balance",
                                                        "Evaluations",
                                                        "",
                                                        Message.INTERNAL,
                                                        InternationalizationStrings.COMMON_RESOURCE_CLASS,
                                                        mjs.core.utils.ComponentUtils.getLocale() );

         // Display message
         MessageDialogHandler.showMessage( message, null, "WSFontDialog" );
         return edtFontName;
      }
   }

   /**
    * This method is called when the key pressed event is thrown by one of the text fields. <p>
    * It performs the lookup to select the item in the related listbox that
    * most closely resembles the current text in the textfield.
    */
   private void textField_KeyReleased(KeyEvent e)
   {
      boolean bFound = false;
      if (e.getSource() instanceof TextField)
      {
         // Get the text field and the related list box.
         TextField textfield = (TextField)(e.getSource());
         ListBox listbox = getRelatedListBox(textfield);
         try
         {
            // The current content of the text field.
            String sTextContent = textfield.getText().toLowerCase();
            int nKeyCode = e.getKeyCode();
            // Select the item in the list that is that best match.
            for (int C = 0; C <= listbox.getModel().getSize() - 1; C++)
            {
               String sValue = listbox.getModel().getElementAt(C).toString();
               if (sValue.toLowerCase().startsWith(sTextContent))
               {
                  listbox.setSelectedIndex(C);
                  bFound = true;
                  break;
               }
            }

            /* If enter pressed, replace the current text with the selected value
               of the list box.  */

            if ((nKeyCode == KeyEvent.VK_ENTER) && (bFound))
            { textfield.setText(listbox.getSelectedItem().toString()); }
            // Not found.  Select the first item in the list.
            if (!bFound)
            { listbox.setSelectedIndex(0); }
         }
         catch (java.lang.Exception exp)
         {
            exp.printStackTrace();
         }
      }
   }

   /**
    * This method is called when the key pressed event is thrown by one of the list boxes. <p>
    * It replaces the text in the related text field with the text of the currently selected item in the list.
    */
   private void listbox_keyPressed(KeyEvent e)
   {
      if (e.getKeyChar() == KeyEvent.VK_ENTER)
      {
         // Get the list box and the related text field.
         ListBox listbox = (ListBox)(e.getSource());
         TextField textfield = getRelatedTextField(listbox);
         textfield.setText(listbox.getSelectedItem().toString());
      }
   }

   /**
    * Return the currently selected Font based on the selections in the list boxes.
    */
   private Font extractSelectedFont()
   {
      String sName = SystemDefaults.getDefaultFont().getFamily();
      if (lstFontName.getSelectedItem() != null)
      { sName = lstFontName.getSelectedItem().toString(); }
      int nSize = SystemDefaults.getDefaultFont().getSize();
      if (lstFontSize.getSelectedItem() != null)
      { nSize = Integer.parseInt(lstFontSize.getSelectedItem().toString()); }
      int nStyle = SystemDefaults.getDefaultFont().getStyle();
      if (lstFontStyle.getSelectedItem() != null)
      { nStyle = getFontStyleAsInt(lstFontStyle.getSelectedItem().toString()); }
      return new Font(sName, nStyle, nSize);
   }

   /**
    * This method is called when the list selection changes in one of the font display list boxes.
    */
   private void listbox_valueChanged(ListSelectionEvent e)
   {
      if (e.getSource() instanceof JList)
      {
         edtSampleView.setFont(extractSelectedFont());
         edtSampleView.repaint();
         // Get the list box and the related text field.
         if (e.getSource() instanceof JList)
         {
            JList list = (JList)(e.getSource());
            ListBox listbox = (ListBox)(list.getParent().getParent());
            TextField textfield = getRelatedTextField(listbox);
            if (!textfield.hasFocus())
            { textfield.setText(listbox.getSelectedItem().toString()); }
         }
      }
   }

   /**
    * Called by a button on the dialog when it is clicked by the user.
    * @param   e              The action event associated with the button click.
    * @param   sDisplayText   The display text of the button.
    * @param   nObjectID      The unique numeric ID associated with the button.
    */
   public void buttonClicked(ButtonEvent e)
   {
      // EVENT.
      event_OnButtonClicked(e);
      if (e.getButtonCaption().equals(InternationalizationStrings.szOK))
      {
         // OK Button clicked.
         event_okButtonClicked();
      }
      else
      {
         // Cancel Button clicked.
         event_cancelButtonClicked();
      }
   }

   /**
    * The user clicked the ok button.
    */
   private void event_okButtonClicked()
   {
      setComponentFont(edtSampleView.getFont());
      this.setVisible(false);
   }

   /**
    * The user clicked the cancel button.
    */
   private void event_cancelButtonClicked()
   {
      this.setVisible(false);
   }
}
// $Log:
//  8    Balance   1.7         6/6/2003 8:40:20 AM    Mike Shoemake   #18823
//       Forms:  Can not save Form
//  7    Balance   1.6         3/7/2003 9:28:20 AM    Mike Shoemake   Removed
//       reference to forms.utils.ComponentUtils.  Replaced with
//       core.utils.ComponentUtils.  Not sure yet why the same method is in
//       both classes.
//  6    Balance   1.5         1/29/2003 4:47:14 PM   Helen Faynzilberg Fixed #
//       16409
//       Moved getLocale() method from common.forms.ComponentUtils class to a
//       new ComponentUtils class under common.core.utils. And modified
//       references to to the new  ComponentUtils. getLocale()
//  5    Balance   1.4         1/17/2003 8:50:49 AM   Peter Lichtenwalner ONYX
//       16212 - Modified for improved Error Handling initiative, using new
//       framework for displaying messages to user.
//  4    Balance   1.3         12/20/2002 10:05:04 AM Mike Shoemake   Onyx
//       #15901  Default colors should reside in one place (SystemDefaults
//       class).
//  3    Balance   1.2         11/25/2002 3:19:46 PM  Mike Shoemake   Update to
//       the listener model.
//  2    Balance   1.1         10/11/2002 8:54:20 AM  Mike Shoemake
//       Deprecated all classes under mjs.core.exceptions.  These
//       will be replaced during the Error Handling effort for build 20.
//  1    Balance   1.0         8/23/2002 2:45:16 PM   Mike Shoemake
// $

//
//     Rev 1.2   Nov 25 2002 15:19:46   mshoemake
//  Update to the listener model.
//
//     Rev 1.1   Oct 11 2002 08:54:20   mshoemake
//  Deprecated all classes under mjs.core.exceptions.  These will be replaced during the Error Handling effort for build 20.
//
//     Rev 1.0   Aug 23 2002 14:45:16   mshoemake
//  Initial revision.
//
//     Rev 1.0   Jun 20 2002 17:27:52   mshoemake
//  Initial revision.
//
//     Rev 1.4   May 21 2002 10:53:38   mshoemake
//  Relocated the label factories from baseclasses.components to baseclasses.factories.
//
//     Rev 1.3   Feb 22 2002 16:30:46   mshoemake
//  Fixed #11799.  The border color of the Font Dialog doesn't match the rest of the dialog.
//
//  Still need to setup a default dialog background in the WSDefaults class and use that instead of hardcoding the RGB value.
//
//     Rev 1.2   Feb 01 2002 10:15:06   sputtagunta
//  Fixed #11670 : Styles in Font Dialog are showing the wrong output
//
//     Rev 1.1   Dec 18 2001 08:49:58   mshoemake
//  Integrated the dialogs with the new buttonClicked() method.  Reserving the event_OnButtonClicked() method for aplication specific functionality.
//
//     Rev 1.0   Dec 17 2001 11:13:02   mshoemake
//  Initial revision.


