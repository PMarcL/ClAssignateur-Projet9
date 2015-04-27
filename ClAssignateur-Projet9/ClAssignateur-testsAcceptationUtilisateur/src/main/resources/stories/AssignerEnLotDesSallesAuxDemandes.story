Assigner en lot des salles aux demandes

Narrative:
In order to être informé plus rapidement du résultat de ma demande lorsqu'il y a une forte demande
As a organisateur
I want to traiter les demandes plus rapidement en période de haute affluence
					 
Scenario:  Les demandes sont traitées lorsqu'il y en a X d'accumulées
Given une limite de 2 demandes
When la limite de demandes en attente est atteinte
Then l'assignation des demandes en attente est déclenchée

Scenario: La minuterie est réinitialisée lors du traitement des demandes
Given une limite de 2 demandes
When la limite de demandes en attente est atteinte
Then la minuterie est réinitialisée

Scenario: La limite est configurable
Given une limite de 2 demandes
When je configure le système pour tolérer 1 demandes
And j'ajoute une demande en attente
Then le traitement des demandes est lancé