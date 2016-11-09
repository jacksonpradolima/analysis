package br.ufpr.inf.gres.gui.action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import br.ufpr.inf.gres.gui.window.MessageBox;

/**
 * This class is a base class that implements all methods that a action (when a
 * user pushes a button) have to do.
 * 
 * @author Thiago Nascimento
 * @since 2015-10-27
 * @version 1.0.0
 */
public abstract class DoAction extends AbstractAction{

	private static final long serialVersionUID = -1974741216475489083L;
	
	protected JFrame parent;
	
	public DoAction(JFrame parent){
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			execute(event);
		} catch (Exception ex) {
			MessageBox.error(parent, ex);
		}
	}
	
	public abstract void execute(ActionEvent event) throws Exception;
}
