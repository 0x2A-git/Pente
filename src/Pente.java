import MG2D.*;
import MG2D.geometrie.Texte;
import math.Vecteur2;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Pente {

    public static void main(String[] args){


        Fenetre fenetrePrincipale = new Fenetre("Test", 800, 600);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Jeu");

        // Historique de la partie
        JMenuItem historique = new JMenuItem("Historique");


        historique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Historique ouvert");
                JDialog historiqueFrame = new JDialog(fenetrePrincipale, "Historique", true);


                JPanel logsPanel = new JPanel();

                logsPanel.setLayout(new BoxLayout(logsPanel, BoxLayout.Y_AXIS));

                DefaultListModel<String> logs = new DefaultListModel<>();

                logs.addAll(Jeu.getInstance().getLogs());

                JList<String> listeLogs = new JList<String>(logs);


                logsPanel.add(new JScrollPane(listeLogs) );

                JButton btnFermer = new JButton("Fermer");

                btnFermer.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        historiqueFrame.dispose();
                    }
                });

                logsPanel.add(btnFermer);

                historiqueFrame.add(logsPanel);


                historiqueFrame.pack();
                historiqueFrame.getContentPane().validate();
                historiqueFrame.getContentPane().repaint();
                historiqueFrame.setVisible(true);
            }
        });

        menu.add(historique);

        // Coup

        JMenuItem annulerCoup = new JMenuItem("Annuler dernier coup");


        annulerCoup.setEnabled(false);
        menu.add(annulerCoup);
        menuBar.add(menu);
        fenetrePrincipale.setJMenuBar(menuBar);

        Jeu jeu = Jeu.getInstance();


        Scene scenePrincipale = new Scene(
                fenetrePrincipale,
                new Grille(
                        fenetrePrincipale.getP().getWidth(),
                        fenetrePrincipale.getP().getHeight(),
                        8,
                        8
                ));



        jeu.init(fenetrePrincipale, scenePrincipale);


        // Debug seulement
        scenePrincipale.getGrille().dessinerDebug().forEach(d -> fenetrePrincipale.ajouter(d));

        scenePrincipale.getGrille().getMatrice().get(1).get(1).etendreColonne(5);
        scenePrincipale.getGrille().getMatrice().get(1).get(1).etendreLigne(5);

        // La position est passée par référence, ainsi lorsque la case de départ bouge la grille bouge avec


        // Ajout de l'acteur plateau
        Plateau plateau = new Plateau();

        scenePrincipale.ajouter(
                new Vecteur2<>(1,1),
                plateau
        );
        plateau.getGrille().setCouleurLignes(Couleur.NOIR, Couleur.NOIR);


        Clavier clavier = fenetrePrincipale.getClavier();
        fenetrePrincipale.addKeyListener(clavier);


        scenePrincipale.getGrille().addOnCaseCliqueeCallback(new Grille.OnCaseCliqueeListener() {
            @Override
            public void onCliquee(Case caseCliquee) {
            }
        });

        // Attache un callback lorsque clique gauche sur la fenêtre ( via awt )
        fenetrePrincipale.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {


                // Conversion du clique de souris en coordonnées de la grille
                int xGrid =
                        scenePrincipale.getGrille().getMatrice().size() -
                                Math.floorDiv(scenePrincipale.getGrille().getLargeur() - mouseEvent.getX(), scenePrincipale.getGrille().getLargeurCases());

                int yGrid = Math.floorDiv(
                        scenePrincipale.getGrille().getHauteur() - mouseEvent.getY() + fenetrePrincipale.getInsets().top,
                        scenePrincipale.getGrille().getHauteurCases());


                // Trigger l'event case cliquee
                scenePrincipale.getGrille().onCaseCliquee(new Vecteur2<>(xGrid - 1, yGrid), mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        // Dialogue des paramètres de la partie
        JDialog parametresPartieFrame = new JDialog(fenetrePrincipale, "Paramètres de la partie", true);

        parametresPartieFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {

            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {

                fenetrePrincipale.dispatchEvent(new WindowEvent(fenetrePrincipale, WindowEvent.WINDOW_CLOSING));
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });

        JPanel parametresPartiePanel = new JPanel();

        JLabel nbJoueursLabel = new JLabel("Nombre de joueurs");

        parametresPartiePanel.add(nbJoueursLabel);

        SpinnerNumberModel spinnerValue = new SpinnerNumberModel(
                2, //initial value
                2, //min
                8, //max
                1 //step
        );

        JSpinner nbJoueursSpinner =
                new JSpinner(
                        spinnerValue
                );

        parametresPartiePanel.add(nbJoueursSpinner);

        JButton demarrerBouton = new JButton("Démarrer");
        parametresPartiePanel.add(demarrerBouton);

        parametresPartiePanel.setLayout(new BoxLayout(parametresPartiePanel,BoxLayout.Y_AXIS));


        demarrerBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                parametresPartieFrame.setTitle("Ajouter les joueurs");
                JPanel creationJoueurPanel = new JPanel();

                // Menu des couleurs
                HashMap<String, MG2D.Couleur> couleursDisponibles = new HashMap<>(Map.ofEntries(
                        Map.entry(" ", Couleur.NOIR),
                        Map.entry("Jaune", Couleur.JAUNE),
                        Map.entry("Rouge", Couleur.ROUGE),
                        Map.entry("Bleu", Couleur.BLEU),
                        Map.entry("Vert", Couleur.VERT),
                        Map.entry("Magenta", Couleur.MAGENTA),
                        Map.entry("Cyan", Couleur.CYAN),
                        Map.entry("Orange", Couleur.ORANGE),
                        Map.entry("Gris", Couleur.GRIS_FONCE)
                ));

                creationJoueurPanel.setLayout(new GridBagLayout());

                GridBagConstraints contrainteGrille = new GridBagConstraints();


                ArrayList<Joueur> joueurs = new ArrayList<>();



                // Stock les références fortes des JComboBox de chaque joueur dans un tableau
                ArrayList<JComboBox<String>> referencesChoixCouleur = new ArrayList<>();


                // Bouton démarrer
                JButton validerBtn = new JButton("Valider");

                validerBtn.setEnabled(false);

                // Pour chaque joueur...

                for(int i = 0; i < spinnerValue.getNumber().intValue(); i++){

                    JPanel joueurPanel = new JPanel();
                    joueurPanel.setLayout(new BoxLayout(joueurPanel, BoxLayout.Y_AXIS));
                    // Joueur

                    Joueur joueur = new Joueur(); // Cette référence va nous servir dans les callbacks

                    joueurPanel.add(new JLabel("Joueur " + (i + 1)));

                    joueurPanel.add(new JLabel("Choisir couleur : "));

                    // Sélection couleur

                    JComboBox<String> selectionCouleursComboBox = new JComboBox<>(couleursDisponibles.keySet().toArray(new String[0]));

                    // Ajoute la référence forte au tableau
                    referencesChoixCouleur.add(selectionCouleursComboBox);
                    
                    selectionCouleursComboBox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent item) {                            
                           
                            // Switch en fonction de l'event de l'item
                            switch(item.getStateChange()){
                                case ItemEvent.SELECTED:

                                    joueur.setCouleur( couleursDisponibles.get( String.valueOf( item.getItem() )) );
                                    selectionCouleursComboBox.setSelectedItem(selectionCouleursComboBox.getSelectedItem());
                                    
                                    // Empêche de selectionner une couleur déjà prise
                                    selectionCouleursComboBox.setEnabled(false);
                                    referencesChoixCouleur.remove(selectionCouleursComboBox);
                                    referencesChoixCouleur.forEach(comboBox ->
                                        comboBox.removeItemAt( selectionCouleursComboBox.getSelectedIndex() )
                                    );

                                    // Si plus de références dans le tableau alors toutes les couleurs sont sélectionnées 
                                    if(referencesChoixCouleur.size() == 0)
                                        validerBtn.setEnabled(true);

                                    break;

                                default:
                                    break;
                            }

                        }
                    });

                    joueurPanel.add(selectionCouleursComboBox);

                    // Nom
                    joueurPanel.add(new JLabel("Nom"));

                    JTextField nomJoueur = new JTextField();

                    nomJoueur.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent documentEvent) {
                            joueur.setNom(nomJoueur.getText());
                            System.out.println("Nouveau nom :" + joueur.getNom());
                        }

                        @Override
                        public void removeUpdate(DocumentEvent documentEvent) {

                        }

                        @Override
                        public void changedUpdate(DocumentEvent documentEvent) {

                        }
                    });
                    joueurPanel.add(nomJoueur);

                    // Prénom
                    joueurPanel.add(new JLabel("Prénom"));


                    JTextField prenomJoueur = new JTextField();

                    prenomJoueur.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent documentEvent) {
                            joueur.setPrenom(prenomJoueur.getText());
                            System.out.println("Nouveau prénom : " + prenomJoueur.getText());
                        }

                        @Override
                        public void removeUpdate(DocumentEvent documentEvent) {

                        }

                        @Override
                        public void changedUpdate(DocumentEvent documentEvent) {

                        }
                    });

                    // Ajout du joueur au tableau
                    joueurs.add(joueur);


                    // On affiche les panels des différents joueurs
                    joueurPanel.add(prenomJoueur);

                    contrainteGrille.fill = GridBagConstraints.HORIZONTAL;
                    contrainteGrille.gridx = i;
                    contrainteGrille.gridy = 0;

                    creationJoueurPanel.add(joueurPanel, contrainteGrille);
                }


                // Zone de texte concernant l'avancé de la partie

                ZoneTexte joueursZoneTexte = new ZoneTexte();

                Texte joueurActuel = new Texte();
                Texte joueurSuivant = new Texte();

                joueursZoneTexte.ajouterTexte(joueurActuel);
                joueursZoneTexte.ajouterTexte(joueurSuivant);

                scenePrincipale.ajouter(new Vecteur2<>(1, 0), joueursZoneTexte, Scene.Placement.MILIEU);

                validerBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        validerBtn.setEnabled(false);

                        Collections.shuffle(joueurs, new Random());

                        Jeu.getInstance().getJoueursQueue().addAll(joueurs);

                        
                        joueurActuel.setTexte("");
                        joueurSuivant.setTexte(String.format("Joueur qui démarre : %s %s",
                        Jeu.getInstance().getJoueursQueue().peek().getNom(),
                        Jeu.getInstance().getJoueursQueue().peek().getPrenom()
                        ));



                        parametresPartieFrame.dispose();

                        Jeu.getInstance().setJoueurActuel(Jeu.getInstance().getJoueursQueue().poll());

                        // Zone de texte spécifique au joueur actuel

                        ZoneTexte joueurZoneTexte = new ZoneTexte();


                        Texte pionsJoueurActuel = new Texte();
                        Texte nombreCapture = new Texte();

                        pionsJoueurActuel.setTexte("");
                        nombreCapture.setTexte("");

                        joueurZoneTexte.ajouterTexte(pionsJoueurActuel);
                        joueurZoneTexte.ajouterTexte(nombreCapture);

                        scenePrincipale.ajouter(new Vecteur2<>(3, 0), joueurZoneTexte, Scene.Placement.MILIEU);

                        plateau.ajouterOnPionPlaceListerner(new Plateau.OnPionPlaceListener() {
                            @Override
                            public void onPionPlaceListener(Case casePlacement, Pion pion) {

                                Jeu.getInstance().getDerniersPionsSupprimes().clear();
                                annulerCoup.setEnabled(true);

                                plateau.capturer(casePlacement);

                                plateau.capturer(casePlacement, pion, fenetrePrincipale);



                                if(plateau.getPartieEstGagnante(casePlacement)){
                                    System.out.println("Partie gagnante");
                                }

                                Jeu.getInstance().getJoueurActuel().ajouterPion(pion);
                                Jeu.getInstance().setDernierPionPlace(pion);

                                // Enlève callback précédent si existant
                                if(annulerCoup.getActionListeners().length > 0 )
                                    annulerCoup.removeActionListener(annulerCoup.getActionListeners()[0]);

                                // Ajoute un callback pour supprimer le pion du plateau
                                annulerCoup.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent actionEvent) {

                                        annulerCoup.setEnabled(false);

                                        //plateau.getGrille().supprimer(casePlacement.getPosition(), pion);

                                        // Pion joué à supprimer
                                        plateau.getGrille().supprimer(casePlacement.getPosition(), Jeu.getInstance().getDernierPionPlace());

                                        Jeu.getInstance().getJoueursQueue().peek().getPions().remove(pion);

                                        fenetrePrincipale.supprimer(pion.getDessins().get(0));
                                        // Pions à rajouter avant la capture
                                        if(Jeu.getInstance().getDerniersPionsSupprimes().size() > 0) {
                                            Jeu.getInstance().getJoueursQueue().peek().setNbrCapture(
                                                    Jeu.getInstance().getJoueursQueue().peek().getNbrCapture() -
                                                            (int) (Jeu.getInstance().getDerniersPionsSupprimes().keySet().size() / 2));


                                            Jeu.getInstance().getDerniersPionsSupprimes().keySet().forEach(p -> Jeu.getInstance().getJoueurActuel().ajouterPion(p));
                                        }
                                        Jeu.getInstance().getDerniersPionsSupprimes().keySet().forEach(p -> {
                                            plateau.getGrille().ajouter(Jeu.getInstance().getDerniersPionsSupprimes().get(p), p);
                                            fenetrePrincipale.ajouter(p.getDessins().get(0));
                                        });



                                        //fenetrePrincipale.supprimer(pion.getDessins().get(0));


                                        joueurActuel.setTexte(String.format("Prochain Joueur : %s %s",
                                                Jeu.getInstance().getJoueurActuel().getNom(),
                                                Jeu.getInstance().getJoueurActuel().getPrenom()
                                                )
                                        );

                                        // Màj de l'interface



                                        joueurSuivant.setTexte(String.format("Joueur Actuel : %s %s",
                                                Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                                Jeu.getInstance().getJoueursQueue().peek().getPrenom()
                                                )
                                        );

                                        Jeu.getInstance().getJoueursQueue().add(Jeu.getInstance().getJoueurActuel());

                                        Jeu.getInstance().setJoueurActuel(Jeu.getInstance().getJoueursQueue().poll());



                                        pionsJoueurActuel.setTexte(
                                                String.format("Pions du joueur actuel : %d",
                                                        Jeu.getInstance().getJoueurActuel().getNbrPions()
                                                )
                                        );

                                        nombreCapture.setTexte(
                                                String.format("Nombre de captures : %d",
                                                        Jeu.getInstance().getJoueurActuel().getNbrCapture()
                                                )
                                        );
                                        plateau.dessiner();
                                        fenetrePrincipale.rafraichir();

                                    }
                                });

                                Jeu.getInstance().ajouterLog(
                                        String.format(
                                                "Le joueur %s %s a placé un pion en %d, %d",
                                                Jeu.getInstance().getJoueurActuel().getNom(),
                                                Jeu.getInstance().getJoueurActuel().getPrenom(),
                                                casePlacement.getPosition().getX(),
                                                casePlacement.getPosition().getY()
                                                )
                                );

                                // Màj de l'interface

                                joueurActuel.setTexte(String.format("Prochain Joueur : %s %s",
                                        Jeu.getInstance().getJoueurActuel().getNom(),
                                        Jeu.getInstance().getJoueurActuel().getPrenom()
                                        )
                                        );

                                joueurSuivant.setTexte(String.format("Joueur Actuel : %s %s",
                                        Jeu.getInstance().getJoueursQueue().peek().getNom(),
                                        Jeu.getInstance().getJoueursQueue().peek().getPrenom()
                                        )
                                        );

                                pionsJoueurActuel.setTexte(
                                        String.format("Pions du joueur actuel : %d",
                                                Jeu.getInstance().getJoueurActuel().getNbrPions()
                                                )
                                                );

                                nombreCapture.setTexte(
                                    String.format("Nombre de captures : %d",
                                            Jeu.getInstance().getJoueurActuel().getNbrCapture()
                                            )
                                            );

                                fenetrePrincipale.rafraichir();

                            }
                        });



                    }
                });

                contrainteGrille.fill = GridBagConstraints.HORIZONTAL;
                contrainteGrille.gridx = 0;
                contrainteGrille.gridy = 1;
                contrainteGrille.gridwidth = spinnerValue.getNumber().intValue();
                creationJoueurPanel.add(validerBtn, contrainteGrille);

                parametresPartieFrame.getContentPane().removeAll();

                parametresPartieFrame.getContentPane().add(creationJoueurPanel);


                parametresPartieFrame.pack();

            }
        });


        fenetrePrincipale.rafraichir();
        fenetrePrincipale.repaint();
        fenetrePrincipale.revalidate();
        parametresPartieFrame.getContentPane().add(parametresPartiePanel);
        parametresPartieFrame.pack();
        parametresPartieFrame.setVisible(true);




    }
}

