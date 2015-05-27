package org.eclipse.wb.swt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import javax.swing.JOptionPane;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;


public class Ksiazka implements java.io.Serializable
{

	public static ArrayList<Potrawa> getBaza_Potraw() {
		return Baza_Potraw;
	}

	private static transient Sortowanie sortowanie = new Sortowanie();
	private static transient ArrayList<String>Rodzaje;
	private transient Text TSkladnik;
	private static transient int listagoniec;
	
	//Pola
	private static transient ArrayList<Potrawa> Baza_Potraw = new ArrayList<Potrawa>(){};
	private static transient ArrayList<Potrawa> Baza_PotrawKopia = new ArrayList<Potrawa>(){};
	
	
	
	
	private static transient ArrayList<Skladnik>skladniki;

	
	protected transient Shell shell;
	private Text TNazwa;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) 
	{
	        
	        
		try {
			Ksiazka window = new Ksiazka();
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


	
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(776, 531);
		shell.setText("SWT Application");
		DodajPrzepisOkno kuchnie = new DodajPrzepisOkno();
		kuchnie.TworzenieRodzai();
		
		
		Rodzaje = kuchnie.getListakuchni();
		try {
			Baza_Potraw = DeSerializacjaPotraw();
		} catch (ClassNotFoundException | IOException e2) {
			//JOptionPane.showMessageDialog(null, e2);
		}
		Baza_PotrawKopia = Baza_Potraw;
		List lista = new List(shell, SWT.BORDER);
		lista.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lista.setBounds(182, 29, 395, 449);
		
		Tablica_Na_Liste(Baza_Potraw,lista);
		
		
		Button przycisk1 = new Button(shell, SWT.NONE);
		przycisk1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				shell.dispose();
			}
		});
		
		przycisk1.setBounds(638, 415, 75, 63);
		przycisk1.setText("Koniec");
		
		Button PrzyciskDodaj = new Button(shell, SWT.NONE);
		PrzyciskDodaj.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				DodajPrzepisOkno dodawanie = new DodajPrzepisOkno();
				dodawanie.open();
				Tablica_Na_Liste(Baza_Potraw,lista);
				Baza_PotrawKopia = Baza_Potraw;
				
				lista.removeAll();
				sortowanie.Alfabetyczne(Baza_Potraw);
				Tablica_Na_Liste(Baza_Potraw,lista);
				
				try {
					SerializacjaPotraw(Baza_Potraw);
				} catch (ClassNotFoundException | IOException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		PrzyciskDodaj.setBounds(638, 28, 102, 25);
		PrzyciskDodaj.setText("Dodaj przepis");
		
		Button PrzysiskUsun = new Button(shell, SWT.NONE);
		PrzysiskUsun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if((lista.getItems()).length!=0&&lista.getSelectionIndex()>-1)
				{
					int i = lista.getSelectionIndex();
					Baza_Potraw.remove(i);
					Tablica_Na_Liste(Baza_Potraw,lista);
					try {
						SerializacjaPotraw(Baza_Potraw);
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		PrzysiskUsun.setBounds(638, 59, 102, 25);
		PrzysiskUsun.setText("Usu\u0144 przepis");
		
		Button PrzyciskWyswietl = new Button(shell, SWT.NONE);
		PrzyciskWyswietl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(lista.getSelectionCount()!=0)
				{
					listagoniec = lista.getSelectionIndex();
					
					WyœwietlOkno okno = new WyœwietlOkno();
					okno.open();
					try {
						SerializacjaPotraw(Baza_Potraw);
					} catch (ClassNotFoundException | IOException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		PrzyciskWyswietl.setBounds(638, 90, 102, 25);
		PrzyciskWyswietl.setText("Wy\u015Bwitl");
		
		CCombo combo = new CCombo(shell, SWT.BORDER);
		combo.setBounds(91, 32, 85, 21);
		DodajRodzajKuchni(combo,Rodzaje);
		//combo = kuchnie.getRodzaj();
		
		Button btnSortujPoRodzaju = new Button(shell, SWT.NONE);
		btnSortujPoRodzaju.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				//Baza_Potraw2 = sortowanie.Po_Rodzaju(Baza_Potraw, combo.getItem(combo.getSelectionIndex()));
				if(combo.getSelectionIndex()!=-1)
				{
					//Baza_PotrawKopia=Baza_Potraw;
					Baza_Potraw = sortowanie.Po_Rodzaju(Baza_Potraw, combo.getItem(combo.getSelectionIndex()));
					Tablica_Na_Liste(Baza_Potraw,lista);
				}
			}
		});
		btnSortujPoRodzaju.setBounds(10, 29, 75, 25);
		btnSortujPoRodzaju.setText("Po rodzaju");
		

		
		Label lblSzukaj = new Label(shell, SWT.NONE);
		lblSzukaj.setBounds(51, 10, 68, 15);
		lblSzukaj.setText("Sortowanie");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 15, SWT.BOLD));
		lblNewLabel.setBounds(320, -1, 118, 28);
		lblNewLabel.setText("Przepisy:");
		
		Button btnPoOcenie = new Button(shell, SWT.NONE);
		btnPoOcenie.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lista.removeAll();
				sortowanie.Po_Ocenie(Baza_Potraw);
				Tablica_Na_Liste(Baza_Potraw,lista);
			}
		});
		btnPoOcenie.setBounds(10, 59, 75, 25);
		btnPoOcenie.setText("Po ocenie");
		
		Button btnAlfabetycznie = new Button(shell, SWT.NONE);
		btnAlfabetycznie.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lista.removeAll();
				sortowanie.Alfabetyczne(Baza_Potraw);
				Tablica_Na_Liste(Baza_Potraw,lista);
			}
		});
		btnAlfabetycznie.setBounds(10, 90, 75, 25);
		btnAlfabetycznie.setText("Alfabetycznie");
		
		Button btnPoNazwie = new Button(shell, SWT.NONE);
		btnPoNazwie.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(!TNazwa.getText().isEmpty())
				{
					//Baza_PotrawKopia=Baza_Potraw;
					Baza_Potraw = sortowanie.Po_Nazwie(Baza_Potraw,TNazwa.getText());
					Tablica_Na_Liste(Baza_Potraw,lista);
				}
				
			}
		});
		btnPoNazwie.setBounds(10, 121, 75, 25);
		btnPoNazwie.setText("Po nazwie");
		
		Button btnPoSkladniku = new Button(shell, SWT.NONE);
		btnPoSkladniku.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!TSkladnik.getText().isEmpty())
				{
					Baza_Potraw = sortowanie.Po_Skladniku(Baza_Potraw, TSkladnik.getText());
					Tablica_Na_Liste(Baza_Potraw,lista);
				}
				
				
				
			}
		});
		btnPoSkladniku.setBounds(10, 152, 75, 25);
		btnPoSkladniku.setText("Po skladniku");
		
		TSkladnik = new Text(shell, SWT.BORDER);
		TSkladnik.setBounds(91, 152, 85, 21);
		
		Button btnGlownaBaza = new Button(shell, SWT.NONE);
		btnGlownaBaza.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!Baza_PotrawKopia.isEmpty())
				{
					Baza_Potraw = Baza_PotrawKopia;
					Tablica_Na_Liste(Baza_Potraw,lista);
				}
			}
		});
		btnGlownaBaza.setBounds(10, 183, 75, 25);
		btnGlownaBaza.setText("G\u0142\u00F3wna baza");
		
		TNazwa = new Text(shell, SWT.BORDER);
		TNazwa.setBounds(91, 121, 85, 21);

	}
	
	public static Potrawa GetSelectedPotrawa()
	{
		return Baza_Potraw.get(listagoniec);
	}
	
	public static void DodajKomentarz(String kom)
	{
		Baza_Potraw.get(listagoniec).Dodaj_Komentarz(kom);
	}
	public static void Usun_Komentarze()
	{
		Baza_Potraw.get(listagoniec).Usun_Komentarze();
	}
	public static void UpdatePotrawa(Potrawa potrawa)
	{
		Baza_Potraw.set(listagoniec, potrawa);
	}

	
	private static void Tablica_Na_Liste(ArrayList<Potrawa> Tablica,List lista)
	{
		int index = 0;
		lista.removeAll();
		if(Tablica.isEmpty()!=true)
		{
			for (Potrawa Goniec : Tablica)
	    	{
	    		lista.add((index+1)+". "+Goniec.NAZWA_POTRAWY()+"  Ocena: "+Goniec.OCENA());
	    		index++;
	    	}
		}
	}
	public static void dodaj_Baza_Potraw(Potrawa Potrawa)
	{
		Baza_Potraw.add(Potrawa);
	}

	private static void DodajRodzajKuchni(CCombo Rodzaj,ArrayList<String> lista)
	{
		for(String Goniec:lista)
		{
			Rodzaj.add(Goniec);
		}
	}
	private static void SerializacjaPotraw(ArrayList<Potrawa> baza)  throws IOException, ClassNotFoundException
	{
		ObjectOutputStream wyjscie = new ObjectOutputStream(new FileOutputStream("Potrawy.dat"));
		wyjscie.writeObject(baza);
		wyjscie.close();
	}
	private static ArrayList<Potrawa> DeSerializacjaPotraw()  throws IOException, ClassNotFoundException
	{
		ArrayList<Potrawa> wczyt = new ArrayList<Potrawa>(){};
		ObjectInputStream wejscie = new ObjectInputStream(new FileInputStream("Potrawy.dat"));
		wczyt= (ArrayList<Potrawa>)wejscie.readObject();
		wejscie.close();
		return wczyt;
	}
}
