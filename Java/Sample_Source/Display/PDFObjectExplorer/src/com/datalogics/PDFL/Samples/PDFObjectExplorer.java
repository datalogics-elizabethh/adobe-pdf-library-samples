/*
 * 
 * This sample demonstrates using the PDF Object Explorer, a viewing tool that allows you to look at
 * information about objects embedded in a PDF file. These objects include arrays, dictionaries,
 * streams of characters, and integers.
 *
 * For more detail see the description of the PDFObjectExplorer sample on our Developer's site, 
 * http://dev.datalogics.com/adobe-pdf-library/sample-program-descriptions/java-sample-programs/listing-information-about-values-and-objects-in-pdf-files#pdfobjectexplorer
 * 
 * Copyright (c) 2009-2017, Datalogics, Inc. All rights reserved.
 *
 * For complete copyright information, refer to:
 * http://dev.datalogics.com/adobe-pdf-library/license-for-downloaded-pdf-samples/
 *
 */
package com.datalogics.pdfl.samples.Display.PDFObjectExplorer;

import apple.dts.samplecode.osxadapter.OSXAdapter;
import java.awt.HeadlessException;
import java.io.File;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JDialog;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

// Import classes from DLE
import com.datalogics.PDFL.Library;
import com.datalogics.PDFL.Document;
import com.datalogics.PDFL.PDFObject;
import com.datalogics.PDFL.PDFArray;
import com.datalogics.PDFL.PDFBoolean;
import com.datalogics.PDFL.PDFDict;
import com.datalogics.PDFL.PDFInteger;
import com.datalogics.PDFL.PDFName;
import com.datalogics.PDFL.PDFReal;
import com.datalogics.PDFL.PDFStream;
import com.datalogics.PDFL.PDFString;
import com.datalogics.PDFL.PermissionRequestOperation;
import java.awt.FileDialog;
import java.awt.Frame;


/**
 *
 * @author Datalogics
 */
@SuppressWarnings("serial")
public class PDFObjectExplorer extends javax.swing.JFrame {
    private Library library;

