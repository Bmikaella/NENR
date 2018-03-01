package hr.fer.zemris.interactive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmihaela.
 */
public class Canvas extends JPanel {

	private List<Point> dots = new ArrayList<>();
	private static final Color LINE_COLOR = new Color(198, 59, 134);
	private static final Color BACKGROUND_COLOR = new Color(104, 188, 198);

	public Canvas() {

		MouseMotionAdapter motion = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				dots.add(e.getPoint());
				repaint();
			}
		};
		MouseAdapter mouseAction = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				dots.clear();
				dots.add(e.getPoint());
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);

			}

		};
		this.addMouseMotionListener(motion);
		this.addMouseListener(mouseAction);

	}

	public List<Point> getDots() {
		return new ArrayList<>(dots);
	}

	public void clearDots() {
		dots.clear();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, 1400, 750);

		g.setColor(LINE_COLOR);
		for (Point dot : dots) {
			g.fillOval(dot.x, dot.y, 5, 5);
		}
	}
}
