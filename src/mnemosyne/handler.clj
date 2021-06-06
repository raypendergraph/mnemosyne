(ns mnemosyne.handler
  (:require [[compojure.api.sweet :refer :all]
             [compojure.core :refer :all]]))

(s/defschema Pizza
  {:name s/Str
   (s/optional-key :description) s/Str
   :size (s/enum :L :M :S)
   :origin {:country (s/enum :FI :PO)
            :city s/Str}})

(def app
  (api
   {:swagger
    {:ui "/api-docs"
     :spec "/swagger.json"
     :data {:info {:title "Sample API"
                   :description "Compojure Api example"}
            :tags [{:name "api", :description "some apis"}]
            :consumes ["application/json"]
            :produces ["application/json"]}}}

   (context "/api" []
     :tags ["api"]

     (GET "/plus" []
       :return {:result Long}
       :query-params [x :- Long, y :- Long]
       :summary "adds two numbers together"
       (ok {:result (+ x y)}))

     (POST "/echo" []
       :return Pizza
       :body [pizza Pizza]
       :summary "echoes a Pizza"
       (ok pizza)))))