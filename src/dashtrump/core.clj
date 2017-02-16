(ns dashtrump.core
  (:require [org.httpkit.client :as http :as http]
            [net.cgrand.enlive-html :as html]
            [ring.adapter.jetty :as jetty]
            ))

(def appr-url "http://www.gallup.com/poll/201617/gallup-daily-trump-job-approval.aspx")

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

(base {:title "test" :main (yup 50)})

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (get-template
           (find-rating-in-dom
             (html/html-snippet (:body @(http/get appr-url)))))})

(defn -main [& [port]]
  (let [port 5000]
    (jetty/run-jetty handler {:port port :join? false})))


