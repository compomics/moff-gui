/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.moff.gui.view;

import com.compomics.moff.gui.control.step.MoFFApexStep;
import com.compomics.moff.gui.control.step.MoFFMBRStep;
import com.compomics.moff.gui.view.filter.CpsFileFilter;
import com.compomics.moff.gui.view.filter.RawFileFilter;
import com.compomics.moff.gui.view.filter.TabSeparatedFileFilter;
import com.compomics.pladipus.core.model.processing.ProcessingStep;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * The GUI main controller.
 *
 * @author Niels Hulstaert
 */
public class MainController {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private static final String LINK_SEPARATOR = "\t";

    private static final String FIRST_PANEL = "firstPanel";
    private static final String SECOND_PANEL = "secondPanel";
    private static final String THIRD_PANEL = "thirdPanel";
    private static final String LAST_PANEL = "lastPanel";

    /**
     * Model fields.
     */
    private final DefaultMutableTreeNode fileLinkerRootNode = new DefaultMutableTreeNode("RAW - identification file links");
    private final DefaultTreeModel fileLinkerTreeModel = new DefaultTreeModel(fileLinkerRootNode);
    private MoffRunSwingWorker moffRunSwingWorker;
    /**
     * The moFF output directory.
     */
    private File outPutDirectory;
    /**
     * The views of this controller.
     */
    private final MainFrame mainFrame = new MainFrame();

    /**
     * Initialize the controller.
     */
    public void init() {
        //select directories only
        mainFrame.getOutputDirectoryChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //select only files
        mainFrame.getRawFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        mainFrame.getCpsFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        mainFrame.getTsvFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        //disable multiple file selection
        mainFrame.getRawFileChooser().setMultiSelectionEnabled(false);
        mainFrame.getCpsFileChooser().setMultiSelectionEnabled(false);
        mainFrame.getTsvFileChooser().setMultiSelectionEnabled(false);
        //set file filters
        mainFrame.getRawFileChooser().setFileFilter(new RawFileFilter());
        mainFrame.getCpsFileChooser().setFileFilter(new CpsFileFilter());
        mainFrame.getTsvFileChooser().setFileFilter(new TabSeparatedFileFilter());

        //set file linker tree model
        mainFrame.getFileLinkerTree().setRootVisible(true);
        mainFrame.getFileLinkerTree().setModel(fileLinkerTreeModel);

        //add log text area appender
        LogTextAreaAppender logTextAreaAppender = new LogTextAreaAppender();
        logTextAreaAppender.setThreshold(Level.INFO);
        logTextAreaAppender.setImmediateFlush(true);
        PatternLayout layout = new org.apache.log4j.PatternLayout();
        layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} - %m%n");
        logTextAreaAppender.setLayout(layout);

        LOGGER.addAppender(logTextAreaAppender);
        LOGGER.setLevel((Level) Level.INFO);

        logTextAreaAppender.setLogTextArea(mainFrame.getLogTextArea());

        mainFrame.getLogTextArea().setText("..." + System.lineSeparator());

        //select the PeptideShaker radio button
        mainFrame.getPeptideShakerRadioButton().setSelected(true);
        //select the APEX radio button
        mainFrame.getApexModeRadioButton().setSelected(true);

        //disable outliers threshold text field
        mainFrame.getOutlierThresholdTextField().setEnabled(false);

        //show info
        updateInfo("Click on \"proceed\" to link the RAW and identification files.");

