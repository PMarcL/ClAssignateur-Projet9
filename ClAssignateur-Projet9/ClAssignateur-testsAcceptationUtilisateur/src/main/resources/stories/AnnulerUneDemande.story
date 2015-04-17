Annuler une demande

Narrative:
In order to ne pas monopoliser une salle inutilement
As a organisateur
I want to pouvoir annuler une réservation
					 
Scenario:  Annuler une réservation déjà assignée
Given une demande assignée
When on annule la demande assignée
Then la demande assignée est annulée

Scenario: Annuler une demande en attente de traitement
Given une demande en attente de traitement
When on annule la demande en attente
Then la demande en attente est annulée

Scenario: Archiver une demande annulée
Given une demande en attente de traitement
When on annule la demande en attente
Then la demande est archivée

Scenario: Retirer la salle d'une demande annulée
Given une demande assignée
When on annule la demande assignée
Then la salle assignée à cette demande lui est retirée

Scenario: Le statut d'une demande est modifié pour un statut d'annulation
Given une demande assignée
When on annule la demande assignée
Then le satut de la demande est changer pour un statut d'annulation