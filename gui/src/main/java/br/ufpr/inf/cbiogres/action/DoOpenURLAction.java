package br.ufpr.inf.cbiogres.action;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.net.URI;
import javax.swing.Action;
import javax.swing.JFrame;

/**
 * This class is used to open a url in this tool
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public class DoOpenURLAction extends DoAction {
	
	private static final long serialVersionUID = 3492481247457943363L;

	public DoOpenURLAction(JFrame parent, String link) {
		this(parent, link, link);
	}
	
	public DoOpenURLAction(JFrame parent, String text, String link) {
		super(parent);
		putValue(Action.NAME, text);  
        putValue(Action.SHORT_DESCRIPTION, link); 
	}

	@Override
	public void execute(ActionEvent event) throws Exception {
		String url = (String) getValue(Action.SHORT_DESCRIPTION);

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(new URI(url));
		} else {
			throw new IllegalArgumentException("Desktop is not supported");
		}
	}
}
