package com.compomics.moff.gui.view;

import com.compomics.pladipus.moff.logic.step.MoFFPeptideShakerConversionStep;
import com.compomics.moff.gui.control.step.MoFFStep;
import com.compomics.moff.gui.config.ConfigHolder;
import com.compomics.pladipus.moff.logic.util.FileChangeScanner;
import com.compomics.moff.gui.view.filter.CpsFileFilter;
import com.compomics.moff.gui.view.filter.FastaAndMgfFileFilter;
import com.compomics.moff.gui.view.filter.RawFileFilter;
import com.compomics.moff.gui.view.filter.TabSeparatedFileFilter;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
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
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

/**
 * The GUI main controller.
 *
 * @author Niels Hulstaert
 * @author Kenneth Verheggen
 */
public class MainController {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    /**
     * The separator between file links
     */
    private static final String LINK_SEPARATOR = "\t";

    //card layout panels
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
     * The PeptideShaker directory.
     */
    private File peptideShakerDirectory;
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
        mainFrame.getPeptideShakerDirectoryChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        //select only files
        mainFrame.getRawFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        mainFrame.getCpsFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        mainFrame.getTsvFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        mainFrame.getFastaAndMgfFileChooser().setFileSelectionMode(JFileChooser.FILES_ONLY);
        //enable multple file selection
        mainFrame.getRawFileChooser().setMultiSelectionEnabled(true);
        //disable multiple file selection
        mainFrame.getOutputDirectoryChooser().setMultiSelectionEnabled(false);
        mainFrame.getPeptideShakerDirectoryChooser().setMultiSelectionEnabled(false);
        mainFrame.getCpsFileChooser().setMultiSelectionEnabled(false);
        mainFrame.getTsvFileChooser().setMultiSelectionEnabled(false);
        mainFrame.getFastaAndMgfFileChooser().setMultiSelectionEnabled(false);
        //set file filters
        mainFrame.getRawFileChooser().setFileFilter(new RawFileFilter());
        mainFrame.getCpsFileChooser().setFileFilter(new CpsFileFilter());
        mainFrame.getTsvFileChooser().setFileFilter(new TabSeparatedFileFilter());
        mainFrame.getFastaAndMgfFileChooser().setFileFilter(new FastaAndMgfFileFilter());

        //set file linker tree model
        mainFrame.getFileLinkerTree().setRootVisible(true);
        mainFrame.getFileLinkerTree().setModel(fileLinkerTreeModel);

        mainFrame.setTitle("moFF GUI " + ConfigHolder.getInstance().getString("moff_gui.version", "N/A"));

        //set PeptideShaker directory
        peptideShakerDirectory = new File(ConfigHolder.getInstance().getString("peptide_shaker.directory"));
        mainFrame.getPeptideShakerDirectoryTextField().setText(peptideShakerDirectory.getAbsolutePath());

        //get the gui appender for setting the log text area
        LogTextAreaAppender logTextAreaAppender = (LogTextAreaAppender) Logger.getRootLogger().getAppender("gui");
        logTextAreaAppender.setLogTextArea(mainFrame.getLogTextArea());

        mainFrame.getLogTextArea().setText("..." + System.lineSeparator());

        //select the PeptideShaker radio button
        mainFrame.getPeptideShakerRadioButton().setSelected(true);
        //select the APEX radio button
        mainFrame.getApexModeRadioButton().setSelected(true);

        //show info
        updateInfo("Click on \"proceed\" to link the RAW and identification files.");

