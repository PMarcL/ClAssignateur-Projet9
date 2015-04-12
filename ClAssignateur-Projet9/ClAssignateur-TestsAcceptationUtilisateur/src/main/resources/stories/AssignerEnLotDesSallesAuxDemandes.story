Assigner en lot des salles aux demandes

Narrative:
In order to être informé plus rapidement du résultat de ma demande lorsqu'il y a une forte demande
As a un organisateur
I want to traiter les demandes plus rapidement en période de haute affluence
					 
Scenario:  La limite de demandes en attente est atteinte
Given j'ai configuré le système pour tolérer X demandes
When le nombre de demandes en attente atteint X demandes
Then l'assignation des demandes en attente est déclenchée