;; Agencia de viajes al fin del mundo y mas alla.
;; El predicado "transporte" define un transporte entre dos ciudades disponibles
;; El predicado "visited" define una ciudad por la que el viaje pasa
;; El predicado "hospedado" define un hotel de una ciudad en el que se ha dormido
;; El predicado "in" significa que se esta en una ciudad

(define (domain basic-viajes)
  (:requirements :strips :typing :fluents)
  (:types lugar transporte - object  
          ciudad hotel - lugar)
  
  (:predicates (in ?ciutat - ciudad) (visited ?ciutat - ciudad) (not-visited ?ciutat - ciudad)
         (starting ?ciutat - ciudad) (transporte ?ciutat1 - ciudad ?ciutat2 - ciudad) 
         (localizado ?ciutat - ciudad ?htl - hotel) (hospedado ?ciutat - ciudad ?htl - hotel))

  (:functions
    (interes ?ciutat - ciudad)
    (min_total_dias_rec)
    (max_days_city)
    (min_days_city)
    (dias_city)
    (interes_total)
  )

  (:action siguiente-ciudad
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat1 ?ciutat2) (localizado ?ciutat2 ?htl)   
                  (>= (+ (dias_city) 1) (min_days_city)))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl) 
            (increase (min_total_dias_rec) 1) (decrease (dias_city) (- (dias_city) 1)) (increase (interes_total) (interes ?ciutat2))))

  (:action siguiente-ciudad-starting
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat1 ?ciutat2) (localizado ?ciutat2 ?htl)   
                  (starting ?ciutat1))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl)
            (increase (min_total_dias_rec) 1) (increase (dias_city) 1) (increase (interes_total) (interes ?ciutat2))))

  (:action al-reves-ciudad-starting
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat2 ?ciutat1) (localizado ?ciutat2 ?htl)   
                  (starting ?ciutat1))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl)
            (increase (min_total_dias_rec) 1) (increase (dias_city) 1) (increase (interes_total) (interes ?ciutat2))))

  (:action misma-ciudad
    :parameters (?ciutat1 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (localizado ?ciutat1 ?htl) (not (starting ?ciutat1)) (< (dias_city) (max_days_city)))
    :effect (and  (increase (min_total_dias_rec) 1) (increase (dias_city) 1)))

  (:action al_reves_siuiente-ciudad
    :parameters (?ciutat1 - ciudad ?ciutat2 - ciudad ?htl - hotel)
    :precondition (and (in ?ciutat1) (not-visited ?ciutat2) (transporte ?ciutat2 ?ciutat1) (localizado ?ciutat2 ?htl)   
                  (>= (+ (dias_city) 1) (min_days_city)))
    :effect (and (not (in ?ciutat1)) (in ?ciutat2) (visited ?ciutat2) (not (not-visited ?ciutat2)) (hospedado ?ciutat2 ?htl)
            (increase (min_total_dias_rec) 1) (decrease (dias_city) (- (dias_city) 1)) (increase (interes_total) (interes ?ciutat2))))
 )
