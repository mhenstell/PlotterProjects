class LogicLayer extends Layer {
  
  boolean captured = false;
  
  LogicLayer(PApplet parent) {
    super(parent); // This is necessary!
  }

  void draw() {

    if (mode == "COUNTDOWN") {
      println("Countdown: " + countdownDuration);
      countdownDuration -= millis() - countdownLastTime;
      countdownLastTime = millis();

      //println(countdownDuration);
      countdown = (countdownDuration/1000) + 1;
      ol.update();

      if (countdownDuration < 50) {
        mode = "FADEUP";
        countdownDuration = 2999;
      }
    } 
    else if (mode == "CAPTURING") {
      if (!captured) {
        saveImage();
        runScript();
        captured = true;
        println("Captured!");
        mode = "STANDBY";
        shapeLoaded = false;
        captured = false;
      }
      
      
      
    }
    else if (mode == "PLOTTING") {
       if ((millis() - plottedTime) > 90000) {
         reset(); 
       }
    }
  }
}

