package og.basics.gui;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * Diese Klasse erweitert die AbstractAction um eine Methode getActionName(), da
 * man bei der AbstractAction nur so umständlich da wieder über
 * getValue(AbstractAction.NAME) rankommt.
 * 
 * @author Geith
 * 
 */
public abstract class NamedAbstractAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private String actionName = "";

	public NamedAbstractAction(String actionName, Icon arg1) {
		super(actionName, arg1);
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}

}
