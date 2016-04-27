package carlorolf;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

class Images {
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
