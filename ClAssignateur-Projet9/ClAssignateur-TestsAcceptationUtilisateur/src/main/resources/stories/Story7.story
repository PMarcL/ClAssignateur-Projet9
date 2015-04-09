Notifier par courriel lors d'une annulation

Narrative:
In order to : Afin de ne pas me rendre inutilement à une réunion
As a : En tant que participant
I want to : Je veux recevoir un courriel m'informant de l'annulation de ma réservation

Scenario: Envoyer un courriel informant de l'annulation à tous les participants (si la liste des courriels des participants est disponible)
Given une demande assignée avec une liste des courriels des participants
When cette demande est annulée
Then un courriel est envoyé a tous les participants de la liste

Scenario: Une copie du courriel est envoyée au responsable des réservations
Given un demande assignée avec le responsable X
When cette demande est annulée
Then une copie du courriel envoyé aux participants est envoyé au reponsable X

Scenario: Une copie du courriel est envoyée à l'organisateur pour la réservation
Given un demande assignée avec l'organisateur X
When cette demande est annulée
Then une copie du courriel envoyé aux participants est envoyé à l'organisateur X

Scenario: Le courriel indique le titre de la réservation donnée initialement par l'organisateur
Given un demande assignée initlialement avec le titre X
When cette demande est annulée
Then le courriel envoyé indique le titre X