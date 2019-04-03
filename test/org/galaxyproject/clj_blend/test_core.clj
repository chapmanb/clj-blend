(ns org.galaxyproject.clj-blend.test-core
  "Basic usage tests for Blend Galaxy interoperability.
  Set GALAXY_APIKEY environmental variable, and optionally GALAXY_URL,
  before running. GALAXY_URL defaults to Galaxy main."
  (:use [clojure.test])
  (:require [org.galaxyproject.clj-blend.core :as core]))

(deftest galaxy-interoperability
  (let [client (core/get-client (or (System/getenv "GALAXY_URL") "https://main.g2.bx.psu.edu/")
                                (System/getenv "GALAXY_APIKEY"))]
    (println "User info" (core/get-user-info client))
    (println "List available histories" (core/list-histories client))
    (let [vcf-ds (core/get-datasets-by-type client :vcf)]
      (println "Retrieve VCF datasets" vcf-ds)
      (when-let [test-vcf (first vcf-ds)]
        (println "Download dataset" (core/download-dataset client test-vcf "/dev/null"))))))
