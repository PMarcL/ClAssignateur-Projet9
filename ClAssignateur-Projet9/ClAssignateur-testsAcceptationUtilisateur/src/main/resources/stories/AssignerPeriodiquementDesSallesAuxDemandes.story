Assigner périodiquement des salles aux demandes

Narrative:
In order to tenir ma réunion
As a organisateur
I want to obtenir une salle réservée pour ma réunion

Scenario:  La demande est assignée à la première salle disponible (sans autre critère)
Given une salle disponible X
And une demande en attente
When les demandes en attente sont traitées
Then la demande est assignée à la salle X

Scenario:  Les demandes sont traitées séquentiellement
Given une demande X en attente
And une demande Y en attente
When les demandes sont traitées
Then la demande X est traitée avant la demande Y

Scenario:  La fréquence est configurable
Given une fréquence de 3 minutes
When je change la fréquence de traitement
Then les demandes en attente sont traitées aux 3 minutes

Scenario:  Les demandes sont traitées aux X minutes
Given une fréquence quelconque
And une demande en attente
When la fréquence de traitement est atteinte
Then la demande en attente est traitée

Scenario:  Les demandes sont accumulées
Given une nouvelle demande
When la demande est mise en attente
Then la liste de demandes en attente contient la demande