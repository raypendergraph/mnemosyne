(ns mnemosyne.model.person
  (:require [clojure.spec.alpha :as spec]
            [mnemosyne.model.common :as common]))

(spec/def ::name string?)
(spec/def ::born ::common/datetime)
(spec/def ::died (spec/nilable ::common/datetime))
(spec/def ::person
  (spec/keys :req-un [::name ::born]
             :opt-un [::died]))

(spec/def ::person-entity (spec/merge ::person ::common/entity))