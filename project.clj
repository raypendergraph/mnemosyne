(defproject mnemosyne "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[compojure "1.6.1"]
                 [gorillalabs/neo4j-clj "4.1.2"]
                 [org.clojure/clojure "1.10.0"]
                 [ring/ring-jetty-adapter "1.7.1"] 
                 [metosin/compojure-api "2.0.0-alpha31"]]
  :ring {:handler mnemosyne.handler/start}
  :repl-options {:init-ns mnemosyne.handler}
  :profiles {:dev {:dependencies [[ring/ring-mock "0.3.2"]]}})