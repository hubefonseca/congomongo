(defproject congomongo
  "0.1.2-SNAPSHOT"
  :description "clojure-friendly api for MongoDB"
  :dependencies [[org.clojure/clojure "1.2.0-master-SNAPSHOT"]
                 [org.clojure/clojure-contrib "1.2.0-SNAPSHOT"]
                 [org.clojars.somnium/mongo-java-driver "1.1.0-SNAPSHOT"]
                 [org.clojars.somnium/clojure-db-object "0.1.1-SNAPSHOT"]]
  :dev-dependencies [[swank-clojure "1.1.0-SNAPSHOT"]])
                     ;[org.clojars.somnium/user "0.1.0-SNAPSHOT"]]) <- Doesn't appear to be 1.2 compat- blows up when loading.
