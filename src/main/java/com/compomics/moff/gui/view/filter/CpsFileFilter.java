package com.compomics.moff.gui.view.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * A file filter for PeptideShaker .cps files.
 *
 * @author Niels Hulstaert
 */
public class CpsFileFilter extends FileFilter {

    private static final String CPS_EXTENSION = "cpsx";
    private static final String DESCRIPTION = "*.cpsx";

    @Override
    public boolean accept(File file) {
        boolean accept = false;

        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.isEmpty() && extension.equals(CPS_EXTENSION)) {
                accept = true;
            }
        } else {
            accept = true;
        }

        return accept;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
