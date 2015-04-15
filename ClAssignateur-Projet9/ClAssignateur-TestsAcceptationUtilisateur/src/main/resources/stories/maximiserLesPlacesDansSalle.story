Maximiser les places dans la salle

Narrative:
In order to maximiser l'utilisation des places dans les salles de réunion
As a entreprise
I want to utiliser la salle la plus petite qui répond aux besoins de la réunion

Scenario:  La salle disponible avec le moins de places, mais qui en a suffisamment pour la réunion, est assignée
Given plusieurs salle disponible
When assigner salle
Then la salle assigne est celle avec le minimum de place pour la reunion

Scenario: En cas d'égalité, une des salles est sélectionnée (pas de condition particulière)
Given deux salles optimales avec meme nombre de place
When assigner salle
Then une des deux salle est assignee