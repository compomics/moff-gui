/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.moff.gui.view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;

/**
 * The GUI main frame.
 *
 * @author Niels Hulstaert
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Output directory chooser.
     */
    private final JFileChooser outputDirectoryChooser = new JFileChooser();
    /**
     * PeptideShaker directory chooser.
     */
    private final JFileChooser peptideShakerDirectoryChooser = new JFileChooser();
    /**
     * TSV directory chooser.
     */
    private final JFileChooser tsvFileChooser = new JFileChooser();
    /**
     * File directory chooser.
     */
    private final JFileChooser directoryChooser = new JFileChooser();

    /**
     * No-arg constructor.
     */
    public MainFrame() {
        initComponents();

        fileChooserTree.setPreferredSize(null);
        fileLinkerTree.setPreferredSize(null);

        fileChooserTreeScrollPane.getViewport().setOpaque(false);
        fileLinkerTreeScrollPane.getViewport().setOpaque(false);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getDeleteFileButton() {
        return deleteFileButton;
    }

    public JTree getFileLinkerTree() {
        return fileLinkerTree;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public ButtonGroup getIdentificationDataTypeButtonGroup() {
        return identificationDataTypeButtonGroup;
    }

    public JRadioButton getPeptideShakerRadioButton() {
        return peptideShakerRadioButton;
    }

    public JButton getProceedButton() {
        return proceedButton;
    }

    public JRadioButton getTabSeparatedRadioButton() {
        return tabSeparatedRadioButton;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public JRadioButton getApexModeRadioButton() {
        return apexModeRadioButton;
    }

    public JRadioButton getMatchingBetweenRunsRadioButton() {
        return matchingBetweenRunsRadioButton;
    }

    public JFileChooser getTsvFileChooser() {
        return tsvFileChooser;
    }

    public JCheckBox getCombinationWeighingCheckBox() {
        return combinationWeighingCheckBox;
    }

    public JCheckBox getFilterOutliersCheckBox() {
        return filterOutliersCheckBox;
    }

    public JTextField getOutlierThresholdTextField() {
        return outlierThresholdTextField;
    }

    public JTextField getPeakRetentionTimeWindowTextField() {
        return peakRetentionTimeWindowTextField;
    }

    public JTextField getPrecursorMassToleranceTextField() {
        return precursorMassToleranceTextField;
    }

    public JTextField getXicRetentionTimeWindowTextField() {
        return xicRetentionTimeWindowTextField;
    }

    public JTextField getMatchedPeaksRetentionTimeWindowTextField() {
        return matchedPeaksRetentionTimeWindowTextField;
    }

    public JPanel getMatchingBetweenRunsSettingsPanel() {
        return matchingBetweenRunsSettingsPanel;
    }

    public JTextArea getLogTextArea() {
        return logTextArea;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getOutputDirectoryChooseButton() {
        return outputDirectoryChooseButton;
    }

    public JTextField getOutputDirectoryTextField() {
        return outputDirectoryTextField;
    }

    public JFileChooser getOutputDirectoryChooser() {
        return outputDirectoryChooser;
    }

    public JButton getPeptideShakerDirectoryChooseButton() {
        return peptideShakerDirectoryChooseButton;
    }

    public JTextField getPeptideShakerDirectoryTextField() {
        return peptideShakerDirectoryTextField;
    }

    public JFileChooser getPeptideShakerDirectoryChooser() {
        return peptideShakerDirectoryChooser;
    }

    public JCheckBox getCustomPeptidesCheckBox() {
        return customPeptidesCheckBox;
    }

    public JButton getCustomPeptidesChooseButton() {
        return customPeptidesChooseButton;
    }

    public JTextField getCustomPeptidesFileTextField() {
        return customPeptidesFileTextField;
    }

    public JTree getFileChooserTree() {
        return fileChooserTree;
    }

    public JButton getBrowseDirectoriesButton() {
        return browseDirectoriesButton;
    }

    public JFileChooser getDirectoryChooser() {
        return directoryChooser;
    }

    public JButton getAddFileButton() {
        return addFileButton;
    }

    public JTextField getPeptideTagNameTextField() {
        return peptideTagNameTextField;
    }

    public JCheckBox getSummedIntensityCheckbox() {
        return summedIntensityCheckbox;
    }        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        identificationDataTypeButtonGroup = new javax.swing.ButtonGroup();
        moffModeButtonGroup = new javax.swing.ButtonGroup();
        parentPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        firstPanel = new javax.swing.JPanel();
        identificationDataTypeLabel = new javax.swing.JLabel();
        peptideShakerRadioButton = new javax.swing.JRadioButton();
        tabSeparatedRadioButton = new javax.swing.JRadioButton();
        moffModeLabel = new javax.swing.JLabel();
        apexModeRadioButton = new javax.swing.JRadioButton();
        matchingBetweenRunsRadioButton = new javax.swing.JRadioButton();
        outputDirectoryLabel = new javax.swing.JLabel();
        outputDirectoryTextField = new javax.swing.JTextField();
        outputDirectoryChooseButton = new javax.swing.JButton();
        peptideShakerDirectoryTextField = new javax.swing.JTextField();
        peptideShakerDirectoryChooseButton = new javax.swing.JButton();
        secondPanel = new javax.swing.JPanel();
        deleteFileButton = new javax.swing.JButton();
        treesPanel = new javax.swing.JPanel();
        fileChooserTreeParentPanel = new javax.swing.JPanel();
        fileChooserTreeScrollPane = new javax.swing.JScrollPane();
        fileChooserTree = new javax.swing.JTree();
        fileLinkerTreePanel = new javax.swing.JPanel();
        fileLinkerTreeScrollPane = new javax.swing.JScrollPane();
        fileLinkerTree = new javax.swing.JTree();
        addFileButton = new javax.swing.JButton();
        browseDirectoriesButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        thirdPanel = new javax.swing.JPanel();
        apexSettingsPanel = new javax.swing.JPanel();
        xicRetentionTimeWindowLabel = new javax.swing.JLabel();
        precursorMassToleranceLabel = new javax.swing.JLabel();
        peakRetentionTimeWindowLabel = new javax.swing.JLabel();
        xicRetentionTimeWindowTextField = new javax.swing.JTextField();
        peakRetentionTimeWindowTextField = new javax.swing.JTextField();
        precursorMassToleranceTextField = new javax.swing.JTextField();
        matchingBetweenRunsSettingsPanel = new javax.swing.JPanel();
        matchedPeakRetentionTimeWindowLabel = new javax.swing.JLabel();
        matchedPeaksRetentionTimeWindowTextField = new javax.swing.JTextField();
        combinationWeighingCheckBox = new javax.swing.JCheckBox();
        filterOutliersCheckBox = new javax.swing.JCheckBox();
        outlierThresholdLabel = new javax.swing.JLabel();
        outlierThresholdTextField = new javax.swing.JTextField();
        customPeptidesCheckBox = new javax.swing.JCheckBox();
        customPeptidesFileTextField = new javax.swing.JTextField();
        customPeptidesChooseButton = new javax.swing.JButton();
        exportSettingsPanel = new javax.swing.JPanel();
        summedIntensityCheckbox = new javax.swing.JCheckBox();
        peptideTagNameTextField = new javax.swing.JTextField();
        peptideTagNameLabel = new javax.swing.JLabel();
        lastPanel = new javax.swing.JPanel();
        logTextAreaScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        clearButton = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        proceedButton = new javax.swing.JButton();
        infoLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        parentPanel.setBackground(new java.awt.Color(255, 255, 255));

        topPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        topPanel.setOpaque(false);
        topPanel.setLayout(new java.awt.CardLayout());

        firstPanel.setName("firstPanel"); // NOI18N
        firstPanel.setOpaque(false);

        identificationDataTypeLabel.setText("Choose the identification data type:");

        peptideShakerRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        identificationDataTypeButtonGroup.add(peptideShakerRadioButton);
        peptideShakerRadioButton.setText("PeptideShaker (*.cpsx)");

        tabSeparatedRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        identificationDataTypeButtonGroup.add(tabSeparatedRadioButton);
        tabSeparatedRadioButton.setText("Tab-separated (*.tsv or *.txt, PeptideShaker default PSM reports are supported as well) ");

        moffModeLabel.setText("Choose the run mode:");

        apexModeRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        moffModeButtonGroup.add(apexModeRadioButton);
        apexModeRadioButton.setText("APEX");

        matchingBetweenRunsRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        moffModeButtonGroup.add(matchingBetweenRunsRadioButton);
        matchingBetweenRunsRadioButton.setText("Matching between runs");

        outputDirectoryLabel.setText("Choose an output directory:");

        outputDirectoryTextField.setEnabled(false);

        outputDirectoryChooseButton.setText("choose");
        outputDirectoryChooseButton.setToolTipText("Select a directory for the moFF output");
        outputDirectoryChooseButton.setMaximumSize(new java.awt.Dimension(80, 25));
        outputDirectoryChooseButton.setMinimumSize(new java.awt.Dimension(80, 25));
        outputDirectoryChooseButton.setPreferredSize(new java.awt.Dimension(80, 25));

        peptideShakerDirectoryTextField.setEnabled(false);

        peptideShakerDirectoryChooseButton.setText("choose");
        peptideShakerDirectoryChooseButton.setToolTipText("Choose the PeptideShaker directory location\n");
        peptideShakerDirectoryChooseButton.setMaximumSize(new java.awt.Dimension(80, 25));
        peptideShakerDirectoryChooseButton.setMinimumSize(new java.awt.Dimension(80, 25));
        peptideShakerDirectoryChooseButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout firstPanelLayout = new javax.swing.GroupLayout(firstPanel);
        firstPanel.setLayout(firstPanelLayout);
        firstPanelLayout.setHorizontalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(identificationDataTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                    .addComponent(moffModeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(185, 185, 185))
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apexModeRadioButton)
                    .addComponent(matchingBetweenRunsRadioButton)
                    .addComponent(tabSeparatedRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peptideShakerRadioButton)
                    .addComponent(outputDirectoryLabel))
                .addGap(18, 18, 18)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(peptideShakerDirectoryTextField)
                    .addComponent(outputDirectoryTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(outputDirectoryChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peptideShakerDirectoryChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        firstPanelLayout.setVerticalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addComponent(identificationDataTypeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peptideShakerRadioButton)
                    .addComponent(peptideShakerDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peptideShakerDirectoryChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabSeparatedRadioButton)
                .addGap(18, 18, 18)
                .addComponent(moffModeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(apexModeRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchingBetweenRunsRadioButton)
                .addGap(18, 18, 18)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputDirectoryLabel)
                    .addComponent(outputDirectoryChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 199, Short.MAX_VALUE))
        );

        topPanel.add(firstPanel, "firstPanel");

        secondPanel.setName("secondPanel"); // NOI18N
        secondPanel.setOpaque(false);

        deleteFileButton.setText("delete");
        deleteFileButton.setToolTipText("Select one or more RAW and/or identification files to delete");
        deleteFileButton.setMaximumSize(new java.awt.Dimension(80, 25));
        deleteFileButton.setMinimumSize(new java.awt.Dimension(80, 25));
        deleteFileButton.setPreferredSize(new java.awt.Dimension(80, 25));

        treesPanel.setOpaque(false);
        treesPanel.setLayout(new java.awt.GridBagLayout());

        fileChooserTreeScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("File selection"));
        fileChooserTreeScrollPane.setPreferredSize(new java.awt.Dimension(10, 10));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        fileChooserTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        fileChooserTree.setMaximumSize(new java.awt.Dimension(600, 600));
        fileChooserTree.setPreferredSize(new java.awt.Dimension(50, 50));
        fileChooserTreeScrollPane.setViewportView(fileChooserTree);

        javax.swing.GroupLayout fileChooserTreeParentPanelLayout = new javax.swing.GroupLayout(fileChooserTreeParentPanel);
        fileChooserTreeParentPanel.setLayout(fileChooserTreeParentPanelLayout);
        fileChooserTreeParentPanelLayout.setHorizontalGroup(
            fileChooserTreeParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooserTreeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
        );
        fileChooserTreeParentPanelLayout.setVerticalGroup(
            fileChooserTreeParentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooserTreeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        treesPanel.add(fileChooserTreeParentPanel, gridBagConstraints);

        fileLinkerTreeScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("File linking"));
        fileLinkerTreeScrollPane.setPreferredSize(new java.awt.Dimension(10, 10));

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        fileLinkerTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        fileLinkerTree.setPreferredSize(new java.awt.Dimension(50, 50));
        fileLinkerTreeScrollPane.setViewportView(fileLinkerTree);

        javax.swing.GroupLayout fileLinkerTreePanelLayout = new javax.swing.GroupLayout(fileLinkerTreePanel);
        fileLinkerTreePanel.setLayout(fileLinkerTreePanelLayout);
        fileLinkerTreePanelLayout.setHorizontalGroup(
            fileLinkerTreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileLinkerTreeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
        );
        fileLinkerTreePanelLayout.setVerticalGroup(
            fileLinkerTreePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileLinkerTreeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        treesPanel.add(fileLinkerTreePanel, gridBagConstraints);

        addFileButton.setText(">>>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        treesPanel.add(addFileButton, gridBagConstraints);

        browseDirectoriesButton.setText("browse");
        browseDirectoriesButton.setMaximumSize(new java.awt.Dimension(80, 25));
        browseDirectoriesButton.setMinimumSize(new java.awt.Dimension(80, 25));
        browseDirectoriesButton.setPreferredSize(new java.awt.Dimension(80, 25));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Link the RAW/mzML file(s) to the corresponding identification file(s). Each RAW/mzML file has to be linked to one.\nPeptideShaker file (*.cpsx) or a tab separated file (*.tab, *.tsv, *.txt).\nFor a PeptideShaker file, the used FASTA (*.fasta) and MGF (*.mgf) files have to be specified as well.\nSelect file(s) on the left and move to the right with the \">>>\" button.\nSelect \"RAW/mzML - identification file links\" for adding RAW/mzML files,\nselect a RAW/mzML file for linking it with an identification file,\nselect a PeptideShaker .cpsx file for linking it FASTA and MGF files.");
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout secondPanelLayout = new javax.swing.GroupLayout(secondPanel);
        secondPanel.setLayout(secondPanelLayout);
        secondPanelLayout.setHorizontalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addComponent(browseDirectoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(treesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        secondPanelLayout.setVerticalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secondPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(treesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseDirectoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        topPanel.add(secondPanel, "secondPanel");

        thirdPanel.setName("thirdPanel"); // NOI18N
        thirdPanel.setOpaque(false);

        apexSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("APEX settings"));
        apexSettingsPanel.setOpaque(false);

        xicRetentionTimeWindowLabel.setText("XiC retention time window (minutes):");

        precursorMassToleranceLabel.setText("Precursor mass tolerance (ppm):");

        peakRetentionTimeWindowLabel.setText("Peak retention time window (minutes):");

        precursorMassToleranceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precursorMassToleranceTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout apexSettingsPanelLayout = new javax.swing.GroupLayout(apexSettingsPanel);
        apexSettingsPanel.setLayout(apexSettingsPanelLayout);
        apexSettingsPanelLayout.setHorizontalGroup(
            apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(apexSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xicRetentionTimeWindowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(peakRetentionTimeWindowLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(precursorMassToleranceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(peakRetentionTimeWindowTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(xicRetentionTimeWindowTextField)
                    .addComponent(precursorMassToleranceTextField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        apexSettingsPanelLayout.setVerticalGroup(
            apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(apexSettingsPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xicRetentionTimeWindowLabel)
                    .addComponent(xicRetentionTimeWindowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(peakRetentionTimeWindowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peakRetentionTimeWindowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(apexSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precursorMassToleranceLabel)
                    .addComponent(precursorMassToleranceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        matchingBetweenRunsSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Matching between runs settings"));
        matchingBetweenRunsSettingsPanel.setOpaque(false);

        matchedPeakRetentionTimeWindowLabel.setText("Matched peaks retention time window (minutes):");

        combinationWeighingCheckBox.setText("Use combination weighing");

        filterOutliersCheckBox.setText("Filter outliers");

        outlierThresholdLabel.setText("Width:");

        customPeptidesCheckBox.setText("use custom peptides");
        customPeptidesCheckBox.setToolTipText("Select a tab separated file with custom peptides used to train the RT prediction models. The first column must contain the peptide sequence, the second column the peptide mass.");

        customPeptidesFileTextField.setEnabled(false);

        customPeptidesChooseButton.setText("choose");
        customPeptidesChooseButton.setMaximumSize(new java.awt.Dimension(80, 25));
        customPeptidesChooseButton.setMinimumSize(new java.awt.Dimension(80, 25));
        customPeptidesChooseButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout matchingBetweenRunsSettingsPanelLayout = new javax.swing.GroupLayout(matchingBetweenRunsSettingsPanel);
        matchingBetweenRunsSettingsPanel.setLayout(matchingBetweenRunsSettingsPanelLayout);
        matchingBetweenRunsSettingsPanelLayout.setHorizontalGroup(
            matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(matchingBetweenRunsSettingsPanelLayout.createSequentialGroup()
                .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(combinationWeighingCheckBox)
                    .addGroup(matchingBetweenRunsSettingsPanelLayout.createSequentialGroup()
                        .addComponent(filterOutliersCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(outlierThresholdLabel))
                    .addComponent(customPeptidesCheckBox)
                    .addComponent(matchedPeakRetentionTimeWindowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(matchingBetweenRunsSettingsPanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(customPeptidesFileTextField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(customPeptidesChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(matchingBetweenRunsSettingsPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outlierThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matchedPeaksRetentionTimeWindowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        matchingBetweenRunsSettingsPanelLayout.setVerticalGroup(
            matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(matchingBetweenRunsSettingsPanelLayout.createSequentialGroup()
                .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matchedPeakRetentionTimeWindowLabel)
                    .addComponent(matchedPeaksRetentionTimeWindowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combinationWeighingCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filterOutliersCheckBox)
                    .addComponent(outlierThresholdLabel)
                    .addComponent(outlierThresholdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(matchingBetweenRunsSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customPeptidesCheckBox)
                    .addComponent(customPeptidesFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customPeptidesChooseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        exportSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Export settings"));
        exportSettingsPanel.setOpaque(false);

        summedIntensityCheckbox.setText("include peptide summed intensity");

        peptideTagNameTextField.setEnabled(false);

        peptideTagNameLabel.setText("Peptide tag name: ");

        javax.swing.GroupLayout exportSettingsPanelLayout = new javax.swing.GroupLayout(exportSettingsPanel);
        exportSettingsPanel.setLayout(exportSettingsPanelLayout);
        exportSettingsPanelLayout.setHorizontalGroup(
            exportSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(summedIntensityCheckbox)
                .addGap(87, 87, 87)
                .addComponent(peptideTagNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(peptideTagNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        exportSettingsPanelLayout.setVerticalGroup(
            exportSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(summedIntensityCheckbox)
                    .addComponent(peptideTagNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(peptideTagNameLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout thirdPanelLayout = new javax.swing.GroupLayout(thirdPanel);
        thirdPanel.setLayout(thirdPanelLayout);
        thirdPanelLayout.setHorizontalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(matchingBetweenRunsSettingsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(apexSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exportSettingsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        thirdPanelLayout.setVerticalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdPanelLayout.createSequentialGroup()
                .addComponent(apexSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(matchingBetweenRunsSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        topPanel.add(thirdPanel, "thirdPanel");

        lastPanel.setName("lastPanel"); // NOI18N
        lastPanel.setOpaque(false);

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logTextAreaScrollPane.setViewportView(logTextArea);

        clearButton.setText("clear");
        clearButton.setMaximumSize(new java.awt.Dimension(80, 25));
        clearButton.setMinimumSize(new java.awt.Dimension(80, 25));
        clearButton.setPreferredSize(new java.awt.Dimension(80, 25));

        javax.swing.GroupLayout lastPanelLayout = new javax.swing.GroupLayout(lastPanel);
        lastPanel.setLayout(lastPanelLayout);
        lastPanelLayout.setHorizontalGroup(
            lastPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 759, Short.MAX_VALUE)
            .addGroup(lastPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        lastPanelLayout.setVerticalGroup(
            lastPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lastPanelLayout.createSequentialGroup()
                .addComponent(logTextAreaScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        topPanel.add(lastPanel, "lastPanel");

        bottomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        bottomPanel.setOpaque(false);

        backButton.setText("back");
        backButton.setMaximumSize(new java.awt.Dimension(80, 25));
        backButton.setMinimumSize(new java.awt.Dimension(80, 25));
        backButton.setPreferredSize(new java.awt.Dimension(80, 25));

        proceedButton.setText("proceed");
        proceedButton.setMaximumSize(new java.awt.Dimension(80, 25));
        proceedButton.setMinimumSize(new java.awt.Dimension(80, 25));
        proceedButton.setPreferredSize(new java.awt.Dimension(80, 25));

        infoLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        infoLabel.setMaximumSize(new java.awt.Dimension(34, 20));
        infoLabel.setMinimumSize(new java.awt.Dimension(34, 20));
        infoLabel.setName(""); // NOI18N
        infoLabel.setPreferredSize(new java.awt.Dimension(34, 20));

        startButton.setText("start");
        startButton.setMaximumSize(new java.awt.Dimension(80, 25));
        startButton.setMinimumSize(new java.awt.Dimension(80, 25));
        startButton.setPreferredSize(new java.awt.Dimension(80, 25));
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("cancel");
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 25));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proceedButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout parentPanelLayout = new javax.swing.GroupLayout(parentPanel);
        parentPanel.setLayout(parentPanelLayout);
        parentPanelLayout.setHorizontalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        parentPanelLayout.setVerticalGroup(
            parentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, parentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 429, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 791, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(parentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void precursorMassToleranceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precursorMassToleranceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precursorMassToleranceTextFieldActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFileButton;
    private javax.swing.JRadioButton apexModeRadioButton;
    private javax.swing.JPanel apexSettingsPanel;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton browseDirectoriesButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JCheckBox combinationWeighingCheckBox;
    private javax.swing.JCheckBox customPeptidesCheckBox;
    private javax.swing.JButton customPeptidesChooseButton;
    private javax.swing.JTextField customPeptidesFileTextField;
    private javax.swing.JButton deleteFileButton;
    private javax.swing.JPanel exportSettingsPanel;
    private javax.swing.JTree fileChooserTree;
    private javax.swing.JPanel fileChooserTreeParentPanel;
    private javax.swing.JScrollPane fileChooserTreeScrollPane;
    private javax.swing.JTree fileLinkerTree;
    private javax.swing.JPanel fileLinkerTreePanel;
    private javax.swing.JScrollPane fileLinkerTreeScrollPane;
    private javax.swing.JCheckBox filterOutliersCheckBox;
    private javax.swing.JPanel firstPanel;
    private javax.swing.ButtonGroup identificationDataTypeButtonGroup;
    private javax.swing.JLabel identificationDataTypeLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel lastPanel;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JScrollPane logTextAreaScrollPane;
    private javax.swing.JLabel matchedPeakRetentionTimeWindowLabel;
    private javax.swing.JTextField matchedPeaksRetentionTimeWindowTextField;
    private javax.swing.JRadioButton matchingBetweenRunsRadioButton;
    private javax.swing.JPanel matchingBetweenRunsSettingsPanel;
    private javax.swing.ButtonGroup moffModeButtonGroup;
    private javax.swing.JLabel moffModeLabel;
    private javax.swing.JLabel outlierThresholdLabel;
    private javax.swing.JTextField outlierThresholdTextField;
    private javax.swing.JButton outputDirectoryChooseButton;
    private javax.swing.JLabel outputDirectoryLabel;
    private javax.swing.JTextField outputDirectoryTextField;
    private javax.swing.JPanel parentPanel;
    private javax.swing.JLabel peakRetentionTimeWindowLabel;
    private javax.swing.JTextField peakRetentionTimeWindowTextField;
    private javax.swing.JButton peptideShakerDirectoryChooseButton;
    private javax.swing.JTextField peptideShakerDirectoryTextField;
    private javax.swing.JRadioButton peptideShakerRadioButton;
    private javax.swing.JLabel peptideTagNameLabel;
    private javax.swing.JTextField peptideTagNameTextField;
    private javax.swing.JLabel precursorMassToleranceLabel;
    private javax.swing.JTextField precursorMassToleranceTextField;
    private javax.swing.JButton proceedButton;
    private javax.swing.JPanel secondPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JCheckBox summedIntensityCheckbox;
    private javax.swing.JRadioButton tabSeparatedRadioButton;
    private javax.swing.JPanel thirdPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel treesPanel;
    private javax.swing.JLabel xicRetentionTimeWindowLabel;
    private javax.swing.JTextField xicRetentionTimeWindowTextField;
    // End of variables declaration//GEN-END:variables
}
