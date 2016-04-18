package com.compomics.moff.gui.control.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.log4j.Logger;

/**
 * This class scans the given folder for changes in log and txt files and prints
 * the last line of the file. It is intended to print log file output to console
 * for processes that cannot be seen directly through java
 *
 * @author Kenneth Verheggen
 */
public class FileChangeScanner implements Runnable {

    private final File folderToCheck;
    private final static Logger LOGGER = Logger.getLogger(FileChangeScanner.class);
    private boolean finish = false;

    public FileChangeScanner(File folderToCheck) {
        this.folderToCheck = folderToCheck;
    }

    public void stop() {
        finish = true;
    }

    @Override
    public void run() {
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = Paths.get(folderToCheck.getAbsolutePath());
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            LOGGER.info("Watch Service registered for outputfolder: " + dir.getFileName());

            while (!finish) {
                WatchKey key;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    File modifiedFile = ev.context().toFile();
                    if (kind == ENTRY_MODIFY && (modifiedFile.getName().toLowerCase().endsWith(".log") || modifiedFile.getName().toLowerCase().endsWith(".txt"))) {
                        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(new File(folderToCheck, modifiedFile.getName()))) {
                            LOGGER.info(reader.readLine());
                        }
                    }
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            LOGGER.error(ex);
        } finally {
            LOGGER.info("Shutting down MoFF logger");
        }
    }

}
