# Aeroclub
Sujet rattrapage PA 2A1

Feuille de route :

● une gestion des membres (qui peuvent aussi être des clients de l'aérodrome, cf deuxième 
question, les deux pouvant donc être fusionnés), avec un outil d'exportation des activités 
mensuelles suivies par les membres à destination de la comptabilité --> Java avec export PDF


● un système de planification, tenant compte des contraintes liées à l'utilisation des avions et 
des ULM de l'association --> Android


● une gestion des leçons et des formateurs --> Android



### ANDROID ###

Fonctionnalités : 

- Incription via Firebase Authentification
  

- A la création du compte, création d'un noeud dans Firebase Realtime Database qui a l'UID de l'adresse mail crée
  

- Interface avec 4 fenêtres majeures :

  
        - Fenêtre données utilisateur : upload image (ID), date de naissance, mail secondaire


        - Services basiques : Seulement Stationnement et Ravitaillement effectués pour l'instant avec ListView et choix enregistrés dans la bdd Firebase

  
        - Aéroclub : Leçons de pilotage, ULM, Parachutisme et Baptème de l'air principalement via un calendrier qui check dans la bdd (dans une table
          DateReservee et les noeuds Robin et Piper) si les avions sont déjà réservés à certaines dates


        - Checklist : Récapitulatif de tous les choix effectués dans l'application avec extraction des données dans la bdd


  PROBLEMES EN COURS :

  ● Lorsqu'une date est sauvegardée, elle ne peut plus être supprimée : si un utilisateur veut changer une date qu'il a réservé, cela marchera pour lui mais l'ancienne sera toujours conservée
  dans la table DataReserve --> Pour l'instant cela est indiqué dans l'UI


  ● Lorsqu'un utilisateur veut choisir une seconde date avec un type d'avion auquel il a déjà réservé une date, celle ci sera écrasée : un avion = une date


  ● Lorsqu'un utilisateur se reconnecte, cela supprime toute sa bdd et la remet à 0 (facilement réparable mais à faire)



  
