package carlorolf;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Images
{
    public static Image getImage(String url){
	try {
	    Image image;
	    URL u = ClassLoader.getSystemResource(url);
	    image = ImageIO.read(u);
	    return image;
	} catch(MalformedURLException e){
	    e.printStackTrace();
	} catch(IOException e){
	    e.printStackTrace();
	}
	return null;
    }
}
