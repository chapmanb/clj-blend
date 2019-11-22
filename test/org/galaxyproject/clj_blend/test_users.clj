(ns org.galaxyproject.clj-blend.test-users
  "Basic usage tests for clj-blend.users namespace.
  Set GALAXY_URL and GALAXY_APIKEY environmental variable
  before running."
  (:use [clojure.test])
  (:require [org.galaxyproject.clj-blend.core :as core]
            [org.galaxyproject.clj-blend.users :as users]))

(deftest test-get-users
  (let [client (core/get-client (System/getenv "GALAXY_URL")
                                (System/getenv "GALAXY_APIKEY"))]
    (is (every? true? (map #(contains? % :username) (users/get-users client))) "All users should have :username key")))

