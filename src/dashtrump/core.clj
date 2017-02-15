(ns dashtrump.core
  (:require [org.httpkit.client :as http :as http]
            [net.cgrand.enlive-html :as html]))

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


(defn get-template [rating]
  (if (< rating 50)
    (str "Yup. Trump's approval rating is down to " rating "%")
    (str "Dear God, something is wrong. "
         rating "% of Americans approve of DT")))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (get-template
           (find-rating-in-dom
             (html/html-snippet (:body @(http/get appr-url))))
           )
  }
)
