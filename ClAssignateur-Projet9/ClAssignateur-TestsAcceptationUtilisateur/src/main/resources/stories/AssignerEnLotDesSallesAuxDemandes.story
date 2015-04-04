Assigner en lot des salles aux demandes

Narrative:
In order to être informé plus rapidement du résultat de ma demande lorsqu'il y a une forte demande
As a un organisateur
I want to traiter les demandes plus rapidement en période de haute affluence
					 
Scenario:  La limite de demandes en attente est atteinte
Given un lot de X demandes
And une instance du service de reservation n'ayant aucune demande en attente
When j'envoie toutes les demandes du lot
Then l'assignation des demandes en attente est declenchée