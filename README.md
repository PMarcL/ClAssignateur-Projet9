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

Statut des user stories:

* User story 1: terminée
* User story 2: terminée
* User story 3: terminée
* User story 4: terminée
* User story 5: terminée
* User story 6: terminée
* User story 7: terminée
* User story 8: terminée
* User story 9: terminée
* User story 10: terminée
* User story 11: implémentation de la classe HibernateDemandeEntrepot incomplète.

Test d'acceptation:
* Les tests d'acceptation sont dans le module testsAcceptationUtilisateur.
* Tous les tests d'acceptation demandés ont été fait.
 
Test de performance et de concurrence:
* Les tests de performances ont été fait dans jMeter. Le fichier .jmx que nous avons utilisé est dans le dossier ClAssignateur-Projet9/doc.
* Les résultats de ces tests nous démontrent que notre système peut supporter un grand nombre de requêtes concurentes (20 threads qui font 2 requêtes par seconde) avec un taux d'erreur négligeable après la première requête.
* En expérimentant, nous avons découvert que le taux d'erreurs est initialement élevé (+-15%) lors des premières vagues de requêtes, mais il descend rapidement vers un taux d'erreur presque nul après quelques secondes.
* Pour arriver à ce résultat, nous avons dû synchroniser l'accès aux demandes (la classe ConteneurDemandes) avec le mot clé synchronized de java.
* Ces tests nous démontrent aussi que notre système est capable de supporter 40 requêtes par séconde avec un temps de réponse < 100 ms.

Test de flot:
* Les tests de flot sont dans le module ClAssignateur-TestDeFlot.
* Puisque l'annulation n'était pas dans l'interface REST, nous avons fait ces tests à travers nos services dans un fichier de test qui utilise jUnit avec le contexte de développement.
* Nous avons testé les deux scénarios suivants:
  1. Faire une réservation, vérifier le statut et la salle pendant que la réservation est en attente, annuler la réservation.
  2. Faire une réservation, vérifier le statut et la salle après le traitement de la réservation, annuler la réservation.

Test de la base de données:
* Nous avons plusieurs tests pour tester l'implémentation de Hibernate, mais nous n'avons pas réussie à la compléter et à la faire fonctionner.
* Donc, nous n'utilisons pas cette implémentation dans nos contextes de développement et de production.

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
