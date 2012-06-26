
(ns clj-practice.rpn
  (:require [clojure.string :as string]))


(def operators (hash-map "+" + "-" - "/" / "*" *))


(defn operator? [op]
  (some #{op} '("+" "-" "*" "/")))


(defn apply-operator [op stack]
  (let [v1 (second stack)
        v2 (first stack)
        stack (rest (rest stack))]
    (cons (apply (get operators op) [v1 v2]) stack)))


(defn rpn-process
  ([tokens]
     (rpn-process tokens []))
  ([tokens stack]
     (let [token (first tokens)
           tokens (rest tokens)]
       (if token
         (if (operator? token)
           (rpn-process tokens (apply-operator token stack))
           (rpn-process tokens (cons (Float/parseFloat  token) stack)))
         stack))))

(defn rpn-parse [line stack]
  (rpn-process (string/split line #"\s") stack))

(defn rpn
  ([stack]
     (println stack)
     (rpn (rpn-parse (read-line) stack)))
  ([]
     (rpn (rpn-parse (read-line) []))))
