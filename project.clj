(defproject dashtrump "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.9.7"]
            [environ/environ.lein "0.2.1"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [org.clojure/core.cache "0.6.5"]
                 [enlive "1.1.6"]
                 [environ "0.5.0"]
                 [http-kit "2.2.0"]
                 ]
  :min-lein-version "2.0.0"
  :uberjar-name "dashtrump-standalone.jar"
  :ring {:handler dashtrump.core/handler}
  :main dashtrump.core
  :profiles {:uberjar {:aot :all}}
  )
