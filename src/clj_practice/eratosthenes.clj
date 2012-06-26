(ns clj-practice.eratosthenes)


(defn clear-multiples [number-array current-number]
  (amap ^booleans number-array idx ret
        (and (aget number-array idx)
             (not (and (aget number-array idx) (> (+ 3 idx) current-number)  (= (mod (+ idx 3) current-number) 0))))))


(defn sieve-filter [number-array current-number limit]
  (if (< current-number limit)
    (sieve-filter (if (aget number-array (- current-number 3))
                    (clear-multiples number-array current-number)
                    number-array) (+ 1 current-number) limit)
    number-array))

(defn map-back-to-numbers [number-array maximum]
  (keep-indexed (fn [idx itm] (if (aget number-array idx) itm nil)) (range 3 (+ 1 maximum))))

(defn sieve [maximum]
  (cons 2 (map-back-to-numbers
           (sieve-filter
            (boolean-array (map odd? (range 3 (+ maximum 1))))
            3
            (+ (Math/sqrt maximum) 1)) maximum)))
