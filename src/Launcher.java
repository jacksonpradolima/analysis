import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.thiagodnf.analysis.gui.window.MainWindow;

/**
 * Main Class
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class Launcher {

	public static void main(String[] args) throws Exception {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Here, we can safely update the GUI
				// because we'll be called from the
				// event dispatch thread
				MainWindow window = new MainWindow();
				window.setVisible(true);
			}
		});
	}
}
