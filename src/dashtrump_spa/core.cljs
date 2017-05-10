(ns dashtrump_spa.core
  (:require 
    [ajax.core :refer [GET]]
    [reagent.core :as reagent]
    ))

(enable-console-print!)

(println "This text is printed from src/dashtrump_spa/core.cljs. Go ahead and edit it and see reloading in action.")

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

;; define your app data so that it doesn't get over-written on reload

(def api-url "http://dashtrump.herokuapp.com/rating")

(defonce app-state
  (reagent/atom {:rating "not set"
                 :fetched-state "NOT FETCHED"
                 }))

(defn set-getting-rating []
  "set state to fetching"
  (swap! app-state assoc :fetched-state "FETCHING"))

(defn set-got-rating [rating]
  "update rating"
  (reset! app-state {:rating rating :fetched-state "FETCHED"}))

;; (defn page-load)

(defn get-rating []
  "get approval page, return HTML sting"
  ;; (println "calling")
  (set-getting-rating)
  (GET api-url {:handler
                #(set-got-rating (get % "rating"))
                }))

(defn message []
  [:div
   [:h1 "rating is " (:rating @app-state)]
   [:p "app state is " (:fetched-state @app-state)]
   [:button {:onClick #(get-rating)} "get rating"]
   ])


(reagent/render [message] (js/document.querySelector "#app"))
(get-rating) 
