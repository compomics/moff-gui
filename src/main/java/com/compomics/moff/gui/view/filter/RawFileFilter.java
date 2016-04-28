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

    public static final String RAW_EXTENSION = "raw";
    public static final String RAW_EXTENSION_CAPS = "RAW";
    private static final String DESCRIPTION = "*.raw, *.RAW";

    @Override
    public boolean accept(File file) {
        boolean accept = false;

        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.isEmpty() && (extension.equals(RAW_EXTENSION) || extension.equals(RAW_EXTENSION_CAPS))) {
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
