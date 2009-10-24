(ns congomongo-test
  (:use clojure.test
        somnium.congomongo
        somnium.congomongo.coerce
        somnium.congomongo.config)
  (:import com.mongodb.util.JSON))

(deftest config
  (mongo! :db "test")
  (is (= [:keywords :query-operators :object-ids]
         (@*mongo-config* :coerce-to)))
  (is (= [:keywords] (@*mongo-config* :coerce-from))))

(deftest coercion-without-save
  (let [test-map {:foo "a" :bar [{:baz "zonk"} :zam]}
        as-obj   (map-to-object test-map)
        and-back (object-to-map test-map)]
    (is (= com.mongodb.BasicDBObject (class as-obj)))
    (is (= and-back test-map))))

;; this is really way too convoluted
(deftest coerce-object-ids
  (let [a {:foo "foo"}]
    (insert! :things a)
    (let [a-frozen (first (fetch :things))
          a-frozen-id (:_id a-frozen)
          b {:foo "baz"
             :_a-frozen-id a-frozen-id}]
      (insert! :things b)
      (let [b-frozen (first (fetch :things
                                   :where {:foo "baz"}))]
        (is (= (:_a-frozen-id b-frozen) (:_id a-frozen))
            "ids match after pickling")
        (let [b-coerced (-> (first
                             (fetch :things
                                    :where  {:foo "baz"}
                                    :as     :json))
                            JSON/parse
                            object-to-map
                            map-to-object
                            object-to-map)]
          (is (= b-coerced b-frozen) "through the wash and back")))))
  (drop-coll! :things))