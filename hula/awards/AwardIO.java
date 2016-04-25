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
						if (line.trim() == award) {
							/**
							 * don't write
							 */
							System.out.println("line already exists!");
							canWeWrite = false;
						}
					}
				}

				if (canWeWrite) {
					bw.write(award + System.getProperty("line.separator"));
				}

				bw.close();

				System.out.println("Successfully saved game award data");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
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
					System.out.println("it doesnt exist");
				}

				List<String> lines = new ArrayList<String>();

				try (BufferedReader br = new BufferedReader(new FileReader(awardsFile.getPath()))) {
					for (String line; (line = br.readLine()) != null;) {
						Award.valueOf(line);
						System.out.println("line exists!");
					}					
				}

			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
