package comparator;

import java.io.Serializable;
import java.util.Comparator;

import model.NetworkTarget;

public class NTComparator implements Comparator<NetworkTarget>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int compare(NetworkTarget e1, NetworkTarget e2) {

		if (e1.getIP().compareTo(e2.getIP()) == 1) {
			return 1;
		} else if (e1.getIP().compareTo(e2.getIP()) == -1) {
			return -1;
		}

		if (e1.getPort() > e2.getPort()) {
			return 1;
		} else if (e1.getPort() < e2.getPort()) {
			return -1;
		}

		return 0;

	}
}
