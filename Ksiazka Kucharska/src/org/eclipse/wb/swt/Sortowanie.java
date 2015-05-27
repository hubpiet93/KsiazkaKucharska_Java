package org.eclipse.wb.swt;
import java.util.*;

import javax.swing.JOptionPane;

public class Sortowanie implements java.io.Serializable
{
    public static ArrayList<Potrawa> Alfabetyczne(ArrayList<Potrawa> lista) //sortowanie do potraw
    {
        ArrayList<Potrawa> wynik = new ArrayList<Potrawa>(){};

            Collections.sort(lista, new Comparator()
            {
            @Override
                public int compare(Object lista1, Object lista2)
                {
                  return ((((Potrawa)lista1).NAZWA_POTRAWY()).toLowerCase()).compareTo(((Potrawa)lista2).NAZWA_POTRAWY().toLowerCase());
                 }
            });

        wynik = lista;

        return  wynik;
    } 
    public static ArrayList<String> Alfabetyczne2(ArrayList<String> lista) //sortowanie do combo box
    {
        ArrayList<String> wynik = new ArrayList<String>(){};

            Collections.sort(lista, new Comparator()
            {
            @Override
                public int compare(Object lista1, Object lista2)
                {
                  return ((((String)lista1).toLowerCase()).compareTo(((String)lista2).toLowerCase()));
                 }
            });

        wynik = lista;

        return  wynik;
    }
    public static ArrayList<Potrawa> Po_Ocenie(ArrayList<Potrawa> lista)
    {
    	ArrayList<Potrawa> wynik = new ArrayList<Potrawa>(){};

        Collections.sort(lista, new Comparator()
        {
        @Override
            public int compare(Object lista1, Object lista2)
            {
              return (((Potrawa)lista1).OCENA() -((Potrawa)lista2).OCENA());
             }
        });

    wynik = lista;

    return  wynik;
    }
    public static ArrayList<Potrawa> Po_Rodzaju(ArrayList<Potrawa> lista, String rodzaj)
    {
    	ArrayList<Potrawa> wynik = new ArrayList<Potrawa>(){};
    	
    	for(Potrawa Goniec:lista)
    	{
    		if(Goniec.RODZAJ_POTRAWY().equals(rodzaj))
    		{
    			wynik.add((Potrawa)Goniec);
    		}
    	}
    	
    	if(wynik.isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Brak przepisów z rodzaju: "+rodzaj);
    		return lista;
    	}

    	
    	return wynik;
    }
    public static ArrayList<Potrawa> Po_Nazwie(ArrayList<Potrawa> lista, String nazwa)
    {
    	ArrayList<Potrawa> wynik = new ArrayList<Potrawa>(){};
    	
    	for(Potrawa Goniec:lista)
    	{
    		if(Goniec.NAZWA_POTRAWY().equals(nazwa))
    		{
    			wynik.add((Potrawa)Goniec);
    		}
    	}
    	
    	if(wynik.isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Brak przepisów o nazwie: "+nazwa);
    		return lista;
    	}

    	
    	return wynik;
    }
    public static ArrayList<Potrawa> Po_Skladniku(ArrayList<Potrawa> lista, String Skladnik)
    {
    	ArrayList<Potrawa> wynik = new ArrayList<Potrawa>(){};
    	ArrayList<Skladnik> skladniki = new ArrayList<Skladnik>(){};
    
    	for(Potrawa Goniec:lista)
    	{
    		skladniki = Goniec.getSkladniki();
    		
    		for(Skladnik Goniec2 :skladniki)
    		{
    			if(Goniec2.NAZWA_SKLADNIKA().equals(Skladnik))
    			{
    				wynik.add((Potrawa)Goniec);
    			}
    		}

    	}
    	
    	if(wynik.isEmpty())
    	{
    		JOptionPane.showMessageDialog(null, "Brak przepisów ze: "+Skladnik);
    		return lista;
    	}

    	
    	return wynik;
    }

}

