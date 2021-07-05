(ns mnemosyne.system.neo4j
  (:require [clojure.spec.alpha :as spec]
            [com.stuartsierra.component :as component]
            [neo4j-clj.core :as db])

  (:import (java.net URI)))

(spec/def ::string uri?)
(spec/def ::user string?)
(spec/def ::secret string?)
(spec/def ::neo4j-config
  (spec/keys :req-un [::uri ::user ::secret]))

(defrecord Neo4j [conn config]
  component/Lifecycle
  (start [this]
    (let [{:keys [uri secret user]} (:config this)
          conn (db/connect (URI. uri)
                           user
                           secret)]
      (assoc this :conn conn)))

  (stop [this]
    (.stop conn)
    (assoc this :conn nil)))


(defn create-neo4j [config]
  {:pre [(or (spec/valid? ::neo4j-config config)
             (spec/explain ::neo4j-config config))]}
  (map->Neo4j {:config config}))
