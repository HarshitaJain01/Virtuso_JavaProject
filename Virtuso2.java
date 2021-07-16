package virtuso2;

/**
 *
 * @author sp111
 */
//IMPORT FILES//

import com.jhlabs.image.*;

import virtuso2.filter.ResizeFilter;

import virtuso2.backend.OpenBrowser;

import virtuso2.backend.ColorSliderPanel;

import virtuso2.backend.ImagePanel;

import virtuso2.Virtuso2;

import virtuso2.backend.RotateSliderPanel;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;

import javax.swing.JFrame;

import javax.swing.JMenu;

import javax.swing.JMenuBar;

import javax.swing.JMenuItem;

import virtuso2.backend.JHistogramD;

import javax.imageio.ImageIO;

import javax.swing.*;

import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;

import java.awt.event.*;

import java.awt.image.BufferedImage;

import java.awt.image.ColorModel;

import java.awt.image.WritableRaster;

import java.io.File;

import java.io.IOException;

import java.util.logging.Level;

import java.util.logging.Logger;

import javax.swing.event.ChangeEvent;

import javax.swing.event.ChangeListener;

import keeptoo.KGradientPanel;

import virtuso2.backend.JScrollPaneImage;

import java.awt.geom.Point2D;

import javax.swing.plaf.FontUIResource;

import tabbedpane.ClosableTabbedPane;






public class Virtuso2 extends JFrame {
    
    private BufferedImage image = null;
    
    private ImagePanel imagePanel;
    
    private String myText;
    
    private RotateSliderPanel rotateSliderPanel;
    
    private JTextField textField;
    
    private ColorSliderPanel redSlider,blueSlider,greenSlider;
    
    private JButton resetButton , cropButton;
    
   private BufferedImage backupImage; 
   
   private Point mousePt1, mousePt2;
   
    private int x1,y1,x2,y2;
    
    private JButton filterButton;
    
    private int id = 1;
    
    private JScrollPaneImage pane;
    
    
    
    
    
    private final ClosableTabbedPane jImagesTab = new ClosableTabbedPane(){
        
        
        
        public boolean tabAboutToClose(int tabIndex){
            
            String tab = jImagesTab.getTabTitleAt(tabIndex);
            
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(  
          "Montserrat", Font.BOLD, 18))); 
            
            int choice = JOptionPane.showConfirmDialog(null,"You Are About To Close"+tab+"\nDo You Want To Proceed?","Confirming",JOptionPane.INFORMATION_MESSAGE);
            
           return choice ==0;
           
        }
        
    };
    
    public static JHistogramD jHistogram;
    
    public JCheckBoxMenuItem jMenuViewHistogram;
    
    public void addImageTab(BufferedImage image){
        
        addImageTab("Image",image);
    }
    
    public void addImageTab(String name,BufferedImage image,int x , int y){
        
        pane = new JScrollPaneImage(image,x,y);
        
        jImagesTab.add(name+"-"+(id++),pane);
        
        jImagesTab.setSelectedIndex(jImagesTab.getTabCount()-1);
        
        try{
            jImagesTab.tabAboutToClose(id);
            
        }catch(Exception e){
            
            System.out.println("Something Went Wrong");
        }
        
        
    }
    
    public void addImageTab(String name, BufferedImage image)
    {
        
        pane = new JScrollPaneImage(image);
        
        jImagesTab.add(name+"-"+(id++),pane);
        
        jImagesTab.setSelectedIndex(jImagesTab.getTabCount()-1);
        
        try{
            
            jImagesTab.tabAboutToClose(id);
            
        }catch(Exception e){
            
            System.out.println("Something Went Wrong");
            
        }
        
    }
    
    public BufferedImage getImageTab(){
        
        Component c = jImagesTab.getSelectedComponent();
        
        if(c!=null){
            
            JScrollPaneImage image = (JScrollPaneImage)c;
            
            return image.getImage();
            
        }
        
        return null;
    }
    
    public JScrollPaneImage getJScrollPaneImageTab(){
        
        Component c = jImagesTab.getSelectedComponent();
        
        if(c!=null){
            
            JScrollPaneImage image = (JScrollPaneImage)c;
            
            return image;
            
        }
        
        return null;
        
    }
    
    public BufferedImage getImageTab(int i){
        
        try{
            
            Component c = jImagesTab.getComponentAt(i);
            
            JScrollPaneImage image = (JScrollPaneImage)c;
            
            return image.getImage();
            
        }catch(IndexOutOfBoundsException e){
            
        }
        
        return null;
        
    }
    
    public void setTabTitle(String title){
        
        jImagesTab.setTitleAt(jImagesTab.getSelectedIndex(), title);
        
    }
    
    public BufferedImage promptImage(){
        
        String input = JOptionPane.showInputDialog("Enter The Tab Number :-");
        
        if(input==null){
            
            return null;
            
        }
        
        int number;
        
        try{
            
            number = Integer.parseInt(input);
            
        }catch(NumberFormatException e){
            
            number = 1;
            
        }
        
        return Virtuso2.this.getImageTab(number - 1);
        
    }
    
    
    
    public Virtuso2(int defaultCloseOperation, int width , int height)throws IOException{
        
        super("VIRTUSO");                                                                                       //SET THE TITLE FOR THE FRAME
        
        this.setSize(width, height);                                                                      //SET THE SIZE OF THE FRAME
        
        this.setBackground(Color.BLACK);                                                      //SET THE BACKGROUND OF THE FRAME
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);                     //THIS SETS WHETHER TO MAXIMIZE BOTH HEIGHT AND WIDTH USING MAXIMIZING OPTION
        
        ImageIcon img = new ImageIcon("E://Virtuso//JFrameicon2.png");
        
        this.setIconImage(img.getImage());
        
        this.setFont(new java.awt.Font("Montserrat Medium", 5, 12));

        this.setResizable(true);
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        this.setLocationRelativeTo(null);
        
        Container content = this.getLayeredPane();
        
        SpringLayout layout = new SpringLayout();
        
        this.setLayout(layout);
        
        KGradientPanel bottompanel = new keeptoo.KGradientPanel();
        
        bottompanel.setkEndColor(new java.awt.Color(0, 0, 0));
        
        bottompanel.setkStartColor(new java.awt.Color(204, 204, 204));
        
       
        
        Icon youtubei = new ImageIcon("E:\\Download\\image-editor-master\\Virtuso2\\src\\resource\\youtube.png");
       
        Icon instagrami = new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\instagram.png");
        
        Icon webi = new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\web.png");
        
        Icon cropi = new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\crop.png");
        
        JLabel Select1 = new JLabel("                                                                                                                                                                                                                                                                                                                                                                                                 ");
        
        JLabel youtubel = new JLabel(webi);
        
        JLabel Select3 = new JLabel(" ");
        
        JLabel instal =  new JLabel(instagrami);
        
        JLabel webl =  new JLabel(youtubei);
          
        JButton changel= new JButton("Change Value");
        
        changel.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
        
        JButton cropb= new JButton(cropi);
        
        cropb.setBackground(Color.BLACK);
        
        changel.setBackground(Color.BLACK);
        
        changel.setForeground(Color.WHITE);
        
        changel.setVisible(false);
        
        cropb.addActionListener(e->{
            ImageIcon ready = new ImageIcon("E://Virtuso//ready.png");
            
            JOptionPane.showMessageDialog(null,"Image Is Ready To Crop","Ready",JOptionPane.INFORMATION_MESSAGE,ready);
            
            pane.addMouseListener(new MouseAdapter(){
                
                public void mousePresses(MouseEvent evt){
                    
                    x1 = evt.getX();
                    
                    y1 = evt.getY();
                    
                    if(x2>x1){
                        
                        pane.setX1(x1);
                        
                        pane.setX2(x2);
                        
                    }
                    
                    else{
                        
                        pane.setX1(x2);
                        
                        pane.setX2(x1);
                        
                        addImageTab("CROP",image,x1,x2);
                        
                    }
                    
                    if(y2>y1){
                        
                        pane.setY1(y1);
                        
                        pane.setY2(y2);
                        
                    }else{
                        
                        pane.setY1(y2);
                        
                        pane.setY2(y1);
                        
                    }
                    
                    Cursor cropcursor;
                    
                    cropcursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
                    
                    setCursor(cropcursor);
                    
                    pane.setVisible(true);
                    
                    pane.repaint();
                    
                    Virtuso2.this.repaint();
                            
                }

            });
            
            pane.addMouseMotionListener(new MouseAdapter(){
                
                public void mouseDragged(MouseEvent evt){
                    
                    x2 = evt.getX();
                    
                    y2 = evt.getY();
                    
                    pane.getGraphics().setColor(Color.red);
                    
                    if(x2>x1&&y2>y1)pane.getGraphics().draw3DRect(x1, y1, x2-x1, y2-y1, true);
                    
                    else if (x2 > x1 && y2 < y1) pane.getGraphics().draw3DRect(x1, y2, x2 - x1, y1 - y2,true);
                    
                    else if (x2 < x1 && y2 > y1) pane.getGraphics().draw3DRect(x2, y1, x1 - x2, y2 - y1,true);
                    
                    else if (x2 < x1 && y2 < y1) pane.getGraphics().draw3DRect(x2, y2, x1 - x2, y1 - y2,true);
                    
                    Cursor cursor;
                    
                    cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                    
                    setCursor(cursor);
                    
                    pane.repaint();
                    
                    pane.setVisible(true);
                    
                    Virtuso2.this.repaint();
                    
                    Virtuso2.this.setVisible(true);
                    
                    
                }
                
            });
                
       });
        
      instal.addMouseListener(new java.awt.event.MouseAdapter() {
          
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                OpenBrowser.openURL("https://www.instagram.com/virt_uso/?r=nametag");
                
            }
            
        });
        
      youtubel.addMouseListener(new java.awt.event.MouseAdapter() {
          
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                OpenBrowser.openURL("https://www.youtube.com/channel/UC2jUEcubrsl7g5WIDwv7Tvw");
                
            }
            
        });
      
      webl.addMouseListener(new java.awt.event.MouseAdapter() {
          
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                OpenBrowser.openURL("https://www.instagram.com/sahil_sharma0810?r=nametag");
                
            }
            
        });
      
      bottompanel.setPreferredSize(new Dimension(1565,48));
      
      bottompanel.setVisible(true);
      
      layout.putConstraint(SpringLayout.NORTH, bottompanel,770, SpringLayout.NORTH, content);
      
      bottompanel.add(changel);
      
      bottompanel.add(cropb);
      
      bottompanel.add(Select1);
      
      bottompanel.add(youtubel);
      
      bottompanel.add(instal);
      
      bottompanel.add(webl);
      
      this.add(bottompanel);
      
      this.setDefaultCloseOperation(defaultCloseOperation);
      
      JMenuBar menuBar = new JMenuBar();
      
      JMenu fileMenu = new JMenu("FILE");
      
      fileMenu.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
      
      Cursor cursor2;
      
      cursor2 = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
      
      fileMenu.setCursor(cursor2);
      
