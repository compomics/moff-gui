package com.compomics.moff.gui.view.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * A file filter for RAW files.
 *
 * @author Niels Hulstaert
 */
public class RawAndMzmlFileFilter extends FileFilter {

    public static final String RAW_EXTENSION = "raw";
    public static final String MZML_EXTENSION = "mzml";
    private static final String DESCRIPTION = "*.raw, *.mzml";

    @Override
    public boolean accept(File file) {
        boolean accept = false;

        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.isEmpty() && (extension.equalsIgnoreCase(RAW_EXTENSION) || extension.equalsIgnoreCase(MZML_EXTENSION))) {
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
