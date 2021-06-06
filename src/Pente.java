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

                System.out.println(fenetrePrincipale.getInsets());

                // Conversion du clique de souris en coordonnées de la grille
                int xGrid =
                        scenePrincipale.getGrille().getMatrice().size() -
                                Math.floorDiv(scenePrincipale.getGrille().getLargeur() - mouseEvent.getX(), scenePrincipale.getGrille().getLargeurCases());

                int yGrid = Math.floorDiv(
                        scenePrincipale.getGrille().getHauteur() - mouseEvent.getY() + fenetrePrincipale.getInsets().top,
                        scenePrincipale.getGrille().getHauteurCases());

                System.out.println(xGrid - 1 + ", " + yGrid);

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

                                Jeu.getInstance().getJoueurActuel().ajouterPion(pion);

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
                                    String.format("Nombre de caputre : %d",
                                            Jeu.getInstance().getJoueurActuel().getNbrCapture()
                                            )
                                            );

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
                parametresPartieFrame.getContentPane().validate();
                parametresPartieFrame.getContentPane().repaint();

            }
        });


        parametresPartieFrame.getContentPane().add(parametresPartiePanel);
        parametresPartieFrame.pack();
        parametresPartieFrame.setVisible(true);
        while(!clavier.getEchapTape()) {
            try {
                Thread.sleep(200);
            } catch(Exception ex){

                System.exit(-1);

            }
            fenetrePrincipale.rafraichir();
        }
        fenetrePrincipale.fermer();


    }
}

