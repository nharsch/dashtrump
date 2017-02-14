(ns dashtrump.core
  (:require [org.httpkit.client :as http :as http]
            [net.cgrand.enlive-html :as html]))

(def appr-url "http://www.gallup.com/poll/201617/gallup-daily-trump-job-approval.aspx")

(defn find-rating-in-dom [dom]
  (let
    [trump-selector
     [:div#rbnSection323SECTION-526 :> :table :> :tbody :>
       [:tr (html/nth-of-type 6)] :> :td.rbnsctPoint]]
    (Integer. (re-find #"[0-9]+" (first
        (:content (first (html/select dom trump-selector))))))))

(def text (:body @(http/get appr-url)))
(println (clojure.string/includes? text "rbnSection323SECTION-526"))
(def rating (find-rating-in-dom (html/html-snippet (:body @(http/get appr-url)))))
(println rating)
