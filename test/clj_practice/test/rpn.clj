(ns clj-practice.test.rpn
  (:use [clj-practice.rpn])
  (:use [midje.sweet]))


(fact "+ is an operator"
      (operator? "+") => truthy)

(fact "a digit is not an operator"
      (operator? "4") => falsey)

(fact
 (apply-operator "+" [3 2 1]) => [5 1])

(fact
 (rpn-process ["19" "2.14" "+"]) => (just [(roughly 21.14)]))

(fact
 (rpn-parse "19 2.14 + 4.5 2 4.3 / - *") => (just [(roughly 85.2974)]))
