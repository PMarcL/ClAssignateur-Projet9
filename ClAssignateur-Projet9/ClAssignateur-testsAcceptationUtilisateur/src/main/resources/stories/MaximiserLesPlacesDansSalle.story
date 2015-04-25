Maximiser les places dans la salle

Narrative:
In order to maximiser l'utilisation des places dans les salles de réunion
As a entreprise
I want to utiliser la salle la plus petite qui répond aux besoins de la réunion

Scenario:  La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given une salle X avec une capacité de 25 participants
And une salle Y avec une capacité de 15 participants
And une salle Z avec une capacité de 10 participants
And une demande pour 13 participants
When les demandes sont traitées
Then la salle Y est assignée à la demande

Scenario: En cas d'égalité, une des salles est sélectionnée (pas de condition particulière)
Given une salle X avec une capacité de 15 participants
And une salle Y avec une capacité de 15 participants
And une demande pour 13 participants
When les demandes sont traitées
Then la salle X ou la salle Y est assignée à la demande