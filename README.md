# ClAssignateur-Projet9
Projet fait dans le cadre du cours GLO-4002,
Équipe 9

Fait par:
* Michaël Blanchet (111 078 159)
* Pierre-Marc Levasseur ( 111 080 897 )
* Simon B. Robert
* Pascal Labbé
* Sébastion Reader
* Pier-Alex Côté-Sarrazin
* Margot Beugniot

Statut des scénarios:

* Scénario 1: terminé
* Scénario 2: terminé
* Scénario 3: terminé
* Scénario 4: terminé

Utilisation de la bibliothèque:
 1. Il faut tout d'abord créer un EntrepotSalles pour y ajouter les salles à réserver.
 2. Il faut ensuite créer une FileDemande qui servira à entreposer les demandes avant qu'elles soient traitées et un DeclencheurAssignationSalle avec une fréquence en minutes et une limite pour définir une quantité de demande à recevoir avant de procéder à la réservation.
 3. On peut alors créer un objet de la classe ServiceReservationSalle avec les objet créés précédement et avec une objet qui implémente l'interface Executor. Celui-ci servira à démarrer le traitement lors de la création du Service.
 4. On peut finalement ajouter des demandes avec l'objet ServiceReservationSalle et elle seront traitées périodiquement selon la fréquence et la limite du déclencheur. Cette fréquence ainsi que la limite sont configurable selon les besoins de l'utilisateur via la classe ServiceReservationSalle.

Définition de terminé

 1. Le code est uniforme; il respecte le format défini dans la convention de code ci-dessous.
 2. Le code respecte le format du formateur choisi ensemble
 3. La grande majorité du code (environ 90% de code coverage) a été testé.
 4. L’ensemble des tests roule avec succès avec « maven integration test » en ligne de commande.
 5. Le code ne contient pas de « warning ».
 6. Le code ne contient pas de code inutilisé ou code mort.
 7. Tout ce qui doit être commenté dans le code l’est.
 8. Tout le code a été « poussé » dans le répertoire git.
 9. Une revue de code a été faite par quelqu’un d’autre que son auteur.
10. Les fonctionnalités sont complètement intégrées au système
11. Le déploiement a été un succès.


Convention de code

1. Le code est réalisé en français à l’exception de get et set.
2. Les noms de classes commencent par une majuscule. ex: FileDemande
3. Les noms de méthodes et de variables commencent par une minuscule. ex: uneVariable
4. Les noms de méthodes commencent par un verbe conjugué. ex: estEnConflit()
4bis.Une exception peut être faite pour les tests.
5. Les noms de variable sont des noms communs ou des groupes nominaux. ex: demandeEnTraitement ou demandeTraitée

