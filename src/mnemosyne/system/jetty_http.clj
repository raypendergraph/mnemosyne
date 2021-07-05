(ns mnemosyne.system.jetty-http
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [clojure.spec.alpha :as spec]))
(spec/def ::jetty-config
  (spec/keys :opt-un [::port]))
(spec/def ::port number?)

(defrecord Jetty [handler context server config]
  component/Lifecycle
  (start [{:keys [context handler] :as this}]
    (assoc this :server (jetty/run-jetty (handler context) {})))
  (stop [this]
    (.stop server)
    (assoc this :server nil)))

(defn create-jetty [handler config]
  {:pre [(spec/valid? ::jetty-config config)]}
  (map->Jetty {:handler handler :config config}))