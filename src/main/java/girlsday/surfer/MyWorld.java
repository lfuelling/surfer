package girlsday.surfer;

import greenfoot.*;
import girlsday.surfer.MyActor;

import java.util.Random;

public class MyWorld extends World
{

  GroundOrObstacle[] groundPlanes = new GroundOrObstacle[13]; // array of obstacle/ground tiles
  long lastUpdate;                                            // timestamp of last "movement"
  Counter counter;                                            // counter


  /**
   * Constructor for MyWorld.
   */
  public MyWorld()
  {
    super(800, 400, 1);        // set world dimensions
    prepare();                                                // call prepare()
  }

  /**
   * Prepare the world for the start of the program.
   * That is: create the initial objects and add them to the world.
   * Z-Index is based on order of addition (last to be added is on top)
   */
  private void prepare()
  {

    counter = new Counter();                          // initialize counter

    for (int i = 0; i < groundPlanes.length; i++) {   // iterate over all the ground plane slots
      groundPlanes[i] = getRandomGround();            // and generate a random tile
    }

    addObject(new Sun(), 50, 10);               // add the sun
    addObject(new MyActor(), 10, 0);            // add the surfer
    addObject(counter, getWidth() - 64, 0);     // add the counter

    updateGround();                                    // draw the ground for the first time
  }


  void updateGround()
  {
    if (lastUpdate - System.currentTimeMillis() <= -400) { // if it's time to move
      shiftGround();                                       // move by one slot
      lastUpdate = System.currentTimeMillis();             // set timestamp of last update
    }
    drawGround();                                          // draw all the tiles
  }

  void drawGround()
  {
    getObjects(GroundOrObstacle.class).forEach(this::removeObject);                   // remove all tiles
    for (int i = 0; i < groundPlanes.length; i++) {                                   // iterate over all tiles
      GroundOrObstacle groundPlane = groundPlanes[i];                                 // get current tile
      addObject(groundPlane, i * groundPlane.getImage().getWidth(), getHeight());  // add current tile to level
    }
  }

  void shiftGround()
  {
    for (int i = 0; i < groundPlanes.length; i++) {   // iterate over all tiles
      if(i != groundPlanes.length -1) {               // if we are NOT on the last tile
        groundPlanes[i] = groundPlanes[i+1];          // shift the current tile towards the start
      } else {                                        // if we are on the last tile
        groundPlanes[i] = getRandomGround();          // generate a new one
      }
    }
  }

  @Override
  public void act()
  {
    updateGround(); // update ground on each frame
    super.act();
  }

  public GroundOrObstacle getRandomGround()
  {
    if (new Random().nextBoolean()) {     // if random boolean is true
      return new Ground();                // return ground
    } else {                              // otherwise
      if (new Random().nextBoolean()) {   // if another random boolean is true
        return new Ground();              // return ground
      } else {                            // otherwise
        return new Obstacle();            // return obstacle
      }
    }
  }

  public void incrementCounter()
  {
    counter.increment(); // increment counter
  }

  public void gameOver()
  {
    Greenfoot.stop(); // stop game on game over
  }

  @Override
  public void started()
  {
    super.started();
    counter.reset(); // reset counter if the game starts
  }
}
