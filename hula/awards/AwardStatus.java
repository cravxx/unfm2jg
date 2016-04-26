package hula.awards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Award Status<br>
 * - Track Award Progress
 * @author eli
 *
 */
public class AwardStatus {
	/**
	 * Set location for the statistics
	 * TODO security
	 */
	public static final String statisticsDir = "data/";
	
	
	/**
	 * total damage taken by you
	 */
	public static float Take1000UnitsOfDamage_Stat_1 = 0;
	
	/**
	 * total damage taken by you
	 */
	public static float Give1000UnitsOfDamage_Stat_1 = 0;
	
	public static void setTakeOneThousandUnitsOfDamage_Stat_1(final float damageTaken) {		
		Take1000UnitsOfDamage_Stat_1 += damageTaken;
		System.out.println("Added " + damageTaken + " to Take1000UnitsOfDamage_Stat_1");	
		
		if (Take1000UnitsOfDamage_Stat_1 >= 1000F && Award.TakeOneThousandUnitsOfDamage.getState() == false) {
			AwardEvent.giveAward(Award.TakeOneThousandUnitsOfDamage);
		}
		
	}
	
	public static void setGiveOneThousandUnitsOfDamage_Stat_1(final float damageGiven) {		
		Give1000UnitsOfDamage_Stat_1 += damageGiven;
		System.out.println("Added " + damageGiven + " to Give1000UnitsOfDamage_Stat_1");
		
		if (Give1000UnitsOfDamage_Stat_1 >= 1000F && Award.GiveOneThousandUnitsOfDamage.getState() == false) {
			AwardEvent.giveAward(Award.GiveOneThousandUnitsOfDamage);
		}
	}
	
	private static String returnFormatted(Float... statistics) {
		String fullString = "";
		for (Float singleStat : statistics) {
	        fullString += (singleStat + System.getProperty("line.separator"));   
	    }
		return fullString;
	}
	
	public static void saveStatistics() {
		try {
			try {
				File awardsFile = new File(statisticsDir + "statistics.xyz");

				if (!awardsFile.exists()) {
					awardsFile.createNewFile();
					System.out.println("it doesnt exist yet, made one");
				}

				FileWriter fw = new FileWriter(awardsFile.getPath());

				BufferedWriter bw = new BufferedWriter(fw);

				/**
				 * every stat written in declaration order
				 */
				bw.write(returnFormatted(Take1000UnitsOfDamage_Stat_1, Give1000UnitsOfDamage_Stat_1));

				bw.close();

				System.out.println("Successfully saved statistics");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void readStatistics() {
		try {
			try {
				File awardsFile = new File(statisticsDir + "statistics.xyz");

				if (!awardsFile.exists()) {
					/**
					 * does not exist
					 */
				}else{
					
					List<String> lines = new ArrayList<String>();								
					
					try (BufferedReader br = new BufferedReader(new FileReader(awardsFile.getPath()))) {
						for (String line; (line = br.readLine()) != null;) {
							lines.add(line);		
						}					
					}
					
					String[] arrayOfLines = lines.toArray(new String[0]);
					
					Take1000UnitsOfDamage_Stat_1 = Float.parseFloat(arrayOfLines[0]);
					Give1000UnitsOfDamage_Stat_1 = Float.parseFloat(arrayOfLines[1]);
					
					System.out.println("Successfully read statistics " + arrayOfLines);
				}
				
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