        //add action listeners
        mainFrame.getPeptideShakerDirectoryChooseButton().addActionListener(e -> {
            int returnVal = mainFrame.getPeptideShakerDirectoryChooser().showOpenDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                peptideShakerDirectory = mainFrame.getPeptideShakerDirectoryChooser().getSelectedFile();
                mainFrame.getPeptideShakerDirectoryTextField().setText(peptideShakerDirectory.getAbsolutePath());
            }
        });

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
                        for (File rawFile : mainFrame.getRawFileChooser().getSelectedFiles()) {
                            DefaultMutableTreeNode rawFileNode = new DefaultMutableTreeNode(rawFile);
                            fileLinkerTreeModel.insertNodeInto(rawFileNode, fileLinkerRootNode, fileLinkerTreeModel.getChildCount(fileLinkerRootNode));
                        }

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
                                returnVal = getCurrentImportFileChooser(level).showOpenDialog(mainFrame);
                                if (returnVal == JFileChooser.APPROVE_OPTION) {
                                    File importFile = getCurrentImportFileChooser(level).getSelectedFile();

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
                        case 2:
                            if (mainFrame.getPeptideShakerRadioButton().isSelected()) {
                                if (selectedNode.getChildCount() != 2) {
                                    returnVal = getCurrentImportFileChooser(level).showOpenDialog(mainFrame);
                                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                                        File fastaOrMgfFile = getCurrentImportFileChooser(level).getSelectedFile();

                                        //check if the PeptideShaker file is already linked to a file of the same type (FASTA or MGF)
                                        if (selectedNode.getChildCount() == 0) {
                                            DefaultMutableTreeNode importFileNode = new DefaultMutableTreeNode(fastaOrMgfFile);
                                            fileLinkerTreeModel.insertNodeInto(importFileNode, selectedNode, fileLinkerTreeModel.getChildCount(selectedNode));
                                        } else {
                                            String alreadyPresentExtension = FilenameUtils.getExtension(((File) ((DefaultMutableTreeNode) selectedNode.getChildAt(0)).getUserObject()).getName());
                                            if (!alreadyPresentExtension.equals(FilenameUtils.getExtension(fastaOrMgfFile.getName()))) {
                                                DefaultMutableTreeNode importFileNode = new DefaultMutableTreeNode(fastaOrMgfFile);
                                                fileLinkerTreeModel.insertNodeInto(importFileNode, selectedNode, fileLinkerTreeModel.getChildCount(selectedNode));
                                            } else {
                                                List<String> messages = new ArrayList<>();
                                                messages.add("The PeptideShaker cpsx file is already linked to a "
                                                        + alreadyPresentExtension
                                                        + " file."
                                                        + System.lineSeparator()
                                                        + "Please remove that file before adding another one.");
                                                showMessageDialog("FASTA/MGF file addition", messages, JOptionPane.WARNING_MESSAGE);
                                            }
                                        }

                                        //expand the tree
                                        mainFrame.getFileLinkerTree().expandPath(new TreePath(selectedNode.getPath()));
                                    }
                                } else {
                                    List<String> messages = new ArrayList<>();
                                    messages.add("The PeptideShaker cpsx file is already linked to a FASTA and an MGF file."
                                            + System.lineSeparator()
                                            + "Please remove these files before adding other ones.");
                                    showMessageDialog("FASTA/MGF file addition", messages, JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                List<String> messages = new ArrayList<>();
                                messages.add("You have selected a tab separated identification file."
                                        + System.lineSeparator()
                                        + "Please select a RAW file to add an identification file to.");
                                showMessageDialog("Identification file addition", messages, JOptionPane.WARNING_MESSAGE);
                            }
                            break;
                        default:
                            List<String> messages = new ArrayList<>();
                            messages.add("You have selected a FASTA or an MGF file."
                                    + System.lineSeparator()
                                    + "Please select a PeptideShaker cpsx file to add a FASTA or an MGF file to.");
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
                    List<String> firstPanelValidationMessages = validateFirstPanel();
                    if (firstPanelValidationMessages.isEmpty()) {
                        getCardLayout().show(mainFrame.getTopPanel(), SECOND_PANEL);
                        onCardSwitch();
                    } else {
                        showMessageDialog("Validation failure", firstPanelValidationMessages, JOptionPane.WARNING_MESSAGE);
                    }
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
                MoFFStep step = moffRunSwingWorker.getStep();
                if (step != null) {
                    step.stop();
                }
                logTextAreaAppender.close();
                FileChangeScanner fileChangeScanner = moffRunSwingWorker.getFileChangeScanner();
                if (fileChangeScanner != null) {
                    fileChangeScanner.stop();
                }
                moffRunSwingWorker = null;
            }
            mainFrame.dispose();
        });

        //load the parameters from the properties file
        loadParameterValues();
        //call onCardSwitch
        onCardSwitch();
    }

    /**
     * Show the view of this controller.
     */
    public void showView() {
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Load the parameter values from the properties file and set them in the
     * matching fields.
     */
    private void loadParameterValues() {
        mainFrame.getXicRetentionTimeWindowTextField().setText(ConfigHolder.getInstance().getString("retention_time.xic.window"));
        mainFrame.getPeakRetentionTimeWindowTextField().setText(ConfigHolder.getInstance().getString("retention_time.peak.window"));
        mainFrame.getPrecursorMassToleranceTextField().setText(ConfigHolder.getInstance().getString("precursor_mass.tolerane"));
        mainFrame.getMatchedPeaksRetentionTimeWindowTextField().setText(ConfigHolder.getInstance().getString("retention_time.matched_peak.window"));
        mainFrame.getCombinationWeighingCheckBox().setSelected(ConfigHolder.getInstance().getBoolean("combination_weighing"));
        mainFrame.getFilterOutliersCheckBox().setSelected(ConfigHolder.getInstance().getBoolean("outliers_filtering"));
        mainFrame.getOutlierThresholdTextField().setEnabled(mainFrame.getFilterOutliersCheckBox().isSelected());
        mainFrame.getOutlierThresholdTextField().setText(Double.toString(ConfigHolder.getInstance().getDouble("outliers_filtering.window")));
    }

    /**
     * Get the file link map in case of tab-separated identification files (key:
     * RAW file absolute path; value: tab-separated file absolute path).
     *
     * @return the link map
     */
    private Map<File, File> getRawTabSeparatedLinks() {
        Map<File, File> links = new HashMap<>();

        //iterate over the nodes
        Enumeration children = fileLinkerRootNode.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode rawFileNode = (DefaultMutableTreeNode) children.nextElement();
            File rawFile = ((File) ((DefaultMutableTreeNode) rawFileNode).getUserObject());
            File tsvFile = ((File) ((DefaultMutableTreeNode) rawFileNode.getChildAt(0)).getUserObject());
            links.put(rawFile, tsvFile);
        }

        return links;
    }

    /**
     * Get the file link map in case of PeptideShaker identification files (key:
     * RAW file absolute path; value: an array with 3 PeptideShaker related
     * files (first element: the PeptideShaker .cpsx file; second element: the
     * FASTA file; third element: the MGF file)).
     *
     * @return the link map
     */
    private Map<File, File[]> getRawPeptideShakerLinks() {
        Map<File, File[]> links = new HashMap<>();

        //iterate over the nodes
        Enumeration children = fileLinkerRootNode.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode rawFileNode = (DefaultMutableTreeNode) children.nextElement();
            File rawFile = ((File) ((DefaultMutableTreeNode) rawFileNode).getUserObject());

            DefaultMutableTreeNode peptideShakerNode = (DefaultMutableTreeNode) rawFileNode.getChildAt(0);
            File peptideShakerFile = ((File) (peptideShakerNode).getUserObject());
            File[] peptideShakerFiles = new File[3];
            peptideShakerFiles[0] = peptideShakerFile;

            //add FASTA and MGF files
            Enumeration peptideShakerChildren = peptideShakerNode.children();
            while (peptideShakerChildren.hasMoreElements()) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) peptideShakerChildren.nextElement();
                File fastaOrMgfFile = ((File) (childNode).getUserObject());
                if (FilenameUtils.getExtension(fastaOrMgfFile.getName()).equals("fasta")) {
                    peptideShakerFiles[1] = fastaOrMgfFile;
                } else {
                    peptideShakerFiles[2] = fastaOrMgfFile;
                }
            }

            links.put(rawFile, peptideShakerFiles);
        }

        return links;
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
     * Validate the user input in the first panel.
     *
     * @return the list of validation messages.
     */
    private List<String> validateFirstPanel() {
        List<String> validationMessages = new ArrayList<>();

        //check if an output directory has been chosen
        if (mainFrame.getOutputDirectoryTextField().getText().isEmpty()) {
            validationMessages.add("Please choose an output directory.");
        }
        if (mainFrame.getPeptideShakerRadioButton().isSelected() && !mainFrame.getPeptideShakerDirectoryTextField().getText().isEmpty()) {
            //check if the PeptideShaker directory exists
            File peptideShakerDirectory = new File(mainFrame.getPeptideShakerDirectoryTextField().getText());
            if (!peptideShakerDirectory.exists()) {
                validationMessages.add("The specified PeptideShaker directory location doesn't exist.");
            }
        } else {
            validationMessages.add("Please provide the PeptideShaker directory location.");
        }

        return validationMessages;
    }

    /**
     * Validate the user input in the second panel.
     *
     * @return the list of validation messages.
     */
    private List<String> validateSecondPanel() {
        List<String> validationMessages = new ArrayList<>();

        //first check if all RAW files are linked to an identification file
        if (areAllFilesLinked()) {
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
            if (mainFrame.getPeptideShakerRadioButton().isSelected()) {
                validationMessages.add("Each PeptideShaker cpsx file has to be linked to a FASTA and an MGF file.");
            }
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
                        Double outlierThresholdValue = Double.valueOf(mainFrame.getOutlierThresholdTextField().getText());
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
     * Get the correct file choose depending on the import data type and the
     * tree level.
     *
     * @param the current node level
     * @return the current file chooser.
     */
    private JFileChooser getCurrentImportFileChooser(int level) {
        if (mainFrame.getPeptideShakerRadioButton().isSelected()) {
            if (level == 1) {
                return mainFrame.getCpsFileChooser();
            } else {
                return mainFrame.getFastaAndMgfFileChooser();
            }
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
            int level = node.getLevel();
            if (level == 1) {
                node.removeAllChildren();
            }
        }

        //reload jtree
        fileLinkerTreeModel.reload(fileLinkerRootNode);
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
     * Check if all files in the tree are linked correctly. Each RAW file has to
     * be linked to an identification file and each PeptideShaker file has to be
     * linked to a FASTA and an MGF file.
     *
     * @return whether all files are linked correctly
     */
    private boolean areAllFilesLinked() {
        boolean areAllFilesLinked = true;

        Enumeration breadthFirstEnumeration = fileLinkerRootNode.breadthFirstEnumeration();
        while (breadthFirstEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) breadthFirstEnumeration.nextElement();
            int level = node.getLevel();
            if (level == 1) {
                if (node.getChildCount() == 0) {
                    areAllFilesLinked = false;
                    break;
                }
            } else if (mainFrame.getPeptideShakerRadioButton().isSelected() && level == 2) {
                if (node.getChildCount() != 2) {
                    areAllFilesLinked = false;
                    break;
                }
            }
        }

        return areAllFilesLinked;
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

        private MoFFStep moffStep;
        private FileChangeScanner fcs;

        public MoFFStep getStep() {
            return moffStep;
        }

        public FileChangeScanner getFileChangeScanner() {
            return fcs;
        }

        @Override
        protected Void doInBackground() throws Exception {
            LOGGER.info("Preparing to run moFF...");
            // get the MoFF parameters
            HashMap<String, String> moffParameters;
            if (mainFrame.getApexModeRadioButton().isSelected()) {
                moffParameters = getApexParametersFromGUI();
                moffParameters.put("mode", "APEX");
            } else {
                moffParameters = getMBRParametersFromGUI();
                moffParameters.put("mode", "MBR");
            }
            LOGGER.info("MoFF will be run in " + moffParameters.get("mode") + " mode.");
            Map<File, File> inputMapping;
            if (mainFrame.getTabSeparatedRadioButton().isSelected()) {
                inputMapping = getRawTabSeparatedLinks();
            } else {
                LOGGER.info("Converting PeptideShaker output files to MoFF compatible tab separated files");
                inputMapping = new HashMap<>();;
                //converting the peptideshaker input files where necessary to the MoFF format
                Map<File, File[]> rawFilePeptideShakerMapping = getRawPeptideShakerLinks();
                for (Map.Entry<File, File[]> moffEntry : rawFilePeptideShakerMapping.entrySet()) {
                    File peptideShakerInputFile = moffEntry.getValue()[0];
                    File fastaFile = moffEntry.getValue()[1];
                    File mgfFile = moffEntry.getValue()[2];

                    HashMap<String, String> parameters = new HashMap<>();
                    parameters.put("ps_folder", peptideShakerDirectory.getAbsolutePath());
                    parameters.put("ps_output", peptideShakerInputFile.getAbsolutePath());
                    if (peptideShakerInputFile.getName().toUpperCase().endsWith(".CPSX")) {
                        parameters.put("mgf", mgfFile.getAbsolutePath());
                        parameters.put("fasta", fastaFile.getAbsolutePath());
                    }
                    MoFFPeptideShakerConversionStep conversionStep = new MoFFPeptideShakerConversionStep();
                    conversionStep.setParameters(parameters);
                    conversionStep.doAction();
                    //make the new mapping with the converted files
                    //key = raw file, value = tsv
                    inputMapping.put(moffEntry.getKey(), conversionStep.getMoffFile());
                }
                LOGGER.info("PeptideShaker output conversion complete...");
            }
            //write the cpsToMoffMapping to a File?
            File tempMappingFile = writeToTempFile(inputMapping);
            moffParameters.put("--map_file", tempMappingFile.getAbsolutePath());

            if (mainFrame.getApexModeRadioButton().isSelected()) {
                moffParameters.put("mode", "APEX");
            } else {
                moffParameters.put("mode", "MBR");
            }
            //prepare to capture the logging
            LOGGER.info("Starting moFF run, please note that this is a long process...");
            fcs = new FileChangeScanner(outPutDirectory);
            new Thread(fcs).start();
            //execute MoFF itself
            moffStep = new MoFFStep();
            moffStep.setParameters(moffParameters);
            moffStep.doAction();
            fcs.stop();
            //  LOGGER.info("MoFF run completed");
            return null;
        }

        private File writeToTempFile(Map<File, File> fileMapping) throws IOException {
            File tempFile = new File(outPutDirectory, "mapping.tsv");
            if (tempFile.exists()) {
                //@ToDo how to handle this properly?
                LOGGER.warn("Mapping file already exists, overriding...");
            }
            try (FileWriter writer = new FileWriter(tempFile)) {
                for (Map.Entry<File, File> aPeptideShakerFile : fileMapping.entrySet()) {
                    writer.append(aPeptideShakerFile.getKey().getAbsolutePath()
                            + LINK_SEPARATOR
                            + aPeptideShakerFile.getValue().getAbsolutePath())
                            .append(System.lineSeparator())
                            .flush();
                }
            }
            return tempFile;
        }

        @Override
        protected void done() {
            try {
                get();
                LOGGER.info("Finished moFF run");
                List<String> messages = new ArrayList<>();
                messages.add("The moFF run has finished successfully.");
                showMessageDialog("MoFF run completed", messages, JOptionPane.INFORMATION_MESSAGE);
            } catch (CancellationException ex) {
                LOGGER.info("The moFF run was cancelled");
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
            parameters.put("--tol", mainFrame.getPrecursorMassToleranceTextField().getText());    //                     specify the tollerance parameter in ppm
            parameters.put("--rt_w", mainFrame.getXicRetentionTimeWindowTextField().getText());    //                    specify rt window for xic (minute). Default value is 3 min
            parameters.put("--rt_p", mainFrame.getPeakRetentionTimeWindowTextField().getText());    //                  specify the time windows for the peak ( minute). Default value is 0.1
            parameters.put("--output_folder", mainFrame.getOutputDirectoryChooser().getSelectedFile().getAbsolutePath());    //             specify the folder output
            return parameters;
        }

        private HashMap<String, String> getMBRParametersFromGUI() {
            HashMap<String, String> parameters = new HashMap<>();
            if (mainFrame.getFilterOutliersCheckBox().isSelected()) {
                parameters.put("--out_filt", "1");    // OUT_FLAG   filter outlier in each rt time allignment   Default val =1
                parameters.put("--filt_width", mainFrame.getOutlierThresholdTextField().getText());    // W_FILT   width value of the filter k * mean(Dist_Malahobis)  Default val = 1.5
            } else {
                parameters.put("--out_filt", "0");
            }
            if (mainFrame.getCombinationWeighingCheckBox().isSelected()) {
                parameters.put("--weight_comb", "1");    // W_COMB  weights for model combination combination : 0 for no weight 1 weighted devised by trein err of the model. Default val =1
            } else {
                parameters.put("--weight_comb", "0");
            }
            parameters.put("--tol", mainFrame.getPrecursorMassToleranceTextField().getText());   // TOLL            specify the tollerance parameter in ppm
            parameters.put("--rt_w", mainFrame.getXicRetentionTimeWindowTextField().getText());    // RT_WINDOW      specify rt window for xic (minute). Default value is  5  min
            parameters.put("--rt_p", mainFrame.getPeakRetentionTimeWindowTextField().getText());    // RT_P_WINDOW    specify the time windows for the peak ( minute). Default value is 0.1
            parameters.put("--rt_p_match", mainFrame.getMatchedPeaksRetentionTimeWindowTextField().getText());    //_match RT_P_WINDOW_MATCH  specify the time windows for the matched peptide peak ( minute). Default value is 0.4
            parameters.put("--output_folder", mainFrame.getOutputDirectoryChooser().getSelectedFile().getAbsolutePath());    // LOC_OUT         specify the folder output (mandatory)
            return parameters;
        }
    }
}
