(ns home.utils
  (:require [cljs.nodejs :as nodejs]))

(def yaml (nodejs/require "yaml"))
(def fs (nodejs/require "fs"))

(defn get-slot-value-by-name [{:keys [slots]} name]
  (some-> (filter #(= (:slotName %1) name) slots)
          first :value :value))

(defn read-config []
      (some-> (.readFileSync fs "./Snipsfile" "utf-8")
              (yaml.eval)
              (js->clj :keywordize-keys true)))
