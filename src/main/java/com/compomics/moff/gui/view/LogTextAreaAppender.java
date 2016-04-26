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
public class LogTextAreaAppender extends WriterAppender implements Runnable {

    /**
     * The log text area to log to.
     */
    private JTextArea logTextArea;
    /**
     * The cycle of string characters to iterate over during waiting
     */
    private String[] cycle = new String[]{"", ".", "..", "..."};
    private boolean lock;
    private boolean closed = false;
    private Thread animationThread;
    private int currentLength;

    /**
     * No-arg constructor.
     */
    public LogTextAreaAppender() {
        animationThread = new Thread(this);
        animationThread.start();
    }

    public void setLogTextArea(JTextArea logTextArea) {
        this.logTextArea = logTextArea;
    }

    @Override
    public void close() {
        closed = true;
        animationThread.interrupt();
    }

    @Override
    public void append(LoggingEvent event) {
        lock = true;
        final String message = this.layout.format(event);
        SwingUtilities.invokeLater(() -> {
            logTextArea.setText(logTextArea.getText().substring(0, currentLength));
            logTextArea.append(message);
            //repaint view
            logTextArea.validate();
            logTextArea.repaint();
            currentLength = logTextArea.getText().length();
        });
        lock = false;
    }

    @Override
    public void run() {

        int index = 0;
        while (true) {
            if (closed) {
                break;
            } else if (!lock && logTextArea != null) {
                logTextArea.setText(logTextArea.getText().substring(0, currentLength) + cycle[index]);
                index++;
                if (index > cycle.length - 1) {
                    index = 0;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                //ignore
            }
        }
    }

}
