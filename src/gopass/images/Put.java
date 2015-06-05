package gopass.images;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * A class for putting images onto the GUI
 * 
 * @author Jason Mey
 * @version 1.0
 */
public class Put {

   /**
    * Gets an image for use in the GUI
    * 
    * @param creator an object in this project
    * @param file the name of the image
    * @return the image
    * @throws IOException
    */
   public static Image image(Object creator, String file)
      throws IOException {
      InputStream is = creator.getClass().getResourceAsStream(
         "/gopass/images/" + file);
      if (is == null) {
         return null;
      }
      return ImageIO.read(is);
   }
}