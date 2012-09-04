(ns clj-practice.test.zeckendorf
  (:use [clj-practice.zeckendorf])
  (:use [midje.sweet]))

(fact "zeckendorf 4 is 3, 1"
      (zeckendorf 4) => (just [3 1]))

(fact "zeckendorf 20 is 13, 5, 2"
      (zeckendorf 20) => (just [13 5 2]))

(fact "zeckendorf 100 is 89, 8, 3"
      (zeckendorf 100) => (just [89 8 3]))
