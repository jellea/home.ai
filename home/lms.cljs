(ns home.lms
  (:require [cljs.nodejs :as nodejs]
            [home.intent :refer [intent]]))

(defmethod intent "playPlaylist" [_ _]
  (prn "play!"))
