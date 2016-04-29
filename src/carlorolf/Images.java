package carlorolf;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

/**
 * This class is used to load images from the recourse directory.
 */
public final class Images {
    private Images() {}

    //This method always works the same, that's why it's static
    public static Image getImage(String url) {
        try {
            Image image;
            URL u = ClassLoader.getSystemResource(url);
            image = ImageIO.read(u);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
