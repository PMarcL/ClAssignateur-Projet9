Assigner périodiquement des salles aux demandes

Narrative:
In order to tenir ma réunion
As a organisateur
I want to obtenir une salle réservée pour ma réunion

Scenario:  La demande est assignée à la première salle disponible (sans autre critère)
Given une liste de salle dont la première salle disponible est X
And une nouvelle demande
When les demandes sont toutes traitées
Then la demande est assignée à la salle X

Scenario:  Les demandes sont traitées séquentiellement
Given plusieurs demandes de même priorité en attente de traitement
When les demandes sont traitées
Then les demandes sont traitées selon leur ordre d'arrivée

Scenario:  La fréquence est configurable
Given une fréquence par défaut
When la fréquence est modifiée
Then la fréquence est changée

Scenario:  Les demandes sont traitées aux X minutes
Given une fréquence de traitement X
When la fréquence est atteinte
Then le traitement des demandes est lancé

Scenario:  Les demandes sont accumulées
Given une nouvelle demande
When la demande est ajoutée
Then la liste de demandes en attente contient la demande