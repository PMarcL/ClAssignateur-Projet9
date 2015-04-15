Assigner en lot des salles aux demandes

Narrative:
In order to être informé plus rapidement du résultat de ma demande lorsqu'il y a une forte demande
As a un organisateur
I want to traiter les demandes plus rapidement en période de haute affluence
					 
Scenario:  Les demandes sont traitées lorsqu'il y en a X d'accumulées
Given j'ai configuré le système pour tolérer X demandes
When le nombre de demandes en attente atteint X demandes
Then l'assignation des demandes en attente est déclenchée

Scenario: Lorsque la limite de X est atteinte et que les demandes sont traitées, la minuterie est réinitialisée
Given j'ai configuré le système pour tolérer X demandes
When le nombre de demandes en attente atteint X demandes
Then l'assignation des demandes en attente est déclenchée
And la minuterie est réinitialisée

Scenario: La limite est configurable
Given j'ai configuré le système pour tolérer X demandes
And il y a présentement Y demandes en attente
When je configure le système pour tolérer Y demandes
Then l'assignation des demandes en attente est déclenchée