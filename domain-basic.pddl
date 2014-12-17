;; Agencia de viajes al fin del mundo y mas alla.
;; El predicado "transporte" define un transporte entre dos ciudades disponibles
;; El predicado "visited" define una ciudad por la que el viaje pasa
;; El predicado "hospedado" define un hotel de una ciudad en el que se ha dormido
;; El predicado "in" significa que se esta en una ciudad

(define (domain basic-viajes)
  (:requirements :adl :typing)
  (:types lugar transporte - object  
          ciudad hotel - lugar)
  (:predicates (in ?ciutat - ciudad) (visited ?ciutat - ciudad) (not-visited ?ciutat - ciudad)
         (starting ?ciutat - ciudad) (transporte ?ciutat1 - ciudad ?ciutat2 - ciudad) 
         (localizado ?ciutat - ciudad ?htl - hotel) (hospedado ?ciutat - ciudad ?htl - hotel))

  (:action siguiente-ciudad
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat1 ?ciutat2) (localizado ?ciutat2 ?htl))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl) 
            (increase (total_ciudades) 1)))

  (:action al_reves_siuiente-ciudad
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat2 ?ciutat1) (localizado ?ciutat2 ?htl))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl)
            (increase (total_ciudades) 1)))

 )
