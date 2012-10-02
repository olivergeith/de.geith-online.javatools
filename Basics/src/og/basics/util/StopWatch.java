package og.basics.util;
/*
 * Created on 19.12.2006 with Eclipse
 *
 */

/**
 * @author André Kolster
 * 
 * created 19.12.2006
 * 
 */
public class StopWatch
{
  private long startTime = 0;
  private long time = 0;

  /**
   * 
   */
  public StopWatch()
  {
    super();
    reset();
  }
  
  synchronized public void start()
  {
    if(isRunning()) throw new IllegalStateException("stopwatch already running");
    startTime = currentTime();
  }
  
  synchronized public void stop()
  {
    if(!isRunning()) throw new IllegalStateException("stopwatch not running");
    time += currentTime() - startTime;
    startTime = 0;
  }
  
  synchronized public void reset()
  {
    if(isRunning()) stop();
    startTime = 0;
    time = 0;
  }
  
  synchronized public long time()
  {
    if(!isRunning()) return time;
    
    return time+(currentTime()-startTime);
  }

  /**
   * COMMENT
   * @return
   */
  synchronized private boolean isRunning()
  {
    return startTime!=0;
  }
  
  private static long currentTime()
  {
    return System.nanoTime();
  }
  
  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    final long t = time();
    final String s = Long.toString(t/1000000); 
    final String n = Long.toString(t%1000000);
    
    return s+"."+"000000".substring(0,6-n.length())+n+"ms";
  }
}