(ns mnemosyne.entities.person
  (:require [clojure.spec.alpha :as spec])
  (:import (java.util Date)))

(spec/def ::person
  (spec/keys :req-un [::name ::born ::died]
             :opt-un [::died ::links]))

(spec/def ::name string?)
(spec/def ::born (fn [v]
                   (instance? Date v)))
(spec/def ::died (fn [v]
                   (instance? Date v)))
(spec/def ::links (spec/* string?))