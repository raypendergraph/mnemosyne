(ns mnemosyne.system
  (:require
   [mnemosyne.system
    [http-server :as http]
    [neo4j :as neo4j]]
   [mnemosyne.adapters.ring :as ring]))

(defn system [config]
    (system/map 
        :neo4j (neo4j/neo4j config)
        :context (component/using {} {:fetch-person-with-id}
        :http (component/using {} (http/http/server ring/ring-handler)))
    )