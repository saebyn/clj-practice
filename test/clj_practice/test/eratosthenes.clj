(ns clj-practice.test.eratosthenes
  (:use [clj-practice.eratosthenes])
  (:use [midje.sweet]))

(fact
 (sieve 30) => (just [2 3 5 7 11 13 17 19 23 29]))

(fact
 (sieve 10) => (just [2 3 5 7]))

(fact
 (sieve 20) => (just [2 3 5 7 11 13 17 19]))
