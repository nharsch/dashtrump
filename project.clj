(defproject dashtrump "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.521"]
                 [reagent "0.5.0"]
                 [cljs-ajax "0.5.9"]
                 ;; [com.cemerick/piggieback "0.2.1"]
                 ;; [org.clojure/tools.nrepl "0.2.10"]
                 ;; [davidsantiago/hickory "0.7.1"]
                 ;; [http-kit "2.2.0"]
                 ]

  :source-paths  ["src"]

  :plugins [[lein-cljsbuild "1.1.6"]
            [lein-figwheel "0.3.7"]
            ]

  ;; :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :figwheel true
                        :compiler {
                           :optimizations :none
                           :output-to "resources/public/javascripts/dev.js"
                           :output-dir "resources/public/javascripts/cljs-dev/"
                           :pretty-print true
                           :source-map true }}]}

  :min-lein-version "2.0.0"
  :uberjar-name "dashtrump-standalone.jar"
  :ring {:handler dashtrump.core/handler}
  :main dashtrump.core
  :profiles {
             :uberjar {:aot :all}
             }
  )