        //add action listeners
        mainFrame.getOutputDirectoryChooseButton().addActionListener(e -> {
            int returnVal = mainFrame.getOutputDirectoryChooser().showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                outPutDirectory = mainFrame.getOutputDirectoryChooser().getSelectedFile();

                mainFrame.getOutputDirectoryTextField().setText(outPutDirectory.getAbsolutePath());
            }
        });

        mainFrame.getPeptideShakerRadioButton().addActionListener(e -> {
            removeAllIdentificationFiles();
        });

        mainFrame.getTabSeparatedRadioButton().addActionListener(e -> {
            removeAllIdentificationFiles();
        });

        mainFrame.getAddFileButton().addActionListener(e -> {
            TreeSelectionModel selectionModel = mainFrame.getFileLinkerTree().getSelectionModel();
            int selectionCount = selectionModel.getSelectionCount();
            int returnVal;
            switch (selectionCount) {
                case 0:
                    returnVal = mainFrame.getRawFileChooser().showOpenDialog(mainFrame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File rawFile = mainFrame.getRawFileChooser().getSelectedFile();
                        DefaultMutableTreeNode rawFileNode = new DefaultMutableTreeNode(rawFile);
                        fileLinkerTreeModel.insertNodeInto(rawFileNode, fileLinkerRootNode, fileLinkerTreeModel.getChildCount(fileLinkerRootNode));

                        //expand the tree
                        mainFrame.getFileLinkerTree().expandPath(new TreePath(fileLinkerRootNode));
                    }
                    break;
                case 1:
                    //check which level has been selected
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectionModel.getLeadSelectionPath().getLastPathComponent();
                    int level = selectedNode.getLevel();
                    switch (level) {
                        case 0:
                            returnVal = mainFrame.getRawFileChooser().showOpenDialog(mainFrame);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File rawFile = mainFrame.getRawFileChooser().getSelectedFile();
                                DefaultMutableTreeNode rawFileNode = new DefaultMutableTreeNode(rawFile);
                                fileLinkerTreeModel.insertNodeInto(rawFileNode, fileLinkerRootNode, fileLinkerTreeModel.getChildCount(fileLinkerRootNode));

                                //expand the tree
                                mainFrame.getFileLinkerTree().expandPath(new TreePath(fileLinkerRootNode));
                            }
                            break;
                        case 1:
                            if (selectedNode.getChildCount() == 0) {
                                returnVal = getCurrentImportFileChooser().showOpenDialog(mainFrame);
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    File importFile = getCurrentImportFileChooser().getSelectedFile();

                                    DefaultMutableTreeNode importFileNode = new DefaultMutableTreeNode(importFile);
                                    fileLinkerTreeModel.insertNodeInto(importFileNode, selectedNode, fileLinkerTreeModel.getChildCount(selectedNode));

                                    //expand the tree
                                    mainFrame.getFileLinkerTree().expandPath(new TreePath(selectedNode.getPath()));
                                }
                            } else {
                                List<String> messages = new ArrayList<>();
                                messages.add("The RAW file is already linked to an identification file."
                                        + System.lineSeparator()
                                        + "Please remove the identification file before adding another one.");
                                showMessageDialog("Identification file addition", messages, JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        default:
                            List<String> messages = new ArrayList<>();
                            messages.add("You have selected an identification file."
                                    + System.lineSeparator()
                                    + "Please select a RAW file to add an identification file to.");
                            showMessageDialog("Identification file addition", messages, JOptionPane.WARNING_MESSAGE);
                            break;
                    }
                    break;
                default:
                    List<String> messages = new ArrayList<>();
                    messages.add("Please select only one RAW file to add an identification file to.");
                    showMessageDialog("Identification file addition", messages, JOptionPane.WARNING_MESSAGE);
                    break;
            }
        });

        mainFrame.getDeleteFileButton().addActionListener(e -> {
            TreeSelectionModel selectionModel = mainFrame.getFileLinkerTree().getSelectionModel();

            if (isValidDeleteSelection()) {
                for (TreePath treePath : selectionModel.getSelectionPaths()) {
                    DefaultMutableTreeNode fileLinkerNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    fileLinkerTreeModel.removeNodeFromParent(fileLinkerNode);
                }
            } else {
                List<String> messages = new ArrayList<>();
                messages.add("Please select RAW or identification file(s) to delete.");
                showMessageDialog("Identification file addition", messages, JOptionPane.WARNING_MESSAGE);
            }
        });

        mainFrame.getFilterOutliersCheckBox().addActionListener(e -> {
            mainFrame.getOutlierThresholdTextField().setEnabled(mainFrame.getFilterOutliersCheckBox().isSelected());
        });

        mainFrame.getClearButton().addActionListener(e -> {
            mainFrame.getLogTextArea().setText("..." + System.lineSeparator());
        });

        mainFrame.getProceedButton().addActionListener(e -> {
            String currentCardName = getVisibleChildComponent(mainFrame.getTopPanel());
            switch (currentCardName) {
                case FIRST_PANEL:
                    getCardLayout().show(mainFrame.getTopPanel(), SECOND_PANEL);
                    onCardSwitch();
                    break;
                case SECOND_PANEL:
                    List<String> secondPanelValidationMessages = validateSecondPanel();
                    if (secondPanelValidationMessages.isEmpty()) {
                        //check the moFF mode
                        mainFrame.getMatchingBetweenRunsSettingsPanel().setVisible(mainFrame.getMatchingBetweenRunsRadioButton().isSelected());
                        getCardLayout().show(mainFrame.getTopPanel(), THIRD_PANEL);
                        onCardSwitch();
                    } else {
                        showMessageDialog("Validation failure", secondPanelValidationMessages, JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                default:
                    break;
            }
        });

        mainFrame.getBackButton().addActionListener(e -> {
            String currentCardName = getVisibleChildComponent(mainFrame.getTopPanel());
            switch (currentCardName) {
                case SECOND_PANEL:
                    getCardLayout().show(mainFrame.getTopPanel(), FIRST_PANEL);
                    break;
                default:
                    getCardLayout().previous(mainFrame.getTopPanel());
                    break;
            }
            onCardSwitch();
        });

        mainFrame.getStartButton().addActionListener(e -> {
            List<String> thirdPanelValidationMessages = validateThirdPanel();
            if (thirdPanelValidationMessages.isEmpty()) {
                getCardLayout().show(mainFrame.getTopPanel(), LAST_PANEL);
                onCardSwitch();

                //run the moFF swing worker
                moffRunSwingWorker = new MoffRunSwingWorker();
                moffRunSwingWorker.execute();
            } else {
                showMessageDialog("Validation failure", thirdPanelValidationMessages, JOptionPane.WARNING_MESSAGE);
            }
        });

        mainFrame.getCancelButton().addActionListener(e -> {
            if (moffRunSwingWorker != null) {
                moffRunSwingWorker.cancel(true);
                moffRunSwingWorker = null;
            }
            mainFrame.dispose();
        });

    }

    /**
     * Show the view of this controller.
     */
    public void showView() {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * This method takes a file a writes the file links to it. The format is
     * 'RAW_file_path'-TAB-'identification_file_path'.
     *
     * @param fileLinksFile the file where the file links will be written to
     */
    private void writeFileLinksToFile(File fileLinksFile) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(fileLinksFile.toPath())) {
            //iterate over the nodes
            Enumeration children = fileLinkerRootNode.children();
            while (children.hasMoreElements()) {
                DefaultMutableTreeNode rawFileNode = (DefaultMutableTreeNode) children.nextElement();
                //write to the file
                writer.write(((File) rawFileNode.getUserObject()).getAbsolutePath()
                        + LINK_SEPARATOR
                        + ((File) ((DefaultMutableTreeNode) rawFileNode.getChildAt(0)).getUserObject()).getAbsolutePath());
                if (children.hasMoreElements()) {
                    writer.newLine();
                }
            }
        }
    }

    /**
     * Show a message dialog with a text area if the messages list contains more
     * than one message.
     *
     * @param title the dialog title
     * @param messages the dialog messages list
     * @param messageType the dialog message type
     */
    private void showMessageDialog(final String title, final List<String> messages, final int messageType) {
        if (messages.size() > 1) {
            String message = messages.stream().collect(Collectors.joining(System.lineSeparator()));

            //add message to JTextArea
            JTextArea textArea = new JTextArea(message);
            //put JTextArea in JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            scrollPane.getViewport().setOpaque(false);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            JOptionPane.showMessageDialog(mainFrame.getContentPane(), scrollPane, title, messageType);
        } else {
            JOptionPane.showMessageDialog(mainFrame.getContentPane(), messages.get(0), title, messageType);
        }
    }

    /**
     * Show the correct info and disable/enable the right buttons when switching
     * between cards.
     */
    private void onCardSwitch() {
        String currentCardName = getVisibleChildComponent(mainFrame.getTopPanel());
        switch (currentCardName) {
            case FIRST_PANEL:
                mainFrame.getBackButton().setEnabled(false);
                mainFrame.getProceedButton().setEnabled(true);
                mainFrame.getStartButton().setEnabled(false);
                //show info
                updateInfo("Click on \"proceed\" to link the RAW and identification files.");
                break;
            case SECOND_PANEL:
                mainFrame.getBackButton().setEnabled(true);
                mainFrame.getProceedButton().setEnabled(true);
                mainFrame.getStartButton().setEnabled(false);
                //show info
                updateInfo("Click on \"proceed\" to go to the moFF settings.");
                break;
            case THIRD_PANEL:
                mainFrame.getBackButton().setEnabled(true);
                mainFrame.getProceedButton().setEnabled(false);
                mainFrame.getStartButton().setEnabled(true);
                //show info
                updateInfo("Click on \"start\" to run moFF.");
                break;
            case LAST_PANEL:
                mainFrame.getBackButton().setEnabled(false);
                mainFrame.getProceedButton().setEnabled(false);
                mainFrame.getStartButton().setEnabled(false);
                //show info
                updateInfo("");
                break;
            default:
                break;
        }
    }

    /**
     * Update the info label.
     *
     * @param message the info message
     */
    private void updateInfo(String message) {
        mainFrame.getInfoLabel().setText(message);
    }

    /**
     * Validate the user input in the second panel.
     *
     * @return the list of validation messages.
     */
    private List<String> validateSecondPanel() {
        List<String> validationMessages = new ArrayList<>();

        //first check if all RAW files are linked to an identification file
        if (areAllRawFilesLinked()) {
            int childCount = fileLinkerRootNode.getChildCount();
            //in APEX mode, at least one RAW has to be linked to an identification file
            if (mainFrame.getApexModeRadioButton().isSelected()) {
                if (childCount == 0) {
                    validationMessages.add("Add at least one RAW and identification file.");
                }
            } else if (childCount < 2) {
                validationMessages.add("Add at least two RAW and identification files.");
            }
        } else {
            validationMessages.add("Each RAW file has to be linked to one and only one identification file.");
        }

        return validationMessages;
    }

    /**
     * Validate the user input in the third panel.
     *
     * @return the list of validation messages.
     */
    private List<String> validateThirdPanel() {
        List<String> validationMessages = new ArrayList<>();

        if (mainFrame.getXicRetentionTimeWindowTextField().getText().isEmpty()) {
            validationMessages.add("Please provide an XiC retention time window value.");
        } else {
            try {
                Double xicRententionTimeWindowValue = Double.valueOf(mainFrame.getXicRetentionTimeWindowTextField().getText());
                if (xicRententionTimeWindowValue < 0.0) {
                    validationMessages.add("Please provide a positive XiC retention time window value.");
                }
            } catch (NumberFormatException nfe) {
                validationMessages.add("Please provide a numeric XiC retention time window value.");
            }
        }
        if (mainFrame.getPeakRetentionTimeWindowTextField().getText().isEmpty()) {
            validationMessages.add("Please provide a retention time window value.");
        } else {
            try {
                Double peakRetentionTimeWindowValue = Double.valueOf(mainFrame.getPeakRetentionTimeWindowTextField().getText());
                if (peakRetentionTimeWindowValue < 0.0) {
                    validationMessages.add("Please provide a positive peak retention time window value.");
                }
            } catch (NumberFormatException nfe) {
                validationMessages.add("Please provide a numeric peak retention time window value.");
            }
        }
        if (mainFrame.getPrecursorMassToleranceTextField().getText().isEmpty()) {
            validationMessages.add("Please provide precursor mass tolerance value.");
        } else {
            try {
                Double tolerance = Double.valueOf(mainFrame.getPrecursorMassToleranceTextField().getText());
                if (tolerance < 0.0) {
                    validationMessages.add("Please provide a positive precursor tolerance value.");
                }
            } catch (NumberFormatException nfe) {
                validationMessages.add("Please provide a numeric precursor tolerance value.");
            }
        }

        if (mainFrame.getMatchingBetweenRunsRadioButton().isSelected()) {
            if (mainFrame.getMatchedPeaksRetentionTimeWindowTextField().getText().isEmpty()) {
                validationMessages.add("Please provide a matched peaks retention time window value.");
            }
            if (mainFrame.getFilterOutliersCheckBox().isSelected()) {
                if (mainFrame.getOutlierThresholdTextField().getText().isEmpty()) {
                    validationMessages.add("Please provide an outlier threshold value.");
                } else {
                    try {
                        Double outlierThresholdValue = Double.valueOf(mainFrame.getMatchingBetweenRunsRadioButton().getText());
                        if (outlierThresholdValue < 0.0) {
                            validationMessages.add("Please provide a positive outlier threshold value.");
                        }
                    } catch (NumberFormatException nfe) {
                        validationMessages.add("Please provide a numeric outlier threshold value.");
                    }
                }
            }
        }

        return validationMessages;
    }

    /**
     * Check if the delete selection is valid; the selection has to contain at
     * least one node and it can't be the root node.
     *
     * @return the boolean result
     */
    private boolean isValidDeleteSelection() {
        boolean isValidDeleteSelection = true;

        TreePath[] selectionPaths = mainFrame.getFileLinkerTree().getSelectionModel().getSelectionPaths();
        //check if one or more nodes are selected
        if (selectionPaths.length >= 1) {
            for (TreePath selectionPath : selectionPaths) {
                if (selectionPath.getParentPath() == null) {
                    isValidDeleteSelection = false;
                    break;
                }
            }
        } else {
            isValidDeleteSelection = false;
        }

        return isValidDeleteSelection;
    }

    /**
     * Get the correct file choose depending on the import data type.
     *
     * @return the current file chooser.
     */
    private JFileChooser getCurrentImportFileChooser() {
        if (mainFrame.getPeptideShakerRadioButton().isSelected()) {
            return mainFrame.getCpsFileChooser();
        } else {
            return mainFrame.getTsvFileChooser();
        }
    }

    /**
     * Remove all identification files from the file linker tree.
     */
    private void removeAllIdentificationFiles() {
        //remove all identification files from the file linker tree
        Enumeration breadthFirstEnumeration = fileLinkerRootNode.breadthFirstEnumeration();
        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) breadthFirstEnumeration.nextElement();
            if (!node.isRoot() && node.getParent().equals(fileLinkerRootNode)) {
                if (node.getChildCount() == 0) {
                    node.removeAllChildren();
                }
            }
        }
    }

    /**
     * Get the name of the visible child component. Returns null if no
     * components are visible.
     *
     * @param parentContainer the parent container
     * @return the visible component name
     */
    private String getVisibleChildComponent(final Container parentContainer) {
        String visibleComponentName = null;

        for (Component component : parentContainer.getComponents()) {
            if (component.isVisible()) {
                visibleComponentName = component.getName();
                break;
            }
        }

        return visibleComponentName;
    }

    /**
     * Check if all RAW files in the tree have an identification file child.
     *
     * @return whether all RAW files are linked or not
     */
    private boolean areAllRawFilesLinked() {
        boolean areAllRawFilesLinked = true;

        Enumeration breadthFirstEnumeration = fileLinkerRootNode.breadthFirstEnumeration();
        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) breadthFirstEnumeration.nextElement();
            if (!node.isRoot() && node.getParent().equals(fileLinkerRootNode)) {
                if (node.getChildCount() == 0) {
                    areAllRawFilesLinked = false;
                    break;
                }
            }
        }

        return areAllRawFilesLinked;
    }

    /**
     * Get the card layout.
     *
     * @return the CardLayout
     */
    private CardLayout getCardLayout() {
        return (CardLayout) mainFrame.getTopPanel().getLayout();
    }

    /**
     * MoFF Swing worker for running moFF.
     */
    private class MoffRunSwingWorker extends SwingWorker<Void, Void> {

        @Override
        protected Void doInBackground() throws Exception {
            LOGGER.info("starting spectrum similarity score pipeline");
            System.out.println("start ----------------------");
            Thread.sleep(5000);
            ProcessingStep moffStep;
            HashMap<String, String> moffParameters;
            if (mainFrame.getApexModeRadioButton().isSelected()) {
                //run apex step
                moffStep = new MoFFApexStep();
                moffParameters = getApexParametersFromGUI();
            } else {
                //run MBR step
                moffStep = new MoFFMBRStep();
                moffParameters = getMBRParametersFromGUI();
            }
            moffStep.setParameters(moffParameters);
            if (moffStep.doAction()) {
                System.out.println("finish ----------------------");
            } else {
                System.out.println("an error occurred ----------------------");
            }
            return null;
        }

        @Override
        protected void done() {
            try {
                get();
                LOGGER.info("finished moFF run");
                List<String> messages = new ArrayList<>();
                messages.add("The score pipeline has finished.");
                showMessageDialog("moFF run completed", messages, JOptionPane.INFORMATION_MESSAGE);
            } catch (CancellationException ex) {
                LOGGER.info("the moFF run was cancelled");
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
                List<String> messages = new ArrayList<>();
                messages.add(ex.getMessage());
                showMessageDialog("Unexpected error", messages, JOptionPane.ERROR_MESSAGE);
            } finally {

            }
        }

        private HashMap<String, String> getApexParametersFromGUI() {
            HashMap<String, String> parameters = new HashMap<>();
            //@ToDo fill the parameters
            return parameters;
        }

        private HashMap<String, String> getMBRParametersFromGUI() {
            HashMap<String, String> parameters = new HashMap<>();
            //@ToDo fill the parameters
            return parameters;
        }

    }

}
