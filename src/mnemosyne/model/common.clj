(ns mnemosyne.model.common
  (:import (java.util UUID))
  (:import (java.time ZonedDateTime))
  (:require [clojure.spec.alpha :as spec]))

(defn- uuid-string? [s]
  (try
    (uuid? (UUID/fromString s))
    (catch IllegalArgumentException _ false)))
(spec/def ::uuid-string uuid-string?)

(defn- datetime? [v]
     (instance? ZonedDateTime v))
(spec/def ::datetime datetime?)

;; Entity
(spec/def ::id ::uuid-string)
(spec/def ::created ::datetime)
(spec/def ::deleted (spec/nilable ::datetime))
(spec/def ::creator string?)
(spec/def ::deleter string?)
(spec/def ::entity (spec/keys :req-un [::id ::created ::creator]
                              :opt-un [::deleted ::deleter]))