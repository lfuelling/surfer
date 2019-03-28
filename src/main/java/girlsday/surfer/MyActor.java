package girlsday.surfer;

import greenfoot.*;

public class MyActor extends Actor {

  private long lastPointIncrementTimestamp = 0; // last time we increased the counter
  private boolean hasJumpingImage = false;      // if the actor has the jumping image (dude02.png)
  private boolean touchedGround = false;        // if the player touched the ground since the last jump
  private int jumpCount = 0;                    // number of jumps since player last touched the ground

  @Override
  public void act()
  {
    if (getIntersectingObjects(Obstacle.class).size() > 0) { // if the player is touching an obstacle
      ((MyWorld) getWorld()).gameOver();                     // stop the game
    }
    if (isJumping()) {                                       // if the player is jumping
      if (!hasJumpingImage) {                                // if the actor doesn't have the jumping image
        setImage(new GreenfootImage("dude02.png")); // set it
        hasJumpingImage = true;                              // set that the actor currently has jumping image
      }
      getImage().rotate(30);                         // rotate the actor image
      return;                                                // prevent gravity while jumping
    } else if (hasJumpingImage) {                            // if the player is not jumping but still has the image
      setImage(new GreenfootImage("dude01.png"));   // reset the image to the surfing image
      hasJumpingImage = false;                               // set that the actor currently has the regular image
      jumpCount++;                                           // increase the jump counter
    }
    handleGravity();                                         // handle gravity
  }

  private void handleGravity()
  {
    if (getObjectsInRange(64, GroundOrObstacle.class).size() == 0) {        // if the player is currently in the air
      setLocation(getX(), getY() + 10);                                         // move it ten pixels towards the bottom
      touchedGround = false;                                                       // set that the player is not at the ground
    } else if (lastPointIncrementTimestamp - System.currentTimeMillis() <= -100) { // if it's time to increment points
      ((MyWorld) getWorld()).incrementCounter();                                   // increment counter
      lastPointIncrementTimestamp = System.currentTimeMillis();                    // set the time for last point given
      touchedGround = true;                                                        // set that player is touching ground
      jumpCount = 0;                                                               // set jump count to zero
    }
  }

  private boolean isJumping()
  {
    if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("space")) {// if space or arrow up is pressed
      if (touchedGround || jumpCount <= 2) {                                          // if the player is able to jump
        setLocation(getX(), getY() - 10);                                          // let him jump
        return true;                                                                  // return "yes, we are jumping"
      } else {                                                                        // if the player can not jump
        return false;                                                                 // return "no, no jumping"
      }
    }
    return false;                                                                     // same if space/up is not pressed
  }


}
