(ns clj-practice.fibonacci)


(defn fib
  "Return a lazy sequence of fibonacci numbers."
  []
  ((fn rfib [a b]
     (cons a (lazy-seq (rfib b (+ a b)))))
   0 1))
