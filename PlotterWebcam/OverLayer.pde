Capture video;


class OverLayer extends Layer {

  PFont font = loadFont("MarkerFelt-Thin-48.vlw");
  boolean printed = true;
  int fadeAlpha;
  boolean plotted = false;
  
  OverLayer(PApplet parent) {
    super(parent); // This is necessary!
  }

  public void update() {
    printed = false;
  }

  void draw() {
    //background(0);

    if (mode == "GREETING") {
      if (!printed) {
        background(0, 0);
        noStroke();
        fill(255,100);
        rect(0, height-100, width, 100);

        textFont(font);
        fill(0);
        text("Click to take a picture!", 20, height-50);
        printed = true;
      }
    } 
    else if (mode == "COUNTDOWN") {
      if (!printed) {
        noStroke();
        fill(255, 100);
        rect(0, height-100, width, 100);
        textFont(font);
        fill(0);
        text("Capturing in " + countdown, 20, height-50);
        printed = true;
      }
    } 
    else if (mode == "FADEUP") {

      fill(255, fadeAlpha);
      rect(0, 0, width, height);
      fadeAlpha += 10;

      if (fadeAlpha > 240) {
        mode = "CAPTURING";
      }
    }

    else if (mode == "STANDBY") {

      noStroke();
      fill(255);
      rect(0, height-100, width, 100);
      textFont(font);
      fill(0);
      text("Please stand by...", 20, height-50);
      //printed = true;
      mode = "CONFIRM";
      delay(2000);
      printed = false;
    }
    else if (mode == "CONFIRM") {
      if (!printed) {
        background(255);
        //tint(255, 100);
        PImage p = loadImage("captureChan2.jpg");
        image(p, 0, 0);

        noStroke();
        fill(255, 100);
        rect(0, height-100, width, 100);
        textFont(font);
        fill(0);
        text("Click to Plot. Press Escape to try again.", 20, height-50);
        printed = true;
      }
    }
    else if (mode == "PLOTTING") {
      if (!plotted) {
        background(0,0);
        tint(255, 100);
        PImage p = loadImage("captureChan2.jpg");
        image(p, 0, 0);

        noStroke();
        fill(255, 100);
        rect(0, height-100, width, 100);
        textFont(font);
        fill(0);
        text("Plotting... Please stand by.", 20, height-50);
        plotted = true;
        plottedTime = millis();
      }
    }
  }
}

