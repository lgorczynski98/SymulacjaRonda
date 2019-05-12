package sample;

import java.util.concurrent.Semaphore;

public class DriveRoundaboutSemaphore
{
    private static int maxPermits = 10;

    volatile private static Semaphore quarter1Semaphore = new Semaphore(maxPermits);
    volatile private static Semaphore quarter2Semaphore = new Semaphore(maxPermits);
    volatile private static Semaphore quarter3Semaphore = new Semaphore(maxPermits);
    volatile private static Semaphore quarter4Semaphore = new Semaphore(maxPermits);

    public void acquire(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.acquire();
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquire Error");
                }
                break;
            }
            case 2:
            {
                try
                {
                    quarter2Semaphore.acquire();
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquire Error");
                }
                break;
            }
            case 3:
            {
                try
                {
                    quarter3Semaphore.acquire();
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquire Error");
                }
                break;
            }
            case 4:
            {
                try
                {
                    quarter4Semaphore.acquire();
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquire Error");
                }
                break;
            }
        }
    }

    public void release(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.release();
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem release Error");
                }
                break;
            }
            case 2:
            {
                try
                {
                    quarter2Semaphore.release();
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem release Error");
                }
                break;
            }
            case 3:
            {
                try
                {
                    quarter3Semaphore.release();
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem release Error");
                }
                break;
            }
            case 4:
            {
                try
                {
                    quarter4Semaphore.release();
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem release Error");
                }
                break;
            }
        }
    }

    public static Semaphore getQuarterSemaphore(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                return quarter1Semaphore;
            }
            case 2:
            {
                return quarter2Semaphore;
            }
            case 3:
            {
                return quarter3Semaphore;
            }
            case 4:
            {
                return quarter4Semaphore;
            }
        }
        return null;
    }

    public boolean tryAcquireMax(int quarter)
    {
        boolean result = false;
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    result = quarter1Semaphore.tryAcquire(maxPermits);
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 2:
            {
                try
                {
                    result = quarter2Semaphore.tryAcquire(maxPermits);
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 3:
            {
                try
                {
                    result = quarter3Semaphore.tryAcquire(maxPermits);
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
            case 4:
            {
                try
                {
                    result = quarter4Semaphore.tryAcquire(maxPermits);
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
        return result;
    }

    public void acquireMax(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.acquire(maxPermits);
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMax Error");
                }
                break;
            }
            case 2:
            {
                try
                {
                    quarter2Semaphore.acquire(maxPermits);
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMax Error");
                }
                break;
            }
            case 3:
            {
                try
                {
                    quarter3Semaphore.acquire(maxPermits);
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMax Error");
                }
                break;
            }
            case 4:
            {
                try
                {
                    quarter4Semaphore.acquire(maxPermits);
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMax Error");
                }
                break;
            }
        }
    }

    public void releaseMax(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.release(maxPermits);
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem releaseeMax Error");
                }
                break;
            }
            case 2:
            {
                try
                {
                    quarter2Semaphore.release(maxPermits);
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem releaseeMax Error");
                }
                break;
            }
            case 3:
            {
                try
                {
                    quarter3Semaphore.release(maxPermits);
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem releaseeMax Error");
                }
                break;
            }
            case 4:
            {
                try
                {
                    quarter4Semaphore.release(maxPermits);
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem releaseeMax Error");
                }
                break;
            }
        }
    }

    public static int getMaxPermits() {
        return maxPermits;
    }

    public void acquireMaxRelease(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.acquire(maxPermits);
                    quarter1Semaphore.release(maxPermits);
                    //System.out.println("Q1 " + quarter1Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMaxRelease Error");
                }
                break;
            }
            case 2:
            {
                try
                {
                    quarter2Semaphore.acquire(maxPermits);
                    quarter2Semaphore.release(maxPermits);
                    //System.out.println("Q2 " + quarter2Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMaxRelease Error");
                }
                break;
            }
            case 3:
            {
                try
                {
                    quarter3Semaphore.acquire(maxPermits);
                    quarter3Semaphore.release(maxPermits);
                    //System.out.println("Q3 " + quarter3Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMaxRelease Error");
                }
                break;
            }
            case 4:
            {
                try
                {
                    quarter4Semaphore.acquire(maxPermits);
                    quarter4Semaphore.release(maxPermits);
                    //System.out.println("Q4 " + quarter4Semaphore.availablePermits());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("DriveRoundaboutSem acquireMaxRelease Error");
                }
                break;
            }
        }
    }
}
