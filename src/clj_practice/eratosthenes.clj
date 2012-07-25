(ns clj-practice.eratosthenes)


(defn clear-multiples [^booleans number-array current-number]
  (let [ret (aclone number-array)]
    (doseq [idx (range (- (* 2 current-number) 3) (alength number-array) current-number)]
      (aset ret idx false))
    ret))


(defn sieve-filter [^booleans number-array current-number limit]
  (if (< current-number limit)
    (sieve-filter (if (aget number-array (- current-number 3))
                    (clear-multiples number-array current-number)
                    number-array) (inc current-number) limit)
    number-array))

(defn map-back-to-numbers [^booleans number-array maximum]
  (keep-indexed (fn [idx itm] (when (aget number-array idx) itm)) (range 3 (inc maximum))))

(defn sieve [maximum]
  (cons 2 (map-back-to-numbers
           (sieve-filter
            (boolean-array (map odd? (range 3 (inc maximum))))
            3
            (inc (Math/sqrt maximum))) maximum)))
