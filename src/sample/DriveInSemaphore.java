package sample;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DriveInSemaphore
{
    private static Semaphore Entrance1 = new Semaphore(1);
    private static Semaphore Entrance2 = new Semaphore(1);
    private static Semaphore Entrance3 = new Semaphore(1);
    private static Semaphore Entrance4 = new Semaphore(1);

    private int entranceNumber;

    public DriveInSemaphore(int entranceNumber)
    {
        this.entranceNumber = entranceNumber;
    }

    public void acquire()
    {
        switch(entranceNumber)
        {
            case 1:
            {
                try
                {
                    Entrance1.acquire();
                    System.out.println("Q1 length: " + Entrance1.getQueueLength());
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
                    Entrance2.acquire();
                    System.out.println("Q2 length: " + Entrance2.getQueueLength());
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
                    Entrance3.acquire();
                    System.out.println("Q3 length: " + Entrance3.getQueueLength());
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
                    Entrance4.acquire();
                    System.out.println("Q4 length: " + Entrance4.getQueueLength());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void release()
    {
        switch(entranceNumber)
        {
            case 1:
            {
                try
                {
                    Entrance1.release();
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
                    Entrance2.release();
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
                    Entrance3.release();
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
                    Entrance4.release();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public boolean tryAcquire()
    {
        boolean result = false;

        switch(entranceNumber)
        {
            case 1:
            {
                try
                {
                    result = Entrance1.tryAcquire(100, TimeUnit.MICROSECONDS);
                    //System.out.println("Q1 length: " + Entrance1.getQueueLength());
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
                    result = Entrance2.tryAcquire(100, TimeUnit.MICROSECONDS);
                    //System.out.println("Q2 length: " + Entrance2.getQueueLength());
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
                    result = Entrance3.tryAcquire(100, TimeUnit.MICROSECONDS);
                    //System.out.println("Q3 length: " + Entrance3.getQueueLength());
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
                    result = Entrance4.tryAcquire(100, TimeUnit.MICROSECONDS);
                    //System.out.println("Q4 length: " + Entrance4.getQueueLength());
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
}
