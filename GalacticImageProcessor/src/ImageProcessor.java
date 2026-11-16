import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ImageProcessor {

    public static final int BLUR_RADIUS = 5;

    private ImageProcessor() {
    }

    public static ImageResult processImage(Path imagePath, Path modeOutputDirectory, String executionMode) {
        long startTime = System.nanoTime();

        try {
            String fileName = imagePath.getFileName().toString();
            String threadName = Thread.currentThread().getName();
            System.out.println("[" + threadName + "] START " + fileName);

            BufferedImage originalImage = ImageIO.read(imagePath.toFile());
            if (originalImage == null) throw new IOException("Unsupported image or unable to read: " + imagePath);

            ImageMetadata metadata = ImageMetadata.readForImage(imagePath);

            BufferedImage grayImage = ImageFilters.grayscale(originalImage);
            BufferedImage blurImage = ImageFilters.blur(originalImage, BLUR_RADIUS);
            BufferedImage edgeImage = ImageFilters.detectEdges(originalImage);

            Path grayScaleDir = modeOutputDirectory.resolve("grayScale");
            Path blurDir = modeOutputDirectory.resolve("blur");
            Path edgeDir = modeOutputDirectory.resolve("edge");

            Files.createDirectories(grayScaleDir);
            Files.createDirectories(blurDir);
            Files.createDirectories(edgeDir);

            int dotIndex = fileName.lastIndexOf(".");
            String baseName = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);

            Path grayPath = grayScaleDir.resolve(baseName + "_grayScale.png");
            Path blurPath = blurDir.resolve(baseName + "_blur.png");
            Path edgePath = edgeDir.resolve(baseName + "_edge.png");

            boolean grayWritten = ImageIO.write(grayImage, "png", grayPath.toFile());
            boolean blurWritten = ImageIO.write(blurImage, "png", blurPath.toFile());
            boolean edgeWritten = ImageIO.write(edgeImage, "png", edgePath.toFile());

            if (!grayWritten || !blurWritten || !edgeWritten) {
                throw new IOException("PNG writer not available or failed to write one of the outputs.");
            }

            long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
            System.out.println("[" + threadName + "] DONE " + fileName + " " + elapsedMillis + " ms");

            return new ImageResult(
                    executionMode,
                    fileName,
                    metadata,
                    threadName,
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    elapsedMillis,
                    true,
                    null,
                    grayPath.toString(),
                    blurPath.toString(),
                    edgePath.toString()
            );
        } catch (IOException error) {
            long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
            String threadName = Thread.currentThread().getName();
            String fileName = imagePath.getFileName().toString();
            return new ImageResult(
                    executionMode,
                    fileName,
                    null,
                    threadName,
                    0, 0,
                    elapsedMillis,
                    false,
                    error.getMessage(),
                    null, null, null
            );
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            long elapsedMillis = (System.nanoTime() - startTime) / 1_000_000;
            String threadName = Thread.currentThread().getName();
            String fileName = imagePath.getFileName().toString();
            return new ImageResult(
                    executionMode,
                    fileName,
                    null,
                    threadName,
                    0, 0,
                    elapsedMillis,
                    false,
                    "Interrupted: " + error.getMessage(),
                    null, null, null
            );
        }
    }
}
