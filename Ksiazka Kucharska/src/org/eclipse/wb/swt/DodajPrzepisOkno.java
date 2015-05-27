package org.eclipse.wb.swt;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;


public class DodajPrzepisOkno implements java.io.Serializable
{

	
	protected transient Shell shell;
	private transient Text Nazwa;
	private transient Text Ilosc;
	private static Sortowanie sortowanie;
	
	//POLA
	private static  ArrayList<Skladnik>Baza_Skladnikow;
	private static  ArrayList<Skladnik>Baza_Skladnikow2;
	private static transient ArrayList<Skladnik>Wybrane_Skladniki;
	private static transient String NazwaPotrawy;
	private static transient int Czas;
	private static transient String Rodzaj;
	private static transient int Porcje;
	private static transient int Ocena;
	private static transient Potrawa Potrawa;
	private static transient String Opis;
	private static transient CCombo Rodzaje_Combo;
	private transient Text text;
	
	private static transient ArrayList<String>listakuchni ;
	private transient Text text_1;

	public ArrayList<String> getListakuchni() {
		return listakuchni;
	}

	public void setListakuchni(ArrayList<String> listakuchni) {
		this.listakuchni = listakuchni;
	}

	public static Potrawa getPotrawa() {
		return Potrawa;
	}

	//
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args)
	{
		sortowanie = new Sortowanie();
		
		try {
			DodajPrzepisOkno window = new DodajPrzepisOkno();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	public static void TworzenieRodzai()
	{
		listakuchni = new ArrayList<String>(){};
		
		listakuchni.add("Kuchnia Polska");
		listakuchni.add("Kuchnia Japoñska");
		listakuchni.add("Kuchnia Chiñska");
		listakuchni.add("Kuchnia W³oska");
		listakuchni.add("Kuchnia Amerykañska");
		listakuchni.add("Kuchnia Indyjska");
		listakuchni.add("Kuchnia Œwiata");
		listakuchni.add("Kuchnia Rosyjska");
		listakuchni.add("Kuchnia ¯ydowska");
		listakuchni.add("Kuchnia Koreañska");
		listakuchni.add("Kuchnia Czeska");
		listakuchni.add("Kuchnia Ukraiñska");
		listakuchni.add("Kuchnia Grecka");
		listakuchni.add("Kuchnia Egipska");
		listakuchni.add("Kuchnia Brytyjska");
		listakuchni.add("Kuchnia Niemiecka");
		listakuchni.add("Fasto food");
		listakuchni.add("Obiad");
		listakuchni.add("Œniadanie");
		listakuchni.add("Kolacja");
		listakuchni.add("Deser");
		listakuchni.add("Przystawka");
		sortowanie.Alfabetyczne2(listakuchni);
	}
	protected void createContents()
	{
		TworzenieRodzai();
		Wybrane_Skladniki = new ArrayList<Skladnik>(){};
		Baza_Skladnikow = new ArrayList<Skladnik>(){};
		try {
			Baza_Skladnikow = DeSerializacjaSkladnikow();
		} catch (ClassNotFoundException | IOException e2) {
		//	JOptionPane.showMessageDialog(null, e2);
		}
		
		
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shell.setSize(773, 685);
		shell.setText("SWT Application");
		
		Nazwa = new Text(shell, SWT.BORDER);
		Nazwa.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		Nazwa.setBounds(33, 27, 688, 33);
		//NazwaPotrawy = Nazwa.getText();
		
		
		Label lblNazwaPotrawy = new Label(shell, SWT.NONE);
		lblNazwaPotrawy.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		lblNazwaPotrawy.setBounds(318, 0, 119, 21);
		lblNazwaPotrawy.setText("Nazwa Potrawy");
		
		Label lblCzas = new Label(shell, SWT.NONE);
		lblCzas.setBounds(148, 69, 55, 15);
		lblCzas.setText("Czas");
		
		CCombo CCzas = new CCombo(shell, SWT.BORDER);
		CCzas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		CCzas.setBounds(148, 90, 130, 21);
		CCzas.setEditable(false);
		CCzas.add("15 min");
		CCzas.add("20 min");
		CCzas.add("25 min");
		CCzas.add("30 min");
		CCzas.add("35 min");
		CCzas.add("40 min");
		CCzas.add("45 min");
		CCzas.add("50 min");
		CCzas.add("55 min");
		CCzas.add("60 min");
		CCzas.add("1.5 godziny");
		CCzas.add("2 godziny");
		CCzas.add("2.5 godziny");
		CCzas.add("3 godziny");
	  //  int CzasNapis =CCzas.getSelectionIndex();
		//Czas = NapisNaCzas(CzasNapis);
	    
	    
		Label lblRodzaj = new Label(shell, SWT.NONE);
		lblRodzaj.setBounds(307, 69, 55, 15);
		lblRodzaj.setText("Rodzaj");
		
		
		Label lblLPorcji = new Label(shell, SWT.NONE);
		lblLPorcji.setBounds(466, 69, 55, 15);
		lblLPorcji.setText("L. Porcji");
		
		CCombo CPorcje = new CCombo(shell, SWT.BORDER);
		CPorcje.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		CPorcje.setBounds(466, 90, 62, 21);
		CPorcje.setEditable(false);
		CPorcje.add("1");
		CPorcje.add("2");
		CPorcje.add("3");
		CPorcje.add("4");
		CPorcje.add("5");
		CPorcje.add("6");
		CPorcje.add("7");
		CPorcje.add("8");
		CPorcje.add("9");
		CPorcje.add("10");
		//int PorcjeIndeks= CPorcje.getSelectionIndex();
		//Porcje = NapisNaOcena(PorcjeIndeks);
		
		Label lblOcena = new Label(shell, SWT.NONE);
		lblOcena.setBounds(546, 69, 55, 15);
		lblOcena.setText("Ocena");
		
		CCombo COcena = new CCombo(shell, SWT.BORDER);
		COcena.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		COcena.setBounds(546, 90, 62, 21);
		COcena.setEditable(false);
		COcena.add("1");
		COcena.add("2");
		COcena.add("3");
		COcena.add("4");
		COcena.add("5");
		COcena.add("6");
		COcena.add("7");
		COcena.add("8");
		COcena.add("9");
		COcena.add("10");
		//int OcenaIndeks = COcena.getSelectionIndex();
		//Ocena = NapisNaOcena(OcenaIndeks);
		
		
		List lista1 = new List(shell,  SWT.WRAP | SWT.V_SCROLL);
		lista1.setBounds(148, 135, 152, 250);
		//TworzenieBazySkladnikow(Baza_Skladnikow,lista1);	//Na pewno do zmiany
		WyswietlListe(Baza_Skladnikow,lista1);
		
		
		
		
		List lista2 = new List(shell, SWT.WRAP | SWT.V_SCROLL);
		lista2.setBounds(456, 135, 152, 250);
		
		Button BUsun = new Button(shell, SWT.NONE);
		BUsun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			if((lista2.getItems()).length!=0&&lista2.getSelectionCount()!=0)
				{
					int Wybrany_Skladnik;
					Wybrany_Skladnik = lista2.getSelectionIndex();
					lista1.add(Wybrane_Skladniki.get(Wybrany_Skladnik).NAZWA_SKLADNIKA());
					Wybrane_Skladniki.remove(Wybrany_Skladnik);			
					lista2.remove(lista2.getItem(Wybrany_Skladnik));
			
					String[] items = lista1.getItems();
					java.util.Arrays.sort(items);
					lista1.setItems(items);
				}
			}
		});
		BUsun.setBounds(342, 145, 76, 76);
		BUsun.setText("<<");
		
		Button BDodaj = new Button(shell, SWT.NONE);
		BDodaj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(Ilosc.getText()!=""&&lista1.getSelectionCount()!=0)
				{
					int Wybrany_Skladnik;
					Wybrany_Skladnik = lista1.getSelectionIndex();
					Skladnik dodanyskladnik = new Skladnik(lista1.getItem(Wybrany_Skladnik));
					//Skladnik dodanyskladnik = Baza_Skladnikow.get(Wybrany_Skladnik);
					try
					{
						dodanyskladnik.PodajIlosc(Integer.parseInt(Ilosc.getText()));
						Wybrane_Skladniki.add(dodanyskladnik);
						
						lista2.add(lista1.getItem(Wybrany_Skladnik)+"   "+Ilosc.getText()+"g");
						lista1.remove(lista1.getItem(Wybrany_Skladnik));
						Ilosc.setText("");
					}
					catch(Exception exp)
					{
						JOptionPane.showMessageDialog(null, "Podaj iloœæ w gramach");
					}
				}

			}
		});
		BDodaj.setText(">>");
		BDodaj.setBounds(342, 290, 76, 76);
		
		Label lblListaSkadnikw = new Label(shell, SWT.NONE);
		lblListaSkadnikw.setBounds(148, 117, 100, 15);
		lblListaSkadnikw.setText("Lista sk\u0142adnik\u00F3w");
		
		
		Label lblWybraneSkadniki = new Label(shell, SWT.NONE);
		lblWybraneSkadniki.setBounds(456, 117, 100, 15);
		lblWybraneSkadniki.setText("Wybrane sk\u0142adniki");
		
		Ilosc = new Text(shell, SWT.BORDER);
		Ilosc.setBounds(342, 248, 76, 21);
		
		
		
		Label lblIloscWGram = new Label(shell, SWT.NONE);
		lblIloscWGram.setBounds(342, 227, 66, 15);
		lblIloscWGram.setText("Ilosc w gram");
		
		CCombo CRodzaj = new CCombo(shell, SWT.BORDER);
		CRodzaj.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		CRodzaj.setBounds(307, 90, 130, 21);
		CRodzaj.setEditable(false);
		sortowanie.Alfabetyczne2(listakuchni);
		DodajRodzajKuchni(CRodzaj,listakuchni);
	    int RodzajNapis =0;
	    
	    Rodzaje_Combo = CRodzaj;
	    //if(CRodzaj.getSelectionIndex()!=-1)
	    //{
	    //	RodzajNapis = CRodzaj.getSelectionIndex();
		//	Rodzaj = CRodzaj.getItem(RodzajNapis);
	    //}
		
	    

		
		
		Button Dodaj = new Button(shell, SWT.NONE);
		Dodaj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				NazwaPotrawy = Nazwa.getText();
			    int CzasNapis =CCzas.getSelectionIndex();
				Czas = NapisNaCzas(CzasNapis);
				int PorcjeIndeks= CPorcje.getSelectionIndex();
				Porcje = NapisNaOcena(PorcjeIndeks);
				int OcenaIndeks = COcena.getSelectionIndex();
				Ocena = NapisNaOcena(OcenaIndeks);
			    int RodzajNapis =0;
			    if(CRodzaj.getSelectionIndex()!=-1)
			    {
			    	RodzajNapis = CRodzaj.getSelectionIndex();
					Rodzaj = CRodzaj.getItem(RodzajNapis);
			    }
				Opis = text.getText();
			    
				if(NazwaPotrawy!=""&&Czas!=0&&Porcje!=0&&Ocena!=0&&!Wybrane_Skladniki.isEmpty())
				{
					ArrayList<Skladnik> sklad = new ArrayList<Skladnik>(){};
					Potrawa = new Potrawa(NazwaPotrawy,Czas,Rodzaj,Porcje,Ocena,Wybrane_Skladniki,Opis);
					Ksiazka ksiazka = new Ksiazka();
					ksiazka.dodaj_Baza_Potraw(Potrawa);
					shell.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Zapomnia³eœ wype³niæ któreœ z pól");
				}
			}
		});
		Dodaj.setBounds(148, 602, 152, 40);
		Dodaj.setText("Dodaj");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(33, 159, 76, 21);
		
		Button Anuluj = new Button(shell, SWT.NONE);
		Anuluj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		Anuluj.setBounds(456, 602, 152, 40);
		Anuluj.setText("Anuluj");
		
		text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setBounds(148, 415, 460, 169);
		
		
		Label lblOpis = new Label(shell, SWT.NONE);
		lblOpis.setBounds(148, 394, 55, 15);
		lblOpis.setText("Opis");
		
		Button btnDodajInnySkadnik = new Button(shell,  SWT.WRAP  );
		btnDodajInnySkadnik.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(text_1.getText()!=""){
				String nowyskladnik = text_1.getText();
				//Baza_Skladnikow.add(new Skladnik(nowyskladnik));
				//lista1.removeAll();
				//WyswietlListe(Baza_Skladnikow,lista1);
				lista1.add(nowyskladnik);
				String[] items = lista1.getItems();
				int i = 0;
				for(String Goniec:items)
				{
					items[i]=	Goniec.substring(0,1).toUpperCase()+Goniec.substring(1);
					i++;
				}
				java.util.Arrays.sort(items);
				Baza_Skladnikow.removeAll(Baza_Skladnikow);
				for(String Goniec:items)
				{
					Baza_Skladnikow.add(new Skladnik(Goniec));
				}
				lista1.removeAll();
				lista1.setItems(items);
				text_1.setText("");
				//SortowanieBazySkladnikow(Baza_Skladnikow);
				
				try {
					SerializacjaSkladnikow(Baza_Skladnikow);
				} catch (ClassNotFoundException | IOException e1) {
					
					//JOptionPane.showMessageDialog(null, e1);
				}
				}
			}
		});
		btnDodajInnySkadnik.setBounds(33, 186, 75, 51);
		btnDodajInnySkadnik.setText("Dodaj inny sk\u0142adnik");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblNewLabel.setBounds(117, 200, 25, 21);
		lblNewLabel.setText(">>");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBounds(34, 139, 55, 15);
		lblNewLabel_1.setText("Nazwa");
		

		
		Button btnNewButton = new Button(shell, SWT.MULTI | SWT.WRAP);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(lista1.getSelectionCount()!=0&&Baza_Skladnikow.size()!=0)
				{
					int i = lista1.getSelectionIndex();
					Baza_Skladnikow.remove(i);
					lista1.remove(i);
					
					try {
					SerializacjaSkladnikow(Baza_Skladnikow);
					} catch (ClassNotFoundException | IOException e1) {
						
					//	JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		btnNewButton.setBounds(33, 248, 75, 51);
		btnNewButton.setText("Usu\u0144 sk\u0142adnik");

	}
	
	
	public static CCombo getRodzaj()
	{
		return Rodzaje_Combo;
	}
	
	private static int NapisNaCzas(int indeks)
	{
		int wynik = 0;
		
		switch(indeks)
		{
			case 0:
				wynik =15;
				break;
			case 1:
				wynik =15;
				break;
			case 2:
				wynik =25;
				break;
			case 3:
				wynik =30;
				break;
			case 4:
				wynik =35;
				break;
			case 5:
				wynik =40;
				break;
			case 6:
				wynik =45;
				break;
			case 7:
				wynik =50;
				break;
			case 8:
				wynik =55;
				break;
			case 9:
				wynik =60;
				break;
			case 10:
				wynik =90;
				break;
			case 11:
				wynik =120;
				break;
			case 12:
				wynik =150;
				break;
			case 13:
				wynik =180;
				break;
			default:
				wynik = 0;
				break;
		
		}
		
		return wynik;
	}
	private static int NapisNaOcena(int indeks)
	{
		int wynik = 0;
		
		switch(indeks)
		{
			case 0:
				wynik =1;
				break;
			case 1:
				wynik =2;
				break;
			case 2:
				wynik =3;
				break;
			case 3:
				wynik =4;
				break;
			case 4:
				wynik =5;
				break;
			case 5:
				wynik =6;
				break;
			case 6:
				wynik =7;
				break;
			case 7:
				wynik =8;
				break;
			case 8:
				wynik =9;
				break;
			case 9:
				wynik =10;
				break;

			default:
				wynik = 0;
				break;
		
		}
		
		return wynik;
	}	
	private static void DodajRodzajKuchni(CCombo Rodzaj,ArrayList<String> lista)
	{
		
		for(String Goniec:lista)
		{
			Rodzaj.add(Goniec);
		}
	}
	private static void SortowanieBazySkladnikow(ArrayList<Skladnik> lista)
	{	
		
		ArrayList<String> pomoc = new ArrayList<String>(){};
		for(Skladnik Goniec:lista)
		{
			pomoc.add(Goniec.NAZWA_SKLADNIKA());
		}
		sortowanie.Alfabetyczne2(pomoc);
		
		lista = new ArrayList<Skladnik>(){};
		
		for(String Goniec :pomoc)
		{
			lista.add(new Skladnik(Goniec));
		}
		
	}
	private static void WyswietlListe(ArrayList<Skladnik> lista,List lista2)
	{
		lista2.removeAll();
		
		for(Skladnik Goniec:lista)
		{
			lista2.add(Goniec.NAZWA_SKLADNIKA());
		}
		
		
	}
	private static void SerializacjaSkladnikow(ArrayList<Skladnik> baza)  throws IOException, ClassNotFoundException
	{
		ObjectOutputStream wyjscie = new ObjectOutputStream(new FileOutputStream("Skladniki.dat"));
		wyjscie.writeObject(baza);
		wyjscie.close();
	}
	private static ArrayList<Skladnik> DeSerializacjaSkladnikow()  throws IOException, ClassNotFoundException
	{
		ArrayList<Skladnik> wczyt = new ArrayList<Skladnik>(){};
		ObjectInputStream wejscie = new ObjectInputStream(new FileInputStream("Skladniki.dat"));
		wczyt= (ArrayList<Skladnik>)wejscie.readObject();
		wejscie.close();
		return wczyt;
	}
}
