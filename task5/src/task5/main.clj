(ns task5.main)

(def trapezium-sum (memoize (fn [func step left right mem-func]
    (if (> right 0)
    (+ (mem-func func step left (- right step) mem-func) (func right))
    0))))

(defn integrate
    [func]
    (let [trapezium (fn [func step left right] 
          (* step (+ (* 0.5 (+ (func left) (func right))) 
                     (trapezium-sum func step left right trapezium-sum))))]
    (partial trapezium func 0.1 0)))

(defn -main
    [& raw-args]
    (let [antiderivative (integrate #(* 2 %))]
    (time (antiderivative 6))
    (time (antiderivative 5))
    (time (antiderivative 4))
    (time (antiderivative 3))
    (time (antiderivative 2))))
