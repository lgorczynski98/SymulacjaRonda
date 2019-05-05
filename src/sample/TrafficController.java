package sample;

import java.util.ArrayList;

public class TrafficController
{
    private int quarter;
    private int ID;
    private static ArrayList<Integer> quarter1;
    private static ArrayList<Integer> quarter2;
    private static ArrayList<Integer> quarter3;
    private static ArrayList<Integer> quarter4;

    public TrafficController(int quarter, int ID)
    {
        this.quarter = quarter;
        this.ID = ID;
        quarter1 = new ArrayList<>();
        quarter2 = new ArrayList<>();
        quarter3 = new ArrayList<>();
        quarter4 = new ArrayList<>();
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
        addToList();
    }

    private void addToList()
    {
        quarter1.remove(new Integer(ID));
        quarter2.remove(new Integer(ID));
        quarter3.remove(new Integer(ID));
        quarter4.remove(new Integer(ID));
        switch(quarter)
        {
            case 1:
            {
                quarter1.add(ID);
                break;
            }
            case 2:
            {
                quarter2.add(ID);
                break;
            }
            case 3:
            {
                quarter3.add(ID);
                break;
            }
            case 4:
            {
                quarter4.add(ID);
                break;
            }
        }
    }

    public static void displayTraffic()
    {
        System.out.println("Quater1: " + quarter1.toString());
        System.out.println("Quater2: " + quarter2.toString());
        System.out.println("Quater3: " + quarter3.toString());
        System.out.println("Quater4: " + quarter4.toString());
        //return ("Quarter1: " + quarter1.toString() + "\nQuarter2: " + quarter2.toString() + "\nQuarter3: " + quarter3.toString() + "\nQuarter4: " + quarter4.toString());
    }
}
