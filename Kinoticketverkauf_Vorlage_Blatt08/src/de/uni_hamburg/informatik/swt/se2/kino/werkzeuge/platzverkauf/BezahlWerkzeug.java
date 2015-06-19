package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Platz;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;

public class BezahlWerkzeug
{
    
    private BezahlWerkzeugUI _ui;
    private int _anzahlPlaetze;
    private Vorstellung _vorstellung;
    private int _preis;

    public BezahlWerkzeug()
    {
    
    }

    public void setzePlaetze(Set<Platz> plaetze, Vorstellung vorstellung)
    {
        _anzahlPlaetze = plaetze.size();
        _vorstellung = vorstellung;
        berechneNeuenPreis();
    }
    
    private void berechneNeuenPreis()
    {
        _preis = _anzahlPlaetze * _vorstellung.getPreis(); 
    }
    
    public boolean wurdeBezahlt()
    {
        return _ui.wurdeBezahlt();
    }

    public void bezahle()
    {
        _ui = new BezahlWerkzeugUI(_preis, getVorstellungsDaten(), _anzahlPlaetze);
    }
    
    private String getVorstellungsDaten()
    {
    	return (
    			_vorstellung.getFilm().toString().toUpperCase() 
    		   + " - "
    		   + _vorstellung.getDatum()
    		   + " (Saal "
    		   + _vorstellung.getKinosaal()
    		   + ": "
    		   + _vorstellung.getAnfangszeit()
    		   + ")"
    	);
    }
    
}
