(ns mnemosyne.common
  (:import (java.util UUID)))

(defn uuid-string? [s]
  (try
    (uuid? (UUID/fromString s))
    (catch IllegalArgumentException _ false)))