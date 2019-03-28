package girlsday.surfer;

import greenfoot.Actor;
import greenfoot.CustomFont;
import greenfoot.GreenfootImage;

public class Counter extends Actor
{

  CustomFont customFont; // font to use
  int value = 0; // counter value

  public Counter()
  {
    try {
      customFont = new CustomFont(java.awt.Font.createFont(0, java.awt.Font.class.getResourceAsStream("/fonts/unifont" + ".ttf")).deriveFont(12f));
    } catch (java.awt.FontFormatException | java.io.IOException e) {
      System.err.println("Unable to load fonts!");
      e.printStackTrace();
    }
  }

  @Override
  public void act()
  {
    setCounterValue(value); // update counter on every frame
  }

  public void setCounterValue(int value)
  {
    GreenfootImage buttonBg = new GreenfootImage(100, 50);       // generate empty image
    buttonBg.setFont(customFont);                                              // set font for image
    buttonBg.drawString(value + " Points", 0, buttonBg.getHeight()); // print text into image
    setImage(buttonBg);                                                        // set image for this actor
  }

  public void increment()
  {
    value++; // increase value by one
  }

  public void reset()
  {
    value = 0; // reset value to zero
  }

}
