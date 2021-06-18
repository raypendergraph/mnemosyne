(ns mnemosyne.system
  (:require [com.stuartsierra.component :as component]
            [mnemosyne.adapters.ring :as ring]
            [mnemosyne.system
             [jetty-http :as jetty]
             [neo4j :as neo4j]]))

(defn system [config]
  (component/system-map
    :database (neo4j/neo4j (:neo4j config))
    :context (component/using {} {:person-impl :database})
    :http (component/using (jetty/jetty-http ring/handler (:jetty config))
                           {:context :context})))