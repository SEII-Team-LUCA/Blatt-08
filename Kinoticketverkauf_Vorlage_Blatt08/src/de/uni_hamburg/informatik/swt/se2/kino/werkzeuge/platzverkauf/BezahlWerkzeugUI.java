package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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
    private int _anzahlKommata;
    private int _nachkommastellen;

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
                case ',':
                    e.consume();
                    if (position == 0)
                    {
                    }
                    else if (_anzahlKommata < 1
                            && position + 2 >= _eingabeFeld.getText()
                                .length())
                    {
                        _eingabeFeld.setText(_eingabeFeld.getText()
                            .substring(0, position) + "."
                                + _eingabeFeld.getText()
                                    .substring(position));
                        _anzahlKommata++;
                        //                        refresh();
                        _eingabeFeld.setCaretPosition(position + 1);
                        //                        pruefeGroesse();
                        break;
                    }
                    getToolkit().beep();
                    break;
                case '.':
                    if (position == 0 || _anzahlKommata > 0
                            || position + 2 < _eingabeFeld.getText()
                                .length())
                    {
                        //                        _eingabeFeld.setText(_eingabeFeld.getText().substring(_eingabeFeld.getText().length()-2));
                        getToolkit().beep();
                        e.consume();
                        break;
                    }
                    _anzahlKommata++;
                    //                    refresh();
                    //                    pruefeGroesse();
                    break;
                case KeyEvent.VK_BACK_SPACE:
                case KeyEvent.VK_DELETE:
                    if (wurdeEinKommaGeloescht())
                    {
                        _anzahlKommata--;
                    }
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
                    if (_anzahlKommata == 1)
                    {
                        if (_eingabeFeld.getText()
                            .indexOf('.') + 2 < _eingabeFeld.getText()
                            .length() && position + 3 > _eingabeFeld.getText()
                            .length())
                        {
                            getToolkit().beep();
                            e.consume();
                        }
                    }
                    //                    refresh();
                    //                    pruefeGroesse();
                    break;
                default:
                    getToolkit().beep();
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                //                try
                //                {
                //                    do
                //                    {
                //                        _eingabe = Float.parseFloat(_eingabeFeld.getText());
                //                        aktualisiereRestbetragAnzeiger();
                //                    }
                //                    while (_eingabeFeld.getText()
                //                        .equals("(\\s+\\d+([,\\.]\\d\\d?)?\\s+)"));
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

    private void refresh()
    {
        try
        {
            _eingabe = Double.parseDouble(_eingabeFeld.getText());
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

    /*private void fehler()
     {
         JOptionPane error = new JOptionPane(JOptionPane.OK_OPTION);
         error.showMessageDialog(this, "Bitte nur Zahlen eingeben.", "Warnung",
                 JOptionPane.WARNING_MESSAGE);
         error.setVisible(true);
     }*/

    private boolean wurdeEinKommaGeloescht()
    {
        if (!String.valueOf(_eingabe)
            .contains("E") && String.valueOf(_eingabe)
            .contains(".") && !String.valueOf(_eingabe)
            .substring(String.valueOf(_eingabe)
                .length() - 2)
            .equals(".0"))
        {
            if (!_eingabeFeld.getText()
                .contains("."))
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
