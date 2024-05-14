package boundary;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Cursor;

// this class extends JButton to make the button looks like a hyperlink
public class HyperLinkButtonUI extends JButton {
	public HyperLinkButtonUI(String text, Color color) {
		super(text); // set text
		setForeground(color); // set foreground color for the text
		setFocusPainted(true); // show the focus line
		setMargin(new Insets(0, 0, 0, 0)); // set margin
		setContentAreaFilled(false); // unfill the content area
		setBorderPainted(false); // remove the border line
		setOpaque(false); // set it to non-tranparent
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // change the cursor shape when hovered over
	}
}
