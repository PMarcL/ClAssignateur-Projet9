Ordonner les demandes par priorité

Narrative:
In order to : Afin de pouvoir prioriser les réunions plus importantes
As a : En tant qu'entreprise
I want to : Je veux traiter les demandes selon leur priorité
					 
Scenario: Les demandes sont traitées selon leur priorité
Given une demande à priorité basse en attente
And une demande à priorité haute en attente
When les demandes sont traitées
Then la demande à priorité haute est traitée avant celle à priorité basse

Scenario: Les demandes d'une même priorité sont traitées selon leur ordre d'arrivée
Given deux demandes de même priorité en attente
When les demandes sont traitées
Then les demandes sont traitées selon leur ordre d'arrivée
