import java.nio.file.Path;
import java.util.List;

public class ImageJob implements Runnable {

    private final Path imagePath;
    private final Path outputDirectory;
    private final List<ImageResult> results;
    private final ProcessingStatistics statistics;

    public ImageJob(Path imagePath, Path outputDirectory, List<ImageResult> results, ProcessingStatistics statistics) {
        this.imagePath = imagePath;
        this.outputDirectory = outputDirectory;
        this.results = results;
        this.statistics = statistics;
    }

    @Override
    public void run() {
        ImageResult result = ImageProcessor.processImage(imagePath, outputDirectory, "ExecutorService");
        results.add(result);
        statistics.register(result);
    }
}
