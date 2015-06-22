package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class BezahlWerkzeugUI extends JDialog
{

    private JButton _okButton;
    private JButton _abbrechenButton;
    private JTextField _eingabeFeld;
    private JLabel _summeAnzeiger;
    private JLabel _restbetragAnzeiger;
    private JLabel _eingabeFeldText;
    private JLabel _summeAnzeigerText;
    private JLabel _restbetragAnzeigerText;
    private JPanel _knoepfe;
    private JPanel _textFelder;
    private boolean _istOKButton;
    
    private JPanel _vorangaben;
    private JLabel _vorstellungsText;
    private JLabel _anzahlPlaetzeText;
    
    private String _vorstellungsDaten;
    private int _anzahlPlaetze;
    private int _preis;

    public BezahlWerkzeugUI(int preis, String vorstellungsDaten, int anzahlPlaetze)
    {
        _preis = preis;
        _vorstellungsDaten = vorstellungsDaten;
        _anzahlPlaetze = anzahlPlaetze;
        
        _istOKButton = false;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        setupGUI();
        this.setModal(true); //blockt das aufrufende Fenster
        this.pack();
        this.setVisible(true);
    }

    private void setupGUI()
    {
        setupFachwerte();
        setupGrid();
        setupKnoepfe();
        setVisible(true);
    }

    private void setupFachwerte()
    {
        setupVorangaben();
        setupVorstellungText();
        setupAnzahlPlaetzeText();
    }
    
    private void setupVorangaben()
    {
        _vorangaben = new JPanel(new FlowLayout());
        
    }
    
    private void setupVorstellungText()
    {
        _vorstellungsText = new JLabel();
        _vorstellungsText.setText(_vorstellungsDaten);
        _vorstellungsText.setToolTipText("[Datum] FILM (Saal x: Zeit)");
        _vorangaben.add(this);
    }
    
    private void setupAnzahlPlaetzeText()
    {
        _anzahlPlaetzeText = new JLabel();
        _anzahlPlaetzeText.setText("Ausgewählte Plätze: " + _anzahlPlaetze);
        _anzahlPlaetzeText.setToolTipText("Zeigt die Anzahl der aktuell ausgewählten Plätze");
    }
    
    private void setupGrid()
    {
        setupTextFelderJPanel();
        setupFelder();
        setupEingabeWerte();
    }

    private void setupKnoepfe()
    {
        setupKnoepfeJPanel();
        setupOKButton();
        setupAbbrechenButton();
    }

    private void setupEingabeWerte()
    {
        setupEingabeFeld();
        setupSummenAzeiger();
        setupRestbetragAnzeiger();
    }

    private void setupFelder()
    {
        setupEingabeFeldText();
        setupSummenAzeigerText();
        setupRestbetragAnzeigerText();
    }

    private void setupTextFelderJPanel()
    {
        _textFelder = new JPanel(new GridLayout(2,3));
    }

    private void setupKnoepfeJPanel()
    {
        _knoepfe = new JPanel(new FlowLayout());
    }

    private void setupRestbetragAnzeigerText()
    {
        _restbetragAnzeigerText = new JLabel();
        _restbetragAnzeigerText.setText("Rückgeld:");
        _restbetragAnzeigerText.setToolTipText("Zeigt den Saldo der Kasse an.");
    }

    private void setupSummenAzeigerText()
    {
        _summeAnzeigerText = new JLabel();
        _summeAnzeigerText.setText("Zu zahlen:");
        _summeAnzeigerText.setToolTipText("Zeigt den addierten Preis aller gewählten Sitzplätze an.");
    }

    private void setupEingabeFeldText()
    {
        _eingabeFeldText = new JLabel();
        _eingabeFeldText.setText("Gegeben (BAR):");
        _eingabeFeldText.setToolTipText("Hier eingeben, was der Kunde in Bar gegeben hat.");
    }

    private void setupRestbetragAnzeiger()
    {
        _restbetragAnzeigerText = new JLabel();
    }

    private void setupSummenAzeiger()
    {
        _summeAnzeiger = new JLabel();
    }

    private void setupEingabeFeld()
    {
        _eingabeFeld = new JTextField();
        
        _eingabeFeld.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
//            	_restbetragAnzeiger._____________();
            }
        });

    }

    private void setupAbbrechenButton()
    {
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
        _knoepfe.add(this);
    }

    private void schliessen()
    {
        this.dispose();
    }

    private void setupOKButton()
    {
        _okButton = new JButton();
        _okButton.setText("OK");
        _okButton.setToolTipText("Führt den Bezahlvorgang aus.");
        _okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                bestaetigen();
            }
        });
        _knoepfe.add(this);
    }
    
    private void bestaetigen()
    {
        _istOKButton = true;
        schliessen();
    }
    
    public boolean wurdeBezahlt()
    {
        return _istOKButton;
    }
}
