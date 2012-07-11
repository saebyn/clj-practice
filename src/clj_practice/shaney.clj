(ns clj-practice.shaney
  (:require [clojure.string :as string]))

(def sample-words
  "I seem to be important. For me, it would have agreed with the technical insight that is dear to me. Because of this, I have no advice for someone in that situation! Joining Mensa was something I did him one better. I wore a dress skirt a day for one week. I did him one better. I wore a dress skirt a day for a 2 year relationship. I'm wondering if anyone else out there has ever experienced this phenomena, whether it was actually your contention that this is true for me.  I suppose it depends how you felt about someone before you became emotionally attached and therefore \"safer\" - not to sporting events, but to opera.  I lost 90 lbs a few months during my \"flower child\" days in high school where, due to her high academic standings, was shunned by many of the tube. The experience really screwed them up - if not their heads, their knees. Why does one have to be the prime measurement of manhood. No?  He was a scrawny, spastic nerd in high school, and I fantasized about such a thing. It all depends on the sidelines, listening to what makes the rest of the guys around her - suddenly finds herself in a situation where guys are asking them out!? But this can result in members of either the person of your dreams (in a larger number of males to females studying the field of engineering), the ratio of males to females is somewhere in the past. And, per the other person.  I find it hard to reconcile the notion that something or someone isn't theirs anymore. I have a date with the woman. Subjectively, I have also acted in this weekend.")


(defn incr-sample [chain leading-words word]
  (let [word-path (reverse (cons word (reverse leading-words)))]
    (update-in chain word-path (fnil inc 0))))


(defn add-initial-words-to-chain [chain first-two-words]
  (incr-sample chain ["" ""] first-two-words))


(defn prepare-sample-staging [chain first-two-words all-words-except-first]
  [(add-initial-words-to-chain chain first-two-words) first-two-words all-words-except-first])


(defn progress-sample-staging [[chain leading-two-words remaining-words]]
  [(incr-sample chain leading-two-words (first remaining-words)) [(second leading-two-words) (first remaining-words)] (rest remaining-words)])


(defn process-sample [sample-staging]
  (let [remaining-words (last sample-staging)
        chain (first sample-staging)]
    (if (empty? remaining-words)
      chain
      (process-sample (progress-sample-staging sample-staging)))))


; Input example: {} "This is a test"
; Output Example: {"" {"" {["test" "the"] 2 ["test" "me"] 8}} "test" {"the" {"world" 5 "other" 5} "me" {"now" 5 "other" 5}}}
(defn add-sample [chain sample]
  (let [raw-words (clojure.string/split sample #" ")
        initial-words (take 2 raw-words)
        words (drop 2 raw-words)]
    (process-sample (prepare-sample-staging chain initial-words words))))


(defn markov-chain
  ([samples]
     (markov-chain samples {}))
  ([samples chain]
     (if (empty? samples)
       chain
       (markov-chain (rest samples) (add-sample chain (first samples))))))


(defn spread-probability-map
  ([mapping]
     (let [ks (keys mapping)]
       (spread-probability-map mapping (first ks) (rest ks) 0.0)))
  ([mapping k ks v]
     (if k
       (let [n-v (+ v (get mapping k))]
         (assoc (spread-probability-map mapping (first ks) (rest ks) n-v) k n-v))
       mapping)))


; Search items, passing each item to predicate until it returns true or return
; the last item.
(defn skip-until [items predicate]
  (let [item (first items)
        items (rest items)]
    (if (predicate item)
      item
      (if (empty? items)
        item
        (skip-until items predicate)))))


; Choose a key from a mapping based on proportion of value associated with key
; out of the sum of all values in the mapping.
(defn choose-word [nodes]
  (let [nodes (spread-probability-map nodes)
        sum (apply #'+ (vals nodes))
        choice (rand-int sum)]
    (skip-until (keys nodes) (fn [key] (< choice (get nodes key))))))


(defn generate-word [chain words]
  (let [nodes (get-in chain (take-last 2 words))]
    (if nodes
      (choose-word nodes)
      nil)))


(defn generate-first-words [chain]
  (generate-word chain ["" ""]))


(defn generate-text [chain]
  (let [first-words (generate-first-words chain)]
    (map #'first
         (iterate
          (fn [[next-word chain old-words]]
            (let [next-word (generate-word chain (reverse old-words))
                  words (cons next-word old-words)]
              (if next-word
                [next-word chain words]
                ["" chain old-words])))
          [first-words chain (reverse first-words)]))))

(defn demo [length]
  (println (string/join " " (flatten (take length (generate-text (markov-chain [sample-words])))))))
