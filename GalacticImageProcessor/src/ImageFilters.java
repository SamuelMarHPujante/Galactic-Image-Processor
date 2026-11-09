import java.awt.image.BufferedImage;

/**
 * Filters supplied with the assignment.
 *
 * The methods deliberately check the interruption flag while they work. This
 * allows a running task to stop cooperatively if the executor is cancelled.
 */
public final class ImageFilters {

    private ImageFilters() {
    }

    public static BufferedImage grayscale(BufferedImage source)
            throws InterruptedException {

        int width = source.getWidth();
        int height = source.getHeight();
        int[] input = source.getRGB(0, 0, width, height, null, 0, width);
        int[] output = new int[input.length];

        for (int y = 0; y < height; y++) {
            checkInterruption();

            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                int rgb = input[index];

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int gray = (red * 30 + green * 59 + blue * 11) / 100;
                output[index] = (gray << 16) | (gray << 8) | gray;
            }
        }

        return createImage(output, width, height);
    }

    /**
     * Applies a deliberately CPU-intensive box blur. Increasing the radius
     * produces a stronger blur and increases the amount of real calculation.
     */
    public static BufferedImage blur(BufferedImage source, int radius)
            throws InterruptedException {

        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be at least 1");
        }

        int width = source.getWidth();
        int height = source.getHeight();
        int[] input = source.getRGB(0, 0, width, height, null, 0, width);
        int[] output = new int[input.length];

        for (int y = 0; y < height; y++) {
            checkInterruption();

            for (int x = 0; x < width; x++) {
                long totalRed = 0;
                long totalGreen = 0;
                long totalBlue = 0;
                int pixelCount = 0;

                int minimumY = Math.max(0, y - radius);
                int maximumY = Math.min(height - 1, y + radius);
                int minimumX = Math.max(0, x - radius);
                int maximumX = Math.min(width - 1, x + radius);

                for (int neighbourY = minimumY;
                     neighbourY <= maximumY;
                     neighbourY++) {

                    int rowStart = neighbourY * width;

                    for (int neighbourX = minimumX;
                         neighbourX <= maximumX;
                         neighbourX++) {

                        int rgb = input[rowStart + neighbourX];
                        totalRed += (rgb >> 16) & 0xFF;
                        totalGreen += (rgb >> 8) & 0xFF;
                        totalBlue += rgb & 0xFF;
                        pixelCount++;
                    }
                }

                int red = (int) (totalRed / pixelCount);
                int green = (int) (totalGreen / pixelCount);
                int blue = (int) (totalBlue / pixelCount);

                output[y * width + x] =
                        (red << 16) | (green << 8) | blue;
            }
        }

        return createImage(output, width, height);
    }

    /**
     * Sobel edge detector. Bright pixels in the result represent strong edges.
     */
    public static BufferedImage detectEdges(BufferedImage source)
            throws InterruptedException {

        BufferedImage grayImage = grayscale(source);
        int width = grayImage.getWidth();
        int height = grayImage.getHeight();
        int[] input = grayImage.getRGB(0, 0, width, height, null, 0, width);
        int[] output = new int[input.length];

        int[][] horizontalKernel = {
                {-1, 0, 1},
                {-2, 0, 2},
                {-1, 0, 1}
        };

        int[][] verticalKernel = {
                {-1, -2, -1},
                {0, 0, 0},
                {1, 2, 1}
        };

        for (int y = 1; y < height - 1; y++) {
            checkInterruption();

            for (int x = 1; x < width - 1; x++) {
                int horizontal = 0;
                int vertical = 0;

                for (int kernelY = -1; kernelY <= 1; kernelY++) {
                    for (int kernelX = -1; kernelX <= 1; kernelX++) {
                        int rgb = input[(y + kernelY) * width + x + kernelX];
                        int gray = rgb & 0xFF;

                        horizontal += gray
                                * horizontalKernel[kernelY + 1][kernelX + 1];
                        vertical += gray
                                * verticalKernel[kernelY + 1][kernelX + 1];
                    }
                }

                int magnitude = Math.min(
                        255,
                        (int) Math.sqrt(horizontal * horizontal
                                + vertical * vertical)
                );

                output[y * width + x] =
                        (magnitude << 16) | (magnitude << 8) | magnitude;
            }
        }

        return createImage(output, width, height);
    }

    private static BufferedImage createImage(
            int[] pixels,
            int width,
            int height) {

        BufferedImage result = new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB
        );

        result.setRGB(0, 0, width, height, pixels, 0, width);
        return result;
    }

    private static void checkInterruption() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("Image processing was interrupted");
        }
    }
}
