package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

public class BezahlWerkzeugUI extends JDialog
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L; //Seriennummer weil JDialog
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

    private boolean _istOKButton;

    private String _vorstellungsDaten;
    private int _anzahlPlaetze;
    private int _preis;

    private static final String TITEL = "SE2-Bezahlungsansicht SoSe 2015";
    private double _eingabe;
    private String _eingabeString;
    private String _eingabeStringRemove;
    private int _anzahlKommata;
    private int _wieVieleNullenAlsNachkommaStellen;

    public BezahlWerkzeugUI(int preis, String vorstellungsDaten,
            int anzahlPlaetze)
    {
        _preis = preis;
        _vorstellungsDaten = vorstellungsDaten;
        _anzahlPlaetze = anzahlPlaetze;

        _istOKButton = false;
        _anzahlKommata = 0;

        setupGUI();
    }

    private void setupGUI()
    {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //        _frame = new JFrame(TITEL);
        //        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //        _frame.getContentPane().setLayout(new BorderLayout());
        //        JComponent rightPanel = platzVerkaufsPanel;
        //        JComponent bottomPanel = erstelleBeendenPanel();
        //        JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
        //                leftPanel, rightPanel);
        //        _frame.getContentPane().add(splitter, BorderLayout.CENTER);
        //        _frame.getContentPane().add(topPanel, BorderLayout.NORTH);
        //        _frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

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

    private void setupVorangaben()
    {
        _vorangaben = new JPanel(new FlowLayout());
        _vorangaben.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        _vorstellungsText = new JLabel();
        _vorstellungsText.setText(_vorstellungsDaten);
        _vorstellungsText.setToolTipText("[Datum] - FILM (Saal x: Zeit)");
        _vorangaben.add(_vorstellungsText);

        _anzahlPlaetzeText = new JLabel();
        _anzahlPlaetzeText.setText("Ausgewählte Plätze: " + _anzahlPlaetze);
        _anzahlPlaetzeText.setToolTipText("Zeigt die Anzahl der aktuell ausgewählten Plätze");
        _vorangaben.add(_anzahlPlaetzeText);

        _vorangaben.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                Color.black));
        this.add(_vorangaben, BorderLayout.PAGE_START);

    }

    private void setupTextFelder()
    {

        _textFelder = new JPanel(new GridLayout(3, 2));

        _summeAnzeigerText = new JLabel();
        _summeAnzeigerText.setText("Zu zahlen (In €.Cent):");
        _summeAnzeigerText.setToolTipText("Zeigt den addierten Preis aller gewählten Sitzplätze an.");
        _textFelder.add(_summeAnzeigerText);

        _summeAnzeiger = new JLabel();
        _summeAnzeiger.setText((_preis / 100) + "." + preisberechnung() + "");
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
        _eingabeFeld.addKeyListener(new KeyListener()
        {

            @Override
            public void keyTyped(KeyEvent e)
            {
                //                char c = e.getKeyChar();
                int position = _eingabeFeld.getCaretPosition();
                switch (e.getKeyChar())
                {
                // wenn Komma
                case ',':
                    // loesche Komma
                    e.consume();
                    // wenn am Anfang des Feldes
                    if (position == 0)
                    {
                    }
                    // ansonsten
                    // wenn weniger als ein Komma
                    // und der Text mindestens noch zwei Zeichen weitergeht als aktuelle Position
                    else if (_anzahlKommata < 1
                            && position + 2 >= _eingabeFeld.getText()
                                .length())
                    {
                        // setze Text des Eingabefeldes auf
                        _eingabeFeld.setText(_eingabeFeld.getText()
                            // das, was schon darin steht, bis zur aktuellen Position
                            .substring(0, position) + "."
                        // einen Punkt
                                + _eingabeFeld.getText()
                                    // und das, was nach der aktuellen Position steht
                                    .substring(position));
                        // merke, dass ein Komma gesetzt wurde
                        _anzahlKommata++;
                        //                        refresh();
                        // ruecke Mauszeiger einen weiter
                        _eingabeFeld.setCaretPosition(position + 1);
                        //                        pruefeGroesse();
                        break;
                    }
                    getToolkit().beep();
                    break;
                // wenn Punkt
                case '.':
                    // wenn am Anfang
                    // ODER mehr als 0 Kommata
                    // ODER der Text weniger als 2 zeichen weiter geht
                    if (position == 0 || _anzahlKommata > 0
                            || position + 2 < _eingabeFeld.getText()
                                .length())
                    {
                        //                        _eingabeFeld.setText(_eingabeFeld.getText().substring(_eingabeFeld.getText().length()-2));
                        // hupen
                        getToolkit().beep();
                        // letzte Eingabe loeschen
                        e.consume();
                        break;
                    }
                    // merke, dass ein Komma gesetzt wurde
                    _anzahlKommata++;
                    //                    refresh();
                    //                    pruefeGroesse();
                    break;
                // wenn ZURUECK
                case KeyEvent.VK_BACK_SPACE:
                    // oder DELETE
                case KeyEvent.VK_DELETE:
                    // wenn ein Komma geloescht wurde
                    if (wurdeEinKommaGeloescht())
                    {
                        // merke, dass ein Komma weniger gelesen wird
                        _anzahlKommata--;
                    }
                    break;
                // wenn ZAHLEN
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    // wenn genau 1 Komma
                    if (_anzahlKommata == 1)
                    {
                        // wenn der Text
                        // nach der Position des Punktes
                        // noch mindestens 2 Felder weitergeht
                        // UND nach der Position der Maus
                        // keine 3 Felder weitergeht
                        if (_eingabeFeld.getText()
                            .indexOf('.') + 2 < _eingabeFeld.getText()
                            .length() && position + 3 > _eingabeFeld.getText()
                            .length())
                        {
                            // hupe
                            getToolkit().beep();
                            // loesche letzte Eingabe
                            e.consume();
                        }
                    }
                    //                    refresh();
                    //                    pruefeGroesse();
                    break;
                // sonst (UNGUELTIGE EINGABE)
                default:
                    // hupe einfach so
                    getToolkit().beep();
                    // loesche Eingabe
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                //                String _eingabeString;
                //                if (e.getKeyCode() == e.VK_ENTER)
                //                {
                //                    _eingabeString = _eingabeFeld.getText();
                //                    if (!_eingabeString.matches("/^\\s*([\\d^,]+[,\\.](\\d?){2}\\s*/$"))
                //                    {
                //                        fehler();
                //                    }
                //                    else
                //                    {
                //                        _eingabeString = _eingabeString.replaceAll(",", "\\.");
                //                        // double
                //                        double _betragInDouble = Double.parseDouble(_eingabeString);
                //
                //                        // string
                //                        if (_eingabeString.matches("\\d+,"))
                //                        {
                //                            _eingabeString = _eingabeString + "00";
                //                        }
                //                        else if (_eingabeString.matches("\\d+,\\d"))
                //                        {
                //                            _eingabeString = _eingabeString + "0";
                //                        }
                //
                //                        // int
                //                        _eingabeString.replaceAll("\\.", "");
                //                        int _eingabeInIntFuerEurocent = Integer.parseInt(_eingabeString);
                //
                //                    } // end else [if (!_eingabe.matches("/^\\s*([\\d^,]+[,\\.](\\d?){2}\\s*/$"))]
                //
                //                } // end if (e.getKeyKode() == VK_ENTER)

            } // end keyReleased( KeyEvent e ) 

            @Override
            public void keyPressed(KeyEvent e)
            {
                //                                try
                //                                {
                //                    do
                //                    {
                //                        _eingabe = Float.parseFloat(_eingabeFeld.getText());
                //                        aktualisiereRestbetragAnzeiger();
                //                    }
                //                while (_eingabeFeld.getText()
                //                    .matches("(\\s+\\d+([,\\.]\\d\\d?)?\\s+)"));
                //                }
                //                catch (NumberFormatException e1)
                //                {
                //                    fehler();
                //                }

            }
        });
        _eingabeFeld.getDocument()
            .addDocumentListener(new DocumentListener()
            {

                @Override
                public void changedUpdate(DocumentEvent e)
                {
                    //                    refresh();
                }

                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    refresh();
                }

                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    refresh();
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

    //    private void pruefeGroesse()
    //    {
    //        if (_eingabeFeld.getText()
    //            .length() > 6)
    //        {
    //            _eingabeFeld.setText(_eingabeFeld.getText()
    //                .substring(0, 6));
    //        }
    //    }

    private String preisberechnung()
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

    private void wieVieleNullenAlsNachkommaStellen()
    {
        if (_anzahlKommata == 1)
        {
            String zeugs = _eingabeStringRemove.substring(_eingabeFeld.getText()
                .length() - 2);
            String zeug2 = _eingabeStringRemove.substring(_eingabeFeld.getText()
                .length() - 1);
            String zeug3 = _eingabeStringRemove
                .substring(_eingabeFeld.getText()
                    .length() - 2, _eingabeFeld.getText()
                    .length() - 1);
            String zeug4 = _eingabeFeld.getText()
                .substring(_eingabeFeld.getText()
                    .length() - 1);
            if (zeugs.equals(".00"))
            {
                _wieVieleNullenAlsNachkommaStellen = 2;
            }
            else if (zeug2.equals(".0")
                    || (zeug3.equals(".") && zeug4.equals("0")))
            {
                _wieVieleNullenAlsNachkommaStellen = 1;
            }
            else if(_eingabeStringRemove.contains(".") && !_eingabeString.contains("."))
            {
                _wieVieleNullenAlsNachkommaStellen = 3; //wenn da iwas,iwas steht
            }
            else
            {
                _wieVieleNullenAlsNachkommaStellen = 0;
            }
        }
        else
        {
            _wieVieleNullenAlsNachkommaStellen = 0;
        }
    }

    private void refresh()
    {
        try
        {
            _eingabeStringRemove = _eingabeString;
            _eingabeString = _eingabeFeld.getText();
            _eingabe = Double.parseDouble(_eingabeString);
            aktualisiereRestbetragAnzeiger();
        }
        catch (NumberFormatException e)
        {

        }
        finally
        {
            if (_eingabeFeld.getText()
                .length() == 0)
            {
                _restbetragAnzeiger.setText(_summeAnzeiger.getText());
            }
        }
        if (_restbetragAnzeiger.getText()
            .length() > 0)
        {
            if (_restbetragAnzeiger.getText()
                .charAt(0) == '-' || _restbetragAnzeiger.getText()
                .equals("0.00"))
            {
                _okButton.setEnabled(true);
            }
            else
            {
                _okButton.setEnabled(false);
            }
        }
    }

    //    private void fehler()
    //    {
    //        JOptionPane error = new JOptionPane(JOptionPane.OK_OPTION);
    //        error.showMessageDialog(this, "Bitte nur Zahlen eingeben.", "Warnung",
    //                JOptionPane.WARNING_MESSAGE);
    //        error.setVisible(true);
    //    }

    private boolean wurdeEinKommaGeloescht()
    //TODO ZUKUNFTSIHR
    //_eingabe sieht so aus: xxx.0 oder xxx.x oder xxx.xx oder x.xxEx
    //ACHTUNG man kann auch 99.00 ins _eingabefeld eingeben!!!
    {
        wieVieleNullenAlsNachkommaStellen();
        if (!String.valueOf(_eingabe)
            .contains("E") && String.valueOf(_eingabe)
            .contains(".") && !_eingabeFeld.getText()
            .contains(".") && _restbetragAnzeiger.getText()
            .substring(_restbetragAnzeiger.getText()
                .length() - 3)
            .equals(".00"))
        {
            if (_wieVieleNullenAlsNachkommaStellen > 0) //if eingabe iwas,0 ODER iwas,00 ODER iwas,iwas
            {
                return true;
            }
        }

        return false;
    }

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
                bestaetigen();
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

    private void aktualisiereRestbetragAnzeiger()
    {
        _restbetragAnzeiger.setText(Double.parseDouble(_summeAnzeiger.getText())
                - _eingabe + "");
        //        if (_restbetragAnzeiger.getText()
        //            .charAt(_restbetragAnzeiger.getText()
        //                .length() - 1) == '.' )
        //        {
        //            _restbetragAnzeiger.setText(_restbetragAnzeiger.getText() + "0");
        //        }
        if (_restbetragAnzeiger.getText()
            .charAt(_restbetragAnzeiger.getText()
                .length() - 3) != '.')
        {
            if (!_restbetragAnzeiger.getText()
                .contains("E"))
            {
                //this.pack();
                _restbetragAnzeiger.setText(_restbetragAnzeiger.getText() + "0");
                _restbetragAnzeiger.setText(_restbetragAnzeiger.getText()
                    .substring(0, _restbetragAnzeiger.getText()
                        .indexOf('.') + 3));
            }
        }
    }

    private void schliessen()
    {
        this.dispose();
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
