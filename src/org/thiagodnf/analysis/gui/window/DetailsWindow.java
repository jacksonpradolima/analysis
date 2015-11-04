package org.thiagodnf.analysis.gui.window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.thiagodnf.analysis.gui.component.ResultTable;
import org.thiagodnf.analysis.indicator.Indicator;
import org.thiagodnf.analysis.util.NumberUtils;
import org.thiagodnf.core.util.PropertiesUtils;
import org.thiagodnf.core.util.StringUtils;

public class DetailsWindow extends DialogWindow{

	private static final long serialVersionUID = -2819220375520561750L;
	
	protected ResultTable table;
	
	protected Indicator indicator;
	
	protected List<String> files;
	
	protected String folderName;
	
	public DetailsWindow(JFrame parent, Indicator indicator, String folderName, List<String> files) throws IOException{
		super(parent, indicator.getName()+" Indicator");
		
		this.files = files;
		this.parent = parent;
		this.folderName = folderName;
		this.indicator = indicator;
		
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
		
		this.table = new ResultTable(parent);
		this.table.setModel(new DefaultTableModel(columns, 3));
		this.table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );   
		
		add(new JScrollPane(table));
		
		List<Object[]> data = new ArrayList<Object[]>();
	
		// Get all indicator	
		for (String file : files) {
			String path = StringUtils.replaceFirst(file, folderName, "").replaceFirst("SUMMARY","");
			
			Properties prop = PropertiesUtils.getFromFile(file);
			
			Object[] row = new Object[] {
				new Boolean(false),
				path,
				NumberUtils.round(prop.get(indicator.getKey()+".mean")),
				NumberUtils.round(prop.get(indicator.getKey()+".sd")),				
				NumberUtils.round(prop.get(indicator.getKey()+".max")),
				NumberUtils.round(prop.get(indicator.getKey()+".min")),
				NumberUtils.round(prop.get(indicator.getKey()+".sum")),
				NumberUtils.round(prop.get(indicator.getKey()+".n")),
				NumberUtils.round(prop.get(indicator.getKey()+".variance")),
			};
			
			data.add(row);
		}
		
		//Define the max width of first column. This column contains the checkbox element
		this.table.getColumnModel().getColumn(0).setMaxWidth(25);
		
		// Before add new datas, remove all rows on table
		table.clear();
		
		for (Object[] d : data) {
			((DefaultTableModel) table.getModel()).addRow(d);
		}
	}
}
