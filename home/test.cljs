(ns home.test
  (:require [cljs.nodejs :as nodejs]
            [home.core :as core]
            [home.intent :refer [intent]]))

(def server (.Server. (nodejs/require "mosca") (js-obj) (fn [e])))

(prn (methods intent))
