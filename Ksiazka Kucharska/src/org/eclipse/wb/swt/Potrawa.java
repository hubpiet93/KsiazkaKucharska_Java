package org.eclipse.wb.swt;

import java.util.ArrayList;
import java.util.List;

public class Potrawa implements java.io.Serializable
{
    private ArrayList<Skladnik> Skladniki ;
    private ArrayList<Komentarz> Komentarze ;

    private int Czas_Przygotowania_Potrawy;
    private String Nazwa_Potrawy;
    private String Rodzaj_Potrawy;
    private int Liczba_Porcji;
    private int Ocena;
    private String Opis;

   

    public Potrawa(String Nazwa_Potrawy,int Czas_Przygotowania_Potrawy,String Rodzaj_Potrawy,int Liczba_Porcji,int Ocena,ArrayList<Skladnik> Skladniki,String Opis)
    {
        this.Nazwa_Potrawy=Nazwa_Potrawy;
        this.Czas_Przygotowania_Potrawy = Czas_Przygotowania_Potrawy;
        this.Rodzaj_Potrawy = Rodzaj_Potrawy;
        this.Liczba_Porcji = Liczba_Porcji;
        this.setSkladniki(Skladniki);
        this.Ocena = Ocena;
        this.Opis = Opis;
        this.Komentarze = new ArrayList<Komentarz>();

    }//konstruktor
    
    public int CZAS_PRZYGOTOWANIA_POTRAWY(){return Czas_Przygotowania_Potrawy;}
    public String NAZWA_POTRAWY(){return  Nazwa_Potrawy;}
    public String RODZAJ_POTRAWY(){return  Rodzaj_Potrawy;}
    public int LICZBA_PORCJI(){return  Liczba_Porcji;}
    public int OCENA(){return  Ocena;}

    public void Dodaj_Komentarz(String Komentarz)
    {
        Komentarze.add(new Komentarz(Komentarz));
    }
    public void Usun_Komentarze()
    {
    	Komentarze.clear();
    }
    @Override
    public String toString() {
        return Nazwa_Potrawy;
    }
	public ArrayList<Skladnik> getSkladniki() {
		return Skladniki;
	}
	public String getOpis() {
		return Opis;
	}
	public void setOpis(String opis) {
		Opis = opis;
	}
	public void setSkladniki(ArrayList<Skladnik> skladniki) {
		Skladniki = skladniki;
	}
	public ArrayList<Komentarz> getKomentarze()
	{
		return Komentarze;
	}
	

}

