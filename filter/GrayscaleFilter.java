package virtuso2.filter;

import java.awt.image.BufferedImage;

public class GrayscaleFilter extends BaseFilter {

	public GrayscaleFilter(BufferedImage image) {
		super(image);
	}

	public BufferedImage average() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int c = (rgb[R] + rgb[G] + rgb[B]) / 3;

				newimage.setRGB(i, j, colorRGB(c, c, c));
			}
		}

		return newimage;
	}

	// ITU-R Recommendation BT.601
	public BufferedImage sdtv() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int c = (int) (0.299f * rgb[R] + 0.587f * rgb[G] + 0.114f * rgb[B]);

				newimage.setRGB(i, j, colorRGB(c, c, c));
			}
		}

		return newimage;
	}

	// ITU-R Recommendation BT.709
	public BufferedImage hdtv() {
		newimage = new BufferedImage(width, height, type);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int c = Math.round((0.2126f * rgb[R] + 0.7152f * rgb[G] + 0.0722f * rgb[B]));

				newimage.setRGB(i, j, colorRGB(c, c, c));
			}
		}

		return newimage;
	}

	public BufferedImage threshold(int max) {
		return threshold(0, max);
	}

	public BufferedImage threshold(int min, int max) {
		newimage = hdtv();

		final int BLACK = colorRGB(0, 0, 0);
		final int WHITE = colorRGB(255, 255, 255);

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = newimage.getRGB(i, j) & 0xFF;

				newimage.setRGB(i, j, (px >= min && px <= max) ? BLACK : WHITE);
			}
		}

		return newimage;
	}

	public int[] histogram() {
		int[] hist = new int[256];

		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				int px = image.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int c = (int) (0.299f * rgb[R] + 0.587f * rgb[G] + 0.114f * rgb[B]);

				hist[c]++;
			}
		}

		return hist;
	}

	public int[] histogram(int x, int y, int w, int h) {
		int[] hist = new int[256];

		if (x + w > width) {
			w = width - x;
		}

		if (y + h > height) {
			h = height - y;
		}

		for (int j = y; j < y + h; j++) {
			for (int i = x; i < x + w; i++) {
				int px = image.getRGB(i, j);

				int[] rgb = getRGBComponents(px);
				int c = (int) (0.299f * rgb[R] + 0.587f * rgb[G] + 0.114f * rgb[B]);

				hist[c]++;
			}
		}

		return hist;
	}

}
