package org.thiagodnf.analysis.gui.component;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.thiagodnf.analysis.gui.window.MessageBoxWindow;
import org.thiagodnf.core.util.FilesUtils;
import org.thiagodnf.core.util.PropertiesUtils;

public class ResultTab extends Panel{
	
	private static final long serialVersionUID = -8471009008562846917L;
	
	protected String folderName;
	
	protected JTable table;
	
	protected DefaultTableModel model;
	
	protected JFrame parent;
	
	public ResultTab(JFrame parent, String folderName){
		this.folderName = folderName;	
		this.parent = parent;
		
		String[] columnNames = new String []{"#","Path","Hypervolume", "IGD"};
		
		this.model = new DefaultTableModel(new Object[][]{}, columnNames);
		
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
		
//		sorter.setComparator(2, new DoubleComparator());
//		sorter.setComparator(3, new DoubleComparator());
//		sorter.setComparator(4, new DoubleComparator());
//		sorter.setComparator(5, new DoubleComparator());
//		sorter.setComparator(6, new DoubleComparator());
		
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
		
		List<String> indicators = new ArrayList<String>();
		
		// Get all indicator	
		for (String file : files) {
			Properties prop = PropertiesUtils.getFromFile(file);
			for (Entry<Object, Object> entry : prop.entrySet()) {
				if (!indicators.contains(entry.getKey())) {
					indicators.add((String) entry.getKey());
				}
			}
		}	
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		// Get all indicator	
		for (String file : files) {
			Properties prop = PropertiesUtils.getFromFile(file);
			
			Object[] row = new Object[] { 
				new Boolean(false),
				file.replaceFirst(folderName,"").replaceFirst("SUMMARY",""),
				String.valueOf(prop.get("hypervolume.mean")),
				String.valueOf(prop.get("igd.mean")),					
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
	
	public String getFolderName(){
		return folderName;
	}

}
