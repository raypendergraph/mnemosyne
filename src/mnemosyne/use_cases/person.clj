(ns mnemosyne.use-cases.person
  (:require [clojure.spec.alpha :as spec]
            [mnemosyne.model.person :as person]
            [mnemosyne.model.common :as common]))

;; PersonDatasource protocol and specs -
(defprotocol PersonDatasource
  "Can create a new person entity from data."
  (create-person [ds person-data] "Create a new person based on `person-data`")
  (read-person [ds uuid] "Read a entity based on the id")
  (update-person [ds person-data] "Mutates an existing entity.")
  (delete-person [ds uuid] "Deletes an entity by its UUID"))

(spec/def ::person-datasource (fn [x] (satisfies? PersonDatasource x)))

(spec/fdef read-person :args (spec/cat :ds ::person-datasource
                                       :uuid ::common/uuid-string))
;; end PersonDatasource

;; Person use case methods
(defn fetch [ds uuid] (read-person ds uuid))
(spec/fdef fetch :args (spec/cat :ds ::person-datasource
                                 :uuid ::common/uuid-string?)
                 :ret ::person/person-entity)


(defn create [ds person-data] (create-person ds person-data))
(spec/fdef create :args (spec/cat :ds ::person-datasource
                                  :person ::person)
                  :ret ::common/entity)

(defn write [ds person] (update-person ds person))
(spec/fdef write :args (spec/cat :ds ::person-datasource
                                 :person ::person/person))

(defn delete [ds uuid] (delete-person ds uuid))
(spec/fdef delete :args (spec/cat :ds ::person-datasource
                                  :uuid ::common/uuid-string?))