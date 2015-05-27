package org.eclipse.wb.swt;
public class Skladnik implements java.io.Serializable
{
	private String Nazwa_Skladnika;
	private int Ilosc_W_Gram;
	
	
	public Skladnik(String Nazwa_Skladnika)
	{
		this.Nazwa_Skladnika = Nazwa_Skladnika;
	}

    public  String NAZWA_SKLADNIKA()
    {
        return Nazwa_Skladnika;
    }


    public  int ILOSC_W_GRAM()
    {
        return  Ilosc_W_Gram;
    }
    
    public void PodajIlosc(int ilosc)
    {
    	Ilosc_W_Gram = ilosc;
    }

    @Override
    public String toString() {
        return Nazwa_Skladnika;
    }

}
