(ns mnemosyne.adapters.neo4j
  (:require [neo4j-clj.core :as db]
            [mnemosyne.use-cases.person :as person]))

;; Person queries
(db/defquery create-person-query "CREATE (p:Person $person) SET p.uuid=randomUUID()")
(db/defquery update-person-query "MATCH (p:Person {uuid: $uuid}) SET p=$person")
(db/defquery read-person-query "MATCH (p:Person {uuid: $uuid}) RETURN p")
(db/defquery delete-person-query "MATCH (p:Person) DELETE p")

(defrecord Neo4j [conn config]
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


