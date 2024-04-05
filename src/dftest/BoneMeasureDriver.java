package dftest;

import javax.swing.JOptionPane;

import joinery.DataFrame;
import mgr.measure.*;

public class BoneMeasureDriver {

	public static void main(String[] args) {
		BoneMeasureMgr bmMgr = new BoneMeasureMgr();

		String tattoo = JOptionPane.showInputDialog(null, "A CS_Tattoo please...");
		DataFrame<Object> df = bmMgr.getMeasureByTattoo(tattoo);
		
		if (df != null) {
			System.out.println(df.size() + "x" + df.length());
			System.out.println(df.row(0));
		}
		else
			System.out.println("No measure data for " + tattoo);
	}

}
