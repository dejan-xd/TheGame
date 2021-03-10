package p.Grafics;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	private static final int zombieWIDTH = 49, zombieHEIGHT = 49;

	public static Font papyrus28, papyrus22, papyrus36;

	public static BufferedImage rock, rockEdge1, rockEdge2, rockEdge3, rockEdge4, rockEdge5, rockEdge6, rockEdge7,
			rockEdge8;
	public static BufferedImage sword;
	public static BufferedImage[] wind_up, wind_down, wind_left, wind_right, earth_up, earth_down, earth_left,
			earth_right, water_up, water_down, water_left, water_right, shadow_up, shadow_down, shadow_left,
			shadow_right, storm_up, storm_down, storm_left, storm_right, fire_up, fire_down, fire_left, fire_right,
			arcane_up, arcane_down, arcane_left, arcane_right, hunter_up, hunter_down, hunter_left, hunter_right,
			nature_up, nature_down, nature_left, nature_right, light_up, light_down, light_left, light_right;
	public static BufferedImage[] enemy_up, enemy_down, enemy_left, enemy_right;
	public static BufferedImage[] chest;
	public static BufferedImage[] start, settings, exit, back, newGame, loadGame, music, sound, off, quiet, medium,
			loud, difficulty, easy, normal, hard, next, previous, play, resume, save_game, main_menu, yes, no, ok;

	public static BufferedImage[] firespell, windspell, rockspell, waterspell, shadowspell, stormspell;

	public static void init() {
		papyrus22 = FontLoader.loadFont("source/fonts/papyrus.ttf", 22);
		papyrus28 = FontLoader.loadFont("source/fonts/papyrus.ttf", 28);
		papyrus36 = FontLoader.loadFont("source/fonts/papyrus.ttf", 36);

		SpriteSheet characterSheet = new SpriteSheet(ImageLoader.loadImage("/images/character/player.png"));
		SpriteSheet rockSheet = new SpriteSheet(ImageLoader.loadImage("/images/tiles/tiles.png"));
		SpriteSheet chestSheet = new SpriteSheet(ImageLoader.loadImage("/images/items/chest.png"));
		SpriteSheet itemsSheet = new SpriteSheet(ImageLoader.loadImage("/images/items/items.png"));
		SpriteSheet zombieSheet = new SpriteSheet(ImageLoader.loadImage("/images/enemies/zombies.png"));
		SpriteSheet spells = new SpriteSheet(ImageLoader.loadImage("/images/spells/spells.png"));

		wind_up = new BufferedImage[3];
		wind_down = new BufferedImage[3];
		wind_left = new BufferedImage[3];
		wind_right = new BufferedImage[3];
		earth_up = new BufferedImage[3];
		earth_down = new BufferedImage[3];
		earth_left = new BufferedImage[3];
		earth_right = new BufferedImage[3];
		water_up = new BufferedImage[3];
		water_down = new BufferedImage[3];
		water_left = new BufferedImage[3];
		water_right = new BufferedImage[3];
		shadow_up = new BufferedImage[3];
		shadow_down = new BufferedImage[3];
		shadow_left = new BufferedImage[3];
		shadow_right = new BufferedImage[3];
		storm_up = new BufferedImage[3];
		storm_down = new BufferedImage[3];
		storm_left = new BufferedImage[3];
		storm_right = new BufferedImage[3];
		fire_up = new BufferedImage[3];
		fire_down = new BufferedImage[3];
		fire_left = new BufferedImage[3];
		fire_right = new BufferedImage[3];
		arcane_up = new BufferedImage[3];
		arcane_down = new BufferedImage[3];
		arcane_left = new BufferedImage[3];
		arcane_right = new BufferedImage[3];
		hunter_up = new BufferedImage[3];
		hunter_down = new BufferedImage[3];
		hunter_left = new BufferedImage[3];
		hunter_right = new BufferedImage[3];
		nature_up = new BufferedImage[3];
		nature_down = new BufferedImage[3];
		nature_left = new BufferedImage[3];
		nature_right = new BufferedImage[3];
		light_up = new BufferedImage[3];
		light_down = new BufferedImage[3];
		light_left = new BufferedImage[3];
		light_right = new BufferedImage[3];

		firespell = new BufferedImage[5];
		windspell = new BufferedImage[4];
		rockspell = new BufferedImage[4];
		waterspell = new BufferedImage[8];
		shadowspell = new BufferedImage[3];
		stormspell = new BufferedImage[5];

		enemy_up = new BufferedImage[3];
		enemy_down = new BufferedImage[3];
		enemy_left = new BufferedImage[3];
		enemy_right = new BufferedImage[3];

		chest = new BufferedImage[4];

//		start = new BufferedImage[2];
//		settings = new BufferedImage[2];
//		exit = new BufferedImage[2];
//		back = new BufferedImage[2];
//		newGame = new BufferedImage[2];
//		loadGame = new BufferedImage[2];
//		music = new BufferedImage[2];
//		sound = new BufferedImage[2];
//		off = new BufferedImage[2];
//		quiet = new BufferedImage[2];
//		medium = new BufferedImage[2];
//		loud = new BufferedImage[2];
//		difficulty = new BufferedImage[2];
//		easy = new BufferedImage[2];
//		normal = new BufferedImage[2];
//		hard = new BufferedImage[2];
//		next = new BufferedImage[2];
//		previous = new BufferedImage[2];
//		play = new BufferedImage[2];
//		resume = new BufferedImage[2];
//		save_game = new BufferedImage[2];
//		main_menu = new BufferedImage[2];
//		yes = new BufferedImage[2];
//		no = new BufferedImage[2];
//		ok = new BufferedImage[2];

		wind_down[1] = characterSheet.crop(4, 1, 33, 33);
		wind_down[0] = characterSheet.crop(36, 1, 33, 33);
		wind_down[2] = characterSheet.crop(68, 1, 33, 33);
		wind_left[1] = characterSheet.crop(4, 34, 33, 33);
		wind_left[0] = characterSheet.crop(36, 34, 33, 33);
		wind_left[2] = characterSheet.crop(68, 34, 33, 33);
		wind_right[1] = characterSheet.crop(4, 67, 33, 33);
		wind_right[0] = characterSheet.crop(36, 67, 33, 33);
		wind_right[2] = characterSheet.crop(68, 67, 33, 33);
		wind_up[1] = characterSheet.crop(4, 100, 33, 33);
		wind_up[0] = characterSheet.crop(36, 100, 33, 33);
		wind_up[2] = characterSheet.crop(68, 100, 33, 33);

		earth_down[1] = characterSheet.crop(108, 1, 33, 33);
		earth_down[0] = characterSheet.crop(140, 1, 33, 33);
		earth_down[2] = characterSheet.crop(172, 1, 33, 33);
		earth_left[1] = characterSheet.crop(108, 34, 33, 33);
		earth_left[0] = characterSheet.crop(140, 34, 33, 33);
		earth_left[2] = characterSheet.crop(173, 34, 33, 33);
		earth_right[1] = characterSheet.crop(108, 67, 33, 33);
		earth_right[0] = characterSheet.crop(140, 67, 33, 33);
		earth_right[2] = characterSheet.crop(171, 67, 33, 33);
		earth_up[1] = characterSheet.crop(109, 100, 33, 33);
		earth_up[0] = characterSheet.crop(141, 100, 33, 33);
		earth_up[2] = characterSheet.crop(173, 100, 33, 33);

		water_down[1] = characterSheet.crop(214, 1, 33, 33);
		water_down[0] = characterSheet.crop(247, 1, 33, 33);
		water_down[2] = characterSheet.crop(280, 1, 33, 33);
		water_left[1] = characterSheet.crop(214, 34, 33, 33);
		water_left[0] = characterSheet.crop(247, 34, 33, 33);
		water_left[2] = characterSheet.crop(280, 34, 33, 33);
		water_right[1] = characterSheet.crop(214, 67, 33, 33);
		water_right[0] = characterSheet.crop(247, 67, 33, 33);
		water_right[2] = characterSheet.crop(280, 67, 33, 33);
		water_up[1] = characterSheet.crop(214, 100, 33, 33);
		water_up[0] = characterSheet.crop(247, 100, 33, 33);
		water_up[2] = characterSheet.crop(280, 100, 33, 33);

		shadow_down[1] = characterSheet.crop(319, 1, 33, 33);
		shadow_down[0] = characterSheet.crop(351, 1, 33, 33);
		shadow_down[2] = characterSheet.crop(383, 1, 33, 33);
		shadow_left[1] = characterSheet.crop(319, 34, 33, 33);
		shadow_left[0] = characterSheet.crop(351, 34, 33, 33);
		shadow_left[2] = characterSheet.crop(383, 34, 33, 33);
		shadow_right[1] = characterSheet.crop(319, 67, 33, 33);
		shadow_right[0] = characterSheet.crop(351, 67, 33, 33);
		shadow_right[2] = characterSheet.crop(383, 67, 33, 33);
		shadow_up[1] = characterSheet.crop(319, 100, 33, 33);
		shadow_up[0] = characterSheet.crop(351, 100, 33, 33);
		shadow_up[2] = characterSheet.crop(383, 100, 33, 33);

		storm_down[1] = characterSheet.crop(424, 1, 33, 33);
		storm_down[0] = characterSheet.crop(456, 1, 33, 33);
		storm_down[2] = characterSheet.crop(488, 1, 33, 33);
		storm_left[1] = characterSheet.crop(424, 34, 33, 33);
		storm_left[0] = characterSheet.crop(456, 34, 33, 33);
		storm_left[2] = characterSheet.crop(488, 34, 33, 33);
		storm_right[1] = characterSheet.crop(424, 67, 33, 33);
		storm_right[0] = characterSheet.crop(456, 67, 33, 33);
		storm_right[2] = characterSheet.crop(488, 67, 33, 33);
		storm_up[1] = characterSheet.crop(424, 100, 33, 33);
		storm_up[0] = characterSheet.crop(456, 100, 33, 33);
		storm_up[2] = characterSheet.crop(488, 100, 33, 33);

		fire_down[1] = characterSheet.crop(3, 136, 33, 33);
		fire_down[0] = characterSheet.crop(36, 136, 33, 33);
		fire_down[2] = characterSheet.crop(69, 136, 33, 33);
		fire_left[1] = characterSheet.crop(3, 169, 33, 33);
		fire_left[0] = characterSheet.crop(36, 169, 33, 33);
		fire_left[2] = characterSheet.crop(69, 169, 33, 33);
		fire_right[1] = characterSheet.crop(3, 202, 33, 33);
		fire_right[0] = characterSheet.crop(36, 202, 33, 33);
		fire_right[2] = characterSheet.crop(69, 202, 33, 33);
		fire_up[1] = characterSheet.crop(3, 235, 33, 33);
		fire_up[0] = characterSheet.crop(36, 235, 33, 33);
		fire_up[2] = characterSheet.crop(69, 235, 33, 33);

		arcane_down[1] = characterSheet.crop(109, 136, 33, 33);
		arcane_down[0] = characterSheet.crop(141, 136, 33, 33);
		arcane_down[2] = characterSheet.crop(173, 136, 33, 33);
		arcane_left[1] = characterSheet.crop(109, 170, 33, 33);
		arcane_left[0] = characterSheet.crop(141, 170, 33, 33);
		arcane_left[2] = characterSheet.crop(173, 170, 33, 33);
		arcane_right[1] = characterSheet.crop(109, 203, 33, 33);
		arcane_right[0] = characterSheet.crop(141, 203, 33, 33);
		arcane_right[2] = characterSheet.crop(173, 203, 33, 33);
		arcane_up[1] = characterSheet.crop(109, 236, 33, 33);
		arcane_up[0] = characterSheet.crop(141, 236, 33, 33);
		arcane_up[2] = characterSheet.crop(173, 236, 33, 33);

		hunter_down[1] = characterSheet.crop(213, 136, 33, 33);
		hunter_down[0] = characterSheet.crop(245, 136, 33, 33);
		hunter_down[2] = characterSheet.crop(279, 136, 33, 33);
		hunter_left[1] = characterSheet.crop(213, 169, 33, 33);
		hunter_left[0] = characterSheet.crop(245, 169, 33, 33);
		hunter_left[2] = characterSheet.crop(278, 169, 33, 33);
		hunter_right[1] = characterSheet.crop(213, 202, 33, 33);
		hunter_right[0] = characterSheet.crop(245, 202, 33, 33);
		hunter_right[2] = characterSheet.crop(279, 202, 33, 33);
		hunter_up[1] = characterSheet.crop(213, 235, 33, 33);
		hunter_up[0] = characterSheet.crop(245, 235, 33, 33);
		hunter_up[2] = characterSheet.crop(279, 235, 33, 33);

		nature_down[1] = characterSheet.crop(319, 136, 33, 33);
		nature_down[0] = characterSheet.crop(351, 136, 33, 33);
		nature_down[2] = characterSheet.crop(383, 136, 33, 33);
		nature_left[1] = characterSheet.crop(319, 169, 33, 33);
		nature_left[0] = characterSheet.crop(351, 169, 33, 33);
		nature_left[2] = characterSheet.crop(383, 169, 33, 33);
		nature_right[1] = characterSheet.crop(318, 202, 33, 33);
		nature_right[0] = characterSheet.crop(351, 202, 33, 33);
		nature_right[2] = characterSheet.crop(383, 202, 33, 33);
		nature_up[1] = characterSheet.crop(319, 235, 33, 33);
		nature_up[0] = characterSheet.crop(351, 235, 33, 33);
		nature_up[2] = characterSheet.crop(383, 235, 33, 33);

		light_down[1] = characterSheet.crop(424, 136, 33, 33);
		light_down[0] = characterSheet.crop(456, 136, 33, 33);
		light_down[2] = characterSheet.crop(488, 136, 33, 33);
		light_left[1] = characterSheet.crop(424, 169, 33, 33);
		light_left[0] = characterSheet.crop(456, 169, 33, 33);
		light_left[2] = characterSheet.crop(488, 169, 33, 33);
		light_right[1] = characterSheet.crop(424, 202, 33, 33);
		light_right[0] = characterSheet.crop(456, 202, 33, 33);
		light_right[2] = characterSheet.crop(488, 202, 33, 33);
		light_up[1] = characterSheet.crop(424, 235, 33, 33);
		light_up[0] = characterSheet.crop(456, 235, 33, 33);
		light_up[2] = characterSheet.crop(488, 235, 33, 33);

		enemy_down[1] = zombieSheet.crop(0, 0, zombieWIDTH, zombieHEIGHT);
		enemy_down[0] = zombieSheet.crop(zombieWIDTH, 0, zombieWIDTH, zombieHEIGHT);
		enemy_down[2] = zombieSheet.crop(zombieWIDTH * 2, 0, zombieWIDTH, zombieHEIGHT);
		enemy_left[1] = zombieSheet.crop(0, zombieWIDTH, zombieWIDTH, zombieHEIGHT);
		enemy_left[0] = zombieSheet.crop(zombieWIDTH, zombieHEIGHT, zombieWIDTH, zombieHEIGHT);
		enemy_left[2] = zombieSheet.crop(zombieWIDTH * 2, zombieHEIGHT, zombieWIDTH, zombieHEIGHT);
		enemy_right[1] = zombieSheet.crop(0, zombieHEIGHT * 2, zombieWIDTH, zombieHEIGHT);
		enemy_right[0] = zombieSheet.crop(zombieWIDTH, zombieHEIGHT * 2, zombieWIDTH, zombieHEIGHT);
		enemy_right[2] = zombieSheet.crop(zombieWIDTH * 2, zombieHEIGHT * 2, zombieWIDTH, zombieHEIGHT);
		enemy_up[1] = zombieSheet.crop(0, zombieHEIGHT * 3, zombieWIDTH, zombieHEIGHT);
		enemy_up[0] = zombieSheet.crop(zombieWIDTH, zombieHEIGHT * 3, zombieWIDTH, zombieHEIGHT);
		enemy_up[2] = zombieSheet.crop(zombieWIDTH * 2, zombieHEIGHT * 3, zombieWIDTH, zombieHEIGHT);

		windspell[0] = spells.crop(0, 31, 44, 35);
		windspell[1] = spells.crop(46, 31, 44, 35);
		windspell[2] = spells.crop(90, 31, 44, 35);
		windspell[3] = spells.crop(137, 31, 44, 35);
		rockspell[0] = spells.crop(0, 65, 34, 32);
		rockspell[1] = spells.crop(46, 65, 34, 32);
		rockspell[2] = spells.crop(93, 65, 34, 32);
		rockspell[3] = spells.crop(139, 65, 34, 32);
		waterspell[0] = spells.crop(0, 96, 47, 26);
		waterspell[1] = spells.crop(51, 96, 47, 26);
		waterspell[2] = spells.crop(97, 96, 47, 26);
		waterspell[3] = spells.crop(143, 96, 47, 26);
		waterspell[4] = spells.crop(190, 96, 47, 26);
		waterspell[5] = spells.crop(241, 96, 47, 26);
		waterspell[6] = spells.crop(287, 96, 47, 26);
		waterspell[7] = spells.crop(333, 96, 47, 26);
		shadowspell[0] = spells.crop(2, 120, 55, 25);
		shadowspell[1] = spells.crop(63, 120, 55, 25);
		shadowspell[2] = spells.crop(124, 120, 55, 25);
		stormspell[0] = spells.crop(2, 142, 54, 31);
		stormspell[1] = spells.crop(62, 142, 54, 31);
		stormspell[2] = spells.crop(125, 142, 54, 31);
		stormspell[3] = spells.crop(187, 142, 54, 31);
		stormspell[4] = spells.crop(250, 142, 54, 31);
		firespell[0] = spells.crop(0, 0, 54, 33);
		firespell[1] = spells.crop(54, 0, 54, 33);
		firespell[2] = spells.crop(108, 0, 54, 33);
		firespell[3] = spells.crop(162, 0, 54, 33);
		firespell[4] = spells.crop(216, 0, 54, 33);

		rock = rockSheet.crop(34, 133, 32, 32);
		rockEdge1 = rockSheet.crop(0, 99, 24, 24);
		rockEdge2 = rockSheet.crop(34, 99, 32, 24);
		rockEdge3 = rockSheet.crop(80, 99, 20, 26);
		rockEdge4 = rockSheet.crop(80, 133, 20, 32);
		rockEdge5 = rockSheet.crop(80, 178, 20, 21);
		rockEdge6 = rockSheet.crop(34, 178, 32, 21);
		rockEdge7 = rockSheet.crop(0, 179, 24, 20);
		rockEdge8 = rockSheet.crop(0, 133, 24, 32);

		chest[0] = chestSheet.crop(0, 0, 50, 50);
		chest[1] = chestSheet.crop(0, 50, 50, 50);
		chest[2] = chestSheet.crop(0, 100, 50, 50);
		chest[3] = chestSheet.crop(0, 150, 50, 50);

		sword = itemsSheet.crop(0, 339, 45, 45);

//		start[0] = menu_buttonsSheet.crop(0, 0, 125, 30);
//		start[1] = menu_buttonsSheet.crop(192, 0, 125, 30);
//		settings[0] = menu_buttonsSheet.crop(0, 35, 100, 30);
//		settings[1] = menu_buttonsSheet.crop(192, 35, 100, 30);
//		exit[0] = menu_buttonsSheet.crop(0, 65, 125, 30);
//		exit[1] = menu_buttonsSheet.crop(192, 65, 125, 30);
//		back[0] = menu_buttonsSheet.crop(0, 234, 65, 30);
//		back[1] = menu_buttonsSheet.crop(192, 234, 65, 30);
//		newGame[0] = menu_buttonsSheet.crop(0, 103, 125, 30);
//		newGame[1] = menu_buttonsSheet.crop(192, 103, 125, 30);
//		loadGame[0] = menu_buttonsSheet.crop(0, 135, 125, 30);
//		loadGame[1] = menu_buttonsSheet.crop(192, 135, 125, 30);
//
//		music[0] = music[1] = menu_buttonsSheet.crop(0, 300, 125, 30);
//		sound[0] = sound[1] = menu_buttonsSheet.crop(0, 335, 125, 30);
//		off[0] = menu_buttonsSheet.crop(0, 365, 50, 30);
//		off[1] = menu_buttonsSheet.crop(192, 365, 50, 30);
//		quiet[0] = menu_buttonsSheet.crop(0, 400, 70, 30);
//		quiet[1] = menu_buttonsSheet.crop(192, 400, 70, 30);
//		medium[0] = menu_buttonsSheet.crop(0, 435, 85, 30);
//		medium[1] = menu_buttonsSheet.crop(192, 435, 85, 30);
//		loud[0] = menu_buttonsSheet.crop(0, 465, 65, 30);
//		loud[1] = menu_buttonsSheet.crop(192, 465, 65, 30);
//
//		difficulty[0] = difficulty[1] = menu_buttonsSheet.crop(0, 500, 100, 30);
//		easy[0] = menu_buttonsSheet.crop(0, 533, 60, 30);
//		easy[1] = menu_buttonsSheet.crop(192, 533, 60, 30);
//		normal[0] = menu_buttonsSheet.crop(0, 565, 80, 30);
//		normal[1] = menu_buttonsSheet.crop(192, 565, 80, 30);
//		hard[0] = menu_buttonsSheet.crop(0, 600, 63, 30);
//		hard[1] = menu_buttonsSheet.crop(192, 600, 63, 30);
//		previous[0] = menu_buttonsSheet.crop(332, 3, 28, 33);
//		previous[1] = menu_buttonsSheet.crop(373, 3, 28, 33);
//		next[0] = menu_buttonsSheet.crop(334, 45, 29, 31);
//		next[1] = menu_buttonsSheet.crop(376, 44, 29, 31);
//		play[0] = menu_buttonsSheet.crop(334, 95, 47, 29);
//		play[1] = menu_buttonsSheet.crop(387, 95, 47, 29);
//
//		resume[0] = menu_buttonsSheet.crop(0, 203, 81, 23);
//		resume[1] = menu_buttonsSheet.crop(192, 203, 81, 23);
//		save_game[0] = menu_buttonsSheet.crop(0, 168, 121, 26);
//		save_game[1] = menu_buttonsSheet.crop(192, 168, 121, 26);
//		main_menu[0] = menu_buttonsSheet.crop(0, 627, 120, 23);
//		main_menu[1] = menu_buttonsSheet.crop(192, 627, 120, 23);
//
//		yes[0] = menu_buttonsSheet.crop(335, 137, 41, 24);
//		yes[1] = menu_buttonsSheet.crop(398, 137, 41, 24);
//		no[0] = menu_buttonsSheet.crop(331, 171, 38, 23);
//		no[1] = menu_buttonsSheet.crop(394, 171, 38, 23);
//		ok[0] = menu_buttonsSheet.crop(333, 203, 37, 24);
//		ok[1] = menu_buttonsSheet.crop(396, 203, 37, 24);

	}

}
