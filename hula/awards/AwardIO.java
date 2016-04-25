package hula.awards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AwardIO {
	/**
	 * Set location for the awards.xyz
	 */
	public static final String awardsDirFile = "data/awards.xyz";

	public static void saveAward(String award) {
		try {
			try {
				File awardsFile = new File(awardsDirFile);

				if (!awardsFile.exists()) {
					awardsFile.createNewFile();
					System.out.println("it doesnt exist");
				}
				/**
				 * true argument to append
				 */
				FileWriter fw = new FileWriter(awardsFile.getPath(), true);

				BufferedWriter bw = new BufferedWriter(fw);

				boolean canWeWrite = true;

				try (BufferedReader br = new BufferedReader(new FileReader(awardsFile.getPath()))) {
					for (String line; (line = br.readLine()) != null;) {
						if (line.trim().equals(award.toString())) {
							/**
							 * don't write if the Enum has already been written / saved
							 * 
							 * This probably will never ever happen, because an enum that is TRUE is not given again,
							 * and this method is called from AwardEvent.giveAward()
							 */
							System.out.println("Enum has already been saved!");
							canWeWrite = false;
						}
					}
				}

				if (canWeWrite) {
					bw.write(award + System.getProperty("line.separator"));
				}

				bw.close();

				System.out.println("Successfully saved game (" + award.toString() + ")");
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readAwards() {
		try {
			try {
				File awardsFile = new File(awardsDirFile);

				if (!awardsFile.exists()) {
					awardsFile.createNewFile();
				}

				try (BufferedReader br = new BufferedReader(new FileReader(awardsFile.getPath()))) {
					for (String line; (line = br.readLine()) != null;) {
						try {
							Award.valueOf(line).setStateTrue();
							System.out.println("Enum named " + line + " exists!");
						} catch (IllegalArgumentException e) {
							System.out.println("Enum named " + line + " does not exist!");
							e.printStackTrace();
						}						
					}					
				}

			} catch (SecurityException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
