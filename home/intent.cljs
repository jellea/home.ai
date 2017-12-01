(ns home.intent)

(defmulti intent (fn [topic message] topic))

(defmethod intent :default [topic message]
  (prn (str "intent for topic " topic " not found, message: " message)))
