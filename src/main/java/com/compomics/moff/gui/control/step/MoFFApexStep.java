package com.compomics.moff.gui.control.step;

import com.compomics.moff.gui.control.util.MoffInstaller;
import com.compomics.moff.gui.control.util.PSOutputParser;
import com.compomics.moff.gui.control.util.ToolManager;
import com.compomics.pladipus.core.control.engine.ProcessingEngine;
import com.compomics.pladipus.core.control.engine.callback.CallbackNotifier;
import com.compomics.pladipus.core.model.processing.ProcessingStep;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class MoFFApexStep extends ProcessingStep {

    /**
     * the temp folder for the entire processing
     */
    private File tempResources;
    /**
     * Logging instance
     */
    private static final Logger LOGGER = Logger.getLogger(MoFFApexStep.class);
    /**
     * The moff script file
     */
    private static final File MOFF_SCRIPT_FILE = new File(ToolManager.getInstance().getMoffFolder(), "moff.py");
    /**
     * The folder where raw resources will be temporary copied to (locally) for
     * processing
     */
    private File rawResources;
    /**
     * The folder where peptideshaker resources will be temporary copied to
     * (locally) for processing
     */
    private File inputResources;
    /**
     * The output file of peptideshaker (extended report) or a TSV file
     */
    private File inputFile;

    //DEFAULT MOFF PARAMETERS
    private double tolerance = 10;
    private double rt_window = 3;
    private double rt_p = 0.10;
    private File moffInputFile;

    public MoFFApexStep() {

    }

    @Override
    public boolean doAction() throws Exception {
        tempResources = new File(ToolManager.getInstance().getMoffFolder(), "temp");
        tempResources.mkdirs();
        //@ToDo, do we allow multiple raw files at the same time?
        String[] raw_input_paths = parameters.get("raw").split(",");
        File raw_input_File = new File(raw_input_paths[0]);
        rawResources = raw_input_File.getParentFile();
        inputResources = new File(tempResources, "input");
        if (inputResources.exists()) {
            FileUtils.deleteDirectory(inputResources);
        }
        inputResources.mkdirs();

        for (Map.Entry<String, String> aParam : parameters.entrySet()) {
            System.out.println(aParam.getKey() + "\t" + aParam.getValue());
        }
        inputFile = new File(parameters.get("in"));
        LOGGER.info("Input file : " + inputFile.getAbsolutePath());
        moffInputFile = new File(inputFile.getParentFile(), raw_input_File.getName().toLowerCase().replace(".raw", ".txt"));
        //if the file is a peptideshaker file ?
        if (parameters.containsKey("preprocess_extended_report")) {
            LOGGER.info("Converting PeptideShaker TSV format to MoFF format");
            PSOutputParser.convert(inputFile, moffInputFile);
        } else if (parameters.containsKey("preprocess_cpsx")) {
            throw new UnsupportedOperationException("Not yet implemented...");
        }
        //convert the arguments
        List<String> constructArguments = constructArguments();
        //run the scripts
        CallbackNotifier callbackNotifier = getCallbackNotifier();
        //add custom error words in case something goes wrong, make sure the processing engine can discover it
        List<String> errorKeyWords = new ArrayList<>();
        errorKeyWords.add("ERROR");
        new ProcessingEngine()
                .startProcess(MOFF_SCRIPT_FILE, constructArguments, callbackNotifier, errorKeyWords);
        cleanup();
        return true;
    }

    private void cleanup() throws IOException {
        if (!parameters.containsKey("skip_cleaning") && tempResources.exists()) {
            for (File aFile : tempResources.listFiles()) {
                if (aFile.exists()) {
                    if (aFile.isFile()) {
                        aFile.delete();
                    } else {
                        FileUtils.deleteDirectory(aFile);
                    }
                }
            }
            //also rename the peptideshaker result?
            File outputFolder = new File(parameters.get("output_folder"));
            File peptideShakerResult = new File(outputFolder, outputFolder.getName() + ".zip");
            if (peptideShakerResult.exists()) {
                peptideShakerResult.renameTo(new File(outputFolder, "peptideshaker_out.zip"));
            }
        } else {
            tempResources.mkdirs();
        }
    }

    private List<String> constructArguments() throws IOException, XMLStreamException, URISyntaxException {
        if (!MOFF_SCRIPT_FILE.exists()) {
            MoffInstaller.installMoff();
        }
        ArrayList<String> cmdArgs = new ArrayList<>();
        cmdArgs.add("python");
        cmdArgs.add(MOFF_SCRIPT_FILE.getAbsolutePath());
        cmdArgs.add("--input");
        cmdArgs.add(moffInputFile.getAbsolutePath());
        cmdArgs.add("--tol");
        if (parameters.containsKey("mass_tolerance")) {
            tolerance = Double.parseDouble(parameters.get("mass_tolerance"));
        }
        cmdArgs.add(String.valueOf(tolerance));
        cmdArgs.add("--rt_w");
        if (parameters.containsKey("rt_w")) {
            rt_window = Double.parseDouble(parameters.get("rt_w"));
        }
        cmdArgs.add(String.valueOf(rt_window));
        cmdArgs.add("--rt_p");
        if (parameters.containsKey("rt_p")) {
            rt_p = Double.parseDouble(parameters.get("rt_p"));
        }
        cmdArgs.add(String.valueOf(rt_p));
        cmdArgs.add("--raw_repo");
        //add a forward slash to fix a bug in moff script (has to be fixed over there)
        cmdArgs.add(rawResources.getAbsolutePath() + "/");
        cmdArgs.add("--output_folder");
        cmdArgs.add(parameters.get("output_folder"));

        parameters.put("-raw_repo", rawResources.getAbsolutePath());

        return cmdArgs;
    }

    @Override
    public String getDescription() {
        return "Running MoFF (Apex-mode)";
    }

}
