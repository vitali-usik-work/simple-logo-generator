package generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LogoGenerator {

    public static void main(String[] args) {
        doDemoResizeWithExistingFiles();
        doDemoRisizeWithNewFiles();
    }
    
    private static void doDemoRisizeWithNewFiles() {
        String text = "Success shop!";
        /*
           Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image
         */
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
//        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        
        try {
            ImageIO.write(img, "png", new File("d:\\Text.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try{
            BufferedImage bgImage = ImageIO.read(new File("d:\\green.png"));
            //BufferedImage originalImage = ImageIO.read(new File("d:\\space.jpg"));
            int type = img.getType() == 0? BufferedImage.TYPE_INT_ARGB : img.getType();

            BufferedImage resizeImageJpg = resizeImage(img, type);

            float f = 1.0f;
            int margin = (bgImage.getHeight() - resizeImageJpg.getHeight()) / 2;
            BufferedImage newImage = addImage(bgImage, resizeImageJpg, f, 0, margin);
            ImageIO.write(newImage, "png", new File("d:\\Text1.png"));                    
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static void doDemoResizeWithExistingFiles() {
        try{
            BufferedImage bgImage = ImageIO.read(new File("d:\\green.png"));
            BufferedImage originalImage = ImageIO.read(new File("d:\\space.jpg"));
            int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

            BufferedImage resizeImageJpg = resizeImage(originalImage, type);

            float f = 1.0f;
            int margin = (bgImage.getHeight() - resizeImageJpg.getHeight()) / 2;
            BufferedImage newImage = addImage(bgImage, resizeImageJpg, f, 0, margin);
            ImageIO.write(newImage, "png", new File("d:\\yyaaahooo.png"));                    
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        float k = originalImage.getWidth() / 200;
        System.out.println(k);
        int h = (int) (originalImage.getHeight() / k);
        System.out.println(h);
        BufferedImage resizedImage = new BufferedImage(200, h, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 200, h, null);
        g.dispose();

        return resizedImage;
    }
    
    /**
     * prints the contents of buff2 on buff1 with the given opaque value.
     */
    private static BufferedImage addImage(BufferedImage buff1, BufferedImage buff2, float opaque, int x, int y) {
        Graphics2D g2d = buff1.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
        g2d.drawImage(buff2, x, y, null);
        g2d.dispose();
        return buff1;
    }
}
