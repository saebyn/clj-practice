(ns clj-practice.zeckendorf
  (:use [clj-practice.fibonacci]))


(defn build-set [n]
  (reverse (take-while (partial > n) (fib))))


(defn add-set [fun largest remainder set]
  (cons largest (fun (- remainder largest) set)))


(defn zeckendorf
  "Find the set of non-consecutive fibonacci numbers that sums to n."
  ([n]
                                        ; Construct a list of fibonacci numbers whose values are less than n
     (if (<= n 1)
       [1]
       (let [fibs (build-set n)
             largest (first fibs)]
         (add-set #'zeckendorf largest n (rest fibs)))))
  ([remainder fibs]
                                        ; Keep the subset of fibonacci numbers for reduction so we don't have to recalculate them
     (if (empty? fibs)
       []
       (if (some #(= remainder %) fibs)
         [remainder] ; Stopping when the remainder is a fibonacci number itself, the last value in the sequence of zeckendorf numbers
         (let [fibs (drop-while (partial <= remainder) fibs)]
           (add-set #'zeckendorf (first fibs) remainder (rest fibs))))))
)
