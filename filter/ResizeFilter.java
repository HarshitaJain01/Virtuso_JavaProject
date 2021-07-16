/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virtuso2.filter;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
/**
 *
 * @author sp111
 */
public class ResizeFilter extends BaseFilter {
    public ResizeFilter(BufferedImage image){
        super(image);
    }
    public BufferedImage resize(BufferedImage image2 ){
                String response;
                response = JOptionPane.showInputDialog("Enter X ");
                int x = Integer.parseInt(response);
                response = JOptionPane.showInputDialog("Enter Y Value");
                int y = Integer.parseInt(response);
                Image tmp = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
                BufferedImage dimg = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = dimg.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();
        return dimg;             
        
    }
    

}
