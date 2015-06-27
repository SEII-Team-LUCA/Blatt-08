package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

/**
 * Setzt eine Klasse, die dieses Interface implementiert, zum Beobachter.
 * Sie kann nun auf Zustandsänderungen ihrer beobachteten Objekte reagieren. 
 */
public interface Beobachter
{

    /**
     * Der Beobachter reagiert auf eine Änderung, die ihm durch eine beobachtbare Klasse mitgeteilt wurde.
     * 
     * @param i ein int zur Differenzierung der aufrufenden Klasse
     */
    public void reagiereAufAenderung(BezahlWerkzeugUI ui);

}
