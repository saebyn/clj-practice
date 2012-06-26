
(ns clj-practice.rpn)


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
     (let [op (first tokens)
           tokens (rest tokens)]
       (if op
         (if (operator? op)
           (rpn-process tokens (apply-operator op stack))
           (rpn-process tokens (cons (Float/parseFloat  op) stack)))
         stack))))

(defn rpn-parse [line]
  (rpn-process (clojure.string/split line #"\s")))
