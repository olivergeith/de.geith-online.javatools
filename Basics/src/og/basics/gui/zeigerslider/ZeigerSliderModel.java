package og.basics.gui.zeigerslider;

import javax.swing.SpinnerNumberModel;

/**
 * Ein verbessertes SpinnerNumberModel, dass nun auch auf die Bounds achtet.
 * 
 * @author Geith
 * 
 */
public class ZeigerSliderModel extends SpinnerNumberModel {
	private static final long serialVersionUID = 1L;

	public ZeigerSliderModel(int value, int minimum, int maximum, int step) {
		super(value, minimum, maximum, step);
	}

	public int getIntMin() {
		return ((Integer) getMinimum()).intValue();
	}

	public int getIntMax() {
		return ((Integer) getMaximum()).intValue();
	}

	public int getIntValue() {
		return ((Integer) getValue()).intValue();
	}

	@Override
	public void setValue(Object value) {
		int val = ((Integer) value).intValue();
		if (val > getIntMax())
			val = getIntMax();
		if (val < getIntMin())
			val = getIntMin();
		super.setValue(val);
	}

	public int getIntStep() {
		return ((Integer) getStepSize()).intValue();
	}

	public void stepUp() {
		setValue(getIntValue() + getIntStep());
	}

	public void stepDown() {
		setValue(getIntValue() - getIntStep());
	}
}