    // Check that we are on Mac OS X.  This is crucial to loading and using the OSXAdapter class.
    public static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));

    /** Creates new form PDFObjectExplorer */
    public PDFObjectExplorer() {
        library = new Library();
        library.setAllowOpeningXFA(true);
        initComponents();
        PDFObjectExplorer.super.setTitle("PDF Object Explorer");

        // Set the initial tree to contain a node titled "(No Document)"
        PDFNode root = new PDFNode("(No Document)");
        mainTree.setModel(new DefaultTreeModel(root));

        // Initialize the custom Icon renderer for the tree and assign
        // the icon renderer to the tree
        PDFObjectTreeIconRenderer pdfObjectTreeIconRenderer = new PDFObjectTreeIconRenderer();
        mainTree.setCellRenderer(pdfObjectTreeIconRenderer);

        // Create a tree selection listener for when the user selects
        // a new node to view
        mainTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                //See method:  mainTreeAfterSelect()
                mainTreeAfterSelect(e);
            }
        });

        // Set up our application to respond to the Mac OS X application menu
        registerForMacOSXEvents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        openFile = new javax.swing.JButton();
        refreshFile = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTree = new javax.swing.JTree();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataView = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        streamViewRaw = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        streamViewCooked = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                cleanup(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setAlignmentX(0.0F);

        openFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("icons/FileOpen1616.png"))); // NOI18N
        openFile.setToolTipText("Open a PDF");
        openFile.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        openFile.setFocusable(false);
        openFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileActionPerformed(evt);
            }
        });
        jToolBar1.add(openFile);

        refreshFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("icons/Refresh1616.png"))); // NOI18N
        refreshFile.setToolTipText("Refresh Document");
        refreshFile.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        refreshFile.setFocusable(false);
        refreshFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        refreshFile.setMaximumSize(new java.awt.Dimension(23, 23));
        refreshFile.setMinimumSize(new java.awt.Dimension(23, 23));
        refreshFile.setPreferredSize(new java.awt.Dimension(23, 23));
        refreshFile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshFileActionPerformed(evt);
            }
        });
        jToolBar1.add(refreshFile);

        getContentPane().add(jToolBar1);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 420));

        mainTree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
                mainTreeTreeWillExpand(evt);
            }
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {
            }
        });
        mainTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainTreeMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mainTreeMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(mainTree);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(275, 270));

        dataView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        dataView.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(dataView);

        jSplitPane2.setLeftComponent(jScrollPane2);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(274, 145));

        streamViewRaw.setEditable(false);
        jScrollPane3.setViewportView(streamViewRaw);

        jTabbedPane1.addTab("Unfiltered", jScrollPane3);

        streamViewCooked.setEditable(false);
        jScrollPane4.setViewportView(streamViewCooked);

        jTabbedPane1.addTab("Filtered", jScrollPane4);

        jSplitPane2.setRightComponent(jTabbedPane1);

        jSplitPane1.setRightComponent(jSplitPane2);

        getContentPane().add(jSplitPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** This method is called when the user presses the 'Open File' button.
     *  which opens a PDF to Examine it
     * @param evt
     */
    private void openFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileActionPerformed

        if (MAC_OS_X)
        {
            FileDialog fileDialog = new FileDialog(new Frame(), "Select a PDF File", FileDialog.LOAD);
            fileDialog.setFilenameFilter(new PDFFilenameFilter());
            fileDialog.setVisible(true);
            String fileName = fileDialog.getFile();
            String fileDirectory = fileDialog.getDirectory();
            if (fileName == null) {
                return;
            } else {
                openPDFFile(new File(fileDirectory, fileName));
            }
        }
        else
        {
            // Create a new JFileChooser dialog, set its file filter and title
            // Set a file filter to only display directories and 'pdf' files
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new PDFFilter());
            fileChooser.setDialogTitle("Select a PDF File");

            //Display the file chooser and Save whether the user presses OK or CANCEL
            int returnVal = fileChooser.showOpenDialog(PDFObjectExplorer.this);

            File file = null;
            // If user selects a file, create it as a file, set global title
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                openPDFFile(file);
            } else {
                return;  //Die quietly if user presses "Cancel"
            }
        }
    }

    private void openPDFFile(File file) throws HeadlessException {
        //Die quietly if user presses "Cancel"
        try {
            //If a PDFDocument is currently open, close it
            if (PDFDoc != null) {
                closePDF();
            }
            // First attempt to open the document with no password
            try {
                PDFDoc = new Document(file.getAbsolutePath());
                password = null; // Null out the password if we didn't need it
                PDFObjectExplorer.super.setTitle(file.getName());
            }
            catch (Exception fileOpenException) {
                String exceptionMessage = fileOpenException.toString();
                // Trap password-required exception
                // NOTE: 1073938471 = "file requires password authentication"
                if (exceptionMessage.contains("1073938471")) {
                    password = "";

                    try {
                        // If the user has entered a password and hit enter
                        // or clicked the OK button, open the file with a password
                        if(promptForPassword()) {
                            PDFDoc = new Document(file.getAbsolutePath(), password, PermissionRequestOperation.OPEN, false);
                            PDFObjectExplorer.super.setTitle(file.getName());
                        }
                        else {
                            // If not OK, user cancelled.  Abandon the open, no document available.
                            PDFNode root = new PDFNode("(No Document)");
                            mainTree.setModel(new DefaultTreeModel(root));
                        }
                    }
                    //File can't open because password provided was incorrect
                    catch(Exception fileOpenWithPasswordException) {
                         //Just throw to the outer handler
                        throw fileOpenWithPasswordException;
                    }
                }
            }

            // Now populate the initial tree
            GrabDocRootAndInfo();

            // Initialize the data view
            InitDataView();

            // Save the file path
            currFilePath = file.getAbsolutePath();
            file = null;
        }
        catch (Exception exc) {
            //Build the error string
            String errString = "The File " + file.getPath()
                + " cannot be opened. \n\n" + exc.toString();

            //If the file cant be opened, 1 of 2 errors will be thrown
            //1) If the file can't open, a NullPointer will be thrown
            //   in which case the file may be broken
            //2) If there is a file open, and the file can't open, a Runtime
            //   will be thrown in which case we have to make sure that the
            //   Runtime exception does not contain the "password error".
            //   If it does not contain the password error, the file may be broken
            if(exc instanceof NullPointerException ||
                    (exc instanceof RuntimeException && !exc.toString().contains("1073938471"))){
                errString += "\n\nThe file may be corrupt, attempt to open it in another program";
            }
            // If the file is unable to be opened, throw and display error
            JOptionPane.showMessageDialog(null, errString);

            //If a PDF is already opened, reopen the current document
            if(PDFDoc != null){
                reopenPDF();
            }
        }
    }//GEN-LAST:event_openFileActionPerformed

    private void reopenPDF(){
        //Clear the data table
        ((DefaultTableModel)dataView.getModel()).setRowCount(0);

        // If we opened with a password, use it again
        if (password != null)
            PDFDoc = new Document(currFilePath, password, PermissionRequestOperation.OPEN, false);
        else
            PDFDoc = new Document(currFilePath);

        // Now repopulate the tree
        GrabDocRootAndInfo();

        // Re-initialize the data view
        InitDataView();
    }

    /** Close and reopen the document from the file -
     * useful if you've made changes to the PDF
     */
    private void refreshFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshFileActionPerformed
        // We only need to refesh if a doc is already open
        if (PDFDoc != null) {
            reopenPDF();
        }
    }//GEN-LAST:event_refreshFileActionPerformed

    //not used
    private void mainTreeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainTreeMouseReleased

    }//GEN-LAST:event_mainTreeMouseReleased
    //not used
    private void mainTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainTreeMouseClicked

    }//GEN-LAST:event_mainTreeMouseClicked

    /** Dynamically adds nodes to the tree whenever a user expands a node
     *
     * @param evt
     * @throws javax.swing.tree.ExpandVetoException
     */
    private void mainTreeTreeWillExpand(javax.swing.event.TreeExpansionEvent evt)throws javax.swing.tree.ExpandVetoException {//GEN-FIRST:event_mainTreeTreeWillExpand

        //The node the user has selected to expand
        PDFNode userSelectedNode = (PDFNode)evt.getPath().getLastPathComponent();

        //Initialize object to null so that it can be reused throughout the loop
        PDFNode currentChildNode = null;
        PDFObject pdfObjectOfChildNode = null;

        //Begin outer loop: loop through each of the userSelectedNode's children
        for(int i = 0; i < userSelectedNode.getChildCount(); i++){
            //Create a new node for each child of the userSelectedNode
            currentChildNode = (PDFNode)userSelectedNode.getChildAt(i);
            //Set the currentNode's pdf Object value to a variable for use within the function
            pdfObjectOfChildNode = currentChildNode.getPDFObject();

            //If the ObjectValue is a PDFDict and currently has no children, enumerate
            if(pdfObjectOfChildNode instanceof PDFDict && currentChildNode.getChildCount() == 0){
                //Attach enumerated children to the currentChildNode
                ((PDFDict)pdfObjectOfChildNode).enumPDFObjects(new EnumObjectsForTree(currentChildNode));
            }
            //If the ObjectValue is a PDFArray, create a PDFArray and loop
            //loop through it, adding each child node to the current node
            else if(pdfObjectOfChildNode instanceof PDFArray && currentChildNode.getChildCount() == 0){

                //Create pdfArray, pdfArray contains an array of PDFObjects
                //which hold the VALUE of the node.
                PDFArray pdfArray = (PDFArray)pdfObjectOfChildNode;

                for(int k = 0; k < pdfArray.getLength(); k++){
                    //Create the returnObject
                    Object[] returnObject = new Object[3];
                    //Pass in the object VALUE and have it return an object
                    //array containing a boolean if the data is valid
                    //as well as the ACTUAL value the object contains.
                    //  See method:  GetObjectTypeAndValue()
                    returnObject = GetObjectTypeAndValue(pdfArray.get(k));

                    //If the data is valid
                    if((Boolean)returnObject[0]){
                        //Create a new node with the (value as it's title, and the pdfObject)
                        PDFNode current = new PDFNode(returnObject[1].toString(),pdfArray.get(k));

                        //Also set the ObjectValue to that of the objects' value...
                        current.setPDFObject(pdfArray.get(k));

                        //Add the 'current' node to its' parent
                        currentChildNode.add(current);

                        // If it's an indirect object, append the ID and Generation numbers
                        if (((PDFObject)current.getPDFObject()).getIndirect())
                            //the node's userObject is the text displayed on screen
                            current.setUserObject(current.appendIdAndGeneration());
                    }
                }
            }
            // If the object is a stream, add nodes for each entry in its dictionary
            else if(pdfObjectOfChildNode instanceof PDFStream && currentChildNode.getChildCount() == 0){
                ((PDFStream)pdfObjectOfChildNode).getDict().enumPDFObjects(new EnumObjectsForTree(currentChildNode));
            }
        }//End for loop
    }//GEN-LAST:event_mainTreeTreeWillExpand

    private void cleanup(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_cleanup
        quit();
    }//GEN-LAST:event_cleanup

    /** A password dialog box is displayed if the PDF requires a password
     *  to view it.
     *
     * @return
     */
    private boolean promptForPassword() {
        //Clear the datatable
        ((DefaultTableModel)dataView.getModel()).setRowCount(0);
        // Create objects that will be added to the JDialog box
        final JLabel passwordPrompt1 = new JLabel("The requested operation requires additional permissions.");
        final JLabel passwordPrompt2 = new JLabel("Please enter the document password.");
        final JTextField jpf = new JPasswordField();

        // Add the objects created to a viewing pane
        JOptionPane jop = new JOptionPane(new Object[] {passwordPrompt1, passwordPrompt2, jpf},
                JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);

        // Create the Dialog with the title of 'Password:'
        JDialog dialog = jop.createDialog(PDFObjectExplorer.this, "Password:");

        // When the dialog is shown, give focus to the password field
        dialog.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                jpf.requestFocusInWindow();
            }
        });

        // Display the password dialog, save the value of OK_OPTION or CANCEL_OPTION
        dialog.setVisible(true);
        int result = (Integer)jop.getValue();
        dialog.dispose();

        // Save the password, return true/false
        password = jpf.getText();
        return (result == JOptionPane.OK_OPTION);
    }

    /** Reset the TreeView and add the InfoDict and Root*/
    public void GrabDocRootAndInfo()
    {
        // Create nodes for the root(docNode), infoDict, and root
        PDFNode docNode = new PDFNode("Document");    //DocumentIcon
        PDFNode infoDictNode = new PDFNode("InfoDict", PDFDoc.getInfoDict());   //DictIcon
        PDFNode rootNode = new PDFNode("Root", PDFDoc.getRoot());           //DictIcon

        // Add infoDict and root to the root(docNode)
        docNode.add(infoDictNode);
        docNode.add(rootNode);

        // Acquire data from the PDF document

        // For each PDFObject within the InfoDict,
        // Create a new node passing in the text name of the key
        // and the actual object itself, then add the node to the infoDict

        // COMPARISON WITH THE .NET PDF OBJECT EXPLORER:
        // Each node has to be populated with its children before it is added to
        // the tree so that it will have an expansion handle; when the user
        // expands the node with the handle, we populate the SUB-nodes of the
        // node the user is interacting with. We never travel more than two
        // nodes down the document object tree; we terminaate if we reach
        // a container node with only scalars inside it.

        // In the .NET version, the top node of the tree is collapsible, so we
        // add the InfoDict and Root nodes to it in a collapsed state and rely
        // on the Expand Node event handler to populate the InfoDict and Root
        // subnodes.  Here, we have to add the first level of children right
        // away because the top level node does not have a collapse handle;
        // if we did not populate them immediately, they would never get
        // any children.

        // Note that using the Java iterator is only one way to extract nodes
        // from a PDFDict; as an alternative, we could have used a PDFObjectEnumProc.
        // See EnumObjectsForTree.java for an example of this.

        // For each key in the InfoDict
        for(PDFObject key : PDFDoc.getInfoDict().getKeys()) {
            // Verify that 'key' is really a PDFName (it doesn't have to be)
            if (key instanceof PDFName) {
                // Convert the key into an actual string
                String nodeText = ((PDFName)key).getValue();
                // Extract the object associated with this key
                PDFObject nodeObj = ((PDFDict)infoDictNode.getPDFObject()).get(nodeText);
                // Create a new PDFNode for this infoDict entry
                PDFNode n = new PDFNode(nodeText, nodeObj);
                // Insert the subnode into the infoDict node
                infoDictNode.add(n);
            }
        }

        // Repeat the process as before, but get the PDFObjects from the Root

        // For each key in the Root dictionary
        for(PDFObject key : PDFDoc.getRoot().getKeys()) {
            // Verify that 'key' is really a PDFName (it doesn't have to be)
            if (key instanceof PDFName){
                // Convert the key into an actual actual string
                String nodeText = ((PDFName)key).getValue();
                // Extract the object associated with this key
                // Comapre with above: here we use the PDFName (i.e. 'key') itself
                // as the key instead of the string it converts to
                PDFObject nodeObj = ((PDFDict)rootNode.getPDFObject()).get((PDFName)key);
                // Create a new PDFNode for this Root entry
                PDFNode n = new PDFNode(nodeText, nodeObj);
                rootNode.add(n);
            }
        }

        // Update the on screen display with the new data
        mainTree.setModel(new DefaultTreeModel(docNode));
    }

    // Initialize the data list view
    private void InitDataView() {
        // Set properties for the dataView
        dataView.enableInputMethods(false);
        // Create column headers
        SetHeadersForScalar();
    }

    // Set up data list view for scalar item
    private void SetHeadersForScalar() {
        // Create a table model and add column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type");
        model.addColumn("Value");
        dataView.setModel(model);
    }

    // Set up data list view for dictionary
    private void SetHeadersForDict(){
        // Create a table model and add column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Key");
        model.addColumn("Type");
        model.addColumn("Value");
        dataView.setModel(model);
    }

    // Set up data list view for array
    private void SetHeadersForArray(){
        // Create a table model and add column names
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Index");
        model.addColumn("Type");
        model.addColumn("Value");
        dataView.setModel(model);
    }

    /** Determines what happens when a user selects a node within the tree
     *
     * @param e
     */
    private void mainTreeAfterSelect(TreeSelectionEvent e) {

        //This is the node that is selected by clicking on the expand icon
        //or by selecting the actual node itself.
        PDFNode node = (PDFNode)e.getPath().getLastPathComponent();

        // Do nothing if user selects the root node
        if (node.isRoot())
            return;

        //Clear out the stream view's on screen
        streamViewRaw.setText("");
        streamViewCooked.setText("");

        // Node must be a PDFObject; extract it from the tree node
        PDFObject pdfObject = node.getPDFObject();

        // Select what to do based on the object subtype
        if(pdfObject instanceof PDFInteger){
            SetHeadersForScalar();

            //Grab the tableModel, create a vector to add to the model
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();
            java.util.Vector<Object> row = new java.util.Vector<Object>();

            //Add display elements to the vector
            row.addElement("Integer");
            row.addElement(((PDFInteger)pdfObject).getValue());

            //Add the vector and update the data table
            model.addRow(row);
            dataView.setModel(model);
        }
        else if(pdfObject instanceof PDFBoolean){
            SetHeadersForScalar();

            //Grab the tableModel, create a vector to add to the model
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();
            java.util.Vector<Object> row = new java.util.Vector<Object>();

            //Add display elements to the vector
            row.addElement("Bool");
            row.addElement(((PDFBoolean)pdfObject).getValue());

            //Add the vector and update the data table
            model.addRow(row);
            dataView.setModel(model);
        }
        else if(pdfObject instanceof PDFReal){
            SetHeadersForScalar();

            //Grab the tableModel, create a vector to add to the model
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();
            java.util.Vector<Object> row = new java.util.Vector<Object>();

            //Add display elements to the vector
            row.addElement("Real");
            row.addElement(((PDFReal)pdfObject).getValue());

            //Add the vector and update the data table
            model.addRow(row);
            dataView.setModel(model);
        }
        else if(pdfObject instanceof PDFName){
            SetHeadersForScalar();

            //Grab the tableModel, create a vector to add to the model
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();
            java.util.Vector<Object> row = new java.util.Vector<Object>();

            //Add display elements to the vector
            row.addElement("Name");
            row.addElement(((PDFName)pdfObject).getValue());

            //Add the vector and update the data table
            model.addRow(row);
            dataView.setModel(model);
        }
        else if (pdfObject instanceof PDFString){
            SetHeadersForScalar();

            //Grab the tableModel, create a vector to add to the model
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();
            java.util.Vector<Object> obj = new java.util.Vector<Object>();

            //Add display elements to the vector
            obj.addElement("String");
            obj.addElement(((PDFString)pdfObject).getValue());

            //Add the vector and update the data table
            model.addRow(obj);
            dataView.setModel(model);
        }
        else if(pdfObject instanceof PDFArray){
            SetHeadersForArray();
            //Create an array out of the pdfObject
            PDFArray pdfArray = (PDFArray)pdfObject;
            //Retrieve the current tableModel
            DefaultTableModel model = (DefaultTableModel)dataView.getModel();

            //For each object within the array
            for(int i = 0; i < pdfArray.getLength(); i++){
                //Create an object out of the array's child at position (i)
                PDFObject subObject = pdfArray.get(i);
                //Grab its returnValue array
                Object[] returnValue = GetObjectTypeAndValue(subObject);

                //create a Vector and add its position within the array,
                //and it's returnValue information to the vector
                //then add the item to the model
                if((Boolean)returnValue[0]){
                    java.util.Vector<Object> item = new java.util.Vector<Object>();
                    item.addElement("[" + Integer.toString(i) + "]");
                    item.addElement(returnValue[2]);
                    item.addElement(returnValue[1].toString());
                    model.addRow(item);
                }
            }
            //update the data table
            dataView.setModel(model);
        }
        else if(pdfObject instanceof PDFDict){
            SetHeadersForDict();
            //Enumerate the PDFObject as a PDFDict and populate the list view
            ((PDFDict)pdfObject).enumPDFObjects(new EnumObjectsForList((DefaultTableModel)dataView.getModel(),dataView));
        }
        else if(pdfObject instanceof PDFStream){
            SetHeadersForDict();
            //Create pdfobject as PDFStream
            PDFStream pdfStream = (PDFStream)pdfObject;
            //Put the dictionary into the list view
            pdfStream.getDict().enumPDFObjects(new EnumObjectsForList((DefaultTableModel)dataView.getModel(),dataView));

            //Declare an inputstream reader and a character buffer to hold the data
            java.io.InputStreamReader contentStream;
            char[] cBuff = new char[pdfStream.getLength()];

            //Now populate the raw stream view
            try{
                //Store the Unfiltered Stream as UTF8 encoded
                contentStream = new java.io.InputStreamReader(pdfStream.getUnfilteredStream(), "UTF8");
                //Read the content Stream into the properly sized character buffer
                contentStream.read(cBuff);
                //Store the character buffer into a string
                String s = String.copyValueOf(cBuff);
                //Add the unfiltered text to the window to display it
                streamViewRaw.setText(s);
                streamViewRaw.setCaretPosition(0); // Set the scroll window to the top
            }
            catch(java.io.IOException err){
                JOptionPane.showMessageDialog(null, "The unfiltered PDF Stream can not be read");
            }

            try{
                //Store the Filtered Stream as UTF8 encoded
                contentStream = new java.io.InputStreamReader(pdfStream.getFilteredStream(), "UTF8");
                //Store the character buffer into a string
                String s = ReadAllFromInputStreamReader(contentStream).replace("\n", "\r\n");
                //Add the filtered txt to the window to display it
                streamViewCooked.setText(s);
                streamViewCooked.setCaretPosition(0); // Set the scroll window to the top
            }
            catch(java.io.IOException err){
                JOptionPane.showMessageDialog(null, "The filtered PDF Stream can not be read");
            }
        }
        else
            JOptionPane.showMessageDialog(null,"This data type not recognized");
    }

    private String ReadAllFromInputStreamReader(java.io.InputStreamReader reader) {
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        int nRead = 0;

        while (nRead != -1) {
            try {
                nRead = reader.read(buf);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "The filtered PDF Stream can not be read");
                nRead = -1;
            }
            if (nRead != -1) {
                sb.append(buf, 0, nRead);
            }
        }
        return sb.toString();
    }
    /** Determines whether or not the object passed in is holding valid data
     *  and also what type of data it is, such as PDFInteger, PDFArray, etc...
     *
     * @param obj The PDFObject passed in
     * @return Object array holding a boolean and the value of the object
     */
    public static Object[] GetObjectTypeAndValue(PDFObject obj){

            Object[] returnObject = new Object[3];

            // Classify the object based on object type
            // returnObject[0] = boolean as to whether the data is valid
            // returnObject[1] = the actual value held within the object
            // returnObject[2] = the object type in text, used for icons
            if (obj instanceof PDFBoolean){
                returnObject[0] = true;
                returnObject[1] = ((PDFBoolean)obj).getValue();
                returnObject[2] = "Bool";
            }
            else if (obj instanceof PDFInteger){
                returnObject[0] = true;
                returnObject[1] = ((PDFInteger)obj).getValue();
                returnObject[2] = "Integer";
            }
            else if (obj instanceof PDFReal){
                returnObject[0] = true;
                returnObject[1] = ((PDFReal)obj).getValue();
                returnObject[2] = "Real";
            }
            else if (obj instanceof PDFName){
                returnObject[0] = true;
                returnObject[1] = ((PDFName)obj).getValue();
                returnObject[2] = "Name";
            }
            else if (obj instanceof PDFString){
                returnObject[0] = true;
                returnObject[1] = ((PDFString)obj).getValue();
                returnObject[2] = "String";
            }
            else if (obj instanceof PDFArray){
                returnObject[0] = true;
                returnObject[1] = "[...]";
                returnObject[2] = "Array";
            }
            else if (obj instanceof PDFDict){
                returnObject[0] = true;
                returnObject[1] = "<<...>>";
                returnObject[2] = "Dict";
            }
            else if (obj instanceof PDFStream){
                returnObject[0] = true;
                returnObject[1] = "<<...>>";
                returnObject[2] = "Stream";
            }
            else {
                returnObject[0] = false;
                returnObject[1] = "???";
                returnObject[2] = "Unknown";
            }
            return returnObject;
    }

    /** Clears Data and closes the PDF file
     *
     */
    private void closePDF(){
        //Set the treeModel of mainTree to null
        ((DefaultTreeModel)mainTree.getModel()).setRoot(null);
        //Close the PDFDoc
        PDFDoc.close();
    }

    // Generic registration with the Mac OS X application menu
    // Checks the platform, then attempts to register with the Apple EAWT
    // See OSXAdapter.java to see how this is done without directly referencing any Apple APIs
    public void registerForMacOSXEvents() {
        if (MAC_OS_X) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quit", (Class[])null));
                OSXAdapter.setFileHandler(this, getClass().getDeclaredMethod("openPDFFileFromPath", new Class[] { String.class }));
            } catch (Exception e) {
                System.err.println("Error while loading the OSXAdapter:");
                e.printStackTrace();
            }
        }
    }

    // General quit handler; fed to the OSXAdapter as the method to call when a system quit event occurs
    // A quit event is triggered by Cmd-Q, selecting Quit from the application or Dock menu, or logging out
    public boolean quit() {
        if (library != null) {
            library.delete();
            library = null;
        }
        return true;
    }

    public void openPDFFileFromPath(String path)
    {
        openPDFFile(new File(path));
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        if (MAC_OS_X)
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PDFObjectExplorer().setVisible(true);
            }
        });
    }

    private String password;
    private Document PDFDoc;                // The PDF used in the program
    private String currFilePath = null;     // The path & name of the current document

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable dataView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree mainTree;
    private javax.swing.JButton openFile;
    private javax.swing.JButton refreshFile;
    private javax.swing.JTextPane streamViewCooked;
    private javax.swing.JTextPane streamViewRaw;
    // End of variables declaration//GEN-END:variables

}


