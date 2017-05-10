(ns dashtrump.scraper
  (:require
    ;; [org.httpkit.client :as http]
    [ajax.core :refer [GET]]
    ;; [hickory.core :as hick]
    ))

; TODO stash this in config?
(def appr-url "http://www.gallup.com/poll/201617/gallup-daily-trump-job-approval.aspx")

; TODO alternate location to check
; TODO backup value if nothing is found
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


; TODO: async?
; TODO: error?
;; (defn get-rating []
;;   "fetch gallup site and grab rating from cache or from gallup"
;;   ;call get-rating
;;   ;; (js/console.log "fetching")
;;   (find-rating-in-dom
;;     ;; (html/html-snippet (:body @(http/get appr-url))))))
;;        (html/html-snippet (:body (GET appr-url)))
