package intrum.assessment.framework.util;

import java.io.*;

import static intrum.assessment.framework.util.DateUtil.currentTimeUTC;

public class Log {

	static PrintWriter writer;
	static FileOutputStream fos;
	File progress = new File("./Log/Progress.txt");
	File temp = new File("./temp.txt");

	public void writeProgress(String msg) {
		try {
			String timestamp = currentTimeUTC();
			System.out.println("[LOG] " + timestamp + " " + msg);
			String progresspath = "./Log/";
			progresspath = progresspath + loadTemp(temp);
			progress = new File(progresspath);
			fos = new FileOutputStream(progress, true);
			writer = new PrintWriter(fos);
			writer.println("\r" + timestamp + " : " + msg);
			writer.close();
		} catch (Exception e) {
			System.out.println("[LOG] " + currentTimeUTC() + " Error writing progress to file due " + e.getMessage());
		}
	}

	public String loadTemp(File input) {
		String classname = null;
		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				classname = sCurrentLine;
			}

		} catch (IOException e) {
			System.out.println("[LOG] " + currentTimeUTC() + " Error loading temp file due " + e.getMessage());
		}

		return classname;
	}

}
