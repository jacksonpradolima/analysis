package org.thiagodnf.analysis.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import org.thiagodnf.analysis.gui.window.AboutWindow;

/**
 * This class is used to show a window to user with informations about this tool
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class DoAboutAction extends DoAction {
	
	private static final long serialVersionUID = 3492481247457943363L;

	public DoAboutAction(JFrame parent) {
		super(parent);
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		AboutWindow w = new AboutWindow(parent);
		w.showOptionDialog();
	}
}
