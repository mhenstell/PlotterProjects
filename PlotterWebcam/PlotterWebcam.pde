import processing.video.*;

color black = color(0);
color white = color(255);
int numPixels;
Capture video;

void setup() {
  size(640, 480); // Change size to 320 x 240 if too slow at 640 x 480
  strokeWeight(5);
  // Uses the default video input, see the reference if this causes an error
  video = new Capture(this, width, height, 24);
  numPixels = video.width * video.height;
  noCursor();
  smooth();
}

void draw() {
  if (video.available()) {
    video.read();
    
    image(video, 0, 0);
    
    
  }
}
  
public void keyPressed(KeyEvent e)
  {
    int mods = e.getModifiers();
    String key = KeyEvent.getKeyText(e.getKeyCode());
				
    if (key.equalsIgnoreCase("s")) {
      saveImage();
      runScript();	
      }
  }

public void saveImage() {
   PImage cp = video.get();
   video.save("capture.png"); 
}

public void runScript() {
 try{
      Runtime.getRuntime().exec("/Users/max/Desktop/pngtosvg.sh");
    }
    catch(java.io.IOException e){
      println(e);
    } 
}