//      JMenu editMenu = new JMenu("EDIT");
      
//      editMenu.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
      
      menuBar.add(fileMenu);
      
//      menuBar.add(editMenu);
      
      fileMenu.setMnemonic(KeyEvent.VK_F);
      
//      editMenu.setMnemonic(KeyEvent.VK_E);
      
      JMenuItem NewI = new JMenuItem("NEW", KeyEvent.VK_N);
      
      NewI.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
      
       JMenu Filterm = new JMenu("FILTER");
       
       Filterm.setCursor(cursor2);
       
       Filterm.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
       
       JMenu Blurm = new JMenu("BLUR");
       
       Blurm.setCursor(cursor2);
       
       Blurm.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
       
       JMenu Geometrym = new JMenu("GEOMETRY");
       
       Geometrym.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
       
       Geometrym.setCursor(cursor2);
       
       JMenu ColorFilterm = new JMenu("COLOR");
       
       JMenu DistortingFilterm = new JMenu("DISTORTING");
       
       DistortingFilterm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
       ColorFilterm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
       JMenu WarpingFiltersm = new JMenu("WRAPING");
       
       WarpingFiltersm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
      
       JMenu TexturingFiltersm = new JMenu("TEXTURING");
       
       TexturingFiltersm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
       JMenu SharpeningFilterm = new JMenu("SHARPENING");
       
       SharpeningFilterm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
       JMenu Effectm = new JMenu("EFFECT");
       
       Effectm.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem ContourFilter = new  JMenuItem("CONTOUR");
      
           ContourFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem DiffusionFilter = new  JMenuItem("DIFFUSION");
           
           DiffusionFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem DitherFilter = new  JMenuItem("DITHER");
           
           DitherFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem ExposureFilter = new  JMenuItem("EXPOSURE");
           
           ExposureFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem GainFilter = new  JMenuItem("GAIN");
           
           GainFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
       
           JMenuItem GammaFilter = new  JMenuItem("GAMMA");
           
           GammaFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
        
           JMenuItem GrayscaleFilter = new JMenuItem("GRAY SCALE");
           
           GrayscaleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
        
           JMenuItem HSBAdjustFilter = new  JMenuItem("HSB ADJUST");
           
           HSBAdjustFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
      
           JMenuItem InvertFilter = new  JMenuItem("INVERT");
           
           InvertFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
         
           JMenuItem LevelsFilter= new  JMenuItem("LEVEL");
           
           LevelsFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
         
           JMenuItem LookupFilter= new  JMenuItem("LOOKUP");
           
           LookupFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem MaskFilter= new  JMenuItem("MASK");
           
           MaskFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem PosterizeFilter= new  JMenuItem("POSTERIZE");
           
           PosterizeFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem RescaleFilter= new  JMenuItem("RESCALE");
           
           RescaleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem SolarizeFilter= new  JMenuItem("SOLARIZE");
           
           SolarizeFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem ThresholdFilter= new  JMenuItem("THRESHOLD");
           
           ThresholdFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem BicubicScaleFilter = new JMenuItem("BICUBIC SCALE");
           
           BicubicScaleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem CircleFilter = new  JMenuItem("CIRCLE");
           
           CircleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem DiffuseFilter= new  JMenuItem("DIFFUSE");
           
           DiffuseFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem DisplaceFilter= new  JMenuItem("DISPLACE");
           
           DisplaceFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem KaleidoscopeFilter= new  JMenuItem("Kaleidoscope");
           
           KaleidoscopeFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
          
           JMenuItem MarbleFilter= new  JMenuItem("MARBLE");
           
           MarbleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem WaterFilter= new  JMenuItem("WATER");
           
           WaterFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem BlockFilter= new  JMenuItem("PIXELATE");
           
           BlockFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem chromeButton = new  JMenuItem("CHROME");
           
           chromeButton.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
            
           JMenuItem  CrystallizeFilter  = new  JMenuItem("CRYSTALLIZE");
           
           CrystallizeFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
            
           JMenuItem  EmbossFilter  = new  JMenuItem("EMBOSS");
           
           EmbossFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
            
           JMenuItem  FeedbackFilter  = new  JMenuItem("FEEDBACK FILTER");
           
           FeedbackFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
             
           JMenuItem  HalftoneFilterr  = new  JMenuItem("HALFTONE");
           
           HalftoneFilterr.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
             
           JMenuItem  NoiseFilter  = new  JMenuItem("NOISE");
           
           NoiseFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
             
           JMenuItem  PointillizeFilter  = new  JMenuItem("POINTILLIZE");
           
           PointillizeFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
             
           JMenuItem  StampFilter  = new  JMenuItem("STAMP");
           
           StampFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  WeaveFilter  = new  JMenuItem("WEAVE");
           
           WeaveFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  SmearFilter  = new  JMenuItem("PAINT");
           
           SmearFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  SparkleFilter  = new  JMenuItem("SPARKEL");
           
           SparkleFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  BlurFilter  = new  JMenuItem("SIMPLE BLUR");
           
           BlurFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  BoxBlurFilter  = new  JMenuItem("BOX BLUR");
           
           BoxBlurFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  BumpFilter  = new  JMenuItem("BUMP");
           
           BumpFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  ConvolveFilter  = new  JMenuItem("CONVOLVE");
           
           ConvolveFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  GaussianFilter  = new  JMenuItem("GAUSSIAN BLUR");
           
           GaussianFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  GlowFilter  = new  JMenuItem("INSTAGRAM GLOW ");
           
           GlowFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  LensBlurFilter  = new  JMenuItem("LENS BLUR");
           
           LensBlurFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  MotionBlurFilter  = new  JMenuItem("MOTION BLUR");
           
           MotionBlurFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  OilFilter  = new  JMenuItem("OIL");
           
           OilFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem  Resize  = new  JMenuItem("RESIZE");
           
           Resize.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem MirrorFilter = new JMenuItem("MIRROR");
           
           MirrorFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem PerspectiveFilter = new JMenuItem("PERSPECTIVE");
           
           PerspectiveFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           JMenuItem TileImageFilter = new JMenuItem("TILEIMAGE");
           
           TileImageFilter.setFont(new java.awt.Font("Montserrat Medium", 1, 12));
           
           ContourFilter.addActionListener(e1->{
               
               ContourFilter contour1 = new ContourFilter();
               
               BufferedImage c1 = null;
               
               BufferedImage temp2 = promptImage();
               
               BufferedImage ct1 = contour1.filter(temp2, c1);
               
               Virtuso2.this.addImageTab("CONTOUR",ct1);
               
               changel.setVisible(true);
               
               changel.addActionListener(new ActionListener(){
                   
                   public void actionPerformed(ActionEvent e){
                       
                       ContourFilter contour2 = new ContourFilter();
                       
                       Object[] optional = {"Change Levels","Change Off Set","Change Scale"};
                       
                       int a = JOptionPane.showOptionDialog(null,"Select The Change","Choose",JOptionPane.QUESTION_MESSAGE,JOptionPane.PLAIN_MESSAGE,null,optional,null);
                       
                       try{
                           
                           if(a==0){
                               
                               String value;
                               
                               value = JOptionPane.showInputDialog("Enter Level");
                               
                               float value1 = Float.parseFloat(value);
                               
                               contour2.setLevels(value1);
                               
                           }
                           
                           if(a==1){
                               
                               String value;
                               
                               value = JOptionPane.showInputDialog("Enter Off Set Value");
                               
                               int value1 = Integer.parseInt(value);
                               
                               contour2.setOffset(value1);
                               
                           }
                           
                           if(a==2){
                               
                               String value;
                               
                               value = JOptionPane.showInputDialog("Enter Scale");
                               
                               float value1 = Float.parseFloat(value);
                               
                               contour2.setScale(value1);
                               
                           }
                           
                           BufferedImage ct2 = contour2.filter(temp2, c1);
                           
                           Virtuso2.this.addImageTab("CONTOUR",ct2);
                           
                       }catch(Exception e1){
                           
                           JOptionPane.showMessageDialog(null,"Enter A Valid Value");
                           
                       }
                   }
 
               });

           });
           
           DiffusionFilter.addActionListener(e12->{
               
                      DiffusionFilter diffusion1 = new DiffusionFilter();
               
                      BufferedImage df1 = null;
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage DF1 = diffusion1.filter(temp2, df1);
               
                      Virtuso2.this.addImageTab(DF1);
               
                      changel.setVisible(true);
               
                      changel.addActionListener(new ActionListener(){
                   
                                 public void actionPerformed(ActionEvent e){
                       
                                            DiffusionFilter diffusion2 = new DiffusionFilter();
                       
                                            Object [] optional = {"Color Diether ","Change Levels "," Serpentine "};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change", "Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional, null);
                       
                                            if(a==0){
                       
                                                       diffusion2.setColorDither(true);
                       
                                            }    
                                            if(a==1){
                           
                                                       String value;
                           
                                                       value = JOptionPane.showInputDialog("Enter Levels");
                           
                                                       int value2 = Integer.parseInt(value);
                           
                                                       diffusion2.setLevels(value2);
          
                                            }
                       
                                            if(a==2){
                           
                                                        diffusion2.setSerpentine(true);
                           
                                            }
                       
                                            BufferedImage df2 = diffusion2.filter(temp2,df1);
                       
                                            Virtuso2.this.addImageTab("DIFFUSION",df2);
                   }
                   
               });
               
           });
           
           DitherFilter.addActionListener(e12->{
               
                      DitherFilter dither1 = new DitherFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage di1 = null;
               
                      BufferedImage DI1 = dither1.filter(temp2,di1);
                 
                      Virtuso2.this.addImageTab("DITHER",DI1);
               
                      changel.setVisible(true);
               
                      changel.addActionListener(new ActionListener(){
                   
                      public void actionPerformed(ActionEvent e){
                       
                                 DitherFilter dither2 = new DitherFilter();
                       
                                 Object[] optional = {"Color RGB","Change Levels"};
                       
                                 int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                       
                                 if(a==0){
                           
                                            String value1;
                           
                                            String value11;
                           
                                            String value12;
                           
                                            value1 = JOptionPane.showInputDialog("Enter X");
                           
                                            value11 = JOptionPane.showInputDialog("Enter Y");
                           
                                            value12 = JOptionPane.showInputDialog("Enter RGB");
                           
                                            int value2 = Integer.parseInt(value1);
                           
                                            int value21 = Integer.parseInt(value11);
                           
                                            int value22 = Integer.parseInt(value12);
                           
                                            dither2.filterRGB(value2, value21, value22);
                           
                                 }
                       
                                 if(a==1){
                           
                                            String value;
                           
                                            value = JOptionPane.showInputDialog("Enter Levels");
                           
                                            int value2 = Integer.parseInt(value);
                           
                                            dither2.setLevels(value2);
                           
                                 }
                       
                                 BufferedImage DI2 =dither2.filter(temp2, di1);
                       
                                 Virtuso2.this.addImageTab("DITHER",DI2);
                   }
                   
               });
               
           });
           
           ExposureFilter.addActionListener(e12->{
               
                      ExposureFilter exposurefilter1 = new ExposureFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage ef = null;
               
                      BufferedImage EF = exposurefilter1.filter(temp2, ef);
               
                      Virtuso2.this.addImageTab("EXPOSURE",EF);
               
                      changel.setVisible(true);
               
                      changel.addActionListener(new ActionListener() {
                   
                      public void actionPerformed(ActionEvent e) {
                       
                                 ExposureFilter exposurefilter2 = new ExposureFilter();
                       
                                 Object[] optional = {"Exposure"};
                       
                                 int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                       
                                 if(a==0){
                           
                                            String value;
                           
                                            value = JOptionPane.showInputDialog("Enter Exposure Value");
                           
                                            float value2 = Float.parseFloat(value);
                           
                                            exposurefilter2.setExposure(value2);
                             
                                 }
                       
                                 BufferedImage DI2 = exposurefilter2.filter(temp2, ef);
                       
                                 Virtuso2.this.addImageTab("EXPOSURE",DI2);
                      }
               });
               
               
           });
           
           GainFilter.addActionListener(e12->{
               
                      GainFilter gainfilter1 = new GainFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage gf = null;
               
                      BufferedImage gf1 = gainfilter1.filter(temp2, gf);
                      
                      Virtuso2.this.addImageTab("GAIN",gf1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e){
                                     
                                            GainFilter gainfilter2 = new GainFilter();
                                            
                                            Object[] optional = {"Change Bias","Change Gain "};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                            
                                            if(a==0){
                                                
                                                       String value;
                                                       
                                                       value = JOptionPane.showInputDialog("Enter Bias Value");
                                                       
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       gainfilter2.setBias(value2);
                                                
                                            }
                                            
                                            if(a==1){
                                                
                                                       String value;
                                                       
                                                       value = JOptionPane.showInputDialog("Enter Gain Value"); 
                                                        
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       gainfilter2.setGain(value2);
                                            }
                                            
                                            BufferedImage gf2 = gainfilter2.filter(temp2,gf);
                   
                                            Virtuso2.this.addImageTab("GAIN",gf2);
                                            
                                 }
                          
                      });
               
           });
           
           GammaFilter.addActionListener(e12->{
              
                      GammaFilter gammafilter1 = new GammaFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage gaf = null;
                      
                      BufferedImage gaf1 = gammafilter1.filter(temp2, gaf);
                      
                      Virtuso2.this.addImageTab("GAMMA",gaf1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e){
                              
                                            GammaFilter gammafilter2 = new GammaFilter();
                                           
                                            Object[] optional = {"Change Intensity Of Gamma","Change Values Of Gamma"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                            
                                            if(a==0){
                                                
                                                       String value;
                                                       
                                                       value = JOptionPane.showInputDialog("Enter Gamma Value");
                                                       
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       gammafilter2.setGamma(value2);
                                                
                                            }
                                            
                                            if(a==1){
                                                
                                                       String value;
                                                       
                                                       String value11;
                                                       
                                                       String value12;
                                                       
                                                       value = JOptionPane.showInputDialog("Enter rGamma Value");
                                                       
                                                       value11 = JOptionPane.showInputDialog("Enter gGamma Value");
                                                       
                                                       value12 = JOptionPane.showInputDialog("Enter bGamma Value");
                                                       
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       float value21 = Float.parseFloat(value11);
                                                       
                                                       float value22 = Float.parseFloat(value12);
                                                       
                                                       gammafilter2.setGamma(value2, value21, value22);
                                                
                                            }
                                            
                                            BufferedImage gaf2 = gammafilter2.filter(temp2, gaf);
                                            
                                            Virtuso2.this.addImageTab("GAMMA",gaf2);
                          }
                          
                      });
              
          });
          
           GrayscaleFilter.addActionListener(e12->{
              
                      GrayscaleFilter gray = new GrayscaleFilter();
              
                      BufferedImage temp2 = promptImage();
              
                      BufferedImage gr = null;
              
                      BufferedImage gr2 = gray.filter(temp2,gr);
              
                      Virtuso2.this.addImageTab("GRAY",gr2);
              
          });
          
           HSBAdjustFilter.addActionListener(e12->{
              
                      HSBAdjustFilter hsbAdjustFilter1 = new HSBAdjustFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage hsb = null;
            
                      BufferedImage gf1 = hsbAdjustFilter1.filter(temp2, hsb);
                   
                      Virtuso2.this.addImageTab("HSB ADJUST",gf1);
           
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e){
                               
                                            HSBAdjustFilter hsbAdjustFilter2 = new HSBAdjustFilter();
                              
                                            Object[] optional = {"Change bFactor","Change hFactor","Change sFactor"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                              
                                            if(a==0){
                                                
                                                       String value;
                                                
                                                       value = JOptionPane.showInputDialog("Enter bFactor");
                                                       
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       hsbAdjustFilter2.setBFactor(value2);
                          
                                            }
                                            
                                            if(a==1){
                                                       
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter hFactor");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       hsbAdjustFilter2.setHFactor(value2);
   
                                            }
                                            
                                            if(a==2){
                                                
                                                       String value;
                      
                                                       value= JOptionPane.showInputDialog("Enter sFactor");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       hsbAdjustFilter2.setSFactor(value2);
                                                
                                            }
                                            
                                            BufferedImage hsb2 = hsbAdjustFilter2.filter(temp2, hsb);
                                            
                                            Virtuso2.this.addImageTab("HSB", hsb2);
                              
                              
                          }
                          
                      });
              
          });
          
            InvertFilter.addActionListener(e12->{
              
                      InvertFilter invertalphafilter1 = new InvertFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage iF = null;
            
                      BufferedImage iA1 = invertalphafilter1.filter(temp2, iF);  
              
                      Virtuso2.this.addImageTab("INVERT",iA1);
          });
          
            
           LevelsFilter.addActionListener(e12->{
                
                      LevelsFilter levelfilter1 = new LevelsFilter();
                
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage lf = null;
                
                      BufferedImage LF1 = levelfilter1.filter(temp2,lf);
                        
                      Virtuso2.this.addImageTab("LEVEL",LF1);
                        
                      changel.setVisible(true);
                        
                      changel.addActionListener(new ActionListener(){
                            
                                 public void actionPerformed(ActionEvent e){
                                
                                            LevelsFilter levelfilter2 = new LevelsFilter();
                                
                                            Object[] optional = {"Change Low Level","Change Low Output Level","Change High Level","Change High Output Level"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                
                                            if(a==0){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Low Level");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       levelfilter2.setLowLevel(value2);
                                                    
                                            }
                                            if(a==1){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Low Level");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       levelfilter2.setLowOutputLevel(value2);
                                                    
                                            }
                                                
                                            if(a==2){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Low Level");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       levelfilter2.setHighLevel(value2);
                                                    
                                                }
                                                
                                            if(a==2){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Low Level");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       levelfilter2.setHighOutputLevel(value2);
                                                    
                                           }
                                                
                                            BufferedImage LF2 = levelfilter2.filter(temp2, lf);
                                                
                                            Virtuso2.this.addImageTab("LEVEL",LF2);
                            }
                            
                        });
                
            });
            
           LookupFilter.addActionListener(e12 -> {
                
                      LookupFilter lookfilter1 = new LookupFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage lof = null;
            
                      BufferedImage iA1 = lookfilter1.filter(temp2, lof);
            
           Virtuso2.this.addImageTab(iA1);
      
 });
           MaskFilter.addActionListener(e12->{
                
                      MaskFilter maskfilter1 = new MaskFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage mf = null;
                        
                      BufferedImage MF1 = maskfilter1.filter(temp2, mf);
                        
                      Virtuso2.this.addImageTab("MASK",MF1);
                        
                      changel.setVisible(true);
                        
                      changel.addActionListener(new ActionListener() {
                            
                                 public void actionPerformed(ActionEvent e){
                                
                                            MaskFilter maskfilter2 = new MaskFilter();
                                    
                                            Object[] optional = {"Change Mask"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                
                                            if(a==0){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Mask Value");
                      
                                                       int value2 = Integer.parseInt(value);
                       
                                                       maskfilter2.setMask(value2);
                                                    
                                            }
                                    
                                            BufferedImage MF2 = maskfilter2.filter(temp2, mf);
                   
                                            Virtuso2.this.addImageTab("MASK",MF2);
     
                            }
                            
                        });
                
            });
            
           PosterizeFilter.addActionListener(e12->{
                
                      PosterizeFilter posterizefilter1 = new PosterizeFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage pf = null;
                        
                      BufferedImage PF1 = posterizefilter1.filter(temp2, pf);
                       
                      Virtuso2.this.addImageTab("POSTERIZE",PF1);
                        
                      changel.setVisible(true);
                        
                      changel.addActionListener(new ActionListener() {
                            
                                 public void actionPerformed(ActionEvent e){
                                
                                 PosterizeFilter posterizefilter2 = new PosterizeFilter();
                                    
                                 Object[] optional = {"Change Num Levels"};
                       
                                 int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                
                                 if(a==0){
                                                    
                                            String value;
                      
                                            value = JOptionPane.showInputDialog("Enter Num Level Value");
                      
                                            int value2 = Integer.parseInt(value);
                       
                                            posterizefilter2.setNumLevels(value2);
                                                    
                                    }
                                    
                                 BufferedImage PF2 = posterizefilter2.filter(temp2, pf);
                   
                                 Virtuso2.this.addImageTab("POSTERIZE",PF2);
     
                            }
                            
                        });
                
            });
            
           RescaleFilter.addActionListener(e12->{
                
                      RescaleFilter rescalefilter1 = new RescaleFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage rf = null;
                        
                      BufferedImage RF1 = rescalefilter1.filter(temp2, rf);
                        
                      Virtuso2.this.addImageTab("RESCALE",RF1);
                        
                      changel.setVisible(true);
                        
                      changel.addActionListener(new ActionListener() {
                            
                                 public void actionPerformed(ActionEvent e){
                                
                                            RescaleFilter rescalefilter2 = new RescaleFilter();
                                    
                                            Object[] optional = {"Change Scale"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                
                                            if(a==0){
                                                    
                                                       String value;
                      
                                                       value = JOptionPane.showInputDialog("Enter Num Level Value");
                      
                                                       float value2 = Float.parseFloat(value);
                       
                                                       rescalefilter2.setScale(value2);
                                                    
                                                }
                                    
                                            BufferedImage PF2 = rescalefilter2.filter(temp2, rf);
                   
                                            Virtuso2.this.addImageTab("RESCALE",PF2);
                                                
                            }
                            
                        });
                
            });
            
           SolarizeFilter.addActionListener(e12->{
                
                      SolarizeFilter solarfilter1 = new SolarizeFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage rf = null;
                        
                      BufferedImage RF1 = solarfilter1.filter(temp2, rf);
                        
                      Virtuso2.this.addImageTab("SOLARIZE",RF1);
                        
                      changel.setVisible(true);
                        
                        
                
            });
            
           ThresholdFilter.addActionListener(e12->{
                
                      ThresholdFilter thresholdfilter1 = new ThresholdFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage rf = null;
                        
                      BufferedImage RF1 = thresholdfilter1.filter(temp2, rf);
                        
                      Virtuso2.this.addImageTab("THRESHOLD",RF1);

            });
            
            BicubicScaleFilter.addActionListener(e12->{
                
                      BicubicScaleFilter bicubicscalefilter1 = new BicubicScaleFilter();
                        
                      BufferedImage temp2 = promptImage();
                        
                      BufferedImage rf = null;
                        
                      BufferedImage RF1 = bicubicscalefilter1.filter(temp2, rf);
                        
                      Virtuso2.this.addImageTab("RESCALE",RF1);
                        
                      changel.setVisible(true);
                        
                      changel.addActionListener(new ActionListener() {
                            
                                 public void actionPerformed(ActionEvent e){
                                     
                                            Object[] optional = {"Change Scale"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                
                                            if(a==0){
                                                    
                                                       String valuex;
                                                            
                                                       String valuey;
                      
                                                       valuex = JOptionPane.showInputDialog("Enter Height");
                                                            
                                                       valuey = JOptionPane.showInputDialog("Enter Width");
                                                            
                                                       int height = Integer.parseInt(valuex);
                       
                                                       int width = Integer.parseInt(valuey);
                                                            
                                                       BicubicScaleFilter bicubicscalefilter2 = new BicubicScaleFilter(height,width);
                                                            
                                                       BufferedImage Bsf = bicubicscalefilter2.filter(temp2, rf);
                                                            
                                                       Virtuso2.this.addImageTab("BICUBIC SCALE",Bsf);

                                                }
               
                            }
                            
                        });
                
            });
            
           CircleFilter.addActionListener(e15->{
               
                      CircleFilter circlefilter1 = new CircleFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage cf = null;
               
                      BufferedImage Bm = circlefilter1.filter(temp2,cf);
                      
                      Virtuso2.this.addImageTab("CIRCLE",Bm);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                public void actionPerformed(ActionEvent e){
                              
                                            CircleFilter circlefilter2 = new CircleFilter();
                                            
                                            Object[] optional = {"Change Angle","Change Center","Change Height","Change Radius","Change SpreadAngle"};
                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                                            
                                            if(a==0){
                                                    
                                                       String value;

                                                       value= JOptionPane.showInputDialog("Enter Angle");
                                                            
                                                       float value2 = Float.parseFloat(value);
                                                       
                                                       circlefilter2.setAngle(value2);


                                            }
                                            
                                            if(a==1){
                                                    
                                                       Object[] Center = {"Change CenterX","Change CenterY"};
                                                       
                                                       int cen = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
    
                                                                  if(cen==0){
                                                    
                                                                             String x;
                                                       
                                                                             x = JOptionPane.showInputDialog("Enter CenterX Value");
    
                                                                             float center1 = Float.parseFloat(x);
                                                                             
                                                                             circlefilter2.setCentreX(center1);

                                                                  }
                                                                  
                                                                  if(cen==1){
                                                    
                                                                             String x;
                                                       
                                                                             x = JOptionPane.showInputDialog("Enter CenterY Value");
    
                                                                             float center1 = Float.parseFloat(x);
                                                                             
                                                                             circlefilter2.setCentreY(center1);

                                                                  }
                                                       

                                            }
                                            
                                            if(a==2){
                                                
                                                       String Height;

                                                       Height = JOptionPane.showInputDialog("Enter Height Value");

                                                       float height = Float.parseFloat(Height);
                       
                                                       circlefilter2.setHeight(height);
                                                
                                            }
                                            
                                            if(a==3){
                                                
                                                       String Radius;

                                                       Radius = JOptionPane.showInputDialog("Enter Radius Value");

                                                       float radius = Float.parseFloat(Radius);
                       
                                                       circlefilter2.setHeight(radius);
                                                
                                            }
                                            
                                            if(a==4){
                                                
                                                       String SAngle;

                                                       SAngle = JOptionPane.showInputDialog("Enter Spread  Angle Value");

                                                       float sAngle = Float.parseFloat(SAngle);
                       
                                                       circlefilter2.setHeight(sAngle);
                                                
                                            }
                                            
                                            BufferedImage gf2 = circlefilter2 .filter(temp2, cf);
                   
                                            Virtuso2.this.addImageTab("CIRCLE",gf2);
                                            
                                            
                                            
                              
                                }
                          
                      });
               
           });
           
           DiffuseFilter.addActionListener(e12->{
               
                      DiffuseFilter diffusefilter1 = new DiffuseFilter();
                       
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage df = null;
                      
                      BufferedImage DF = diffusefilter1.filter(temp2, df);
                      
                      Virtuso2.this.addImageTab("DIFFUSE",DF);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            DiffuseFilter diffusefilter2 = new DiffuseFilter();
                                 
                                            Object[] Center = {"Change Scale"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                
                                                       String value;

                                                       value = JOptionPane.showInputDialog("Enter Scale Value");

                                                       float sAngle = Float.parseFloat(value);
                       
                                                       diffusefilter2.setScale(sAngle);

                                            }
                                            
                                            BufferedImage gf2 = diffusefilter2.filter(temp2, df);
                                            
                                            Virtuso2.this.addImageTab("DIFFUSE",gf2);
                                            
                                           
                                            
                          }
                          
                      });
                      
                      
               
           });
           
           DisplaceFilter.addActionListener(e12->{
               
                      DisplaceFilter diffusefilter1 = new DisplaceFilter();
                       
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage dif = null;
                      
                      BufferedImage DiF = diffusefilter1.filter(temp2, dif);
                      
                      Virtuso2.this.addImageTab("DISPLACE",DiF);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            DisplaceFilter diffusefilter2 = new DisplaceFilter();
                                 
                                            Object[] Center = {"Change Amount"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                
                                                       String value;

                                                       value = JOptionPane.showInputDialog("Enter Amount Value");

                                                       float value2 = Float.parseFloat(value);
                       
                                                       diffusefilter2.setAmount(value2);

                                            }
                                            
                                            BufferedImage gf2 = diffusefilter2.filter(temp2, dif);
                                            
                                            Virtuso2.this.addImageTab("DISPLACE",gf2);
                                            
                                           
                                            
                          }
                          
                      });
                      
                      
               
           });
           
           KaleidoscopeFilter.addActionListener(e12->{
               
                      KaleidoscopeFilter kaleidoscopefilter = new KaleidoscopeFilter();
                       
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage dif = null;
                      
                      BufferedImage DiF = kaleidoscopefilter.filter(temp2, dif);
                      
                      Virtuso2.this.addImageTab("KALEIDOSCOPE",DiF);

           });
          
           MarbleFilter.addActionListener(e12->{
               
                      MarbleFilter marblefilter1 = new MarbleFilter();
                       
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage dif = null;
                      
                      BufferedImage DiF = marblefilter1.filter(temp2, dif);
                      
                      Virtuso2.this.addImageTab("MARBLE",DiF);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            MarbleFilter marblefilter2 = new MarbleFilter();
                                 
                                            Object[] Center = {"Change Amount","Change Turbulence"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                
                                                       String value;

                                                       value = JOptionPane.showInputDialog("Enter Amount Value");

                                                       float value2 = Float.parseFloat(value);
                       
                                                       marblefilter2.setAmount(value2);

                                            }
                                            
                                            if(a==0){
                                                
                                                       String value;

                                                       value = JOptionPane.showInputDialog("Enter Trubulence Value");

                                                       float value2 = Float.parseFloat(value);
                       
                                                       marblefilter2.setTurbulence(value2);

                                            }
                                            
                                            BufferedImage gf2 = marblefilter2.filter(temp2, dif);
                                            
                                            Virtuso2.this.addImageTab("MARBLE",gf2);
                                            
                                           
                                            
                          }
                          
                      });
                      
                      
               
           });
           
           WaterFilter.addActionListener(e12->{
               
                      WaterFilter waterfilter1 = new WaterFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = waterfilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("WATER",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            WaterFilter waterfilter2 = new WaterFilter();
                                 
                                            Object[] Center = {"Change Amplitude","Change Center","Change Phase",""};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Amplitude Value");

                                                       float angle = Float.parseFloat(x);

                                                       waterfilter2.setAmplitude(angle);

                                            }
                                            
                                            if(a==1){
                                                
                                                       String x;
                                                       
                                                       String y;
                                                        
                                                       x = JOptionPane.showInputDialog("Enter X Value");
                                                        
                                                       y = JOptionPane.showInputDialog("Enter Y Value");

                                                       double center1 = Double.parseDouble(x);
                                                        
                                                       double center2 = Double.parseDouble(y);
                       
                                                       Point2D p = new Point2D.Double(center1,center2);
                       
                                                       waterfilter2.setCentre(p);

                                            }
                                            
                                            if(a==2){
                                                
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Phase Value");

                                                       float angle = Float.parseFloat(x);

                                                       waterfilter2.setPhase(angle);

                                            }
                                            
                                            if(a==3){
                                                
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Radius Value");

                                                       float angle = Float.parseFloat(x);

                                                       waterfilter2.setRadius(angle);
                      

                                            }
                                            
                                            if(a==4){
                                                
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Wavelength Value");

                                                       float angle = Float.parseFloat(x);

                                                       waterfilter2.setWavelength(angle);
                      

                                            }
                                            
                                            BufferedImage gf2 = waterfilter2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("WATER",gf2);
                                            
                                           
                                            
                          }
                          
                      });
               
               
               
           });
           
           BlockFilter.addActionListener(e12->{
               
                      BlockFilter blockfilter1 = new BlockFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = blockfilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("PIXELATE",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            BlockFilter blockfilter2 = new BlockFilter();
                                 
                                            Object[] Center = {"Change Amount"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Amplitude Value");

                                                       int angle = Integer.parseInt(x);

                                                       blockfilter2.setBlockSize(angle);

                                            }
                                            
                                            
                                            
                                            BufferedImage gf2 = blockfilter2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("PIXELATE",gf2);
                                            
                                           
                                            
                          }
                          
                      });
               
               
               
           });
           
           chromeButton.addActionListener(e12->{
               
                      ChromeFilter chromeFilter1 = new ChromeFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = chromeFilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("CHROME",iA1);

           });
           
           CrystallizeFilter.addActionListener(e12->{
               
                      CrystallizeFilter crystallizfilter = new CrystallizeFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = crystallizfilter.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("CRYSTALLIZE",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            CrystallizeFilter crystallizefilter2 = new CrystallizeFilter();
                                 
                                            Object[] Center = {"Change Edge Color","Change Edge Thickness","Change Fade Edges"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Edge  Color Value");

                                                       int angle = Integer.parseInt(x);

                                                       crystallizefilter2.setEdgeColor(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Edge  Thickness Value");

                                                       int angle = Integer.parseInt(x);

                                                       crystallizefilter2.setEdgeThickness(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;

                                                       crystallizefilter2.setFadeEdges(true);

                                            }
                                            
                                            
                                            
                                            BufferedImage gf2 = crystallizefilter2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("CRYSTALLIZE",gf2);
                                            
                                           
                                            
                          }
                          
                      });

           });
           
           
           EmbossFilter.addActionListener(e12->{
               
                      EmbossFilter embossfilter1 = new EmbossFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = embossfilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("EMBOSS",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            EmbossFilter embossfilter2 = new EmbossFilter();
                                 
                                            Object[] Center = {"Change Azimuth","Change Bum Height","Change Elevation","Set Emboss"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Azimuth Value");

                                                       float angle = Float.parseFloat(x);

                                                       embossfilter2.setAzimuth(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Bump  Height Value");

                                                       int angle = Integer.parseInt(x);

                                                       embossfilter2.setBumpHeight(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;
                      
                                                       x = JOptionPane.showInputDialog("Enter Elevation Value");

                                                       float scale = Float.parseFloat(x);

                                                       embossfilter2.setElevation(scale);

                                            }
                                            
                                            if(a==3){
                                                        
                                                       embossfilter2.setEmboss(true);

                                            }
                                            
                                            
                                            
                                            BufferedImage gf2 = embossfilter2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("EMBOSS",gf2);
                                            
                                           
                                            
                          }
                          
                      });

           });
           
           FeedbackFilter.addActionListener(e12->{
               
                      FeedbackFilter feedbackfilter1 = new FeedbackFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = feedbackfilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("FEEDBACK",iA1);

           });
           
           NoiseFilter.addActionListener(e12->{
               
                      NoiseFilter noisefilter1 = new NoiseFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = noisefilter1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("NOISE",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            NoiseFilter noisefilter2 = new NoiseFilter();
                                 
                                            Object[] Center = {"Change Amount","Change Density","Change Distribution","Set Monochrome"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Amount Value");

                                                       int angle = Integer.parseInt(x);

                                                       noisefilter2.setAmount(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Density Value");

                                                       int angle = Integer.parseInt(x);

                                                       noisefilter2.setDensity(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;
                      
                                                       x = JOptionPane.showInputDialog("Enter Distribution Value");

                                                       int scale = Integer.parseInt(x);

                                                       noisefilter2.setDistribution(scale);

                                            }
                                            
                                            if(a==3){
                                                        
                                                       noisefilter2.setMonochrome(true);

                                            }
                                            
                                            
                                            
                                            BufferedImage gf2 = noisefilter2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("NOISE",gf2);
                                            
                                           
                                            
                          }
                          
                      });

           });
           
           HalftoneFilterr.addActionListener(e12->{

                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                                     
                                            JFileChooser jFileChooser2 = new JFileChooser();
                              
                                            HalftoneFilter halftonefilter1= new HalftoneFilter();
                                            
                                            int returnValue = jFileChooser2.showOpenDialog(null);
                                            
                                            if(returnValue == JFileChooser.APPROVE_OPTION){
                        
                                                       File file2 = jFileChooser2.getSelectedFile();
                    
                                                       if(file2==null){
                                                           
                                                                  JOptionPane.showMessageDialog(null,"Please Enter A Value Next Time");
                                                                  
                                                       }
                        
                        
                                                       try {
                                                                  BufferedImage halftoneimage = ImageIO.read(file2);
                            
                                                                  halftonefilter1.setMask(halftoneimage);
                        
                                                                  BufferedImage weave = halftonefilter1.filter(image,backupImage);
           
                                                                  Virtuso2.this.addImageTab("HALFTONE ", weave);
                                                       } catch (IOException ex) {
                                                                  Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                                                                  }
                                            }                       
        
                                 }     
                          
                      });

           });
           
           PointillizeFilter.addActionListener(e12->{
               
                      PointillizeFilter pointillize1 = new PointillizeFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = pointillize1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("POINTILLIZE",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            PointillizeFilter pointillize2 = new PointillizeFilter();
                                 
                                            Object[] Center = {"Change Edge Color","Change Edge Thickness","Set Fade Edges","Change Fuzziness"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Edge Color Value");

                                                       int angle = Integer.parseInt(x);

                                                       pointillize2.setEdgeColor(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Edge Thickness Value");

                                                       int angle = Integer.parseInt(x);

                                                       pointillize2.setEdgeThickness(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       pointillize2.setFadeEdges(true);

                                            }
                                            
                                            if(a==3){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Edge Thickness Value");

                                                       float scale = Float.parseFloat(x);
                       
                                                       pointillize2.setFuzziness(scale);

                                            }
                                            
                                            
                                            
                                            BufferedImage gf2 = pointillize2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("POINTILLIZE",gf2);
                                            
                                           
                                            
                          }
                          
                      });

           });
           
           StampFilter.addActionListener(e12->{
               
                      StampFilter stamp1 = new StampFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage wf = null;
                      
                      BufferedImage iA1 = stamp1.filter(temp2, wf);
                      
                      Virtuso2.this.addImageTab("STAMP",iA1);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            StampFilter stamp2 = new StampFilter();
                                 
                                            Object[] Center = {"Change Radius","Change Softness ","Change Threshold"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Radius Value");

                                                       float angle = Float.parseFloat(x);

                                                       stamp2.setRadius(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Softness Value");

                                                       int angle = Integer.parseInt(x);

                                                       stamp2.setSoftness(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Threshold Value");

                                                       float scale = Float.parseFloat(x);

                                                       stamp2.setThreshold(scale);

                                            }
                                            
                                            
                                            
                                            
                                            
                                            BufferedImage gf2 = stamp2.filter(temp2, wf);
                                            
                                            Virtuso2.this.addImageTab("STAMP",gf2);
                                            
                                           
                                            
                          }
                          
                      });

           });
           
           WeaveFilter.addActionListener(e17 -> {
        
                      WeaveFilter feedbackfilter1 = new WeaveFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = feedbackfilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("WEAVE", weave);
           
           
        }); 
           
           SmearFilter.addActionListener(e17 -> {
        
                      SmearFilter smearfilter1 = new SmearFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;
                      
                      Object[] optional = {"CROSSES","LINES ","CIRCLE","SQAURES"};
                      
                      int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, optional,null);
                     
                      if(a==0){
                          
                                 

                                 smearfilter1.setShape(0);
                    
                       }
                      
                      if(a==1){
                          
                                 smearfilter1.setShape(1);
                    
                      }
                      
                      if(a==2){
                          
                                 smearfilter1.setShape(2);
                    
                       }
                  
                      
                      if(a==3){
                          
                                 smearfilter1.setShape(3);
                    
                       }

                      BufferedImage weave = smearfilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("PAINT", weave);
           
           
        });
           
           SparkleFilter.addActionListener(e17 -> {
        
                      SparkleFilter sparklefilter1 = new SparkleFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = sparklefilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("SPARKLE", weave);
           
           
        }); 
           
           BlurFilter.addActionListener(e17 -> {
        
                      BlurFilter simplefilter1 = new BlurFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = simplefilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("BLUR", weave);
           
           
        });
           
           BoxBlurFilter.addActionListener(e17 -> {
        
                      BoxBlurFilter boxblurfilter1 = new BoxBlurFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = boxblurfilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("BLUR", weave);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            BoxBlurFilter boxblurfilter2 = new BoxBlurFilter();
                                 
                                            Object[] Center = {"Change HRadius","Change Iterations ","Change Radius","Change VRadius"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter HRadius");

                                                       int angle = Integer.parseInt(x);

                                                       boxblurfilter2.setHRadius(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Iterations");

                                                       int angle = Integer.parseInt(x);

                                                       boxblurfilter2.setIterations(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Radius");

                                                       int scale = Integer.parseInt(x);

                                                       boxblurfilter2.setRadius(scale);

                                            }
                                            
                                            if(a==3){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter VRadius");

                                                       int scale = Integer.parseInt(x);

                                                       boxblurfilter2.setVRadius(scale);

                                            }
                                            
                                            
                                            
                                            
                                            
                                            BufferedImage gf2 = boxblurfilter2.filter(temp2, wef);
                                            
                                            Virtuso2.this.addImageTab("BOX BLUR",gf2);
                                            
                                           
                                            
                          }
                          
                      });
           
           
        }); 
           
           BumpFilter.addActionListener(e17->{
               
                      BumpFilter bumpfilter = new BumpFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage buf = null;
                      
                      BufferedImage bump = bumpfilter.filter(temp2, buf);
                      
                      Virtuso2.this.addImageTab("BUMP",bump);
               
           });
           
           ConvolveFilter.addActionListener(e17->{
               
                      ConvolveFilter convolvefilter = new ConvolveFilter();
                      
                      Object[] Center = {"ZERO EDGES","CLAMP EDGES ","WRAP EDGES"};
                                                       
                      int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                      
                      if(a==0){
                          
                                 convolvefilter.setEdgeAction(0);
                          
                      }
                      
                      if(a==1){
                          
                                 convolvefilter.setEdgeAction(1);
                          
                      }
                      
                      if(a==2){
                          
                                 convolvefilter.setEdgeAction(2);
                          
                      }
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage cof = null;
                      
                      BufferedImage convolve = convolvefilter.filter(temp2,cof);
                      
                      Virtuso2.this.addImageTab("CONVOLVE",convolve);
               
                      
               
           });
           
           GaussianFilter.addActionListener(e17->{
               
                       GaussianFilter gaussianblur = new GaussianFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage gaf = null;
               
                      BufferedImage gaussian = gaussianblur.filter(temp2, gaf);
               
                      Virtuso2.this.addImageTab("GAUSSIAN",gaussian);
               
           });
           
           GlowFilter.addActionListener(e17 -> {
        
                      GlowFilter glowfilter1 = new GlowFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = glowfilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("INSTAGRAM GLOW", weave);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            GlowFilter glowfilter2 = new GlowFilter();
                                 
                                            Object[] Center = {"Change Amount"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Amount");

                                                       int angle = Integer.parseInt(x);

                                                       glowfilter2.setAmount(angle);

                                            }

                                            BufferedImage gf2 = glowfilter2.filter(temp2, wef);
                                            
                                            Virtuso2.this.addImageTab("INSTAGRAM GLOW",gf2);
                                            
                                           
                                            
                          }
                          
                      });
           
           
        }); 
           
           LensBlurFilter.addActionListener(e17 -> {
        
                      LensBlurFilter lensblurfilter1 = new LensBlurFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;

                      BufferedImage weave = lensblurfilter1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("LENS BLUR", weave);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            LensBlurFilter lensblurfilter2 = new LensBlurFilter();
                                 
                                            Object[] Center = {"Change Bloom","Change Bloom Threshold","Change Radius","Change Sides"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Bloom Value");

                                                       float angle = Float.parseFloat(x);

                                                       lensblurfilter2.setBloom(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Bloom Threshold Value");

                                                       float angle = Float.parseFloat(x);

                                                       lensblurfilter2.setBloomThreshold(angle);

                                            }
                                            
                                            if(a==2){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Radius Value");

                                                       float angle = Float.parseFloat(x);

                                                       lensblurfilter2.setRadius(angle);

                                            }
                                            
                                            if(a==3){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Bloom Threshold Value");

                                                       int angle = Integer.parseInt(x);

                                                       lensblurfilter2.setSides(angle);

                                            }

                                            BufferedImage gf2 = lensblurfilter2.filter(temp2, wef);
                                            
                                            Virtuso2.this.addImageTab("LENS BLUR",gf2);
                                            
                                           
                                            
                          }
                          
                      });
           
           
        }); 
           
           MotionBlurFilter.addActionListener(e17 -> {
        
                      MotionBlurFilter motionblur1 = new MotionBlurFilter();
                      
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage wef = null;
                      
                      motionblur1.setWrapEdges(true);

                      BufferedImage weave = motionblur1.filter(temp2,wef);
           
                      Virtuso2.this.addImageTab("MOTION BLUR", weave);
                      
                      changel.setVisible(true);
                      
                      changel.addActionListener(new ActionListener(){
                          
                                 public void actionPerformed(ActionEvent e ){
                              
                                            MotionBlurFilter motionblur2 = new MotionBlurFilter();
                                 
                                            Object[] Center = {"Change Angle","Change Distance ","Change Wrap Edges"};
                                                       
                                            int a = JOptionPane.showOptionDialog(null, "Select The Change","Choose", JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, Center,null);
                                            
                                            if(a==0){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Angle Value");

                                                       float angle = Float.parseFloat(x);

                                                       motionblur2.setAngle(angle);

                                            }
                                            
                                            if(a==1){
                                                        
                                                       String x;

                                                       x = JOptionPane.showInputDialog("Enter Distance Value");

                                                       float angle = Float.parseFloat(x);

                                                       motionblur2.setDistance(angle);

                                            }
                                            
                                            if(a==2){

                                                       motionblur1.setWrapEdges(true);

                                            }
                                           
                                            

                                            BufferedImage gf2 = motionblur2.filter(temp2, wef);
                                            
                                            Virtuso2.this.addImageTab("MOTION BLUR",gf2);
                                            
                                           
                                            
                          }
                          
                      });
           
           
        }); 
           
           OilFilter.addActionListener(e17->{
               
                       OilFilter oilfilter1 = new OilFilter();
               
                      BufferedImage temp2 = promptImage();
               
                      BufferedImage gaf = null;
               
                      BufferedImage gaussian = oilfilter1.filter(temp2, gaf);
               
                      Virtuso2.this.addImageTab("OIL",gaussian);
               
           });
           
           Resize.addActionListener(e17->{
               
                      BufferedImage temp2 = promptImage();
                    
                       ResizeFilter s = new ResizeFilter(temp2);

                      BufferedImage result = s.resize(backupImage);
               
                      Virtuso2.this.addImageTab("RESIZE",result);
               
           });
           
           MirrorFilter.addActionListener(e17->{
               
                      BufferedImage temp2 = promptImage();
                      
                      BufferedImage mf = null;
                    
                       MirrorFilter s = new MirrorFilter();

                      BufferedImage result = s.filter(temp2,mf);
               
                      Virtuso2.this.addImageTab("MIRROR",result);
               
           });
           
           PerspectiveFilter.addActionListener(e17->{
               
                    BufferedImage temp2 = promptImage();
                      
                    BufferedImage mf = null;
                    
                    PerspectiveFilter s = new PerspectiveFilter();

                    BufferedImage result = s.filter(temp2,mf);
               
                     Virtuso2.this.addImageTab("PERSPECTIVE",result);
               
           });
           
           TileImageFilter.addActionListener(e17->{
               
                    BufferedImage temp2 = promptImage();
                      
                    BufferedImage mf = null;
                    
                    TileImageFilter s = new TileImageFilter();

                    BufferedImage result = s.filter(temp2,mf);
               
                     Virtuso2.this.addImageTab("TILEIMAGE",result);
               
           });
           
           menuBar.add(Filterm);
        
           Filterm.add(ColorFilterm);
        
           Filterm.add(DistortingFilterm);
        
           Filterm.add(Effectm);
        
           Filterm.add(DistortingFilterm);
        
           Filterm.add(TexturingFiltersm);
        
           Filterm.add(SharpeningFilterm);
        
           ColorFilterm.add(ContourFilter);
        
           ColorFilterm.add(DiffusionFilter);
        
           ColorFilterm.add(DitherFilter);
        
           ColorFilterm.add(ExposureFilter);
        
           ColorFilterm.add(GainFilter);
        
           ColorFilterm.add(GammaFilter);
        
           ColorFilterm.add(GrayscaleFilter);
        
           ColorFilterm.add(HSBAdjustFilter );
        
           ColorFilterm.add(InvertFilter );
        
           ColorFilterm.add(LevelsFilter);
        
           ColorFilterm.add(LookupFilter);
        
           ColorFilterm.add(MaskFilter);
        
           ColorFilterm.add(PosterizeFilter);
       
           ColorFilterm.add(RescaleFilter);
        
           ColorFilterm.add(ThresholdFilter);
        
           DistortingFilterm.add(BicubicScaleFilter);
         
           DistortingFilterm.add(CircleFilter);
         
           DistortingFilterm.add(DiffuseFilter);
         
           DistortingFilterm.add(DisplaceFilter);
         
           WarpingFiltersm.add(KaleidoscopeFilter);
          
           WarpingFiltersm.add(MarbleFilter);
         
           SharpeningFilterm.add(BumpFilter);
         
           SharpeningFilterm.add(ConvolveFilter);

           Effectm.add(BlockFilter);
         
           Effectm.add(chromeButton);
         
           Effectm.add(CrystallizeFilter);
         
           Effectm.add(EmbossFilter);
         
           Effectm.add(FeedbackFilter);
         
           Effectm.add(HalftoneFilterr);
         
           Effectm.add(NoiseFilter);
         
           Effectm.add(PointillizeFilter);
         
           Effectm.add(GlowFilter);
         
           Effectm.add(OilFilter);
         
         
         
           TexturingFiltersm.add(StampFilter);
         
           TexturingFiltersm.add(SmearFilter);
         
           TexturingFiltersm.add(WeaveFilter);
         
           TexturingFiltersm.add(WaterFilter);
         
           TexturingFiltersm.add(SparkleFilter);
         
           Blurm.add(BlurFilter);
         
           Blurm.add(BoxBlurFilter);
         
           Blurm.add(GaussianFilter);
         
           Blurm.add(LensBlurFilter);
         
           Blurm.add(MotionBlurFilter);
         
           Geometrym.add(Resize);
           
           Geometrym.add(MirrorFilter);
           
           Geometrym.add(PerspectiveFilter);
           
           Geometrym.add(TileImageFilter);

           menuBar.add(Blurm);
        
           menuBar.add(Geometrym);
           
           this.setJMenuBar(menuBar);
           
           this.setVisible(true);
           
           JPopupMenu popup = new JPopupMenu();
           
           popup.setBackground(Color.BLACK);
           
           JMenuItem menuItem1 = new JMenuItem("CUT",new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\cut.png"));
           
           menuItem1.setFont(new java.awt.Font("Montserrat Medium ",1,11));
           
           menuItem1.setMnemonic(KeyEvent.VK_P);
           
           menuItem1.getAccessibleContext().setAccessibleDescription("CUT");
           
           popup.add(menuItem1);
           
           JMenuItem menuItem2 = new JMenuItem("COPY",new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\copy.png"));
           
           menuItem2.setFont(new java.awt.Font("Montserrat Medium ",1,11));
           
           menuItem2.setMnemonic(KeyEvent.VK_F);
           
           popup.add(menuItem2);
           
           JMenuItem menuItem3 = new JMenuItem ("PASTE",new ImageIcon("C:\\Users\\sp111\\OneDrive\\Documents\\NetBeansProjects\\paste.png"));
           
           menuItem3.setFont(new java.awt.Font("Montserrat Medium ",1,11));
           
           menuItem3.setMnemonic(KeyEvent.VK_F);
           
           popup.add(menuItem3);
           
           addMouseListener(new MouseAdapter(){
               
               @Override
               
               public void mouseReleased(MouseEvent e){
                   
                   showPopup(e);
                   
               }
               
               private void showPopup(MouseEvent e){
                  
                   if(e.isPopupTrigger()){
                       
                       popup.show(e.getComponent(), e.getX(), e.getY());
                       
                   }
                   
               }
               
           });
           
           JMenuItem openItem = new JMenuItem("OPEN",KeyEvent.VK_0);
           
           openItem.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
           
           JMenuItem closeItem = new JMenuItem("Close", KeyEvent.VK_C);
           
           closeItem.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
           
           JMenuItem saveItem = new JMenuItem("Save", KeyEvent.VK_S);
           
           saveItem .setFont(new java.awt.Font("Montserrat Medium", 1, 14));
           
           JMenuItem addTextItem = new JMenuItem("SCALING", KeyEvent.VK_T);
           
           addTextItem.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
           
           JMenuItem addStickerItem = new JMenuItem("STICKER", KeyEvent.VK_S);
           
           addStickerItem.setFont(new java.awt.Font("Montserrat Medium", 1, 14));
           
           fileMenu.add(NewI);
           
           NewI.addActionListener(new NewActionListener());
           
           fileMenu.add(openItem);
           
           openItem.addActionListener(new OpenActionListener());
           
           fileMenu.add(closeItem);
           
           closeItem.addActionListener(new CloseActionListener());
           
           fileMenu.add(saveItem);
           
           saveItem.addActionListener(new SaveActionListener());
           
//           editMenu.add(addTextItem);
           
           addTextItem.addActionListener(new AddTextActionListener());
           
//           editMenu.add(addStickerItem);
           
           addStickerItem.addActionListener(new AddStickerActionListener());
           
           jImagesTab.addMouseListener(new MouseAdapter(){
               
                      @Override
                      public void mousePressed(MouseEvent e){
                          
                                 super.mousePressed(e);
                                 
                                 if(e.getButton()==MouseEvent.BUTTON2){
                                     
                                            int index = jImagesTab.getSelectedIndex();
                                            
                                            if(index!=-1){
                                                
                                                       jImagesTab.remove(index);
                                                
                                            }
                                     
                                 }
                          
                      }
               
           });
           
           jImagesTab.addChangeListener(new ChangeListener(){
               
               @Override
               public void stateChanged(ChangeEvent arg0){
                   
                      
                   
               }
               
           });
           
           add(jImagesTab);

    }
    
    private class NewActionListener implements ActionListener {
            
           public void actionPerformed(ActionEvent ae){
               
                      if(pane!=null){
                          
                          JOptionPane.showMessageDialog(null,"Please First Close The Current Image");
                          
                          return;
                          
                      }
                      
                      image = new BufferedImage(960,1280,BufferedImage.TYPE_INT_RGB);
                      
                      Graphics g = image.getGraphics();
                      
                      g.setColor(Color.WHITE);
                      
                      g.fillRect(0,0, 960, 1280);
                      
                      ColorModel cm = image.getColorModel();
                      
                      boolean isAplhaPreMultiplied = cm.isAlphaPremultiplied();
                      
                      WritableRaster raster = image.copyData(null);
                      
                      backupImage = new BufferedImage(cm, raster, isAplhaPreMultiplied, null);
                      
                      Virtuso2.this.addImageTab(image);
                      
               
           } 
            
}

private class OpenActionListener implements ActionListener {
    
           public void actionPerformed(ActionEvent ae){
        
           if(pane!=null){
               
                      JOptionPane.showMessageDialog(null,"Please First Close The Current Image!");
                      
                      return;
               
           }
           
           JFileChooser jFileChooser = new JFileChooser();
           
           int returnValue = jFileChooser.showOpenDialog(null);
           
           if(returnValue == JFileChooser.APPROVE_OPTION){
               
                      File file = jFileChooser.getSelectedFile();
                      
                      String[] filename = file.getName().split("\\.(?=[^\\.]+$)");
                      
                      try{
                          
                                 image = ImageIO.read(file);
                                 
                                 if(image!=null){
                                     
                                            Virtuso2.this.addImageTab(filename[0],image,1527,740);
                                     
                                 }
                                 
                                 else{
                                     
                                            JOptionPane.showMessageDialog(null,"The file could not be opened!","\r\n"+"Invalid format", JOptionPane.ERROR_MESSAGE);
                                     
                                 }
                                 
                                 ColorModel cm = image.getColorModel();
                                 
                                 boolean isAlphaPreMulitplied = cm.isAlphaPremultiplied();
                                 
                                 WritableRaster raster = image.copyData(null);
                                 
                                 backupImage = new BufferedImage(cm, raster, isAlphaPreMulitplied, null);
                                 
                                 Virtuso2.this.setVisible(true);
                                 
                                 Virtuso2.this.revalidate();
                                 
                                 
                                 
                          
                      }catch(IOException e){
                          
                                 e.printStackTrace();
                          
                      }
               
           }
        
           }
    
}

private class CloseActionListener implements ActionListener {
    
        public void actionPerformed(ActionEvent ae) {
            
            if (imagePanel != null) imagePanel.setVisible(false);
            
            if (rotateSliderPanel != null) rotateSliderPanel.setVisible(false);
            
            if (cropButton != null) cropButton.setVisible(false);
            
            if (redSlider != null) redSlider.setVisible(false);
            
            if (greenSlider != null) greenSlider.setVisible(false);
            
            if (blueSlider != null) blueSlider.setVisible(false);
            
            if (textField != null) textField.setVisible(false);
            
            if (resetButton != null) resetButton.setVisible(false);
            
            if (filterButton != null) filterButton.setVisible(false);
            
            resetButton = null;
            
            filterButton = null;
            
            redSlider = null;
            
            greenSlider = null;
            
            blueSlider = null;
            
            textField = null;
            
            rotateSliderPanel = null;
            
            image = null;
            
            imagePanel = null;
            
            backupImage = null;
            
            myText = null;
            
            cropButton = null;
        }
    }

private class SaveActionListener implements ActionListener {
    
           public void actionPerformed(ActionEvent ae){
               
                      JFileChooser jFileChooser = new JFileChooser();
                       
                      jFileChooser.setAcceptAllFileFilterUsed(false);
                       
                      jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                      
                      jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
                      
                      jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));
                      
                      jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
                       
                      jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
                      
                      FileNameExtensionFilter pFilter = new FileNameExtensionFilter("png", "png");
                      
                      jFileChooser.setFileFilter(pFilter);
                      
                      int status = jFileChooser.showSaveDialog(Virtuso2.this);
                      
                      if(status  == JFileChooser.APPROVE_OPTION){
                          
                                 File selectedFile = jFileChooser.getSelectedFile();
                                 
                                 String fileName;
                                 
                                 try{
                                     
                                     fileName = selectedFile.getCanonicalPath();
                                     
                                     String extension = "." + jFileChooser.getFileFilter().getDescription();
                                     
                                     selectedFile = new File(fileName + ".png");
                                     
                                     BufferedImage img = new BufferedImage(pane.getWidth(),pane.getHeight(),BufferedImage.TYPE_INT_RGB);
                                     
                                     pane.paint(img.getGraphics());
                                     
                                     BufferedImage temp = Virtuso2.this.getImageTab();
                                     
                                     ImageIO.write(temp,"png", selectedFile);

                                 }catch(IOException e){
                                     
                                            e.printStackTrace();
                                     
                                 }
                          
                      }
               
           }
    
}

