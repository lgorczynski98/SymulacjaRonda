package sample;

import java.util.concurrent.Semaphore;

public class DriveRoundaboutSemaphore
{
    private static Semaphore quarter1Semaphore = new Semaphore(10);
    private static Semaphore quarter2Semaphore = new Semaphore(10);
    private static Semaphore quarter3Semaphore = new Semaphore(10);
    private static Semaphore quarter4Semaphore = new Semaphore(10);

    public void acquire(int quarter)
    {
        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.acquire();
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
                    quarter2Semaphore.acquire();
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
                    quarter3Semaphore.acquire();
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
                    quarter4Semaphore.acquire();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void release(int quarter)
    {
        quarter--;
        if(quarter <= 0) quarter = 4;

        switch(quarter)
        {
            case 1:
            {
                try
                {
                    quarter1Semaphore.release();
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
                    quarter2Semaphore.release();
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
                    quarter3Semaphore.release();
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
                    quarter4Semaphore.release();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
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
}
