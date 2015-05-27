package org.eclipse.wb.swt;


public class Komentarz implements java.io.Serializable
{
    private String Uwaga;



    public Komentarz(String Uwaga)
    {
        this.Uwaga = Uwaga;
    }
    public  String UWAGA()
    {
        return Uwaga;
    }

   @Override
   public String toString()
   {
      return  Uwaga;
   }
}
