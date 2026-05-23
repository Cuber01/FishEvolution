package org.sim;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Consts {
	public static final long RANDOM_SEED;

	public static final int CANVAS_WIDTH;
	public static final int CANVAS_HEIGHT;
	public static final int SIDEBAR_WIDTH;

	public static final int INITIAL_FISH_COUNT;

	public static final int SIM_DATA_GATHERING_RATE;

	public static final int SHALLOW_BIOME_UPPER_BORDER;
	public static final int MIDDLE_BIOME_UPPER_BORDER;
	public static final int DEEP_BIOME_UPPER_BORDER;

	public static final float SHALLOW_BIOME_ENERGY_MAX;
	public static final float MIDDLE_BIOME_ENERGY_MAX;
	public static final float DEEP_BIOME_ENERGY_MAX;

	public static final float SHALLOW_BIOME_PLANT_ENERGY;
	public static final float MIDDLE_BIOME_PLANT_ENERGY;
	public static final float DEEP_BIOME_PLANT_ENERGY;

	public static final Color SHALLOW_BIOME_COLOR;
	public static final Color MIDDLE_BIOME_COLOR;
	public static final Color DEEP_BIOME_COLOR;

	public static final float EGG_HATCH_TICKS;

	public static final float FISH_STARVING_ENERGY_MIN;
	public static final float FISH_MAX_ENERGY;

	public static final float SPEED_COST_MODIFIER;
	public static final float VISION_RANGE_COST_MODIFIER;
	public static final float MAX_HP_COST_MODIFIER;
	public static final float DAMAGE_COST_MODIFIER;
	public static final float GLOBAL_COST_MODIFIER;

	public static final float SPEED_MUTATION_WEIGHT;
	public static final float MAX_HP_MUTATION_WEIGHT;
	public static final float DAMAGE_MUTATION_WEIGHT;
	public static final float VISION_RANGE_MUTATION_WEIGHT;
	public static final float AGGRESSIVENESS_MUTATION_WEIGHT;
	public static final float PLANT_TO_MEAT_DIGESTION_MUTATION_WEIGHT;
	public static final float EGG_ENERGY_MUTATION_WEIGHT;
	public static final float EGG_CHILDREN_MUTATION_WEIGHT;

	public static final float GENE_EGG_ENERGY_MIN;
	public static final float GENE_EGG_ENERGY_MAX;
	public static final float GENE_EGG_CHILDREN_MIN;
	public static final float GENE_EGG_CHILDREN_MAX;

	public static final float GENE_SPEED_MIN;
	public static final float GENE_SPEED_MAX;
	public static final float GENE_MAX_HP_MIN;
	public static final float GENE_MAX_HP_MAX;
	public static final float GENE_DAMAGE_MIN;
	public static final float GENE_DAMAGE_MAX;
	public static final float GENE_VISION_RANGE_MIN;
	public static final float GENE_VISION_RANGE_MAX;
	public static final float GENE_AGGRESSIVENESS_MIN;
	public static final float GENE_AGGRESSIVENESS_MAX;
	public static final float GENE_PLANT_TO_MEAT_DIGESTION_MIN;
	public static final float GENE_PLANT_TO_MEAT_DIGESTION_MAX;

	static {
		Properties p = loadProperties("config.properties");

		RANDOM_SEED = parseLong(p, "RANDOM_SEED");

		CANVAS_WIDTH = parseInt(p, "CANVAS_WIDTH");
		CANVAS_HEIGHT = parseInt(p, "CANVAS_HEIGHT");
		SIDEBAR_WIDTH = parseInt(p, "SIDEBAR_WIDTH");

		INITIAL_FISH_COUNT = parseInt(p, "INITIAL_FISH_COUNT");

		SIM_DATA_GATHERING_RATE = parseInt(p, "SIM_DATA_GATHERING_RATE");

		SHALLOW_BIOME_UPPER_BORDER = parseInt(p, "SHALLOW_BIOME_UPPER_BORDER");
		MIDDLE_BIOME_UPPER_BORDER = parseInt(p, "MIDDLE_BIOME_UPPER_BORDER");
		DEEP_BIOME_UPPER_BORDER = parseInt(p, "DEEP_BIOME_UPPER_BORDER");

		SHALLOW_BIOME_ENERGY_MAX = parseFloat(p, "SHALLOW_BIOME_ENERGY_MAX");
		MIDDLE_BIOME_ENERGY_MAX = parseFloat(p, "MIDDLE_BIOME_ENERGY_MAX");
		DEEP_BIOME_ENERGY_MAX = parseFloat(p, "DEEP_BIOME_ENERGY_MAX");

		SHALLOW_BIOME_PLANT_ENERGY = parseFloat(p, "SHALLOW_BIOME_PLANT_ENERGY");
		MIDDLE_BIOME_PLANT_ENERGY = parseFloat(p, "MIDDLE_BIOME_PLANT_ENERGY");
		DEEP_BIOME_PLANT_ENERGY = parseFloat(p, "DEEP_BIOME_PLANT_ENERGY");

		SHALLOW_BIOME_COLOR = parseColor(p, "SHALLOW_BIOME_COLOR");
		MIDDLE_BIOME_COLOR = parseColor(p, "MIDDLE_BIOME_COLOR");
		DEEP_BIOME_COLOR = parseColor(p, "DEEP_BIOME_COLOR");

		EGG_HATCH_TICKS = parseFloat(p, "EGG_HATCH_TICKS");

		FISH_STARVING_ENERGY_MIN = parseFloat(p, "FISH_STARVING_ENERGY_MIN");
		FISH_MAX_ENERGY = parseFloat(p, "FISH_MAX_ENERGY");

		SPEED_COST_MODIFIER = parseFloat(p, "SPEED_COST_MODIFIER");
		VISION_RANGE_COST_MODIFIER = parseFloat(p, "VISION_RANGE_COST_MODIFIER");
		MAX_HP_COST_MODIFIER = parseFloat(p, "MAX_HP_COST_MODIFIER");
		DAMAGE_COST_MODIFIER = parseFloat(p, "DAMAGE_COST_MODIFIER");
		GLOBAL_COST_MODIFIER = parseFloat(p, "GLOBAL_COST_MODIFIER");

		SPEED_MUTATION_WEIGHT = parseFloat(p, "SPEED_MUTATION_WEIGHT");
		MAX_HP_MUTATION_WEIGHT = parseFloat(p, "MAX_HP_MUTATION_WEIGHT");
		DAMAGE_MUTATION_WEIGHT = parseFloat(p, "DAMAGE_MUTATION_WEIGHT");
		VISION_RANGE_MUTATION_WEIGHT = parseFloat(p, "VISION_RANGE_MUTATION_WEIGHT");
		AGGRESSIVENESS_MUTATION_WEIGHT = parseFloat(p, "AGGRESSIVENESS_MUTATION_WEIGHT");
		PLANT_TO_MEAT_DIGESTION_MUTATION_WEIGHT = parseFloat(p, "PLANT_TO_MEAT_DIGESTION_MUTATION_WEIGHT");
		EGG_ENERGY_MUTATION_WEIGHT = parseFloat(p, "EGG_ENERGY_MUTATION_WEIGHT");
		EGG_CHILDREN_MUTATION_WEIGHT = parseFloat(p, "EGG_CHILDREN_MUTATION_WEIGHT");

		GENE_EGG_ENERGY_MIN = parseFloat(p, "GENE_EGG_ENERGY_MIN");
		GENE_EGG_ENERGY_MAX = parseFloat(p, "GENE_EGG_ENERGY_MAX");
		GENE_EGG_CHILDREN_MIN = parseFloat(p, "GENE_EGG_CHILDREN_MIN");
		GENE_EGG_CHILDREN_MAX = parseFloat(p, "GENE_EGG_CHILDREN_MAX");

		GENE_SPEED_MIN = parseFloat(p, "GENE_SPEED_MIN");
		GENE_SPEED_MAX = parseFloat(p, "GENE_SPEED_MAX");
		GENE_MAX_HP_MIN = parseFloat(p, "GENE_MAX_HP_MIN");
		GENE_MAX_HP_MAX = parseFloat(p, "GENE_MAX_HP_MAX");
		GENE_DAMAGE_MIN = parseFloat(p, "GENE_DAMAGE_MIN");
		GENE_DAMAGE_MAX = parseFloat(p, "GENE_DAMAGE_MAX");
		GENE_VISION_RANGE_MIN = parseFloat(p, "GENE_VISION_RANGE_MIN");
		GENE_VISION_RANGE_MAX = parseFloat(p, "GENE_VISION_RANGE_MAX");
		GENE_AGGRESSIVENESS_MIN = parseFloat(p, "GENE_AGGRESSIVENESS_MIN");
		GENE_AGGRESSIVENESS_MAX = parseFloat(p, "GENE_AGGRESSIVENESS_MAX");
		GENE_PLANT_TO_MEAT_DIGESTION_MIN = parseFloat(p, "GENE_PLANT_TO_MEAT_DIGESTION_MIN");
		GENE_PLANT_TO_MEAT_DIGESTION_MAX = parseFloat(p, "GENE_PLANT_TO_MEAT_DIGESTION_MAX");
	}

	private static Properties loadProperties(String resourceName) {
		Properties p = new Properties();
		try (InputStream is = Consts.class.getClassLoader().getResourceAsStream(resourceName)) {
			if (is != null) {
				p.load(is);
			}
		} catch (IOException e) {
            System.err.println("File read error");
            System.exit(1);
		}
		return p;
	}

	private static int parseInt(Properties p, String key) {
		String s = p.getProperty(key);
		if (s == null) {
			System.err.println("Missing required integer property '" + key + "'");
			System.exit(1);
			return 0;
		}
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException ex) {
			System.err.println("Invalid integer for property '" + key + "': '" + s + "'");
			ex.printStackTrace(System.err);
			System.exit(1);
			return 0;
		}
	}

	private static long parseLong(Properties p, String key) {
		String s = p.getProperty(key);
		if (s == null) {
			System.err.println("Missing required long property '" + key + "'");
			System.exit(1);
			return 0L;
		}
		try {
			return Long.parseLong(s.trim());
		} catch (NumberFormatException ex) {
			System.err.println("Invalid long for property '" + key + "': '" + s + "'");
			ex.printStackTrace(System.err);
			System.exit(1);
			return 0L;
		}
	}

	private static float parseFloat(Properties p, String key) {
		String s = p.getProperty(key);
		if (s == null) {
			System.err.println("Missing required float property '" + key + "'");
			System.exit(1);
			return 0f;
		}
		try {
			return Float.parseFloat(s.trim());
		} catch (NumberFormatException ex) {
			System.err.println("Invalid float for property '" + key + "': '" + s + "'");
			ex.printStackTrace(System.err);
			System.exit(1);
			return 0f;
		}
	}

	private static Color parseColor(Properties p, String key) {
		String s = p.getProperty(key);
		if (s == null) {
			System.err.println("Missing required color property '" + key + "'");
			System.exit(1);
			return Color.BLACK;
		}
		String[] parts = s.split(",");
		try {
			int r = parts.length > 0 ? Integer.parseInt(parts[0].trim()) : 0;
			int g = parts.length > 1 ? Integer.parseInt(parts[1].trim()) : 0;
			int b = parts.length > 2 ? Integer.parseInt(parts[2].trim()) : 0;
			return new Color(r, g, b);
		} catch (Exception ex) {
			System.err.println("Invalid color for property '" + key + "': '" + s + "'");
			ex.printStackTrace(System.err);
			System.exit(1);
			return Color.BLACK;
		}
	}
}
