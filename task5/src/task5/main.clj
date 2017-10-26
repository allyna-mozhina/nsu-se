(ns task5.main)

(defn integrate
    [func]
    (let [trapezium-sum (fn [func step left right]
          (loop [i (+ left step)
               result 0]
            (if (< i right) 
                (recur (+ i step) (+ result (func i)))
                result)))
          trapezium (fn [func step left right] 
          (* step (+ (* 0.5 (+ (func left) (func right))) 
                     (trapezium-sum func step left right))))]
    (partial trapezium func 0.001 0)))

(defn -main
    [& raw-args]
    (let [antiderivative (integrate #(Math/sin %))]
    (println (antiderivative Math/PI))))
