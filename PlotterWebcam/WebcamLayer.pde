

class WebcamLayer extends Layer {
  WebcamLayer(PApplet parent) {
    super(parent); // This is necessary!
  }

  void draw() {
    //background(0, 0); // clear the background every time, but be transparent
    if (video.available()) {
      video.read();
      image(video, 0, 0, width, height);
    }
    
    if (mode == "STANDBY") {
       this.setVisible(false); 
    }
    
  }
  void invisible() {
    this.setVisible(false);
  }
}

