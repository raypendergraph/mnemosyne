(ns mnemosyne.system.neo4j
  (:require [clojure.spec.alpha :as spec]
            [com.stuartsierra.component :as component]
            [mnemosyne.adapters.neo4j :as neo4j]
            [neo4j-clj.core :as db])
  (:import (java.net URI)
           (mnemosyne.adapters.neo4j Neo4j)))

;; Config for Neo4j
(spec/def ::string uri?)
(spec/def ::user string?)
(spec/def ::secret string?)
(spec/def ::neo4j-config
  (spec/keys :req-un [::uri ::user ::secret]))

(extend-protocol component/Lifecycle
  Neo4j
  (start [this]
    (let [{:keys [uri secret user]} (:config this)
          conn (db/connect (URI. uri)
                           user
                           secret)]
      (assoc this :conn conn)))

  (stop [this]
    (.stop (:conn this))
    (assoc this :conn nil)))

(defn create-neo4j [config]
  {:pre [(or (spec/valid? ::neo4j-config config)
             (spec/explain ::neo4j-config config))]}
  (neo4j/map->Neo4j {:config config}))