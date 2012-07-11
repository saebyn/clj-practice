(ns clj-practice.test.shaney
  (:use [clj-practice.shaney])
  (:use [midje.sweet]))

(fact
 (choose-word {["test" "me"] 1.0}) => ["test" "me"])

(fact
 (choose-word {["test" "this"] 0.0 "at" 1.0}) => "at")

(fact
 (spread-probability-map (sorted-map 'a 0.0, 'b 1.0)) => {'a 0.0 'b 1.0})

(fact
 (spread-probability-map (sorted-map 'a 4, 'b 6)) => {'a 4. 'b 10.0})
