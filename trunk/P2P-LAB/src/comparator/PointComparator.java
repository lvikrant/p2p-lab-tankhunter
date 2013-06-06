package comparator;

import java.awt.Point;
import java.util.Comparator;

public class PointComparator implements Comparator<Point> {

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
