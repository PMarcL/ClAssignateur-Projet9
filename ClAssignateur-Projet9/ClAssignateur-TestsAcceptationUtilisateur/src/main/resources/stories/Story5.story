Notifier par courriel après l'assignation

Narrative:
In order to : Afin de savoir dans quelle salle ma réunion se déroulera
As a : En tant qu'organisateur
I want to : Je veux recevoir un courriel m'informant du résultat de l'assignation

Scenario: En cas de succès (salle assignée) alors, le courriel indique la salle assignée
Given une demande pouvant se faire assigné une salle
When la salle est assignée avec succès
Then le courriel envoyé indique la salle assigné

Scenario: En cas d'échec alors, le courriel indique la raison du refus
Given une demande ne pouvant pas se faire assigné une salle
When assignation échoue
Then le courriel envoyé indique la raison du refus

Scenario: Le courriel est envoyé à l'organisateur ayant soumis la demande
Given une demande avec l'organisateur X
When assignation effectuée
Then le courriel est envoyé à l'organisateur X

Scenario: Une copie du courriel est envoyée au responsable des réservations de l'entreprise
Given une demande avec le responsable X
When assignation effectuée
Then le courriel est envoyé au responsable X
