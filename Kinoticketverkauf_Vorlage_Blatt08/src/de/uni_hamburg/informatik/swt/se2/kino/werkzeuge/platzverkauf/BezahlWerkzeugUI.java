package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Eine Klasse, um eine GUI zum Bezahlen von ausgewählten Plätzen aufzurufen.
 * 
 * @author Utz
 *
 */
public class BezahlWerkzeugUI extends JDialog
{
    /**
     * Eine Seriennummer, weil JDialog eine benötigt.
     */
    private static final long serialVersionUID = 1850002084656808164L;
    private JPanel _vorangaben;
    private JLabel _vorstellungsText;
    private JLabel _anzahlPlaetzeText;

    private JPanel _textFelder;
    private JLabel _summeAnzeigerText;
    private JLabel _summeAnzeiger;
    private JLabel _eingabeFeldText;
    private JTextField _eingabeFeld;
    private JLabel _restbetragAnzeigerText;
    private JLabel _restbetragAnzeiger;

    private JPanel _knoepfe;
    private JButton _okButton;
    private JButton _abbrechenButton;

    private boolean _istDerOKButtonDrueckbar;
    private String _vorstellungsDaten;
    private int _anzahlPlaetze;
    int _preis;
    private String _alterEingabewert;
    private String _aktuellerEingabewert;

    private static final String TITEL = "SE2-Bezahlungsansicht SoSe 2015";

    /**
     * Initialisiert die GUI.
     * 
     * @param preis der Preis aller ausgewählten Plätze zusammen in EuroCent
     * @param vorstellungsDaten die DaTen der Vorstellung, in der die Plätze verkauft werden sollen. In der Form: [Datum] - FILM (Saal x: Zeit)
     * @param anzahlPlaetze die Anzahl der aktuell ausgewählten Plätze
     * @param beobachter die Klasse, die darüber informiert werden soll, dass sich der Eingabestring geändert hat
     */
    public BezahlWerkzeugUI(int preis, String vorstellungsDaten,
            int anzahlPlaetze, Beobachter beobachter)
    {
        _preis = preis;
        _vorstellungsDaten = vorstellungsDaten;
        _anzahlPlaetze = anzahlPlaetze;
        setzeBeobachter(beobachter);
        _alterEingabewert = "";
        _aktuellerEingabewert = "";

        _istDerOKButtonDrueckbar = false;

        setupGUI();
    }

    /**
     * Ein Set aller Beobachter, die sich bei dieser Klasse registriert haben.
     */
    protected Set<Beobachter> _beobachter = new HashSet<Beobachter>();

    /**
     * Eine Methode, um einen Beobachter zum Set hinzuzufügen.
     * 
     * @param b Der Beobachter
     */
    public void setzeBeobachter(Beobachter b)
    {
        _beobachter.add(b);
    }

    /**
     * Sagt allen Beobachtern aus dem Set, dass was voll krasses passiert ist und sie sich das lieber mal anschauen sollten.
     */
    public void informiereUeberAenderung()
    {
        for (Beobachter beobachter : _beobachter)
        {
            beobachter.reagiereAufAenderung(this);
        }
    }

