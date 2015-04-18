Assigner périodiquement des salles aux demandes

Narrative:
In order to tenir ma réunion
As a organisateur
I want to obtenir une salle réservée pour ma réunion

Scenario:  La demande est assignée à la première salle disponible (sans autres critères)
Given une salle
And une demande
When la demande est traité
Then la demande est assignée à la première salle disponible

Scenario:  Les demandes sont traitées séquentiellement
Given une salle
And plusieurs demandes en attente de traitement
When les demandes sont tous traitées
Then les demandes ont été traitées dans leur l'ordre d'arrivée

Scenario:  La fréquence est configurable
Given une frécence par défault
When la fréquence est modifiée
Then la fréquence est changée

Scenario:  Les demandes sont accumulées et traitées aux X minutes
Given des demandes sont ajoutées séquentiellement
When la frécence est atteinte
Then les nouvelles demandes sont traitées