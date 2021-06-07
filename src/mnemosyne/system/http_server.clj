(ns mnemosyne.system.http-server
  (:require []))

(defrecord HttpServer [handler context server]
  (start [this])
  (stop [this]))

(defn http-server [handler]
  map->HttpServer {:handler handler})