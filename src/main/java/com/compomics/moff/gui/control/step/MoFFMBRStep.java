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
import javax.xml.stream.XMLStreamException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class MoFFMBRStep extends ProcessingStep {

    /**
     * the temp folder for the entire processing
     */
    private File tempResources;
    /**
     * Logging instance
     */
    private static final Logger LOGGER = Logger.getLogger(MoFFMBRStep.class);
    /**
     * The moff script file
     */
    private static final File MOFF_SCRIPT_FILE = new File(ToolManager.getInstance().getMoffFolder(), "moff_all.py");
    /**
     * The folder where raw resources will be temporary copied to (locally) for
     * processing
     */
    private File rawResources;
    /**
     * The folder where peptideshaker resources will be temporary copied to
     * (locally) for processing
     */
    private File psResources;

    //DEFAULT MOFF PARAMETERS
    private double tolerance = 10;
    private double rt_window = 3;
    private double rt_p = 0.10;
    //advanced MOFF

    public MoFFMBRStep() {

    }

    @Override
    public boolean doAction() throws Exception {
        tempResources = new File(ToolManager.getInstance().getMoffFolder(), "temp");
        //cleanup();
        tempResources.mkdirs();
        rawResources = new File(parameters.get("raw_repo"));
        psResources = new File(parameters.get("out_reports"));
        LOGGER.info("RAW repository at " + rawResources);
        LOGGER.info("PeptideShaker reports at " + psResources);
        File mbrTempFolder = new File(psResources, "mbr_output");
        if (mbrTempFolder.exists()) {
            LOGGER.info("MoFF - matching between runs was already run in this folder. Cleaning existing temporary files...");
            for (File aFile : mbrTempFolder.listFiles()) {
                aFile.delete();
            }
        }
        //convert the peptideshaker files
        //check all the raw files in the folder and look for matching peptideshaker files?
        
        //@ToDo how to check the mapping between raw files and peptideshaker files?
        /*This part needs work to be in line with updated MoFF that can handle mapping?*/ 
        for (File aRawFile : rawResources.listFiles()) {
            if (aRawFile.getName().toUpperCase().endsWith(".RAW") && parameters.containsKey(aRawFile.getAbsolutePath())) {
                File peptideShakerReport = new File(parameters.get(aRawFile.getAbsolutePath()));
                File moffInputFile = new File(peptideShakerReport.getParentFile(), aRawFile.getName().toLowerCase().replace(".raw", ".txt"));
                LOGGER.info("Converting " + peptideShakerReport.getName() + " to MoFF formatted file : " + moffInputFile.getName());
                PSOutputParser.convert(peptideShakerReport, moffInputFile);
                //delete the original peptideshaker output
                peptideShakerReport.delete();
            }
        }

        //convert the arguments
        List<String> constructArguments = constructArguments();
        //run the scripts
        CallbackNotifier callbackNotifier = getCallbackNotifier();
        //add custom error words?
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
            //also rename the peptideshaker result --> not sure why this is required per se?
            /*  File outputFolder = new File(parameters.get("output_folder"));
            File peptideShakerResult = new File(outputFolder, outputFolder.getName() + ".zip");
            if (peptideShakerResult.exists()) {
                peptideShakerResult.renameTo(new File(outputFolder, "peptideshaker_out.zip"));
            }*/
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
        
        cmdArgs.add("--inputF");
        cmdArgs.add(psResources.getAbsolutePath() + "/");
        
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
        
        //the optional parameters
        if (parameters.containsKey("weight_comb")) {
            cmdArgs.add("--weight_comb");
            cmdArgs.add("1");
        }
          if (parameters.containsKey("out_filt")) {
            cmdArgs.add("--out_filt");
            cmdArgs.add("1");
            cmdArgs.add("--filt_width");
            cmdArgs.add(parameters.getOrDefault("--filt_width","1"));
        }
             
        cmdArgs.add("--raw_repo");
        //add a forward slash to fix a bug in moff script (has to be fixed over there)
        cmdArgs.add(rawResources.getAbsolutePath() + "/");
       
        cmdArgs.add("--output_folder");
        cmdArgs.add(parameters.get("output_folder"));

        //store the raw repo in the parameters as well for future use
        parameters.put("-raw_repo", rawResources.getAbsolutePath());

        return cmdArgs;
    }

    @Override
    public String getDescription() {
        return "Running MoFF (MBR-mode)";
    }

}
