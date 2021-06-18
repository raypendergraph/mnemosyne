(ns mnemosyne.main
  (:require [clj-yaml.core :as yaml]
            [com.stuartsierra.component :as component]
            [clojure.core.async :as async]
            [mnemosyne.system :as system]))

(defn read-config-yaml [filename]
  (yaml/parse-string (slurp filename)))

(defn -main [& args]
  (let [[config-file] ["src/mnemosyne/dev.config.yml"]
        config (read-config-yaml config-file)
        wait (async/chan)
        _ (println config)]
    (component/start (system/system config))))
