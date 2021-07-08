(ns mnemosyne.system
  (:require [clojure.spec.alpha :as spec]
            [com.stuartsierra.component :as component]
            [mnemosyne.adapters.ring :as ring]
            [mnemosyne.system
             [jetty-http :as jetty]
             [neo4j :as neo4j]]))


(spec/def ::system
  (spec/keys :req-un []
             :opt-un []))

(defn system [config]
  (component/system-map
    :neo4j (neo4j/create-neo4j (:neo4j config))
    :context (component/using {} {:datasource :neo4j})
    :http (component/using (jetty/create-jetty ring/handler (:jetty config))
                           {:context :context})))