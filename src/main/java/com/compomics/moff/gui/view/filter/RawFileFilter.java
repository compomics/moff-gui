package com.compomics.moff.gui.view.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * A file filter for RAW files.
 *
 * @author Niels Hulstaert
 */
public class RawFileFilter extends FileFilter {

    private static final String RAW_EXTENSION = "raw";
    private static final String DESCRIPTION = "*.raw";

    @Override
    public boolean accept(File file) {
        boolean accept = false;

        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.isEmpty() && extension.equals(RAW_EXTENSION)) {
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
