(ns home.core
  (:require [cljs.nodejs :as nodejs]
            [home.intent :refer [intent]]
            [clojure.string :as string]
            home.lms))

(def mqtt (nodejs/require "mqtt"))

(defn connect [url]
  (let [client (mqtt.connect url)]
    (.on client "connect" 
       (fn []
         (let [intents (-> (methods intent) (dissoc :default) keys)]
           (doall (mapv 
                    #(.subscribe client (str "hermes/intent/" %1))
                    intents))
           (prn (str "subscribed: " (apply str intents))) 
           (.publish client "hermes/intent/playPlaylist" "Hello mqtt3"))))

    (.on client "message"
      (fn [topic message]
        (let [rm-prefix (string/replace topic "hermes/intent/" "")]
          (intent rm-prefix message))))
    client))

(connect "mqtt://test.mosquitto.org")
