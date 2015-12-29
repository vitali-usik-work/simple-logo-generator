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
        String shopName = "S";
        generateLogoImgFromShopName(shopName);
    }
    
    private static void generateLogoImgFromShopName(String shopName) {
        int logoWidth = 200;
        int logoHeight = 132;
        float transparentValue = 1.0f;
        int marginX = 0, marginY = 0;
        BufferedImage tempShopImg = prepareTextLogoWithShopName(shopName);
        
        int imgType = tempShopImg.getType() == 0? BufferedImage.TYPE_INT_ARGB : tempShopImg.getType();
        BufferedImage resizeImageJpg;
        if (tempShopImg.getWidth() > 200) {
            resizeImageJpg = resizeImage(tempShopImg, imgType);
            marginY = (logoHeight - resizeImageJpg.getHeight()) / 2;
        } else {
            resizeImageJpg = tempShopImg;
            marginY = (logoHeight - resizeImageJpg.getHeight()) / 2;
            marginX = (logoWidth - resizeImageJpg.getWidth()) / 2;
        }
        
        BufferedImage backgroundImg = new BufferedImage(logoWidth, logoHeight, BufferedImage.TYPE_INT_ARGB);

        BufferedImage logo = printTextLogoOnBackground(backgroundImg, resizeImageJpg, transparentValue, marginX, marginY);
        
        try {
            ImageIO.write(logo, "png", new File("d:\\uspech2.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static BufferedImage printTextLogoOnBackground(BufferedImage backgroundImg, BufferedImage resizeImageJpg, float transparentValue, int x,
            int y) {
        Graphics2D g2d = backgroundImg.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparentValue));
        g2d.drawImage(resizeImageJpg, x, y, null);
        g2d.dispose();
        return backgroundImg;
    }

    private static BufferedImage prepareTextLogoWithShopName(String shopName) {
        BufferedImage shopNameImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = shopNameImg.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 48);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(shopName);
        int height = fm.getHeight();
        g2d.dispose();

        shopNameImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = shopNameImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);
        g2d.drawString(shopName, 0, fm.getAscent());
        g2d.dispose();
        
        return shopNameImg;
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        float resizeFactor = originalImage.getWidth() / 200;
        int newHeight = (int) (originalImage.getHeight() / resizeFactor);
        BufferedImage resizedImage = new BufferedImage(200, newHeight, type);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.drawImage(originalImage, 0, 0, 200, newHeight, null);
        g2d.dispose();

        return resizedImage;
    }
}
