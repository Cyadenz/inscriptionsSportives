package IHM;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class BooleanCellEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean EnEquipe;
    private JButton bouton;
 
    public BooleanCellEditor() {
        super();
 
        bouton = new JButton();
        bouton.addActionListener(this);
        bouton.setBorderPainted(false);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        EnEquipe ^= true;
 
        fireEditingStopped();
    }
 
    @Override
    public Object getCellEditorValue() {
        return EnEquipe;
    }
 
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        EnEquipe = (Boolean)value;
 
        return bouton;
    }
}