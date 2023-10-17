# Tamagotchi
Tamagotchi project

Voici le github pour le projet de Kessler avec son Tamagotchi

Pour modifier le projet avec Intellij, il faut ouvrir le fichier build.gradle en faisant : 
File -> open -> sélectionner le fichier -> Open As project

!!! Installer le plugin libGDX dans intellij en en allant dans settings -> plugins !!!

Comment gérer les temps de travail, dodo, manger ? :
Au lieu de faire attendre le tamagotchi avec TimeUnit.seconds.sleep, on ordonne à l'affichage de retirer le tamagotchi ainsi que les boutons d'actions
On affiche une barre de progression avec le temps restant ainsi que le nom de l'action en cours
On donne a une fonction de la view comme argument la/les tables à retirer, l'action en cours qui est effectué et le temps d'attente 
A coté, le moteur de jeu tourne toujours et fait bouger les barres de progression de base
Une fois le temps d'attente finis, la méthode remet tout en place

lien important :
https://lipn.univ-paris13.fr/~gerard/uml-s2/uml-cours05.html
