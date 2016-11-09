package br.ufpr.inf.gres.gui;

import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.ufpr.inf.gres.gui.window.MainWindow;
import br.ufpr.inf.gres.core.util.LoggerUtils;
import br.ufpr.inf.gres.core.util.SettingsUtils;

/**
 * Main Class
 *
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class Launcher {

    public static void main(String[] args) throws Exception {
        Logger logger = LoggerUtils.getLogger(Launcher.class.getName());

        UIManager.setLookAndFeel(SettingsUtils.getLookAndFeel());

        logger.info("Starting...");

        SwingUtilities.invokeLater(() -> {
            // Here, we can safely update the GUI
            // because we'll be called from the
            // event dispatch thread
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