    /**
     * Eine Setupmethode, um die Gui zu erstellen.
     */
    private void setupGUI()
    {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        setupVorangaben();
        setupTextFelder();
        setupKnoepfe();

        setTitle(TITEL);
        setModal(true); // blockt das aufrufende Fenster
        pack();
        Dimension d = Toolkit.getDefaultToolkit()
            .getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x - (this.getWidth() / 2), y - (this.getHeight() / 2));
        setResizable(false);
        setVisible(true);

    }

    /**
     * Eine Methode, um die Nachkommastellen des Preises aller ausgewählten Plätze zusammen zu erhalten.
     * 
     * @return die Nachkommastellen des Preises aller ausgewählten Plätze zusammen
     */
    private String anfangsNachkommastellen()
    {
        if (_preis % 100 < 10)
        {
            return "0" + String.valueOf(_preis % 100);
        }
        else
        {
            return String.valueOf(_preis % 100);
        }
    }

    /**
     * Eine Setupmethode, um die Preisberechnungsfelder in die Gui einzubinden.
     */
    private void setupTextFelder()
    {

        _textFelder = new JPanel(new GridLayout(3, 2));

        _summeAnzeigerText = new JLabel();
        _summeAnzeigerText.setText("Zu zahlen (In €.Cent):");
        _summeAnzeigerText.setToolTipText("Zeigt den addierten Preis aller gewählten Sitzplätze an.");
        _textFelder.add(_summeAnzeigerText);

        _summeAnzeiger = new JLabel();
        _summeAnzeiger.setText((_preis / 100) + "." + anfangsNachkommastellen()
                + "");
        _summeAnzeiger.setToolTipText("Zeigt den addierten Preis aller gewählten Sitzplätze an.");
        _textFelder.add(_summeAnzeiger);

        _eingabeFeldText = new JLabel();
        _eingabeFeldText.setText("Gegeben (In €.Cent):");
        _eingabeFeldText.setToolTipText("Hier eingeben, was der Kunde in Bar gegeben hat.\n"
                + "Bitte nur 2 Nachkommastellen.");
        _textFelder.add(_eingabeFeldText);

        _eingabeFeld = new JTextField();
        _eingabeFeld.setText("");
        _eingabeFeld.setToolTipText("Hier eingeben, was der Kunde in Bar gegeben hat.");
        _eingabeFeld.getDocument()
            .addDocumentListener(new DocumentListener()
            {

                @Override
                public void changedUpdate(DocumentEvent arg0)
                {

                }

                @Override
                public void insertUpdate(DocumentEvent arg0)
                {
                    _alterEingabewert = _aktuellerEingabewert;
                    _aktuellerEingabewert = _eingabeFeld.getText();
                    informiereUeberAenderung();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0)
                {
                    _alterEingabewert = _aktuellerEingabewert;
                    _aktuellerEingabewert = _eingabeFeld.getText();
                    informiereUeberAenderung();
                }

            });
        _textFelder.add(_eingabeFeld);

        _restbetragAnzeigerText = new JLabel();
        _restbetragAnzeigerText.setText("Rückgeld (In €.Cent):");
        _restbetragAnzeigerText.setToolTipText("Zeigt den Saldo der Kasse an.");
        _textFelder.add(_restbetragAnzeigerText);

        _restbetragAnzeiger = new JLabel();
        _restbetragAnzeiger.setText(_summeAnzeiger.getText());
        _restbetragAnzeiger.setToolTipText("Zeigt den Saldo der Kasse an.");
        _textFelder.add(_restbetragAnzeiger);

        this.add(_textFelder, BorderLayout.CENTER);
    }

    /**
     * Eine Setupmethode, um die Angaben zur Kinovorstellung, zu der Plätze verkauft werden sollen, in die Gui einzubinden.
     */
    private void setupVorangaben()
    {
        _vorangaben = new JPanel(new FlowLayout());
        _vorangaben.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        _vorstellungsText = new JLabel();
        _vorstellungsText.setText(_vorstellungsDaten);
        _vorstellungsText.setToolTipText("[Datum] - FILM (Saal x: Startzeit)");
        _vorangaben.add(_vorstellungsText);

        _anzahlPlaetzeText = new JLabel();
        _anzahlPlaetzeText.setText("Ausgewählte Plätze: " + _anzahlPlaetze);
        _anzahlPlaetzeText.setToolTipText("Zeigt die Anzahl der aktuell ausgewählten Plätze");
        _vorangaben.add(_anzahlPlaetzeText);

        _vorangaben.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.black));
        this.add(_vorangaben, BorderLayout.PAGE_START);

    }

    /**
     * Eine Setupmethode, um die Knöpfe in die Gui einzubinden.
     */
    private void setupKnoepfe()
    {
        _knoepfe = new JPanel(new FlowLayout());
        _knoepfe.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        _okButton = new JButton();
        _okButton.setText("OK");
        _okButton.setToolTipText("Führt den Bezahlvorgang aus.");
        _okButton.setEnabled(false);
        _okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _istDerOKButtonDrueckbar = true;
                schliessen();
            }
        });
        _knoepfe.add(_okButton);

        _abbrechenButton = new JButton();
        _abbrechenButton.setText("ABBRECHEN");
        _abbrechenButton.setToolTipText("Bricht den Bezahlprozess ab.");
        _abbrechenButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                schliessen();
            }
        });
        _knoepfe.add(_abbrechenButton);

        this.add(_knoepfe, BorderLayout.PAGE_END);
    }

    /**
     * Schließt das Bezahlfenster 
     */
    private void schliessen()
    {
        this.dispose();
    }

    /**
     * Schaltet die Fähigkeit frei, die gewählten Plätze zu verkaufen, wenn der volle Preis bezahlt wurde
     * 
     * @return ist der OK-Button klickbar?
     */
    public boolean wurdeBezahlt()
    {
        return _istDerOKButtonDrueckbar;
    }

    /**
     * Eine Methode, um den unveränderten Inhalt des _eingabeFeld-es von außen lesen zu können.
     * 
     * @return der unveränderte Inhalt des _eingabeFeld-es
     */
    public String alteEingabe()
    {
        // TODO Auto-generated method stub
        return _alterEingabewert;
    }

    /**
     * Eine Methode, um den veränderten Inhalt - durch Tasteneingabe, oder Ähnliches - aus dem _eingabeFeld von außen lesen zu können.
     * 
     * @return der veränderte Inhalt des _eingabeFeld-es
     */
    public String aktuelleEingabe()
    {
        return _aktuellerEingabewert;
    }

    /**
     * Bietet die Möglichkeit, einen String von außen in das Eingabefeld zu schreiben.
     * 
     * @param zuSetzendeEingabe der Text, der in das Eingabefeld geschrieben werden soll
     */
    public void setzeEingabeText(String zuSetzendeEingabe)
    {
        _eingabeFeld.setText(zuSetzendeEingabe);
    }

    /**
     * Bietet die Möglichkeit, das Eingabefeld von außen auslesen zu können.
     * 
     * @return der Text aus dem Eingabefeld
     */
    public String gibEingabeString()
    {
        return _eingabeFeld.getText();
    }

    /**
     * Bietet die Möglichkeit, die Mausposition im Eingabefeld von außen zu setzen.
     */
    public void setzeEingabeMausPosition(int MausZeigerPosition)
    {
        _eingabeFeld.setCaretPosition(MausZeigerPosition);
    }

    /**
     * Bietet die Möglichkeit, die Mausposition im Eingabefeld von außen auslesen zu können.
     * 
     * @return die Mausposition im Eingabefeld
     */
    public int getEingabeMausPosition()
    {
        return _eingabeFeld.getCaretPosition();
    }
    
    /**
     * Bietet die Möglichkeit, von außen einen Restbetrag zu setzen.
     * 
     * @param Restbetrag der Restbetrag
     */
    public void setzeRestbetrag(String Restbetrag)
    {
        _restbetragAnzeiger.setText(Restbetrag);
    }

    /**
     * Eine Fehlermeldung, die das aktuelle Fenster sperrt.
     * Eventuell später mal einbinden.
     */
    //    private void fehler()
    //    {
    //        JOptionPane error = new JOptionPane(JOptionPane.OK_OPTION);
    //        error.showMessageDialog(this, "Bitte nur sinvolle Geldwerte eingeben.", "Warnung",
    //                JOptionPane.WARNING_MESSAGE);
    //        error.setVisible(true);
    //    }
}
