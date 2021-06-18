(ns mnemosyne.adapters.ring
  (:require
    [mnemosyne.common :as common]
    [mnemosyne.use-cases.people :as people]
    [muuntaja.core :as muuntaja]
    [reitit.ring :as ring]
    [reitit.coercion.spec :as rspec]
    [reitit.ring.coercion :as rrc]
    [reitit.ring.middleware.exception :as exception]
    [reitit.ring.middleware.muuntaja :as muuntaja-middleware]
    [reitit.ring.middleware.parameters :as parameters]
    [clojure.spec.alpha :as spec]
    [expound.alpha :as expound]))

(defn coercion-error-handler [status]
  (let [printer (expound/custom-printer {:theme :figwheel-theme, :print-specs? false})
        handler (exception/create-coercion-handler status)]
    (fn [exception request]
      (printer (-> exception ex-data :problems))
      (handler exception request))))

(defn handler [system-context]
  (ring/ring-handler
    (ring/router
      ["/api"
       ["/people/:id" {:get {:parameters {:path-params {:id common/uuid-string?}}
                             :responses  {201 {:body {:id common/uuid-string?}}}
                             :handler    (fn [{:keys [path-params]}]
                                           (let [id (:id path-params)
                                                 person (people/read-person system-context id)]
                                             {:status 201
                                              :body   {:id "87DCF4F9-9C2C-4BFF-8097-616386812548"}}))}}]]
      ;; router data affecting all routes
      {:data {:coercion   rspec/coercion
              :muuntaja   muuntaja/instance
              :middleware [muuntaja-middleware/format-middleware
                           (exception/create-exception-middleware
                             (merge
                               exception/default-handlers
                               {:reitit.coercion/request-coercion  (coercion-error-handler 400)
                                :reitit.coercion/response-coercion (coercion-error-handler 500)}))
                           parameters/parameters-middleware
                           rrc/coerce-request-middleware
                           rrc/coerce-response-middleware]}})))