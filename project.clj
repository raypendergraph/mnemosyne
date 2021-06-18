(defproject mnemosyne "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[clj-commons/clj-yaml "0.7.106"]
                 [com.stuartsierra/component "1.0.0"] 
                 [compojure "1.6.1"]
                 [expound "0.8.9"]
                 [gorillalabs/neo4j-clj "4.1.2"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.3.618"]
                 [ring/ring-jetty-adapter "1.7.1"] 
                 [ring/ring-json "0.5.1"]
                 [metosin/reitit "0.5.13"]]
  :main ^:skip-aot mnemosyne.main
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
