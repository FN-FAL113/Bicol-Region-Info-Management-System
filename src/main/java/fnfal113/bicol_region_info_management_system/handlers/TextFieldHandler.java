package main.java.fnfal113.bicol_region_info_management_system.handlers;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldHandler implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        handler();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handler();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handler();
    }

    protected void handler() {}

}
