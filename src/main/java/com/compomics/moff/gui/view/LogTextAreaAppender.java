package com.compomics.moff.gui.view;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Appender class for writing log messages to a JTextArea.
 *
 * @author Niels Hulstaert
 */
public class LogTextAreaAppender extends WriterAppender {

    /**
     * The log text area to log to.
     */
    private JTextArea logTextArea;

    /**
     * No-arg constructor.
     */
    public LogTextAreaAppender() {
    }

    public void setLogTextArea(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    @Override
    public void append(LoggingEvent event) {
        final String message = this.layout.format(event);

        SwingUtilities.invokeLater(() -> {
            logTextArea.append(message);
            //repaint view
            logTextArea.validate();
            logTextArea.repaint();
        });
    }

}
