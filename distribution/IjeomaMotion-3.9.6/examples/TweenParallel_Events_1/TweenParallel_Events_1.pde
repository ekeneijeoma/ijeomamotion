import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

TweenParallel tp;

void setup() {
  size(400, 200);

  ellipseMode(CORNER);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  tp = new TweenParallel();
  tp.addChild(new Tween("t1", -width, width, 100));
  tp.addChild(new Tween("t2", width, -width, 200));
  tp.play();
}

void draw() {
  background(255);

  stroke(255);
  fill(0);
  rect(tp.getTween("t1").getPosition(), 0, width, height / 2);
  rect(tp.getTween("t2").getPosition(), height / 2, width, height / 2);

  drawUI();
}

void drawUI() {
  String time;

  // This draws the seek for the ts TweenParallel object
  stroke(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
  line(tp.getSeekPosition() * width, 0, tp.getSeekPosition() * width,
  height);

  // This draws the time for every Tween object
  for (int i = 0; i < tp.getChildCount(); i++) {
    Tween t = tp.getTween(i);
    time = (int) t.getTime() + " / " + (int) t.getDuration();

    fill(lerpColor(0xFF00FF00, 0xFFFF0000, t.getSeekPosition()));
    text(time, 10, i * 100 + 10 + 12);
  }

  // This draws the time for the ts TweenParallel object
  time = (int) tp.getTime() + " / " + (int) tp.getDuration();

  fill(lerpColor(0xFF00FF00, 0xFFFF0000, tp.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  tp.play();
}

void tweenParallelStarted(TweenParallel _tp) {
  println(_tp.getName() + " started");
}

void tweenParallelEnded(TweenParallel _tp) {
  println(_tp.getName() + " ended");
}

// void tweenParallelChanged(TweenParallel _tp) {
// println(_tp.getName() + " resumed");
// }

void tweenParallelRepeated(TweenParallel _tp) {
  println(_tp.getName() + " repeated");
}

