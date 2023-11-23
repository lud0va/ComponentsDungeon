package org.main.componentes.Mlog;

import javax.swing.*;

public class MLog extends JTextArea implements MLogInterface {

    public MLog() {
        super();
        this.setEditable(false);
        this.setRows(10);
        this.setColumns(30);
    }


    @Override
    public void addLogMessage(String message) {
        this.append(message);
    }

    @Override
    public void clearLog() {
        this.setText("");
    }
}
