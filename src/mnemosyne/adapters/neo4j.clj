(ns mnemosyne.adapters.neo4j
  :require
  [mnemosyne.system.neo4j :as db]
  [mnemosyne.use-cases.person :as person]
  (:require [mnemosyne.use-cases.person :as person]))


(extend-type db/Neo4j
  person/PersonDatasource
  (create-person [this person]
    (println "Saved Person!!!" person))
  (read-person [this person]
    (println "Read Person!!!" person))
  (update-person [this person]
    (println "update person"))
  (delete-person [this uuid]
    (println "update person")))


