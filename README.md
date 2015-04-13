# ClAssignateur-Projet9
Projet fait dans le cadre du cours GLO-4002,
Équipe 9

Fait par:
* Michaël Blanchet (111 078 159)
* Pierre-Marc Levasseur (111 080 897)
* Simon B. Robert (111 068 713)
* Pascal Labbé (111 087 177)
* Sébastien Reader (999 165 021)
* Pier-Alex Côté-Sarrazin (111 045 255)
* Margot Beugniot (111 110 352)

Statut des scénarios:

* Scénario 1: terminé
* Scénario 2: terminé
* Scénario 3: terminé
* Scénario 4: terminé
* Scénario 5: Partiellement complété (implémentation de la stratégie de notification par courriel à terminer)
* Scénario 6: terminé
* Scénario 7: Partiellement complété (implémentation de la stratégie de notification par courriel à terminer)
* Scénario 8-9-10: à faire

Utilisation de la bibliothèque:
 1. Il faut tout d'abord créer une SallesEntrepot pour y ajouter les salles disponible pour la réservation et une SalleSelectionStrategie pour choisir comment les salles seront sélectionnées.
 2. Il faut ensuite créer un ConteneurDemandes pour les demandes en attente et un DemandesEntrepotSansDoublon pour les demandes archivées.
 3. Il faut créer un notificateur avec une stratégie de notification pour l'envoie des courriels lors du traitement des demandes.
 4. Il faut ensuite créer un AssignateurSalle qui fera le traitement des demandes.
 5. Il faut enfin créer un ServiceReservationSalle avec l'assignateur et un Timer qui servira de minuterie.
 6. Il sera finalement possible d'ajouter des demandes et elles seront traitées selon la fréquence de la minuterie ou la quantité de demandes.

Définition de terminé

 1. Le code est uniforme; il respecte le format défini dans la convention de code ci-dessous.
 2. Le code respecte le format du formateur choisi ensemble
 3. La grande majorité du code (au minimum 90% de code coverage) a été testé.
 4. L’ensemble des tests roule avec succès avec « maven integration test » en ligne de commande.
 5. Le code ne contient pas de « warning ».
 6. Le code ne contient pas de code inutilisé ou code mort.
 7. Le code ne contient pas de commentaires inutiles.
 8. Tout le code a été « poussé » dans le répertoire git.
 9. Une revue de code a été faite par quelqu’un d’autre que son auteur.
10. Les fonctionnalités sont complètement intégrées au système.
11. Le déploiement a été un succès.


Convention de code

1. Le code est réalisé en français à l’exception de get et set.
2. Les noms de classes commencent par une majuscule. ex: FileDemande
3. Les noms de méthodes et de variables commencent par une minuscule. ex: uneVariable
4. Les noms de méthodes commencent par un verbe conjugué. ex: estEnConflit()
4bis.Une exception peut être faite pour les tests.
5. Les noms de variable sont des noms communs ou des groupes nominaux. ex: demandeEnTraitement ou demandeTraitée

Scénarios BDD pour remise 3  
Les scénarios BDD des tests qui n'avait pas à être automatisés sont dans le wiki du projet. Tous les scénarios devant être automatisés sont dans des fichiers "*.story" dans le module ClAssignateur-TestsAcceptationUtilisateur du projet.
