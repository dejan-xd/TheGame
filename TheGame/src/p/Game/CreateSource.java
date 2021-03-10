package p.Game;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateSource {

	public void create() {
		FileWriter writer = null;
		try {
			Files.createDirectories(Paths.get("source/game/accounts/new game/settings"));
			try {
				writer = new FileWriter("source/game/accounts/new game/settings/settings.txt");
				writer.write("music quiet\r\n");
				writer.write("sound quiet\r\n");
				writer.write("character 1\r\n");
				writer.write("difficulty easy");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}

			try {
				writer = new FileWriter("source/game/accounts/new game/new_game.txt");
				writer.write("player 300 300 0 3.0 30.0\r\n");
				writer.write("chest 200 400 1 0.0 0.0\r\n");
				writer.write("chest 550 450 2 0.0 0.0\r\n");
				writer.write("chest 400 200 3 0.0 0.0\r\n");
				writer.write("zombie 700 700 1 1.0 30.0\r\n");
				writer.write("zombie 700 100 2 1.0 30.0\r\n");
				writer.write("zombie 100 700 3 1.0 30.0\r\n");
				writer.write("zombie 60 60 4 1.0 30.0");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}

			Files.createDirectories(Paths.get("source/game/users"));
			new FileOutputStream("source/game/users/users.txt", true).close();

			Files.createDirectories(Paths.get("source/game/worlds"));
			new FileOutputStream("source/game/worlds/world.txt", true).close();
			try {
				writer = new FileWriter("source/game/worlds/world.txt");
				writer.write("50 25\r\n");
				writer.write(
						"1 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 3\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 1 2 2 2 2 2 3 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 8 9 9 9 9 9 4 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 8 9 9 9 9 9 4 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 8 9 9 9 9 9 4 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 8 9 9 9 9 9 4 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 7 6 6 6 6 6 5 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"8 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 4\r\n");
				writer.write(
						"7 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 5");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				writer.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
