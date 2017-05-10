(ns dashtrump.core
  (:require [org.httpkit.client :as http :as http]
            [net.cgrand.enlive-html :as html]
            [ring.adapter.jetty :as jetty]
            [clojure.core.cache :as cache]
            [clojure.data.json :as json]
            [environ.core :refer [env]]
            [ring.middleware.cors :refer [wrap-cors]]
            ))

(def appr-url "http://www.gallup.com/poll/201617/gallup-daily-trump-job-approval.aspx")
(def cache-timeout-ms (* 1000 60 60 4)); 4 hr

; TODO alternate location to check
; TODO backup value if nothing is found
(defn find-rating-in-dom [dom]
  (let
    [trump-selector
     [:div#rbnSection323SECTION-526 :> :table :> :tbody :>
       [:tr (html/nth-of-type 6)] :> :td.rbnsctPoint]]
    (Integer. (re-find #"[0-9]+" (first
        (:content (first (html/select dom trump-selector))))))))

(html/deftemplate base "dashtrump/templates/base.html"
  [{:keys [title main]}]
  [:title] (html/content title)
  [:body]  (html/substitute main))

(html/defsnippet yup "dashtrump/templates/yup.html" [:body]
  [rating]
  [:span#rating] (html/content (str rating)))

(html/defsnippet nope "dashtrump/templates/yup.html" [:body]
  [rating]
  [:span#rating] (html/content (str rating)))

(defn get-template [rating]
  (if (< rating 50)
    (base {:title "America Still Hates Trump" :main (yup rating)})
    (base {:title "Make America Hate Trump Again" :main (nope rating)})))

(def C (atom (cache/ttl-cache-factory {} :ttl cache-timeout-ms)))

(defn get-rating []
  ;; "grab rating from cache or from gallup"
  (if (cache/has? @C :rating)
    (:rating @C)
    ;update cache
    ;call get-rating
    (do
      (swap! C
        #(cache/miss % :rating
          (find-rating-in-dom
            (->
              (println "calling gallup")
              (html/html-snippet (:body @(http/get appr-url)))))))
      (:rating @C)
)))

(base {:title "test" :main (yup 50)})

; TODO: rating endpoint
(defn handler [request]
  (println (:uri request))
  (if (= "/rating" (:uri request))
    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/write-str {:rating (str (get-rating))})}

    {:status 200
     :headers {"Content-Type" "text/html"}
     :body (get-template
             (get-rating))}))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty
      (wrap-cors handler
        :access-control-allow-origin [#".*"]
        :access-control-allow-methods [:get])
      {:port port :join? false})))
