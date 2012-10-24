(defproject clj-blend "0.1.1-SNAPSHOT"
  :description "Clojure library for interacting with Galaxy, CloudMan, and BioCloudCentral, built on blend4j"
  :url "http://github.com/chapmanb/clj-blend"
  :license {:name "MIT"
            :url "http://www.opensource.org/licenses/mit-license.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [fs "1.3.2"]
                 [com.github.jmchilton.blend4j/blend4j "0.1-SNAPSHOT"]]
  :repositories {"msi-artifactory" {:url "http://artifactory.msi.umn.edu/libs-snapshot-local"}})
