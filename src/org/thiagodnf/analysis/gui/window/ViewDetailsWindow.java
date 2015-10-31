package org.thiagodnf.analysis.gui.window;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.thiagodnf.analysis.custom.DoubleComparator;
import org.thiagodnf.analysis.indicator.Indicator;
import org.thiagodnf.analysis.util.NumberUtils;
import org.thiagodnf.analysis.util.SettingsUtils;
import org.thiagodnf.core.util.PropertiesUtils;

public class ViewDetailsWindow extends JPanel{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected JFrame parent;
	
	protected JTable table;
	
	protected Indicator indicator;
	
	protected List<String> files;
	
	public ViewDetailsWindow(JFrame parent, Indicator indicator, List<String> files) throws IOException{
		this.files = files;
		this.parent = parent;
		this.indicator = indicator;
		
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		this.setBounds(bounds);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		List<String> columnNames = new ArrayList<String>();
		
		columnNames.add("#");
		columnNames.add("Path");
		columnNames.add("Mean");
		columnNames.add("SD");		
		columnNames.add("Max");
		columnNames.add("Min");
		columnNames.add("Sum");
		columnNames.add("N");
		columnNames.add("Variance");
		
		String[] columns = columnNames.toArray(new String[columnNames.size()]);
		
		this.table = new JTable(new DefaultTableModel(new Object[][] {}, columns)) {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                    	return String.class;
                }
                
            }
                      
            public boolean isCellEditable(int row, int column) {
				return false;               
            };
		};
		
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );   
		
		add(new JScrollPane(table));
		
		this.table.setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		
		// Add custom comparator in table's columns
		for (int i = 2; i < columnNames.size(); i++) {
			sorter.setComparator(i, new DoubleComparator());
		}
		
		this.table.setRowSorter(sorter);
		
		//Define the max width of first column. This column contains the checkbox element
		this.table.getColumnModel().getColumn(0).setMaxWidth(25);
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		int round = SettingsUtils.getDecimalPlaces();
		
		int id = 0;
		
		// Get all indicator	
		for (String file : files) {
			String path = file.replaceFirst("SUMMARY","");
			
			Properties prop = PropertiesUtils.getFromFile(file);
			
			Object[] row = new Object[] {
				++id,
				path,
				NumberUtils.round(prop.get(indicator.getKey()+".mean"), round),
				NumberUtils.round(prop.get(indicator.getKey()+".sd"), round),				
				NumberUtils.round(prop.get(indicator.getKey()+".max"), round),
				NumberUtils.round(prop.get(indicator.getKey()+".min"), round),
				NumberUtils.round(prop.get(indicator.getKey()+".sum"), round),
				NumberUtils.round(prop.get(indicator.getKey()+".n"), round),
				NumberUtils.round(prop.get(indicator.getKey()+".variance"), round),
			};
			
			data.add(row);
		}
		
		// Before add new datas, remove all rows on table
		clear();
		
		for (Object[] d : data) {
			((DefaultTableModel) table.getModel()).addRow(d);
		}
	}
	
	public void clear(){
		while (table.getRowCount() != 0) {
			((DefaultTableModel) table.getModel()).removeRow(0);
		}
	}
		
	public int showOptionDialog(){
		Object[] options = { "Close"};

		// Show the indicator name at title
		String title = indicator.getName();
		
		int optionType = JOptionPane.OK_OPTION;
		int messageType = JOptionPane.PLAIN_MESSAGE;

		return JOptionPane.showOptionDialog(parent, this, title, optionType, messageType, null, options, null);
	}	
}