private class AddTextActionListener implements ActionListener {
    
           public void actionPerformed(ActionEvent ae){
        
                      if(jImagesTab == null){
                          
                                 JOptionPane.showMessageDialog(null,"Please First And An Image!");
                          
                      }
                      
                      if(textField == null && jImagesTab !=null){
                          
                                 textField = new JTextField("Enter Text Here",30);
                                 
                                 textField.setPreferredSize(new Dimension(250,50));
                                 
                                 textField.setFont(new Font("Courier",Font.BOLD,20));
                                 
                                 textField.addActionListener(e->{
                                     
                                     String str;
                                     
                                     if(e.getSource()==textField){
                                         
                                         str = String.format("%s", e.getActionCommand());
                                         
                                         myText = str;
                                         
                                         JOptionPane.showMessageDialog(null,"This Text Is Ready To Use"+myText);
                                         
                                         printTextLabel();
                                     }
                                     
                                 });
                          
                      }
        
            }
    
}

private void printTextLabel(){
    
           if (myText != null && pane != null){
    
                      final JLabel label = new JLabel(myText);
                      
                      label.setBounds(250,200,300,80);
                      
                      label.setFont(new Font("Courier",Font.BOLD,20));
                      
                      label.addMouseListener(new MouseAdapter(){
                          
                          public void mouseClicked(MouseEvent e){
                              
                                 final JPanel textEditPanel = new JPanel();
                                 
                                 final JButton editTextButton = new JButton("Edit The Text");
                                 
                                 final JButton sizeButton = new JButton("Change Text Size");
                                 
                                 final JButton colorButton = new JButton("Change Text Color");
                                 
                                 final JButton deleteButton = new JButton("Delete The Text");
                                 
                                 sizeButton.addActionListener(e15->{
                                     
                                     String str = JOptionPane.showInputDialog("Please Enter The Font Size:");
                                     
                                     if(str!=null) label.setFont(new Font("Courier",Font.BOLD,Integer.parseInt(str)));
                                     
                                 });
                                 
                                 deleteButton.addActionListener(e14-> label.setVisible(false));
                                 
                                 editTextButton.addActionListener(e13->{
                                     
                                            String str = JOptionPane.showInputDialog("Please Enter The New Text You Want To Replace With The First One");
                                            
                                            if(str != null) label.setText(str);
                                     
                                 });
                                 
                                 colorButton.addActionListener(e12->{
                                     
                                            final JPanel colorPanel = new JPanel();
                                     
                                            final JRadioButton blackButton = new JRadioButton("Black");
                                     
                                            final JRadioButton blueButton = new JRadioButton("Blue");
                                     
                                            final JRadioButton redButton = new JRadioButton("Red");
                                     
                                            final JRadioButton greenButton = new JRadioButton("Green");
                                     
                                            final JRadioButton yellowButton = new JRadioButton("Yellow");
                                     
                                            final JRadioButton grayButton = new JRadioButton("Gray");
                                     
                                            final JRadioButton cyanButton = new JRadioButton("Cyan");
                                            
                                            final JRadioButton orangeButton = new JRadioButton("Orange");
                                            
                                            final JRadioButton magentaButton = new JRadioButton("Magenta");
                                            
                                            final JRadioButton pinkButton = new JRadioButton("Pink");
                                            
                                            final ButtonGroup buttonGroup = new ButtonGroup();
                                            
                                            buttonGroup.add(blackButton);
                                            
                                            buttonGroup.add(blueButton);
                                            
                                            buttonGroup.add(redButton);
                                            
                                            buttonGroup.add(greenButton);
                                            
                                            buttonGroup.add(yellowButton);
                                            
                                            buttonGroup.add(cyanButton);
                                            
                                            buttonGroup.add(grayButton);
                                            
                                            buttonGroup.add(magentaButton);
                                            
                                            buttonGroup.add(orangeButton);
                                            
                                            buttonGroup.add(pinkButton);
                                            
                                            redButton.addActionListener(e1 -> label.setForeground(Color.RED));
                                            
                                            blackButton.addActionListener(e1213 -> label.setForeground(Color.BLACK));
                                            
                                            blueButton.addActionListener(e1212 -> label.setForeground(Color.BLUE));
                                            
                                            greenButton.addActionListener(e121 -> label.setForeground(Color.GREEN));
                                            
                                            yellowButton.addActionListener(e111 -> label.setForeground(Color.YELLOW));
                                            
                                            cyanButton.addActionListener(e110 -> label.setForeground(Color.CYAN));
                                            
                                            grayButton.addActionListener(e19 -> label.setForeground(Color.GRAY));
                                            
                                            orangeButton.addActionListener(e18 -> label.setForeground(Color.ORANGE));
                                            
                                            magentaButton.addActionListener(e17 -> label.setForeground(Color.MAGENTA));
                                            
                                            pinkButton.addActionListener(e16 -> label.setForeground(Color.PINK));
                                            
                                            colorPanel.add(blackButton);
                                            
                                            colorPanel.add(greenButton);
                                            
                                            colorPanel.add(redButton);
                                            
                                            colorPanel.add(blueButton);
                                            
                                            colorPanel.add(orangeButton);
                                            
                                            colorPanel.add(yellowButton);
                                            
                                            colorPanel.add(grayButton);
                                            
                                            colorPanel.add(pinkButton);
                                            
                                            colorPanel.add(cyanButton);
                                            
                                            colorPanel.add(magentaButton);
                                            
                                            JOptionPane.showMessageDialog(null, colorPanel);
                                     
                                     
                                     
                                 });
                                 
                                            textEditPanel.add(editTextButton);
                                            
                                            textEditPanel.add(sizeButton);
                                            
                                            textEditPanel.add(colorButton);
                                            
                                            textEditPanel.add(deleteButton);
                                            
                                            JOptionPane.showMessageDialog(null, textEditPanel);

                          }
                          
                                 public void mousePressed(MouseEvent me){
                                     
                                            mousePressedAct(me);
                                     
                                 }
                                 
                                 public void mouseReleased(MouseEvent me){
                                     
                                            mouseReleasedAct(me);
                                     
                                 }
                          
                      });
                      
                      label.addMouseMotionListener(new MouseAdapter() {
                          
                                 public void mouseDragged(MouseEvent me){
                                     
                                            mouseDraggedAct(me);
                                     
                                 }
                          
                      });
                      
                      jImagesTab.add(label);
                      
                      Virtuso2.this.setVisible(true);
                      
                      Virtuso2.this.repaint();
                      
                      Virtuso2.this.revalidate();
     
           }
    
}

