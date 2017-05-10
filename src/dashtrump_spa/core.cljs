(ns dashtrump_spa.core
  (:require 
    [ajax.core :refer [GET]]
    ))

(enable-console-print!)

(println "This text is printed from src/dashtrump_spa/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(def api-url "http://doesamericastillhatetrump.com/rating")

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

;; scraper

;; (defn find-rating-in-dom [dom]
;;   "fetch rating number from full dom"
;;   ;; (hick/parse dom)
;; )

;; ( (
;;        -    [trump-selector
;;              -     [:div#rbnSection323SECTION-526 :> :table :> :tbody :>
;;                     -       [:tr  (html/nth-of-type 6)] :> :td.rbnsctPoint]]
;;        -    (Integer.  (re-find #"[0-9]+"  (first
;;                                              -        (:content  (first  (html/select dom trump-selector))))))))


(defn get-rating []
  "get approval page, return HTML sting"
  (println "calling")
  (GET api-url {:handler #(println (str %))})
  )

(get-rating)
