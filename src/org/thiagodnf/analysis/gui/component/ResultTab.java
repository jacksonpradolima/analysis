package org.thiagodnf.analysis.gui.component;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.thiagodnf.analysis.custom.DoubleComparator;
import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.analysis.indicator.EpsilonIndicator;
import org.thiagodnf.analysis.indicator.GDIndicator;
import org.thiagodnf.analysis.indicator.HypervolumeIndicator;
import org.thiagodnf.analysis.indicator.IGDIndicator;
import org.thiagodnf.analysis.indicator.InParetoFrontIndicator;
import org.thiagodnf.analysis.indicator.NumberOfNonRepeatedSolutionsIndicator;
import org.thiagodnf.analysis.indicator.NumberOfSolutionsIndicator;
import org.thiagodnf.analysis.indicator.SpreadIndicator;
import org.thiagodnf.analysis.indicator.TimeIndicator;
import org.thiagodnf.analysis.util.NumberUtils;
import org.thiagodnf.analysis.util.SettingsUtils;
import org.thiagodnf.core.util.FilesUtils;
import org.thiagodnf.core.util.PropertiesUtils;

public class ResultTab extends Panel{
	
	private static final long serialVersionUID = -8471009008562846917L;
	
	protected String folderName;
	
	protected JTable table;
	
	protected DefaultTableModel model;
	
	protected JFrame parent;
	
	protected Map<String, String> map;
	
	public ResultTab(JFrame parent, String folderName){
		this.folderName = folderName;	
		this.parent = parent;
		
		this.map = new HashMap<String, String>();
		
		List<String> columnNames = new ArrayList<String>();
		
		columnNames.add("#");
		columnNames.add("Path");
		columnNames.add(new HypervolumeIndicator().getName());
		columnNames.add(new GDIndicator().getName());
		columnNames.add(new IGDIndicator().getName());
		columnNames.add(new SpreadIndicator().getName());
		columnNames.add(new EpsilonIndicator().getName());
		columnNames.add(new NumberOfSolutionsIndicator().getName());
		columnNames.add(new NumberOfNonRepeatedSolutionsIndicator().getName());
		columnNames.add(new InParetoFrontIndicator().getName());
		columnNames.add(new TimeIndicator().getName());
		
		String[] columns = columnNames.toArray(new String[columnNames.size()]);

		this.model = new DefaultTableModel(new Object[][] {}, columns);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.table = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    default:
                    	return String.class;
                }
                
            }
                      
            public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				}
            	return false;               
            };
		};
		
		this.table.setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		
		// Add custom comparator in table's columns
		for (int i = 2; i < columnNames.size(); i++) {
			sorter.setComparator(i, new DoubleComparator());
		}
		
		this.table.setRowSorter(sorter);
		
		//Define the max width of first column. This column contains the checkbox element
		this.table.getColumnModel().getColumn(0).setMaxWidth(25);
				
		add(new JScrollPane(table));
			
		try {
			load();
		} catch (Exception ex) {
			MessageBoxWindow.error(parent, ex.getClass().getName());
			ex.printStackTrace();
		}
	}
	
	public void load() throws IOException{
		List<String> files = FilesUtils.getFiles(new File(folderName), "SUMMARY");
		
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		int round = SettingsUtils.getDecimalPlaces();
		
		// Get all indicator	
		for (String file : files) {
			String path = file.replaceFirst(folderName, "").replaceFirst("SUMMARY","");
			
			// Save the link to file
			if (!map.containsKey(path)) {
				map.put(path, file);
			}
			
			Properties prop = PropertiesUtils.getFromFile(file);
			
			Object[] row = new Object[] {
				new Boolean(false),
				path,
				NumberUtils.formatNumbers(prop, new HypervolumeIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new GDIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new IGDIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new SpreadIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new EpsilonIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new NumberOfSolutionsIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new NumberOfNonRepeatedSolutionsIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new InParetoFrontIndicator().getKey(), round),
				NumberUtils.formatNumbers(prop, new TimeIndicator().getKey(), round),
			};
			
			data.add(row);
		}
				
		// Before add new datas, remove all rows on table
		clear();
		
		for (Object[] d : data) {
			((DefaultTableModel) table.getModel()).addRow(d);
		}
	}
	
	public JTable getJTable() {
		return this.table;
	}
	
	public void clear(){
		while (table.getRowCount() != 0) {
			((DefaultTableModel) table.getModel()).removeRow(0);
		}
	}
	
	public String getFolderName(){
		return folderName;
	}

	public void clearSelection() {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.getModel().setValueAt(new Boolean(false), i, 0);
		}
	}
	
	public List<Integer> getMarkedRows() {
		List<Integer> selectedRows = new ArrayList<Integer>();

		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			if (table.getModel().getValueAt(i, 0).equals(new Boolean(true))) {
				selectedRows.add(i);
			}
		}

		return selectedRows;
	}
	
	public List<String> getSelectedFolderFiles() {
		List<String> files = new ArrayList<String>();

		List<Integer> markedRows = getMarkedRows();

		for (Integer rowId : markedRows) {
			files.add(map.get(table.getModel().getValueAt(rowId, 1)));
		}

		return files;
	}
}
