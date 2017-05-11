(ns dashtrump_spa.core
  (:require
    [ajax.core :refer [GET]]
    [reagent.core :as reagent]
    [cljs.core.async :refer [<! timeout]]
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


; state changers
(defn reset-state []
  "set to initial state"
  (reset! app-state {
                     :rating "not set"
                     :message "Asking America..."
                     :fetched-state "NOT FETCHED"}))


(defn set-getting-rating []
  "set state to fetching"
  (reset! app-state {:fetched-state "FETCHING" :message "Asking America..."}))


(defn set-got-rating [rating]
  "update rating"
  (reset! app-state {:rating rating
                      :fetched-state "FETCHED"
                      :message (str "Yup. Trump's current Approval Rating is " rating "%")}))


(defn get-rating []
  "get approval page, return HTML sting"
  ;; (println "calling")
  (set-getting-rating)
  (GET api-url {:handler
                #(set-got-rating (get % "rating"))
                }))


(defn message []
  (println @app-state)
     [:h2 {:class "answer"} (:message @app-state)]
   )


(reset-state) 
(reagent/render [message] (js/document.querySelector "#app"))
(get-rating) 
