(ns mnemosyne.entities.person
  (:require [clojure.spec.alpha :as spec]))

(spec/def ::person
  (spec/keys :req-un [::name ::born ::died]
             :opt-un [::died ::links]))

(spec/def ::name string?)
(spec/def ::born (fn [v]
                   (instance? java.util.Date)))
(spec/def ::died (fn [v]
                   (instance? java.util.Date)))
(spec/def ::links (spec/* string?))