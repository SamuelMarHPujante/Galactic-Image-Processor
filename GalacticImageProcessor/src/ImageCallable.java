import java.nio.file.Path;
import java.util.concurrent.Callable;

/**
 * Callable task used by the fourth execution mode.
 *
 * Unlike ImageJob, this class returns its ImageResult to Main. It does not
 * receive or modify the shared result list or the statistics object.
 */
public final class ImageCallable implements Callable<ImageResult> {

    private final Path imagePath;
    private final Path outputDirectory;

    public ImageCallable(Path imagePath, Path outputDirectory) {
        this.imagePath = imagePath;
        this.outputDirectory = outputDirectory;
    }

    @Override
    public ImageResult call() {
        /*
         * TODO: call ImageProcessor.processImage() with this image,
         * outputDirectory and the execution mode "Callable".
         * Return the ImageResult directly. Do not add it to Main.results and
         * do not update ProcessingStatistics here.
         */
        return null;
    }
}
