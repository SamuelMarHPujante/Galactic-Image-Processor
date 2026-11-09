import java.nio.file.Path;
import java.util.List;

public class ImageJob implements Runnable {

    private final Path imagePath;
    private final Path outputDirectory;
    private final List<ImageResult> results;
    private final ProcessingStatistics statistics;

    public ImageJob(
            Path imagePath,
            Path outputDirectory,
            List<ImageResult> results,
            ProcessingStatistics statistics) {

        this.imagePath = imagePath;
        this.outputDirectory = outputDirectory;
        this.results = results;
        this.statistics = statistics;
    }

    @Override
    public void run() {
        /*
         * TODO 1: call ImageProcessor.processImage(imagePath,
         *         outputDirectory, "ExecutorService") and store the returned
         *         object in a local ImageResult variable.
         * TODO 2: add that ImageResult to results. The list received from
         *         Main is already a synchronized list. ImageResult is an
         *         immutable record, so the same object can safely be stored.
         * TODO 3: call statistics.register(result). The register method uses
         *         atomic counters so several workers can update it safely.
         *         Access record data with result.successful(),
         *         result.elapsedMilliseconds(), etc.; do not use get...().
         *
         * Remember that several threads execute this method at the same time.
         */
    }
}
