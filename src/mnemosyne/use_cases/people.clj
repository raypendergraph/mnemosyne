(ns mnemosyne.use-cases.people
  (:require [mnemosyne.entities.person :as person]
            [mnemosyne.common :as common]
            [clojure.spec.alpha :as spec]))

(defprotocol PersonReadWriter
             "Can create a new person entity from data."
             (create [this person-data] "Create a new person based on `person-data`")
             (read [this id] "Read a person based on the id"))

(defn read-person [context id]
      {:pre [(common/uuid-string? id)]}
      (read (:person-impl context) id))

(defn create-person [context person-data]
      {:pre [(spec/valid? ::person/person person-data)]}
      (create (:person-impl context) person-data))
