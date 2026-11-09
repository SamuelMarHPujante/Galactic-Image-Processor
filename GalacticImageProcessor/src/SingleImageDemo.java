import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Complete example supplied to verify the filters before starting the task.
 */
public class SingleImageDemo {

    private static final int BLUR_RADIUS = 5;

    public static void main(String[] args) {
        Path input = Path.of("input", "space_01_ringed_planet.png");
        Path output = Path.of("output", "filter_demo");

        try {
            Path grayScaleDirectory = output.resolve("grayScale");
            Path blurDirectory = output.resolve("blur");
            Path edgeDirectory = output.resolve("edge");
            Files.createDirectories(grayScaleDirectory);
            Files.createDirectories(blurDirectory);
            Files.createDirectories(edgeDirectory);

            BufferedImage original = ImageIO.read(input.toFile());
            if (original == null) {
                System.err.println("Unsupported image: " + input);
                return;
            }

            System.out.println("Applying filters to " + input.getFileName());

            ImageMetadata metadata = ImageMetadata.readForImage(input);
            System.out.println("Location: " + metadata.location());

            BufferedImage grayscale = ImageFilters.grayscale(original);
            BufferedImage blurred = ImageFilters.blur(original, BLUR_RADIUS);
            BufferedImage edges = ImageFilters.detectEdges(original);

            ImageIO.write(
                    grayscale,
                    "png",
                    grayScaleDirectory.resolve(
                            "space_01_ringed_planet_grayScale.png"
                    ).toFile()
            );
            ImageIO.write(
                    blurred,
                    "png",
                    blurDirectory.resolve(
                            "space_01_ringed_planet_blur.png"
                    ).toFile()
            );
            ImageIO.write(
                    edges,
                    "png",
                    edgeDirectory.resolve(
                            "space_01_ringed_planet_edge.png"
                    ).toFile()
            );

            System.out.println("Finished. Open " + output.toAbsolutePath());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("The operation was interrupted");
        } catch (IOException e) {
            System.err.println("File error: " + e.getMessage());
        }
    }
}
