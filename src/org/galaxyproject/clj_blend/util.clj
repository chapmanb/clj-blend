(ns org.galaxyproject.clj-blend.util
  (:import java.util.Base64)
  (:require [clojure.string]))

(defn key-fn [key]
  (keyword (clojure.string/replace key "_" "-")))

(defn b64-encode-str [to-encode]
  (.encodeToString (Base64/getEncoder) (.getBytes to-encode)))
