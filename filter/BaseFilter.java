package virtuso2.filter;

import java.awt.image.BufferedImage;

public abstract class BaseFilter {

	public static final int R = 0;
	public static final int G = 1;
	public static final int B = 2;
	public static final int A = 3;

	protected BufferedImage image, newimage;
	protected final int height, width, type;

	public BaseFilter(final BufferedImage image) {
		this.image = image;

		height = image.getHeight();
		width = image.getWidth();
		type = image.getType();
	}

	public int clamp(final int value) {
		if (value < 0) {
			return 0;
		} else if (value > 255) {
			return 255;
		}

		return value;
	}

	public int clamp(final int value, final int min, final int max) {
		if (value < min) {
			return min;
		} else if (value > max) {
			return max;
		}

		return value;
	}

	public int clamp(final double value) {
		if (value < 0) {
			return 0;
		} else if (value > 255) {
			return 255;
		}

		return (int) value;
	}

	public int colorRGB(final int[] rgb) {
		final int[] color = new int[] { 0, 0, 0 };
		System.arraycopy(rgb, 0, color, 0, rgb.length > 3 ? 3 : rgb.length);

		return colorRGB(color[R], color[G], color[B]);
	}

	public int colorRGB(final int r, final int g, final int b) {
		return (0xFF000000 | r << 16 | g << 8 | b);
	}

	public int colorRGBA(final int[] rgba) {
		final int[] color = new int[] { 0, 0, 0, 0 };
		System.arraycopy(rgba, 0, color, 0, rgba.length > 4 ? 4 : rgba.length);

		return colorRGBA(color[R], color[G], color[B], color[A]);
	}

	public int colorRGBA(final int r, final int g, final int b, final int a) {
		return (a << 24 | r << 16 | g << 8 | b);
	}

	public int getTransparency(final int color) {
		return (color >> 24);
	}

	public int getRed(final int color) {
		return (color >> 16) & 0xFF;
	}

	public int getGreen(final int color) {
		return (color >> 8) & 0xFF;
	}

	public int getBlue(final int color) {
		return (color & 0xFF);
	}

	public int getGrayscale(final int color) {
		final int r = (color >> 16) & 0xFF;
		final int g = (color >> 8) & 0xFF;
		final int b = (color & 0xFF);
		return Math.round((0.2126f * r + 0.7152f * g + 0.0722f * b));
	}

	public int[] getRGBComponents(final int color) {
		return new int[] { (color >> 16) & 0xFF, (color >> 8) & 0xFF, (color & 0xFF) };
	}

	public int[] getRGBAComponents(final int color) {
		return new int[] { (color >> 16) & 0xFF, (color >> 8) & 0xFF, (color & 0xFF), (color >> 24) };
	}

}
