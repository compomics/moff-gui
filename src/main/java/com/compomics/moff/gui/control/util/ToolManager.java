package com.compomics.moff.gui.control.util;

import static com.compomics.software.autoupdater.DownloadLatestZipFromRepo.downloadLatestZipFromRepo;
import com.compomics.software.autoupdater.HeadlessFileDAO;
import com.compomics.util.gui.waiting.waitinghandlers.WaitingHandlerCLIImpl;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.xml.stream.XMLStreamException;
import org.apache.log4j.Logger;

/**
 *
 * @author Kenneth Verheggen
 */
public class ToolManager {

    /**
     * The default moff folder
     */
    private File moffFolder = new File(System.getProperty("user.home") + "/pladipus/tools/moff");
    /**
     * The default searchGUI folder
     */
    private File sgFolder = new File(System.getProperty("user.home") + "/pladipus/tools/SearchGUI/");
    /**
     * The default peptideShaker folder
     */
    private File psFolder = new File(System.getProperty("user.home") + "/pladipus/tools/PeptideShaker");
    /**
     * The logging instance
     */
    private static final Logger LOGGER = Logger.getLogger(ToolManager.class);
    /**
     * The toolmanager instance
     */
    private static ToolManager instance;

    private ToolManager() {

    }

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    /**
     * Verifies the installed tools
     *
     * @return a boolean to indicate all tools were installed
     */
    public boolean checkInstallation() {
        try {
            if (!isMoffInstalled()) {
                installMoff();
            }
            if (!isSearchGUIInstalled()) {
                installSearchGUI();
            }
            if (!isPeptideShakerInstalled()) {
                installPeptideShaker();
            }
        } catch (IOException | URISyntaxException | XMLStreamException e) {
            LOGGER.error(e);
            return false;
        }
        return isMoffInstalled() && isSearchGUIInstalled() && isPeptideShakerInstalled();
    }
    
    public boolean isMoffInstalled() {
        return moffFolder.exists();
    }

    public boolean isSearchGUIInstalled() {
        return sgFolder.exists();
    }

    public boolean isPeptideShakerInstalled() {
        return psFolder.exists();
    }

    public void installMoff() throws IOException, URISyntaxException {
        MoffInstaller.installMoff();
    }

    public void installSearchGUI() throws IOException, XMLStreamException, URISyntaxException {
        //check if this is possible in another way...
        sgFolder.mkdirs();
        //check if searchGUI already exists?
        File temp = new File(sgFolder, "SearchGUI");
        if (!temp.exists()) {
            LOGGER.info("Downloading latest SearchGUI version...");
            URL jarRepository = new URL("http", "genesis.ugent.be", new StringBuilder().append("/maven2/").toString());
            downloadLatestZipFromRepo(temp, "SearchGUI", "eu.isas.searchgui", "SearchGUI", null, null, jarRepository, false, false, new HeadlessFileDAO(), new WaitingHandlerCLIImpl());
        }
    }

    public void installPeptideShaker() throws IOException, XMLStreamException, URISyntaxException {
        //check if this is possible in another way...
        psFolder.mkdirs();
        //check if searchGUI already exists?
        File temp = new File(psFolder, "PeptideShaker");
        if (!temp.exists()) {
            LOGGER.info("Downloading latest PeptideShaker version...");
            URL jarRepository = new URL("http", "genesis.ugent.be", new StringBuilder().append("/maven2/").toString());
            downloadLatestZipFromRepo(temp, "PeptideShaker", "eu.isas.peptideshaker", "PeptideShaker", null, null, jarRepository, false, false, new HeadlessFileDAO(), new WaitingHandlerCLIImpl());
        }
    }

    public File getMoffFolder() {
        return moffFolder;
    }

    public void setMoffFolder(File moffFolder) {
        this.moffFolder = moffFolder;
    }

    public File getSgFolder() {
        return sgFolder;
    }

    public void setSgFolder(File sgFolder) {
        this.sgFolder = sgFolder;
    }

    public File getPsFolder() {
        return psFolder;
    }

    public void setPsFolder(File psFolder) {
        this.psFolder = psFolder;
    }

}