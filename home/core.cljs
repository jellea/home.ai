(ns home.core
  (:require [cljs.nodejs :as nodejs]
            [home.intent :refer [intent]]
            [clojure.string :as string]
            [home.utils :as u]
            home.lms))

(def mqtt (nodejs/require "mqtt"))

(nodejs/enable-util-print!)

(defn connect [url]
  (let [client (mqtt.connect url)]
    (.on client "connect" 
       (fn []
         (let [intents (-> (methods intent) (dissoc :default) keys)]
           (doall (mapv 
                    #(.subscribe client (str "hermes/intent/" %1))
                    intents))
           (prn (str "subscribed: " (apply str (interpose ", " intents)))))))

    (.on client "message"
      (fn [topic message]
        (let [rm-prefix (string/replace topic "hermes/intent/" "")
              message (some-> (js/JSON.parse message) (js->clj :keywordize-keys true))]
          (intent rm-prefix message))))
    client))

(if-let [config (u/read-config)]
  (connect (str "mqtt://" (-> config :mqtt_broker :hostname) ":" (-> config :mqtt_broker :port)))
  (prn "no Snipsfile..."))
