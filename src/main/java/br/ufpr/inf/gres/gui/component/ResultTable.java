package br.ufpr.inf.gres.gui.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import br.ufpr.inf.gres.core.custom.DoubleComparator;
import br.ufpr.inf.gres.core.indicator.EDIndicator;
import br.ufpr.inf.gres.gui.window.MainWindow;
import br.ufpr.inf.gres.core.indicator.EpsilonIndicator;
import br.ufpr.inf.gres.core.indicator.GDIndicator;
import br.ufpr.inf.gres.core.indicator.HypervolumeIndicator;
import br.ufpr.inf.gres.core.indicator.IGDIndicator;
import br.ufpr.inf.gres.core.indicator.InParetoFrontIndicator;
import br.ufpr.inf.gres.core.indicator.NumberOfNonRepeatedSolutionsIndicator;
import br.ufpr.inf.gres.core.indicator.NumberOfSolutionsIndicator;
import br.ufpr.inf.gres.core.indicator.SpreadIndicator;
import br.ufpr.inf.gres.core.indicator.TimeIndicator;
import br.ufpr.inf.gres.core.util.FilesUtils;
import br.ufpr.inf.gres.core.util.NumberUtils;
import br.ufpr.inf.gres.core.util.OSUtils;
import br.ufpr.inf.gres.core.util.PropertiesUtils;
import br.ufpr.inf.gres.core.util.SettingsUtils;

public class ResultTable extends JTable{
	
	private static final long serialVersionUID = -8471009008562846917L;
	
	protected JFrame parent;
	
	protected Map<String, String> map;
	
	protected File directory;
	
	public ResultTable(JFrame parent){
		this.parent = parent;
		
		this.map = new HashMap<>();
		
		List<String> columnNames = new ArrayList<>();
		
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
		columnNames.add("Solutions in PFKnown");
		columnNames.add("PFKnown in PFTrue");
                columnNames.add("Error Ratio (ER)");
                columnNames.add("ED");
		
		String[] columns = columnNames.toArray(new String[columnNames.size()]);

		setModel(new DefaultTableModel(new Object[][] {}, columns));
		
		setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());
		
		// Add custom comparator in table's columns
		for (int i = 2; i < columnNames.size(); i++) {
			sorter.setComparator(i, new DoubleComparator());
		}
		
		setRowSorter(sorter);
		
		//Define the max width of first column. This column contains the checkbox element
		getColumnModel().getColumn(0).setMaxWidth(25);
	}
	
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
	
	public void reload() throws IOException {
		if (directory != null) {
			load(directory);
		}
	}
	
	public void load(File directory) throws IOException{
		this.directory = directory;
		
		List<String> files = FilesUtils.getFiles(directory, "SUMMARY");
		
		List<Object[]> data = new ArrayList<Object[]>();
		
		this.map.clear();
		
		// Get all indicator	
		for (String file : files) {
			String path = formatPath(directory.getAbsolutePath(), file.replaceAll("SUMMARY", ""));
			
			String p = new File(path).getParent();
			
			Properties pPFKnown = null;

			if (new File(directory.getAbsolutePath() + p + File.separator + "QI_PFKNOWN").exists()) {
				pPFKnown = PropertiesUtils.getFromFile(directory.getAbsolutePath() + p + File.separator + "QI_PFKNOWN");
			}
			
			// Save the link to file
			if (!map.containsKey(path)) {
				map.put(path, file);
			}
			
			Properties prop = PropertiesUtils.getFromFile(file);
			
                        double numberOfSolutions = (pPFKnown == null) ? 0.0 : Double.parseDouble(pPFKnown.getProperty("number-of-solutions"));
                        double inParetoFront = (pPFKnown == null) ? 0.0 : Double.parseDouble(pPFKnown.getProperty("in-pareto-front"));
                        double errorRation = (pPFKnown == null) ? 0.0 : ((numberOfSolutions - inParetoFront) / numberOfSolutions);
                        
			Object[] row = new Object[] {
                                false,
				path,
				NumberUtils.formatNumbers(prop, new HypervolumeIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new GDIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new IGDIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new SpreadIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new EpsilonIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new NumberOfSolutionsIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new NumberOfNonRepeatedSolutionsIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new InParetoFrontIndicator().getKey()),
				NumberUtils.formatNumbers(prop, new TimeIndicator().getKey()),
				numberOfSolutions,
				inParetoFront,	
                                NumberUtils.round(errorRation, Integer.valueOf(SettingsUtils.getDecimalPlaces())),
                                NumberUtils.formatNumbers(prop, new EDIndicator().getKey()),
			};
			
			List<String> filter = ((MainWindow) parent).getFilter();

			if (filter == null || filter.isEmpty()) {
				data.add(row);
			} else {
				for (String key : filter) {
					if (path.toLowerCase().contains(key.toLowerCase())) {
						data.add(row);
					}
				}
			}
		}
				
		// Before add new datas, remove all rows on table
		clear();
		
		for (Object[] d : data) {
			((DefaultTableModel) getModel()).addRow(d);
		}
	}
	
	public void clear(){
		while (getRowCount() != 0) {
			((DefaultTableModel) getModel()).removeRow(0);
		}
	}

        @Override
	public void clearSelection() {
		for (int i = 0; i < getRowCount(); i++) {
			getModel().setValueAt(false, i, 0);
		}
	}
	
	public List<Integer> getMarkedRows() {
		List<Integer> selectedRows = new ArrayList<>();

		for (int i = 0; i < getModel().getRowCount(); i++) {
			if (getModel().getValueAt(i, 0).equals(true)) {
				selectedRows.add(i);
			}
		}

		return selectedRows;
	}
	
	public List<String> getSelectedFolderFiles() {
		List<String> files = new ArrayList<>();

		List<Integer> markedRows = getMarkedRows();

                markedRows.stream().forEach((Integer rowId) -> {
                    files.add(map.get(getModel().getValueAt(rowId, 1)));
                });

		return files;
	}
	
	public String formatPath(String directory, String file){
		if (OSUtils.isWindows()) {
			directory = directory.replaceAll("\\\\", "/");
			file = file.replaceAll("\\\\", "/");
		}
		
		return file.replaceAll(directory, "");
	}
	
	public File getDirectory(){
		return this.directory;
	}
}
