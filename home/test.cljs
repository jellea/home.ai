(ns home.test
  (:require [cljs.nodejs :as nodejs]
            [home.core :as core]
            [home.intent :refer [intent]]))

(prn (methods intent))