public void setLocationAct(MouseEvent me){

           Component component = (Component)me.getSource();
           
           Point locationOnScreen = me.getLocationOnScreen();
           
           int x = locationOnScreen.x - mousePt2.x + mousePt1.x;
           
           int y = locationOnScreen.y - mousePt2.y + mousePt1.y;
           
           component.setLocation(x,y);
           
           Virtuso2.this.repaint();

}

private void mouseDraggedAct(MouseEvent me){
    
           setLocationAct(me);
           
           Cursor cursor;
           
           cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
           
           setCursor(cursor);
    
}

private void mousePressedAct(MouseEvent me){
    
           Component component = (Component)me.getSource();
           
           mousePt1 = component.getLocation();
           
           mousePt2 = me.getLocationOnScreen();
           
           Cursor cursor;
           
           cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
           
           setCursor(cursor);
    
}

private void mouseReleasedAct(MouseEvent me){
    
           setLocationAct(me);
           
           Cursor cursor;
           
           cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
           
           setCursor(cursor);
    
}

private void addMouseListenersToLabel(JLabel myLabel){
    
           myLabel.addMouseListener(new MouseAdapter(){
               
                      public void mouseDragged(MouseEvent me){
                   
                                 mouseDraggedAct(me);
                   
                      }
               
           });
           
           myLabel.addMouseListener(new MouseAdapter(){
               
                      public void mousePressed(MouseEvent me){
                          
                                 mousePressedAct(me);
                          
                      }
                      
                      public void mouseClicked(MouseEvent e){
                          
                                 int dialogButton = JOptionPane.YES_NO_CANCEL_OPTION;
                                 
                                 int result = JOptionPane.showConfirmDialog(null,"Would You Like To Remove This Sticker?","Remove Sticker",dialogButton);
                                 
                                 if(result==JOptionPane.YES_OPTION){
                                     
                                     myLabel.setVisible(false);
                                     
                                 }
                          
                      }
                      
                      public void mouseReleased(MouseEvent me){
                          
                                 mouseReleasedAct(me);
                          
                      }
                      
               
           });
    
}

