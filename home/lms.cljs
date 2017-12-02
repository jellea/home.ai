(ns home.lms
  (:require [cljs.nodejs :as nodejs]
            [home.intent :refer [intent]]
            [home.utils :as u]))

(def request (nodejs/require "request"))
(def config (u/read-config))

(defn lms-request [cmd]
  (let [{:keys [server_url player_id]} (some-> config :skills :lms)
        url (str server_url "jsonrpc.js")
        payload (clj->js {:json {:id 1 :method "slim.request" :params [player_id cmd]}})
        _ (prn url payload)]
    (.post request url payload
      (fn [err resp body]
        (prn err resp body)))))

(defmethod intent "resumeMusic" [_ _]
  (lms-request ["play" "0"]))

(defmethod intent "speakerInterrupt" [_ _]
  (lms-request ["pause" "1"]))

(defmethod intent "previousSong" [_ _]
  (lms-request ["playlist" "index" "-1"]))

(defmethod intent "nextSong" [_ _]
  (lms-request ["playlist" "index" "+1"]))

(defmethod intent "playAlbum" [_ message]
  (let [album-name (u/get-slot-value-by-name message "album_name")]
    (prn album-name)))

(defmethod intent "playPlaylist" [_ _]
  (prn "play!"))
