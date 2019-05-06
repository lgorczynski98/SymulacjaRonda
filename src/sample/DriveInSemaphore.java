package sample;

import java.util.concurrent.Semaphore;

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
}
