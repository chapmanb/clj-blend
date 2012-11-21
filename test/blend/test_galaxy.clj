(ns blend.test-galaxy
  "Basic usage tests for Blend Galaxy interoperability.
  Set GALAXY_APIKEY environmental variable, and optionally GALAXY_URL,
  before running. GALAXY_URL defaults to Galaxy main."
  (:use [clojure.test])
  (:require [blend.galaxy.core :as galaxy]))

(deftest galaxy-interoperability
  (let [client (galaxy/get-client (or (System/getenv "GALAXY_URL") "https://main.g2.bx.psu.edu/")
                                  (System/getenv "GALAXY_APIKEY"))]
    (println "User info" (galaxy/get-user-info client))
    (println "List available histories" (galaxy/list-histories client))
    (let [vcf-ds (galaxy/get-datasets-by-type client :vcf)]
      (println "Retrieve VCF datasets" vcf-ds)
      (when-let [test-vcf (first vcf-ds)]
        (println "Download dataset" (galaxy/download-dataset client test-vcf "/dev/null"))))))