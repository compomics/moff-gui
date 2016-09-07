package com.compomics.moff.gui.view.filter;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * A file filter for FASTA and MGF files.
 *
 * @author Niels Hulstaert
 */
public class FastaAndMgfFileFilter extends FileFilter {

    public static final String FASTA_EXTENSION = "fasta";
    public static final String MGF_EXTENSION = "mgf";
    private static final String DESCRIPTION = "*.fasta, *.mgf";

    @Override
    public boolean accept(File file) {
        boolean accept = false;

        if (file.isFile()) {
            String extension = FilenameUtils.getExtension(file.getName());
            if (!extension.isEmpty() && (extension.equalsIgnoreCase(FASTA_EXTENSION) || extension.equalsIgnoreCase(MGF_EXTENSION))) {
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
