package virtuso2.backend;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import virtuso2.filter.GrayscaleFilter;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import virtuso2.backend.ImagePanel;

@SuppressWarnings("serial")
public class JScrollPaneImage extends JScrollPane {

	final private int[] histogram;

	private BufferedImage image;
                       
                      private double degree;
                     
    
                     private int x1, y1, x2, y2;
        
                       
                       
                      
                      
                      
        

	public JScrollPaneImage(BufferedImage image) {
                                             
		this.image = image;
		setViewportView(new JViewportImage());

		// apply grayscale filter
		GrayscaleFilter filter = new GrayscaleFilter(image);
		histogram = filter.histogram();
                                             x1 = y1 = 0;
                                             x2 = image.getWidth();
                                             y2 = image.getHeight();
                                            
                                            
                                             
	}
                      public JScrollPaneImage(BufferedImage image,int x ,int y){
                          this.image=image;
                          setViewportView(new JViewportImage(x,y));
                          GrayscaleFilter filter = new GrayscaleFilter(image);
		histogram = filter.histogram();
                                             x1 = y1 = 0;
                                             x2 = image.getWidth();
                                             y2 = image.getHeight();
                          
                      }
                     
                      public void setX1(int x1) {
                             this.x1 = x1;
                      }
                      public void setY1(int y1) {
                            this.y1 = y1;
                      }
                      public void setX2(int x2) {
                       this.x2 = x2;
                      }

                      public void setY2(int y2) {
                      this.y2 = y2;
                      }

                      public int getX1() {
                      return x1;
                      }

                      public int getY1() {
                      return y1;
                       }

                      public int getX2() {
                      return x2;
                      }

                      public int getY2() {
                      return y2;
                      }
                      
                       public void paintComponent(Graphics g) {
        
                           Graphics2D graphics = (Graphics2D) g;
        
                           int x = this.getWidth() / 2;
        
                           int y = this.getHeight() / 2;
        
                           graphics.rotate(Math.toRadians(degree), x, y);
        
                           super.paintComponent(g);
        
                           if (x2 - x1 < image.getWidth() && y2 - y1 < image.getHeight())
        
                               image = image.getSubimage(x1, y1, x2 - x1, y2 - y1);
        
                           g.drawImage(image, 0, 0, this);
    
                       }
                     

	public int[] getHistogram() {
		return histogram;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public class JViewportImage extends JPanel {
                      
                              private double degree;
                       
                            

		public JViewportImage(int x , int y) {
                                                                      
			setPreferredSize(new Dimension(x,y));
                                                                    
		}
                                            public JViewportImage(){
                                                setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
                                            }
                
                                             public void setDegree(double degree){
                                                       this.degree = degree;
                                             }

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, null);
		}
                                             

                                            
	}

}
