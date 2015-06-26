package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.util.Set;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Platz;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;

/**
 * Eine Klasse, um die GUI zum Bezahlen von ausgewählten Plätzen zu verwalten, und die Rechenarbeit für die zu erledigen.
 * 
 * @author Utz
 *
 */
public class BezahlWerkzeug implements Beobachter
{

    private BezahlWerkzeugUI _ui;
    private int _anzahlPlaetze;
    private Vorstellung _vorstellung;
    private int _preis;
    private final String _eingabeVorschrift;

    /**
     * Initialisiert das BezahlWerkzeug insofern, als dass die Vorschrift, die jede Eingabe befolgen muss, gesetzt wird.
     */
    public BezahlWerkzeug()
    {
        _eingabeVorschrift = "((\\d*([,\\.]\\d?{2})?)?)";
        // der String darf nur in der folgenden Form bestehen:
        // beliebig viele Ziffern - ein . - 0 bis 2 Nachkommastellen
    }

    /**
     * Die Methode um die Plätze und die dazugehörige Vorstellung zu erhalten und sie in die GUI einspeisen zu können.
     * 
     * @param plaetze die ausgewählten Plätze
     * @param vorstellung die Vorstellung, in der die Plätze ausgewählt wurden
     */
    public void setzePlaetze(Set<Platz> plaetze, Vorstellung vorstellung)
    {
        _anzahlPlaetze = plaetze.size();
        _vorstellung = vorstellung;
        berechneNeuenPreis();
    }

    /**
     * Berechnet den Preis aller ausgewählten Plätze zusammen.
     */
    private void berechneNeuenPreis()
    {
        _preis = _anzahlPlaetze * _vorstellung.getPreis();
    }

    /**
     * Prüft, ob die Plätze bereits bezahlt wurden und nun als verkauft gesetzt werden dürfen.
     * 
     * @return ob die Plätze bereits bezahlt wurden
     */
    public boolean wurdeBezahlt()
    {
        return _ui.wurdeBezahlt();
    }

    /**
     * Ruft die GUI auf.
     */
    public void bezahle()
    {
        _ui = new BezahlWerkzeugUI(_preis, getVorstellungsDaten(),
                _anzahlPlaetze, this);
    }

    /**
     * Liefert die Relevanten Vorstellungsdaten zurück.
     * 
     * @return die relevanten Vorstellungsdaten
     */
    private String getVorstellungsDaten()
    {
        return ("[" + _vorstellung.getDatum()
            .getTag() + "." + _vorstellung.getDatum()
            .getMonat() + "." + _vorstellung.getDatum()
            .getJahr() + "] - " + _vorstellung.getFilm()
            .toString()
            .toUpperCase()
            .substring(12) + " (" + _vorstellung.getKinosaal()
            .toString()
            .substring(15) + ": " + _vorstellung.getAnfangszeit() + ")");
    }

    /**
     * Der Beobachter reagiert auf eine Änderung, die ihm durch eine beobachtbare Klasse (in unserem Fall die GUI, die die entsprechenden Methoden selber zur Verfügung stellt, ohne Beobachtbar zu extenden [man kann nur einer Klasse extenden]) mitgeteilt wurde.
     */
    @Override
    public void reagiereAufAenderung()
    {
        if (pruefeObGueltigeEingabe())
        {
            setzeRestbetrag();
        }
        else
        {
            stelleAltenWertWiederHer();
        }

    }

    /**
     * Sofern der eingegebene String nicht der Eingabevorschrift entspricht, setze ihn auf den Wert, den er vor der letzen veränderung hatte, zurück.
     */
    private void stelleAltenWertWiederHer()
    {
        try
        {
            int MausZeigerPosition;
            MausZeigerPosition = _ui._eingabeFeld.getCaretPosition();
            _ui._eingabeFeld.setText(_ui.alteEingabe());
            _ui._eingabeFeld.setCaretPosition(MausZeigerPosition);
        }
        catch (NullPointerException e)
        { // wenn der Codefluss hier landet, ist das Eingabefeld leer

        }

    }

    /**
     * Schreibt den errechneten Restbetrag auf die GUI.
     */
    private void setzeRestbetrag()
    {
        _ui._restbetragAnzeiger.setText(endpreis());

    }

    /**
     * Errechnet den Restbetrag.
     * 
     * @return der errechnete Restbetrag
     */
    private String endpreis()
    {
        long Restbetrag;
        Restbetrag = _ui._preis - getEingabePreis();
        return String.valueOf(Restbetrag)
            .substring(0, String.valueOf(Restbetrag)
                .length() - 2) + "." + String.valueOf(Restbetrag)
            .substring(String.valueOf(Restbetrag)
                .length() - 2);
    }

    /**
     * Macht aus der Eingabe eine Zahl, mit der das Programm arbeiten kann.
     * 
     * @return die geparste Zahl
     */
    private long getEingabePreis()
    {
        String EingabeFeldInhalt;
        if (_ui._eingabeFeld.getText()
            .contains("."))
        { // wenn die Eingabe ein Komma enthält
            if (_ui._eingabeFeld.getText()
                .indexOf('.') == _ui._eingabeFeld.getText()
                .length())
            { // wenn nach dem Komma keine Ziffern mehr folgen
                EingabeFeldInhalt = _ui._eingabeFeld.getText()
                    .substring(0, _ui._eingabeFeld.getText()
                        .indexOf(".")) + "00";
            }
            else if (_ui._eingabeFeld.getText()
                .indexOf('.') + 1 == _ui._eingabeFeld.getText()
                .length())
            { // wenn nach dem Komma noch genau Eine Ziffer folgt
                EingabeFeldInhalt = _ui._eingabeFeld.getText()
                    .substring(0, _ui._eingabeFeld.getText()
                        .indexOf(".")) + _ui._eingabeFeld.getText()
                    .substring(_ui._eingabeFeld.getText()
                        .indexOf(".") + 1) + "0";
            }
            else
            //                if (_ui._eingabeFeld.getText()
            //                .indexOf('.') + 2 == _ui._eingabeFeld.getText()
            //                .length())
            { // wenn nach dem Komma noch genau Zwei Ziffern folgen
                EingabeFeldInhalt = _ui._eingabeFeld.getText()
                    .substring(0, _ui._eingabeFeld.getText()
                        .indexOf(".")) + _ui._eingabeFeld.getText()
                    .substring(_ui._eingabeFeld.getText()
                        .indexOf(".") + 1);
            }
        }
        else
        {
            EingabeFeldInhalt = _ui._eingabeFeld.getText() + "00";
        }
        return Long.parseLong(EingabeFeldInhalt);
    }

    /**
     * Prüft, ob die Eingabe der Vorschrift entspricht.
     * 
     * @return entspricht die Eingabe der Vorschrift?
     */
    private boolean pruefeObGueltigeEingabe()
    {
        String Eingabe = _ui.aktuelleEingabe();
        if (Eingabe.matches(_eingabeVorschrift))
        {
            if (Eingabe.contains(","))
            {
                _ui._eingabeFeld.setText(Eingabe.substring(0,
                        Eingabe.indexOf(','))
                        + "." + Eingabe.substring(Eingabe.indexOf(',') + 1));
            }
            return true;
        }
        return false;
    }
}
