package p.Utils;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import p.Game.Handler;

public class Utils {

	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static float parseFloat(String number) {
		try {
			return Float.parseFloat(number);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void appendStrToFile(String fileName, String str) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
			out.write(str + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void modifyFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}
			String newContent = oldContent.replaceAll(oldString, newString);
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteRowFromFile(String filePath, String tempFilePath, String lineToRemove) {
		File inputFile = new File(filePath);
		File tempFile = new File(tempFilePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String removeLine = lineToRemove;
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
				String trimmedLine = currentLine.trim();
				if (trimmedLine.equals(removeLine))
					continue;
				writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close();
			reader.close();
			inputFile.delete();
			tempFile.renameTo(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createSettingsTxt(File file) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(file);
			writer.println("music quiet");
			writer.println("sound quiet");
			writer.println("character 1");
			writer.println("difficulty easy");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static File[] findFilesInDir(String dirName) {
		File dir = new File(dirName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.toLowerCase().endsWith(".txt");
			}
		});
	}

	public static void deleteLastFileInDir(String path) {
		File f = new File(path);
		String[] pathnames;
		pathnames = f.list();
		String pathname = pathnames[0];
		Path p = Paths.get(path + "/" + pathname);
		if (Files.exists(p)) {
			try {
				Files.delete(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteSavedFile(String path, String name) {
		File f = new File(path);
		String[] pathnames;
		pathnames = f.list();
		for (String pathname : pathnames) {
			String[] tokens = pathname.split("--");
			if (tokens[1].equals(name)) {
				Path p = Paths.get(path + "/" + pathname);
				if (Files.exists(p)) {
					try {
						Files.delete(p);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public static void sortSavedFiles(String path, ArrayList<String> arrayList) {
		File f = new File(path);
		String[] pathnames;
		pathnames = f.list();
		for (int i = 0; i < findFilesInDir(path).length; i++) {
			String pathname = pathnames[i];
			arrayList.add(pathname);
		}
		Collections.sort(arrayList, Collections.reverseOrder());
	}

	public static int textWidth(String text, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	public static int textHeight(String text, Font font) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textHeight = (int) (font.getStringBounds(text, frc).getHeight());
		return textHeight;

	}

//	public static String a(String path) {
//		String[] pathnames;
//		File f = new File(path);
//		pathnames = f.list();
//		for (String pathname : pathnames) {
//			return pathname;
//		}
//		return null;
//
//	}

//	public static Integer numberOfFilesInDir(String path) {
//		File f = new File(path);
//		Path p = Paths.get(path);
//		String[] pathnames;
//		pathnames = f.list();
//
//		if (Files.exists(p)) {
//			return pathnames.length;
//		}
//		return null;
//	}

	public static boolean checkIfSaveFileExists(String dirName, Handler handler) {
		File f = new File(dirName);
		String[] pathnames, pathname;
		String name = null;
		pathnames = f.list();

		for (int i = 0; i < pathnames.length; i++) {
			pathname = pathnames[i].split("--");
			name = pathname[1];
			if (name.equals(handler.getKeyManager().getSaveName().substring(0,
					handler.getKeyManager().getSaveName().length() - 1)))
				return true;
		}
		return false;
	}
}
