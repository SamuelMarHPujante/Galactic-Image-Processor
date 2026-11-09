import java.nio.file.Path;

public final class ImageProcessor {

    public static final int BLUR_RADIUS = 5;

    private ImageProcessor() {
    }

    public static ImageResult processImage(
            Path imagePath,
            Path modeOutputDirectory,
            String executionMode) {

        /*
         * TODO 1: save the start time with System.nanoTime().
         * TODO 2: print a START message containing the thread name and file.
         * TODO 3: read the image with ImageIO.read().
         * TODO 4: load its metadata with ImageMetadata.readForImage().
         * TODO 5: apply grayscale(), blur() and detectEdges().
         * TODO 6: create grayScale, blur and edge with
         *         Files.createDirectories().
         * TODO 7: obtain the original base name without its extension.
         * TODO 8: write originalName_grayScale.png,
         *         originalName_blur.png and originalName_edge.png.
         * TODO 9: create a new ImageResult by passing every value in exactly
         *         the order declared in ImageResult.java. Records are
         *         immutable, so all values are supplied to the constructor.
         *         Store executionMode, metadata and the three output paths.
         * TODO 10: print a DONE message with the elapsed time.
         * TODO 11: return a successful ImageResult created with
         *          new ImageResult(...).
         * TODO 12: catch IOException and return a new failed ImageResult.
         * TODO 13: catch InterruptedException, restore interruption and
         *          return a cancelled ImageResult.
         */

        throw new UnsupportedOperationException(
                "Complete ImageProcessor.processImage()"
        );
    }
}
