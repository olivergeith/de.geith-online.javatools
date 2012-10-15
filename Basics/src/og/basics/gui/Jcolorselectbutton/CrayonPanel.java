package og.basics.gui.Jcolorselectbutton;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/* Used by ColorChooserDemo2.java. */
public class CrayonPanel extends AbstractColorChooserPanel implements ActionListener {
	private static final long serialVersionUID = 2389524499578003673L;
	JToggleButton yellowCrayon;
	JToggleButton orangeCrayon;
	JToggleButton redCrayon;
	JToggleButton greenCrayon;
	JToggleButton blueAOKPCrayon;
	JToggleButton darkgrayCrayon;

	@Override
	public void updateChooser() {
		final Color color = getColorFromModel();

		if (Color.red.equals(color)) {
			redCrayon.setSelected(true);
		} else if (Color.orange.equals(color)) {
			orangeCrayon.setSelected(true);
		} else if (Color.yellow.equals(color)) {
			yellowCrayon.setSelected(true);
		} else if (Color.green.equals(color)) {
			greenCrayon.setSelected(true);
		} else if (blueAOKPCrayon.getBackground().equals(color)) {
			blueAOKPCrayon.setSelected(true);
		} else if (Color.blue.equals(color)) {
			blueAOKPCrayon.setSelected(true);
		}
	}

	protected JToggleButton createCrayon(final String name, final Border normalBorder, final Color col) {
		final JToggleButton crayon = new JToggleButton();
		crayon.setActionCommand(name);
		crayon.addActionListener(this);
		crayon.setText(name);
		crayon.setHorizontalAlignment(JButton.HORIZONTAL);
		crayon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		crayon.setBackground(col);
		return crayon;
	}

	@Override
	protected void buildChooser() {
		setLayout(new GridLayout(0, 1));

		final ButtonGroup boxOfCrayons = new ButtonGroup();
		final Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);

		yellowCrayon = createCrayon("yellow", border, Color.yellow);
		boxOfCrayons.add(yellowCrayon);
		add(yellowCrayon);

		orangeCrayon = createCrayon("orange", border, Color.orange);
		boxOfCrayons.add(orangeCrayon);
		add(orangeCrayon);

		redCrayon = createCrayon("red", border, Color.red);
		boxOfCrayons.add(redCrayon);
		add(redCrayon);

		greenCrayon = createCrayon("green", border, Color.green.darker());
		boxOfCrayons.add(greenCrayon);
		add(greenCrayon);

		blueAOKPCrayon = createCrayon("AOKP blue", border, new Color(15, 174, 234));
		boxOfCrayons.add(blueAOKPCrayon);
		add(blueAOKPCrayon);

		darkgrayCrayon = createCrayon("Dark Gray", border, Color.darkGray);
		boxOfCrayons.add(darkgrayCrayon);
		add(darkgrayCrayon);

	}

	public void actionPerformed(final ActionEvent e) {
		Color newColor = null;
		final JToggleButton toggle = (JToggleButton) e.getSource();
		newColor = toggle.getBackground();
		getColorSelectionModel().setSelectedColor(newColor);
	}

	@Override
	public String getDisplayName() {
		return "Predefined Colors";
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return null;
	}
}