private void addMouseListener(JLabel label){
    
           label.addMouseListener(new MouseAdapter(){
               
                      public void mouseClicked(MouseEvent e){
                          
                                 pane.add(label);
                                 
                                 label.setLocation(300,300);
                                 
                                 addMouseListenersToLabel(label);
                                 
                                 Virtuso2.this.repaint();
                          
                      }
               
           });
    
    
}



public boolean toogleHistogram(){
    
           boolean visible = !jHistogram.isVisible();
           
           jMenuViewHistogram.setSelected(visible);
           
           jHistogram.setVisible(visible);
           
           return visible;
    
}

private class AddStickerActionListener implements ActionListener {
    
           public void actionPerformed(ActionEvent e){
               
                      if(jImagesTab==null){
                          
                          JOptionPane.showMessageDialog(null,"Please FirstAdd An Image");
                          
                          return;
                          
                      }
                      
                      JPanel stickerPanel = new JPanel();
                      
                      JLabel label1 = new JLabel();
                      
                      JLabel label2 = new JLabel();
                      
                      JLabel label3 = new JLabel();
                      
                      JLabel label4 = new JLabel();
                      
                      JLabel label5 = new JLabel();
                      
                      JLabel label6 = new JLabel();
                      
                      JLabel label7 = new JLabel();
                      
                      JLabel label8 = new JLabel();
                      
                      JLabel label9 = new JLabel();
                      
                      JLabel label10 = new JLabel();
                      
                      ImageIcon imageIcon1 = new ImageIcon("stickers\\Monztars_1.png");
                      
                      ImageIcon imageIcon2 = new ImageIcon("stickers\\Monztars_2.png");
                      
                      ImageIcon imageIcon3 = new ImageIcon("stickers\\Monztars_3.png");
                      
                      ImageIcon imageIcon4 = new ImageIcon("stickers\\Monztars_4.png");
                      
                      ImageIcon imageIcon5 = new ImageIcon("stickers\\Monztars_5.png");
                      
                      ImageIcon imageIcon6 = new ImageIcon("stickers\\Monztars_6.png");
                      
                      ImageIcon imageIcon7 = new ImageIcon("stickers\\Monztars_7.png");
                      
                      ImageIcon imageIcon8 = new ImageIcon("stickers\\Monztars_8.png");
                      
                      ImageIcon imageIcon9 = new ImageIcon("stickers\\Monztars_9.png");
                      
                      ImageIcon imageIcon10 = new ImageIcon("stickers\\Monztars_10.png");
                      
                      label1.setIcon(imageIcon1);
                      
                      label2.setIcon(imageIcon2);
                      
                      label3.setIcon(imageIcon3);
                      
                      label4.setIcon(imageIcon4);
                      
                      label5.setIcon(imageIcon5);
                      
                      label6.setIcon(imageIcon6);
                      
                      label7.setIcon(imageIcon7);
                      
                      label8.setIcon(imageIcon8);
                      
                      label9.setIcon(imageIcon9);
                      
                      label10.setIcon(imageIcon10);

                      addMouseListener(label1);
                      
                      addMouseListener(label2);
                      
                      addMouseListener(label3);
                      
                      addMouseListener(label4);
                      
                      addMouseListener(label5);
                      
                      addMouseListener(label6);
                      
                      addMouseListener(label7);
                      
                      addMouseListener(label8);
                      
                      addMouseListener(label9);
                      
                      addMouseListener(label10);


                      stickerPanel.add(label1);
                      
                      stickerPanel.add(label2);
                      
                      stickerPanel.add(label3);
                      
                      stickerPanel.add(label4);
                      
                      stickerPanel.add(label5);
                      
                      stickerPanel.add(label6);
                      
                      stickerPanel.add(label7);
                      
                      stickerPanel.add(label8);
                      
                      stickerPanel.add(label9);
                      
                      stickerPanel.add(label10);
                      
                      JOptionPane.showMessageDialog(null, stickerPanel);
                      
               
           }
    
}
            
            
    
    public static void main(String[]args) throws IOException{
        
        Virtuso2 f =new Virtuso2(JFrame.EXIT_ON_CLOSE, 500, 600);
        f.setVisible(true);
        
        
        
    }
 
}
