package comparator;

import java.awt.Point;
import java.io.Serializable;
import java.util.Comparator;

public class PointComparator implements Comparator<Point>,Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(Point e1, Point e2) {

		if (e1.getX() > e2.getX()) {
			return 1;
		} else if (e1.getX() < e2.getX()) {
			return -1;
		}

		if (e1.getY() > e2.getY()) {
			return 1;
		} else if (e1.getY() < e2.getY()) {
			return -1;
		}

		return 0;

	}
}
