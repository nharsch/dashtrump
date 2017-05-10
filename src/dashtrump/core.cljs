(ns dashtrump.core
  (:require 
    [dashtrump.scraper :as scraper]
    [reagent.core :as reagent]
    ))


;; (html/deftemplate base "dashtrump/templates/base.html"
;;   [{:keys [title main]}]
;;   [:title] (html/content title)
;;   [:body]  (html/substitute main))
;;
;; (html/defsnippet yup "dashtrump/templates/yup.html" [:body]
;;   [rating]
;;   [:span#rating] (html/content (str rating)))
;;
;; (html/defsnippet nope "dashtrump/templates/yup.html" [:body]
;;   [rating]
;;   [:span#rating] (html/content (str rating)))
;;
;; (defn get-template [rating]
;;   (if (< rating 50)
;;     (base {:title "America Still Hates Trump" :main (yup rating)})
;;     (base {:title "Make America Hate Trump Again" :main (nope rating)})))
;;
;; (base {:title "test" :main (yup 50)})
;;
;; (defn handler [request]
;;   {:status 200
;;    :headers {"Content-Type" "text/html"}
;;    :body (get-template
;;            (get-rating))})
;;
;; (defn -main [& [port]]
;;   (let [port (Integer. (or port (env :port) 5000))]
;;     (jetty/run-jetty handler {:port port :join? false})))
;;


;; Reagent time
;; TODO state constants
(defonce app-state
  (reagent/atom {:rating "not set"
                 :fetched-state "NOT FETCHED"
                 }))

;; state changers
(defn set-getting-rating []
  "set state to fetching"
  (swap! app-state assoc :fetched-state "FETCHING"))

(defn set-got-rating [rating]
  "update rating"
  (reset! app-state {:rating rating :fetched-state "FETCHED"}))


;; actions
(defn fetch-rating []
  (do
     ;; (set-getting-rating)
     ; callback
     (set-got-rating scraper/get-rating)
   ))
;;

(defn message []
  [:div
   [:h1 "rating is " (:rating @app-state)]
   [:p "state is " (:fetched-state @app-state)]
   ])

(reagent/render [message] (js/document.querySelector "#cljs-target"))

