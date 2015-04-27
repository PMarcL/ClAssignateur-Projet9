## Scénarios BDD pour remise 3
### Récit utilisateur 5
### Notifier par courriel après l'assignation

**Narrative:**   
In order to savoir dans quelle salle ma réunion se déroulera   
As a organisateur   
I want to recevoir un courriel m'informant du résultat de l'assignation   

**Scenario:** En cas de succès (salle assignée) alors, le courriel indique la salle assignée   
Given une demande pouvant se faire assigner une salle   
When salle assignée avec succès   
Then le message contenu dans le courriel envoyé contient la salle assignée   

**Scenario:** En cas d'échec alors, le courriel indique la raison du refus   
Given une demande ne pouvant pas se faire assigner une salle   
When l'assignation échoue   
Then le message contenu dans le courriel envoyé contient la raison du refus  

**Scenario:** Le courriel est envoyé à l'organisateur ayant soumis la demande   
Given une demande avec l'organisateur X   
When l'assignation est effectuée   
Then le courriel est envoyé à l'organisateur X   

**Scenario:** Une copie du courriel est envoyée au responsable des réservations de l'entreprise   
Given une demande avec le responsable X   
When l'assignation est effectuée   
Then le courriel est envoyé au responsable X   

### Récit utilisateur 7
### Notifier par courriel lors d'une annulation

**Narrative:**     
In order to ne pas me rendre inutilement à une réunion     
As a participant     
I want to recevoir un courriel m'informant de l'annulation de ma réservation     

**Scenario:** Envoyer un courriel informant de l'annulation à tous les participants (si la liste des courriels des participants est disponible)   
Given une demande assignée avec une liste des courriels des participants   
When cette demande est annulée   
Then un courriel est envoyé à tous les participants de la liste   

**Scenario:** Une copie du courriel est envoyée au responsable des réservations   
Given une demande assignée avec le responsable X   
When cette demande est annulée   
Then une copie du courriel envoyé aux participants est envoyé au reponsable X   

**Scenario:** Une copie du courriel est envoyée à l'organisateur pour la réservation   
Given une demande assignée avec l'organisateur X   
When cette demande est annulée   
Then une copie du courriel envoyé aux participants est envoyé à l'organisateur X   

**Scenario:** Le courriel indique le titre de la réservation donnée initialement par l'organisateur   
Given une demande assignée initlialement avec le titre X   
When cette demande est annulée    
Then le message contenu dans le courriel envoyé contient le titre X   
