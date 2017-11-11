(ns task6.main)

(defn partial-sums [seq]
    ;; replace global var with some req-seq macros
    (def sums (lazy-seq
      (cons (first seq) (map + (next seq) sums))))
      sums)

(defn integrate
    [func]
    (let [step 0.01
          left 0
          fvals (map func (iterate #(+ % step) (- left step)))
          trap-sq (fn [h a b] (* h 0.5 (+ a b)))
          traps (map (partial trap-sq step) fvals (rest fvals))
          integrals (partial-sums traps)]
        (fn [x]
            (let [remd (mod x step)
                  n (quot x step)
                  result (nth integrals n)] 
                (if (= 0 remd)
                result
                (+ result (trap-sq remd (func n) (func x))))))))

(defn -main
    [& raw-args]
    (let [antiderivative (integrate #(* 2 %))]
    (time (println (antiderivative 6)))
    (time (println (antiderivative 5)))
    (time (println (antiderivative 4)))
    (time (println (antiderivative 3)))))
