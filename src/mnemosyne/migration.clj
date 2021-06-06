(ns mnemosyne.migration
    (:require [clj-arangodb.arangodb.core :as a]))

(defn initialize[conn tenant-config]
   (let [db (a/create-and-get-database conn (:dbName tenant-config))]))

(-main []
    (let [[file-name] *command-line-args*]
        (println file-name)))