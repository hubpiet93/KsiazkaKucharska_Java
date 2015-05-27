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


public class WyœwietlOkno {

	protected Shell shell;
	private transient Text TSkladniki;
	private transient Text TOpis;
	private transient Text TKomentarze;
	private transient Text TDodajKomentarz;
	private static transient Ksiazka ksiazka;
	private static transient Potrawa potrawa;
	private Text Czas;
	private Text Porcje;
	private Text Rodzaj;
	private Text Ocena;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WyœwietlOkno window = new WyœwietlOkno();
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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(773, 685);
		shell.setText("SWT Application");
		ksiazka = new Ksiazka();
		potrawa = ksiazka.GetSelectedPotrawa();
		
		Label lblNazwaPotrawy = new Label(shell, SWT.NONE);
		lblNazwaPotrawy.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblNazwaPotrawy.setBounds(26, 20, 122, 26);
		lblNazwaPotrawy.setText("Nazwa potrawy : ");
		
		Label LbNazwa = new Label(shell, SWT.NONE);
		LbNazwa.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		LbNazwa.setBounds(154, 20, 433, 25);
		LbNazwa.setText(potrawa.NAZWA_POTRAWY());
		
		
		
		Label lblSkadniki = new Label(shell, SWT.NONE);
		lblSkadniki.setBounds(71, 62, 55, 15);
		lblSkadniki.setText("Sk\u0142adniki");
		
		TSkladniki = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.MULTI);
		TSkladniki.setBounds(26, 83, 150, 554);
		
		ArrayList<Skladnik> skladniki ;
		skladniki = potrawa.getSkladniki();
		
		String wynik ="";
		int licznik = 1;
		for(Skladnik Goniec:skladniki)
		{
			//TSkladniki.setText(TSkladniki.getText()+"\n"+Goniec.NAZWA_SKLADNIKA());
			wynik = wynik +"\n"+licznik+". "+ Goniec.NAZWA_SKLADNIKA() + " "+Goniec.ILOSC_W_GRAM()+"g.";
			licznik++;
		}
		//koooooooooooooooooooooommmmmmmmmmmmmmmmmmmmmeeeeeeeeeeeeeeeeeeeeennnnnnnnnnnnnnnttttttttttt
		TSkladniki.setText(wynik.substring(1));
		
		
		TOpis = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		TOpis.setBounds(313, 83, 422, 203);
		TOpis.setText(potrawa.getOpis());
		
		Label lblOpis = new Label(shell, SWT.NONE);
		lblOpis.setBounds(515, 62, 55, 15);
		lblOpis.setText("Opis");
		
		TKomentarze = new Text(shell, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		TKomentarze.setBounds(313, 318, 422, 141);
		
		String wynik2="";
		int licznik2 = 1;
		if(!potrawa.getKomentarze().isEmpty())
		{
			for(Komentarz Goniec:potrawa.getKomentarze())
			{
				wynik2 = wynik2 +"\n"+licznik2+". "+ Goniec.UWAGA();
				licznik2++;
			}
			TKomentarze.setText(wynik2.substring(1));
		}
		
		
		Label lblKomentarze = new Label(shell, SWT.NONE);
		lblKomentarze.setBounds(499, 297, 77, 15);
		lblKomentarze.setText("Komentarze");
		
		TDodajKomentarz = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		TDodajKomentarz.setBounds(313, 465, 422, 116);
		
		
		Button btnKomentarz = new Button(shell, SWT.NONE);
		btnKomentarz.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) 
			{
				if(TDodajKomentarz.getText()!="")
				{
					ksiazka.DodajKomentarz(TDodajKomentarz.getText());
					Aktualizuj_Komentarze(potrawa.getKomentarze(),TKomentarze);
					ksiazka.UpdatePotrawa(potrawa);
					TDodajKomentarz.setText("");
				}
				
			}
		});
		btnKomentarz.setBounds(313, 587, 132, 50);
		btnKomentarz.setText("Dodaj komentarz");
		
		Button btnWyjscie = new Button(shell, SWT.NONE);
		btnWyjscie.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnWyjscie.setBounds(600, 587, 132, 50);
		btnWyjscie.setText("Wyj\u015Bcie");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				potrawa.Usun_Komentarze();
				TKomentarze.setText("");
				Aktualizuj_Komentarze(potrawa.getKomentarze(),TKomentarze);
				ksiazka.UpdatePotrawa(potrawa);
				
			}
		});
		btnNewButton.setBounds(455, 587, 132, 50);
		btnNewButton.setText("Usu\u0144 komentarze");
		
		
		
		Label lblCzas = new Label(shell, SWT.NONE);
		lblCzas.setBounds(182, 86, 30, 15);
		lblCzas.setText("Czas: ");
		
		Label lblLPorcji = new Label(shell, SWT.NONE);
		lblLPorcji.setBounds(182, 117, 55, 15);
		lblLPorcji.setText("L. Porcji:");
		
		Label lblRodzaj = new Label(shell, SWT.NONE);
		lblRodzaj.setBounds(182, 145, 41, 15);
		lblRodzaj.setText("Rodzaj:");
		
		Czas = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		Czas.setBounds(235, 83, 72, 21);
		
		Czas.setText(Integer.toString(potrawa.CZAS_PRZYGOTOWANIA_POTRAWY()));
		
		Porcje = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		Porcje.setBounds(235, 114, 72, 21);
		
		Porcje.setText(Integer.toString(potrawa.LICZBA_PORCJI()));
		
		Rodzaj = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		Rodzaj.setBounds(235, 142, 72, 21);
		
		Rodzaj.setText(potrawa.RODZAJ_POTRAWY());
		
		Ocena = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		Ocena.setBounds(235, 169, 72, 21);
		
		Ocena.setText(Integer.toString(potrawa.OCENA()));
		
		Label lblOcena = new Label(shell, SWT.NONE);
		lblOcena.setBounds(182, 172, 47, 15);
		lblOcena.setText("Ocena:");

	}
	private static void Aktualizuj_Komentarze(ArrayList<Komentarz> komentarze,Text text)
	{
		if(!potrawa.getKomentarze().isEmpty())
		{
			text.setText("");
			String wynik2="";
			int licznik2 = 1;
			for(Komentarz Goniec:potrawa.getKomentarze())
			{
				wynik2 = wynik2 +"\n"+licznik2+". "+ Goniec.UWAGA();
				licznik2++;
			}
			text.setText(wynik2.substring(1));
		}
	}
}
