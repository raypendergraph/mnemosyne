(ns mnemosyne.system.neo4j
  (:require [clojure.spec.alpha :as spec]
            [com.stuartsierra.component :as component]
            [mnemosyne.use-cases.person :as person]
            [neo4j-clj.core :as db])
  (:import (java.net URI)))

;; Config for Neo4j
(spec/def ::string uri?)
(spec/def ::user string?)
(spec/def ::secret string?)
(spec/def ::neo4j-config
  (spec/keys :req-un [::uri ::user ::secret]))

;; Query functions
(db/defquery ^:private create-person-query "CREATE (p:Person $person) SET p.uuid=randomUUID()")
(db/defquery update-person-query "MATCH (p:Person {uuid: $uuid}) SET p=$person")
(db/defquery read-person-query "MATCH (p:Person {uuid: $uuid}) RETURN p")
(db/defquery delete-person-query "MATCH (p:Person) DELETE p")
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
    (assoc this :conn nil))

  person/PersonDatasource
  (create-person [this person]
    (db/with-transaction (:conn this)
                         tx
                         (create-person-query tx {:person person})))
  (read-person [this uuid]
    (db/with-transaction (:conn this)
                         tx
                         (read-person-query tx {:uuid uuid})))
  (update-person [this person]
    (db/with-transaction (:conn this)
                         tx
                         (update-person-query tx {:person person})))
  (delete-person [this uuid]
    (db/with-transaction (:conn this)
                         tx
                         (delete-person-query tx {:uuid uuid}))))


(defn create-neo4j [config]
  {:pre [(or (spec/valid? ::neo4j-config config)
             (spec/explain ::neo4j-config config))]}
  (map->Neo4j {:config config}))