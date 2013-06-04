package commander.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

public class MyLayout extends FlowLayout {

	public MyLayout(int align) {
		super(align);
	}

	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);
			int nmembers = target.getComponentCount();
			boolean firstVisibleComponent = true;
			boolean useBaseline = getAlignOnBaseline();
			int maxAscent = 0;
			int maxDescent = 0;
			// max height of element in current row
			int maxRowHeight = 0;
			// the sum of height of previous rows
			int sum = 0;

			for (int i = 0; i < nmembers; i++) {
				int tWidth = target.getWidth();

				Component m = target.getComponent(i);
				if (m.isVisible()) {
					Dimension d = m.getPreferredSize();

					if (firstVisibleComponent) {
						firstVisibleComponent = false;
					} else {
						dim.width += getHgap();
					}

					if (dim.width + d.width + getHgap() > tWidth) {
						dim.width = d.width;
						sum += maxRowHeight + getVgap();
						maxRowHeight = d.height;
					} else {
						maxRowHeight = Math.max(maxRowHeight, d.height);
						dim.width += d.width;
					}

					if (useBaseline) {
						int baseline = m.getBaseline(d.width, d.height);
						if (baseline >= 0) {
							maxAscent = Math.max(maxAscent, baseline);
							maxDescent = Math.max(maxDescent, d.height
									- baseline);
						}
					}
				}
			}
			dim.height = sum + maxRowHeight;

			if (useBaseline) {
				dim.height = Math.max(maxAscent + maxDescent, dim.height);
			}
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right + getHgap() * 2;
			dim.height += insets.top + insets.bottom + getVgap() * 2;

			return dim;
		}
	}

}
