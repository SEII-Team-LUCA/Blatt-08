package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    
    private int _preis;

    public BezahlWerkzeugUI(int preis)
    {
        _preis = preis;
        _istOKButton = false;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setupGUI();
        this.setLayout(new BorderLayout());
        this.setModal(true); //blockt das aufrufende Fenster
        this.pack();

    }

    private void setupGUI()
    {
        setupKnoepfe();
        setupFelder();
    }

    private void setupFelder()
    {
        setupTextFelderJPanel();
        setupTextFelder();
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

    private void setupTextFelder()
    {
        setupEingabeFeldText();
        setupSummenAzeigerText();
        setupRestbetragAnzeigerText();
    }

    private void setupTextFelderJPanel()
    {
        _textFelder = new JPanel(new GridLayout());

    }

    private void setupKnoepfeJPanel()
    {
        _knoepfe = new JPanel(new FlowLayout());

    }

    private void setupRestbetragAnzeigerText()
    {
        // TODO Auto-generated method stub

    }

    private void setupSummenAzeigerText()
    {
        // TODO Auto-generated method stub

    }

    private void setupEingabeFeldText()
    {
        // TODO Auto-generated method stub

    }

    private void setupRestbetragAnzeiger()
    {
        // TODO Auto-generated method stub

    }

    private void setupSummenAzeiger()
    {
        // TODO Auto-generated method stub

    }

    private void setupEingabeFeld()
    {
        // TODO Auto-generated method stub

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
