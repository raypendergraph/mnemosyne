(ns mnemosyne.use-cases.fetch-person
    (:require [mnemosyne.entities.person :as person]
              [clojure.spec.alpha :as spec]))

(defn fetch-person [context person-id]
    (fetch-person (get context :fetch-person) id))

(defprotocol FetchPerson
    (fetch-person [this id]))